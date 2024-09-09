package com.tokiomarine.desafiotokiomarinebackend.dto;

import com.tokiomarine.desafiotokiomarinebackend.entities.Transfer;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class TransferDto {
    private Long id;
    private Integer originAccount;
    private Integer targetAccount;
    private Double transferValue;
    private Double transferPercentage;
    private Double feeAmount;
    private Double totalTransferValue;
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate transferDate;
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate scheduledDate;

    public TransferDto() {
    }

    public TransferDto(Long id, Integer originAccount, Integer targetAccount, Double transferValue, Double transferPercentage, Double feeAmount, Double totalTransferValue, LocalDate transferDate, LocalDate scheduledDate) {
        this.id = id;
        this.originAccount = originAccount;
        this.targetAccount = targetAccount;
        this.transferValue = transferValue;
        this.transferPercentage = transferPercentage;
        this.feeAmount = feeAmount;
        this.totalTransferValue = totalTransferValue;
        this.transferDate = transferDate;
        this.scheduledDate = scheduledDate;
    }

    public TransferDto(Transfer transfer) {
        this.id = transfer.getId();
        this.originAccount = transfer.getOriginAccount();
        this.targetAccount = transfer.getTargetAccount();
        this.transferValue = transfer.getTransferValue();
        this.transferPercentage = transfer.getTransferPercentage();
        this.feeAmount = transfer.getFeeAmount();
        this.totalTransferValue = transfer.getTotalTransferValue();
        this.transferDate = transfer.getTransferDate();
        this.scheduledDate = transfer.getScheduledDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(Integer originAccount) {
        this.originAccount = originAccount;
    }

    public Integer getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Integer targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(Double transferValue) {
        this.transferValue = roundToTwoDecimalPlaces(transferValue);
    }

    public Double getTransferPercentage() {
        return transferPercentage;
    }

    public void setTransferPercentage(Double transferPercentage) {
        this.transferPercentage = roundToTwoDecimalPlaces(transferPercentage);
    }

    public Double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = roundToTwoDecimalPlaces(feeAmount);
    }

    public Double getTotalTransferValue() {
        return totalTransferValue;
    }

    public void setTotalTransferValue(Double totalTransferValue) {
        this.totalTransferValue = roundToTwoDecimalPlaces(totalTransferValue);
    }

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    private Double roundToTwoDecimalPlaces(Double value) {
        if (value == null) {
            return null;
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
