package com.samtenna.double_entry_ledger.account.persistence

import com.samtenna.double_entry_ledger.account.Account
import com.samtenna.double_entry_ledger.account.AccountStatus
import com.samtenna.double_entry_ledger.account.AccountType
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals

class AccountMapperTests {
    @Test
    fun `round trip mapping preserves data`() {
        val account = Account(
            id = UUID.randomUUID(),
            type = AccountType.REVENUE,
            currency = "GBP",
            status = AccountStatus.ACTIVE,
        )
        val entity = account.toEntity()
        val finalAccount = entity.toDomain()

        assertEquals(account, finalAccount)
    }
}