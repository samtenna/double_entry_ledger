package com.samtenna.double_entry_ledger.entry.persistence

import com.samtenna.double_entry_ledger.entry.EntryDirection
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType
import java.math.BigDecimal
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "entries")
class EntryEntity (
    @Id
    var id: UUID,

    @Column(name = "account_id")
    var accountId: UUID,

    @Column(name = "transaction_id")
    var transactionId: UUID,

    var amount: BigDecimal,

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    var direction: EntryDirection,

    var description: String,

    var createdAt: Instant = Instant.now(),
)