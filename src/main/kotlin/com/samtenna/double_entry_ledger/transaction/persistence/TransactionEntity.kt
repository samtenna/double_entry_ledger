package com.samtenna.double_entry_ledger.transaction.persistence

import com.samtenna.double_entry_ledger.transaction.TransactionStatus
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.type.PostgreSQLEnumJdbcType
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "transactions")
class TransactionEntity (
    @Id
    var id: UUID,

    @Column(name = "idempotency_key")
    var idempotencyKey: UUID,

    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType::class)
    var status: TransactionStatus,

    var effectiveAt: Instant = Instant.now(),
)