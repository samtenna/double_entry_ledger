package com.samtenna.double_entry_ledger.account.persistence

import com.samtenna.double_entry_ledger.account.Account
import com.samtenna.double_entry_ledger.account.AccountStatus
import com.samtenna.double_entry_ledger.account.AccountType

fun AccountEntity.toDomain(): Account = Account(
    id = this.id,
    type = this.type,
    currency = this.currency,
    status = this.status,
)

fun Account.toEntity(): AccountEntity = AccountEntity(
    id = this.id,
    type = this.type,
    currency = this.currency,
    status = this.status,
)