package com.samtenna.double_entry_ledger.account.persistence

import com.samtenna.double_entry_ledger.account.AccountStatus
import com.samtenna.double_entry_ledger.account.AccountType
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import java.util.UUID
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@SpringBootTest
@Testcontainers
class AccountRepositoryTests @Autowired constructor(
    private val accountRepository: AccountRepository
) {
    companion object {
        @Container
        @ServiceConnection
        val postgres = PostgreSQLContainer("postgres:16-alpine")
    }

    @Test
    fun `should save and retrieve an account`() {
        val id = UUID.randomUUID()
        val entity = AccountEntity(
            id = id,
            type = AccountType.REVENUE,
            currency = "GBP",
            status = AccountStatus.ACTIVE,
        )

        accountRepository.save(entity)

        val retrieved = accountRepository.findById(id)
        assertTrue(retrieved.isPresent)
        assertEquals("GBP", retrieved.get().currency)
        assertEquals(AccountType.REVENUE, retrieved.get().type)
        assertEquals(AccountStatus.ACTIVE, retrieved.get().status)
    }
}