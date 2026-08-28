package com.samtenna.double_entry_ledger.entry.persistence

import com.samtenna.double_entry_ledger.entry.Entry

fun EntryEntity.toDomain(): Entry = Entry(
    id = this.id,
    accountId = this.accountId,
    transactionId = this.transactionId,
    amount = this.amount,
    description = this.description,
    direction = this.direction,
    createdAt = this.createdAt,
)

fun Entry.toEntity(): EntryEntity = EntryEntity(
    id = this.id,
    accountId = this.accountId,
    transactionId = this.transactionId,
    amount = this.amount,
    direction = this.direction,
    description = this.description,
    createdAt = this.createdAt,
)