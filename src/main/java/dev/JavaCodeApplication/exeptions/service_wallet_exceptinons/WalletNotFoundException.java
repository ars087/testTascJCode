package dev.JavaCodeApplication.exeptions.service_wallet_exceptinons;

// Исключение для ошибок, связанных с несуществующим кошельком
public class WalletNotFoundException extends WalletServiceException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}

