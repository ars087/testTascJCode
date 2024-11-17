CREATE TABLE account_wallet
(
    wallet_id      UUID PRIMARY KEY NOT NULL ,
    operation_type VARCHAR(8)       NOT NULL,
    amount         numeric(10, 2)   NOT NULL
);
