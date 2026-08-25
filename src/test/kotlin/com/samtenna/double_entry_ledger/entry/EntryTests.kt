package com.samtenna.double_entry_ledger.entry

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertFailsWith

class EntryTests {
    @ParameterizedTest
    @ValueSource(ints = [0, -1])
    fun `Entry shouldn't accept zero or negative numbers for amount`(amount: Int) {
        assertFailsWith<IllegalArgumentException> {
            Entry(
                id = UUID.randomUUID(),
                accountId = UUID.randomUUID(),
                amount = BigDecimal(amount),
                description = "",
                direction = EntryDirection.CREDIT,
                createdAt = Instant.now(),
            )
        }
    }

    @Test
    fun `Entry should accept positive amount`() {
        assertDoesNotThrow {
            Entry(
                id = UUID.randomUUID(),
                accountId = UUID.randomUUID(),
                amount = BigDecimal(100),
                description = "",
                direction = EntryDirection.CREDIT,
                createdAt = Instant.now(),
            )
        }
    }
}