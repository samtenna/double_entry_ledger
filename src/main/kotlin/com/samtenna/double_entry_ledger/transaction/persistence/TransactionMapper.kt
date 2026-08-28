package com.samtenna.double_entry_ledger.transaction.persistence

import com.samtenna.double_entry_ledger.entry.Entry
import com.samtenna.double_entry_ledger.transaction.Transaction

fun TransactionEntity.toDomain(entries: List<Entry>): Transaction =
    Transaction(
        id = this.id,
        idempotencyKey = this.idempotencyKey,
        status = this.status,
        entries = entries,
        effectiveAt = this.effectiveAt,
    )

fun Transaction.toEntity(): TransactionEntity =
    TransactionEntity(
        id = this.id,
        idempotencyKey = this.idempotencyKey,
        status = this.status,
        effectiveAt = this.effectiveAt,
    )