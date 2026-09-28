package com.samtenna.double_entry_ledger.transaction.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface TransactionRepository : JpaRepository<TransactionEntity, UUID> {
    fun findTransactionById(transactionId: UUID): List<TransactionEntity>
}
