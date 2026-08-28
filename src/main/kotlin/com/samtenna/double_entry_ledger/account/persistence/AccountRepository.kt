package com.samtenna.double_entry_ledger.account.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface AccountRepository : JpaRepository<AccountEntity, UUID> {}