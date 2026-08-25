package com.samtenna.double_entry_ledger.account

import java.util.UUID

data class Account(
    val id: UUID,
    val type: AccountType,
    // ISO-4217 code
    val currency: String,
    val status: AccountStatus,
) {
    init {
        require(currency.matches(Regex("^[A-Z]{3}$")))
    }

    val normalBalance: AccountNormal get() = when (this.type) {
        AccountType.ASSET, AccountType.EXPENSE ->
            AccountNormal.DEBIT
        AccountType.LIABILITY, AccountType.EQUITY, AccountType.REVENUE ->
            AccountNormal.CREDIT
    }
}
