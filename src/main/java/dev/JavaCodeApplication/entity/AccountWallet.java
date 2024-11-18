package dev.JavaCodeApplication.entity;


import dev.JavaCodeApplication.entity.enums.OperationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Version;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "account_wallet")
public class AccountWallet {
    @Id
    // @GeneratedValue(strategy = GenerationType.UUID)
     @Column("wallet_id")
    private UUID walletId;
    @Column("amount")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;
//    @Version
//    private Long version;
}
