package com.samtenna.double_entry_ledger.transaction

import com.samtenna.double_entry_ledger.entry.Entry
import com.samtenna.double_entry_ledger.entry.EntryDirection
import java.math.BigDecimal
import java.util.UUID
import java.time.Instant

data class Transaction(
    val id: UUID,
    val idempotencyKey: UUID,
    val status: TransactionStatus,
    val entries: List<Entry>,
    val effectiveAt: Instant,
) {
    init {
        // At least two legs
        require(entries.size >= 2) {
            "A transaction must have at least 2 entries, got ${entries.size}"
        }

        // Balanced transaction
        val netBalance = entries.fold(BigDecimal.ZERO) { acc, entry ->
            when (entry.direction) {
                EntryDirection.DEBIT -> acc + entry.amount
                EntryDirection.CREDIT -> acc - entry.amount
            }
        }
        // compareTo ignores scale
        require(netBalance.compareTo(BigDecimal.ZERO) == 0) {
            "Transaction sum is unbalanced, net sum is $netBalance"
        }

        // No repeated entry ids
        require(entries.map { it.id }.toSet().size == entries.size) {
            "Duplicate entry IDs given"
        }
    }
}
