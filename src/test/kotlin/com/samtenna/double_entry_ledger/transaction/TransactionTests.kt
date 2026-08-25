package com.samtenna.double_entry_ledger.transaction

import com.samtenna.double_entry_ledger.entry.Entry
import com.samtenna.double_entry_ledger.entry.EntryDirection
import org.junit.jupiter.api.assertDoesNotThrow
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertFailsWith

class TransactionTests {
    fun createTestEntry(
        amount: Int = 69,
        direction: EntryDirection = EntryDirection.CREDIT,
        id: String? = null,
    ): Entry {
        val id = if (id != null) UUID.fromString(id) else UUID.randomUUID()

        return Entry(
            id = id,
            accountId = UUID.randomUUID(),
            amount = BigDecimal(amount),
            description = "",
            direction = direction,
            createdAt = Instant.now(),
        )
    }

    @Test
    fun `Transaction with less than 2 legs fails`() {
        assertFailsWith<IllegalArgumentException> {
            Transaction(
                id = UUID.randomUUID(),
                idempotencyKey = UUID.randomUUID(),
                status = TransactionStatus.COMPLETE,
                entries = listOf(),
                effectiveAt = Instant.now(),
            )
        }
    }

    @Test
    fun `Balanced transactions succeed`() {
        assertDoesNotThrow {
            Transaction(
                id = UUID.randomUUID(),
                idempotencyKey = UUID.randomUUID(),
                status = TransactionStatus.COMPLETE,
                entries = listOf(
                    createTestEntry(1, EntryDirection.CREDIT),
                    createTestEntry(1, EntryDirection.DEBIT),
                ),
                effectiveAt = Instant.now(),
            )
        }
    }

    @Test
    fun `Unbalanced transactions fail`() {
        assertFailsWith<IllegalArgumentException> {
            Transaction(
                id = UUID.randomUUID(),
                idempotencyKey = UUID.randomUUID(),
                status = TransactionStatus.COMPLETE,
                entries = listOf(
                    createTestEntry(67, EntryDirection.CREDIT),
                    createTestEntry(69, EntryDirection.DEBIT),
                ),
                effectiveAt = Instant.now(),
            )
        }
    }

    @Test
    fun `Transactions with duplicated entry ids fail`() {
        assertFailsWith<IllegalArgumentException> {
            Transaction(
                id = UUID.randomUUID(),
                idempotencyKey = UUID.randomUUID(),
                status = TransactionStatus.COMPLETE,
                entries = listOf(
                    createTestEntry(id = "lol"),
                    createTestEntry(id = "lol"),
                ),
                effectiveAt = Instant.now(),
            )
        }
    }
}