package dev.JavaCodeApplication.models;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountWalletRequestDTO {

    @NotEmpty(message = "не может быть пустым поле")
    @Size(min = 36, max = 36, message = "Не верный формат ID")
    private String walletId;
    @NotNull(message = "Operation type cannot be null")
    @Size(min = 7, max = 8, message = "Operation type must be between 7 and 8 characters")
    @Pattern(regexp = "^(DEPOSIT|WITHDRAW)$", message = "Operation type must be either 'DEPOSIT' or 'WITHDRAW'")
    private String operationType;
    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than zero")
    private BigDecimal amount;
}
