package com.samtenna.double_entry_ledger.transaction.persistence

import com.samtenna.double_entry_ledger.entry.Entry
import com.samtenna.double_entry_ledger.entry.EntryDirection
import com.samtenna.double_entry_ledger.transaction.Transaction
import com.samtenna.double_entry_ledger.transaction.TransactionStatus
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class TransactionMapperTests {
    fun createEntry(direction: EntryDirection): Entry =
        Entry(
            id = UUID.randomUUID(),
            accountId = UUID.randomUUID(),
            transactionId = UUID.randomUUID(),
            amount = BigDecimal(67),
            description = "",
            direction = direction,
            createdAt = Instant.now(),
        )

    @Test
    fun `round trip mapping preserves data`() {
        val entryOne = createEntry(EntryDirection.CREDIT)
        val entryTwo = createEntry(EntryDirection.DEBIT)
        val entryList = listOf(entryOne, entryTwo)
        val transaction = Transaction(
            id = UUID.randomUUID(),
            idempotencyKey = UUID.randomUUID(),
            status = TransactionStatus.COMPLETE,
            entries = entryList,
            effectiveAt = Instant.now(),
        )
        val entity = transaction.toEntity()
        val finalTransaction = entity.toDomain(entryList)

        assertEquals(transaction, finalTransaction)
    }
}