package dev.JavaCodeApplication.entity;


import dev.JavaCodeApplication.entity.enums.OperationType;
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
    @Column("operation_type")
    @NotNull(message = "Operation type cannot be null")
    @Size(min = 7, max = 8, message = "Operation type must be between 7 and 8 characters")
    @Pattern(regexp = "^(DEPOSIT|WITHDRAW)$", message = "Operation type must be either 'DEPOSIT' or 'WITHDRAW'")
    private OperationType operationType;
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;
}
