package com.samtenna.double_entry_ledger.transaction.persistence

import com.samtenna.double_entry_ledger.entry.persistence.EntryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository : JpaRepository<EntryEntity, UUID> {
    fun findTransactionById(transactionId: UUID): List<EntryEntity>
}