package com.samtenna.double_entry_ledger.transaction.persistence

import com.samtenna.double_entry_ledger.transaction.Transaction
import com.samtenna.double_entry_ledger.transaction.TransactionStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.dao.DataIntegrityViolationException
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Instant
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

@SpringBootTest
@Testcontainers
class TransactionRepositoryTests @Autowired constructor(
    private val transactionRepository: TransactionRepository
) {
    companion object {
        @Container
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:16-alpine")
    }

    @Test
    fun `should save and retrieve a transaction by id`() {
        val id = UUID.randomUUID()
        val key = UUID.randomUUID()
        val now = Instant.now().truncatedTo(java.time.temporal.ChronoUnit.MICROS)
        val entity = TransactionEntity(
            id = id,
            idempotencyKey = key,
            status = TransactionStatus.COMPLETE,
            effectiveAt = now,
        )

        transactionRepository.save(entity)

        val retrieved = transactionRepository.findById(id)

        assertTrue(retrieved.isPresent)
        assertEquals(id, retrieved.get().id)
        assertEquals(key, retrieved.get().idempotencyKey)
        assertEquals(TransactionStatus.COMPLETE, retrieved.get().status)
        assertEquals(now, retrieved.get().effectiveAt)
    }

    @Test
    fun `should fail when saving duplicate idempotencyKey`() {
        val key = UUID.randomUUID()
        val entity_one = TransactionEntity(
            id = UUID.randomUUID(),
            idempotencyKey = key,
            status = TransactionStatus.COMPLETE,
            effectiveAt = Instant.now(),
        )
        val entity_two = TransactionEntity(
            id = UUID.randomUUID(),
            idempotencyKey = key,
            status = TransactionStatus.COMPLETE,
            effectiveAt = Instant.now(),
        )

        transactionRepository.save(entity_one)

        assertFailsWith<DataIntegrityViolationException> {
            transactionRepository.save(entity_two)
        }
    }
}
