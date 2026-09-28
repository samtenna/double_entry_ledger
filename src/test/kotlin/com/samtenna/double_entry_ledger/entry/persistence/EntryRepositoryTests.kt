package com.samtenna.double_entry_ledger.entry.persistence

import com.samtenna.double_entry_ledger.account.persistence.AccountEntity
import com.samtenna.double_entry_ledger.account.persistence.AccountRepository
import com.samtenna.double_entry_ledger.transaction.persistence.TransactionRepository
import com.samtenna.double_entry_ledger.transaction.persistence.TransactionEntity
import com.samtenna.double_entry_ledger.account.AccountType
import com.samtenna.double_entry_ledger.account.AccountStatus
import com.samtenna.double_entry_ledger.transaction.TransactionStatus
import com.samtenna.double_entry_ledger.entry.EntryDirection
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.containers.PostgreSQLContainer
import java.util.UUID
import java.time.Instant
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertFailsWith

@SpringBootTest
@Testcontainers
class EntryRepositoryTests @Autowired constructor(
    private val entryRepository: EntryRepository,
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {
    companion object {
        @Container
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:16-alpine")
    }

    fun setupEntries(): List<EntryEntity> {
        val account = AccountEntity(
            id = UUID.randomUUID(),
            type = AccountType.ASSET,
            currency = "GBP",
            status = AccountStatus.ACTIVE,
        )
        accountRepository.save(account)
        val transaction = TransactionEntity(
            id = UUID.randomUUID(),
            idempotencyKey = UUID.randomUUID(),
            status = TransactionStatus.PENDING,
            effectiveAt = Instant.now(),
        )
        transactionRepository.save(transaction)
        
        val entries = mutableListOf<EntryEntity>()
        for (i in 0..1) {
            val entry = EntryEntity(
                id = UUID.randomUUID(),
                accountId = account.id,
                transactionId = transaction.id,
                amount = BigDecimal(1.67),
                direction = EntryDirection.CREDIT,
                description = "asfd",
                createdAt = Instant.now(),
            )
            entries.add(entry)
        }

        return entries
    }
    
    @Test
    fun `should find all entries by transactionId`() {
        val entries = setupEntries()
        entries.forEach { entryRepository.save(it) }

        val retrieved = entryRepository.findByTransactionId(entries[0].transactionId)

        assertEquals(2, retrieved.size)
        assertNotEquals(retrieved[0].id, retrieved[1].id)
    }

    @Test
    fun `should find all entries by accountId`() {
        val entries = setupEntries()
        entries.forEach { entryRepository.save(it) }

        val retrieved = entryRepository.findByAccountId(entries[0].accountId)

        assertEquals(2, retrieved.size)
        assertNotEquals(retrieved[0].id, retrieved[1].id)
    }

    @Test
    fun `should fail when accountId doesn't exist`() {
        val transaction = TransactionEntity(
            id = UUID.randomUUID(),
            idempotencyKey = UUID.randomUUID(),
            status = TransactionStatus.PENDING,
            effectiveAt = Instant.now(),
        )
        transactionRepository.save(transaction)

        val entry = EntryEntity(
            id = UUID.randomUUID(),
            accountId = UUID.randomUUID(),
            transactionId = transaction.id,
            amount = BigDecimal(1.67),
            direction = EntryDirection.CREDIT,
            description = "bazinga",
            createdAt = Instant.now(),
        )

        assertFailsWith<DataIntegrityViolationException> {
            entryRepository.save(entry)
        }
    }

    @Test
    fun `should fail when transactionid doesn't exist`() {
        val account = AccountEntity(
            id = UUID.randomUUID(),
            type = AccountType.ASSET,
            currency = "GBP",
            status = AccountStatus.FROZEN
        )
        accountRepository.save(account)

        val entry = EntryEntity(
            id = UUID.randomUUID(),
            accountId = account.id,
            transactionId = UUID.randomUUID(),
            amount = BigDecimal(1.67),
            direction = EntryDirection.CREDIT,
            description = "bazinga",
            createdAt = Instant.now(),
        )

        assertFailsWith<DataIntegrityViolationException> {
            entryRepository.save(entry)
        }
    }

    @Test
    fun `should fail when amount is zero or negative`() {
        val account = AccountEntity(
            id = UUID.randomUUID(),
            type = AccountType.ASSET,
            currency = "GBP",
            status = AccountStatus.ACTIVE,
        )
        accountRepository.save(account)

        val transaction = TransactionEntity(
            id = UUID.randomUUID(),
            idempotencyKey = UUID.randomUUID(),
            status = TransactionStatus.PENDING,
            effectiveAt = Instant.now(),
        )
        transactionRepository.save(transaction)

        val zeroAmountEntry = EntryEntity(
            id = UUID.randomUUID(),
            accountId = account.id,
            transactionId = transaction.id,
            amount = BigDecimal("0.00"),
            direction = EntryDirection.CREDIT,
            description = "zero amount",
            createdAt = Instant.now(),
        )

        assertFailsWith<DataIntegrityViolationException> {
            entryRepository.save(zeroAmountEntry)
        }
                
    }
}
