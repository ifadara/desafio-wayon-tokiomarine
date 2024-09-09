package com.tokiomarine.desafiotokiomarinebackend.entities;

import com.tokiomarine.desafiotokiomarinebackend.dto.TransferDto;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tb_transfer")
public class Transfer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long originAccount;
    @Column(nullable = false)
    private Long targetAccount;
    @Column(nullable = false)
    private Double transferValue;
    @Column(nullable = false)
    private Double transferPercentage;
    @Column(nullable = false)
    private Double feeAmount;
    @Column(nullable = false)
    private Double totalTransferValue;
    @Column(nullable = false)
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate transferDate;
    @Column(nullable = false)
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate scheduledDate;

    public Transfer(){
    }

    public Transfer(Long id, Long originAccount, Long targetAccount, Double transferValue, Double transferPercentage, Double feeAmount, Double totalTransferValue, LocalDate transferDate, LocalDate scheduledDate) {
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

    public Transfer(TransferDto transfer) {
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

    public Long getOriginAccount() {
        return originAccount;
    }

    public void setOriginAccount(Long originAccount) {
        this.originAccount = originAccount;
    }

    public Long getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Long targetAccount) {
        this.targetAccount = targetAccount;
    }

    public Double getTransferValue() {
        return transferValue;
    }

    public void setTransferValue(Double transferValue) {
        this.transferValue = transferValue;
    }

    public Double getTransferPercentage() {
        return transferPercentage;
    }

    public void setTransferPercentage(Double transferPercentage) {
        this.transferPercentage = transferPercentage;
    }

    public Double getFeeAmount() {
        return feeAmount;
    }

    public void setFeeAmount(Double feeAmount) {
        this.feeAmount = feeAmount;
    }

    public Double getTotalTransferValue() {
        return totalTransferValue;
    }

    public void setTotalTransferValue(Double totalTransferValue) {
        this.totalTransferValue = totalTransferValue;
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
}
