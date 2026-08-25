package com.samtenna.double_entry_ledger.entry

import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

data class Entry(
    val id: UUID,
    val accountId: UUID,
    val amount: BigDecimal,
    val description: String,
    val direction: EntryDirection,
    val createdAt: Instant,
) {
    init {
        require(amount > BigDecimal.ZERO) {
            "Entry amount must be strictly positive, but was $amount"
        }
    }
}
