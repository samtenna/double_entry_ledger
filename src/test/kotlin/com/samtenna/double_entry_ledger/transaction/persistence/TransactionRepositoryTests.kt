package com.samtenna.double_entry_ledger.transaction.persistence

import com.samtenna.double_entry_ledger.transaction.Transaction
import com.samtenna.double_entry_ledger.transaction.TransactionStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.time.Instant
import java.util.UUID
import kotlin.test.Test

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
        val entity = TransactionEntity(
            id = id,
            idempotencyKey = UUID.randomUUID(),
            status = TransactionStatus.COMPLETE,
            effectiveAt = Instant.now(),
        )

        transactionRepository.save(entity)

        val retrieved = transactionRepository.findById(id)

    }
}