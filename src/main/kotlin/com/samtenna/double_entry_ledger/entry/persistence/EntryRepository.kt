package com.samtenna.double_entry_ledger.entry.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface EntryRepository : JpaRepository<EntryEntity, UUID> {
    fun findByTransactionId(transactionId: UUID): List<EntryEntity>
    fun findByAccountId(accountId: UUID): List<EntryEntity>
}
