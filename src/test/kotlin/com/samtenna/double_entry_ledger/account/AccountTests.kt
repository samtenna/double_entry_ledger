package com.samtenna.double_entry_ledger.account

import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.ValueSource
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AccountTests {
    @ParameterizedTest
    @EnumSource(
        value = AccountType::class,
        names = ["ASSET", "EXPENSE"]
    )
    fun `asset and expense accounts have debit normal balance`(type: AccountType) {
        val account = Account(
            id = UUID.randomUUID(),
            type = type,
            currency = "GBP",
            status = AccountStatus.ACTIVE,
        )

        assertEquals(AccountNormal.DEBIT, account.normalBalance)
    }

    @ParameterizedTest
    @EnumSource(
        value = AccountType::class,
        names = ["LIABILITY", "EQUITY", "REVENUE"]
    )
    fun `liability, equity, and revenue have credit normal balance`(type: AccountType) {
        val account = Account(
            id = UUID.randomUUID(),
            type = type,
            currency = "GBP",
            status = AccountStatus.ACTIVE,
        )

        assertEquals(AccountNormal.CREDIT, account.normalBalance)
    }

    @ParameterizedTest
    @ValueSource(strings = ["", "A", "gbp", "GBPG", $$"U$D", "GB3"])
    fun `Account must reject an invalid or missing currency code`(currency: String) {
        assertFailsWith<IllegalArgumentException> {
            Account(
                id = UUID.randomUUID(),
                type = AccountType.REVENUE,
                currency = currency,
                status = AccountStatus.ACTIVE,
            )
        }
    }

    @ParameterizedTest
    @ValueSource(strings = ["GBP", "USD", "EUR", "AUD"])
    fun `Account must accept valid currency code`(currency: String) {
        assertDoesNotThrow {
            Account(
                id = UUID.randomUUID(),
                type = AccountType.REVENUE,
                currency = currency,
                status = AccountStatus.ACTIVE,
            )
        }
    }
}