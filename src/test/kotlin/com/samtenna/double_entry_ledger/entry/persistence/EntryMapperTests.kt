package com.samtenna.double_entry_ledger.entry.persistence

import com.samtenna.double_entry_ledger.account.Account
import com.samtenna.double_entry_ledger.account.AccountStatus
import com.samtenna.double_entry_ledger.account.AccountType
import com.samtenna.double_entry_ledger.account.persistence.toDomain
import com.samtenna.double_entry_ledger.account.persistence.toEntity
import com.samtenna.double_entry_ledger.entry.Entry
import com.samtenna.double_entry_ledger.entry.EntryDirection
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class EntryMapperTests {
    @Test
    fun `round trip mapping preserves data`() {
        val entry = Entry(
            id = UUID.randomUUID(),
            accountId = UUID.randomUUID(),
            transactionId = UUID.randomUUID(),
            amount = BigDecimal(69),
            description = "",
            direction = EntryDirection.CREDIT,
            createdAt = Instant.now(),
        )
        val entity = entry.toEntity()
        val finalEntry = entity.toDomain()

        assertEquals(entry, finalEntry)
    }
}