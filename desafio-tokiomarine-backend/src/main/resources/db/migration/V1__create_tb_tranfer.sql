CREATE TABLE tb_transfer
(
    id                   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    origin_account       INT                                     NOT NULL,
    target_account       INT                                     NOT NULL,
    transfer_value       DOUBLE PRECISION                        NOT NULL,
    transfer_percentage  DOUBLE PRECISION                        NOT NULL,
    fee_amount           DOUBLE PRECISION                        NOT NULL,
    total_transfer_value DOUBLE PRECISION                        NOT NULL,
    transfer_date        date                                    NOT NULL,
    scheduled_date       date                                    NOT NULL,
    CONSTRAINT pk_tb_transfer PRIMARY KEY (id)
);