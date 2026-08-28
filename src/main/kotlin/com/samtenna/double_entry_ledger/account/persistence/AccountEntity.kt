package com.samtenna.double_entry_ledger.account.persistence

import com.samtenna.double_entry_ledger.account.AccountStatus
import com.samtenna.double_entry_ledger.account.AccountType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType
import java.util.UUID

@Entity
@Table(name = "accounts")
class AccountEntity(
    @Id
    var id: UUID,

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    var type: AccountType,

    var currency: String,

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    var status: AccountStatus,
)