package com.tokiomarine.desafiotokiomarinebackend.services;

import com.tokiomarine.desafiotokiomarinebackend.dto.TransferDto;
import com.tokiomarine.desafiotokiomarinebackend.entities.Transfer;
import com.tokiomarine.desafiotokiomarinebackend.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;


    @Transactional(readOnly = true)
    public Page<TransferDto> findAll(Pageable pageable){
        Page<Transfer> result = transferRepository.findAll(pageable);
        return result.map(TransferDto::new);
    }

    @Transactional
    public TransferDto save(TransferDto transferDto){
        try {
            TransferDto completeTransfer = calculateFeeAmountAndTotalTransferValue(transferDto);
            Transfer transfer = new Transfer(completeTransfer);
            transfer = transferRepository.save(transfer);

            return new TransferDto(transfer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }



    }

    private TransferDto calculateFeeAmountAndTotalTransferValue(TransferDto transferDto){
        transferDto.setTransferDate(LocalDate.now());
        Long period = ChronoUnit.DAYS.between(transferDto.getTransferDate(), transferDto.getScheduledDate());
        Double percentageValue = 0.0;
        Double totalValue = 0.0;


        if (period == 0 && transferDto.getTransferValue() > 3.00) {
            percentageValue = transferDto.getTransferValue() * 0.025;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.025 * 100);
            totalValue = transferDto.getTransferValue() + percentageValue;
            transferDto.setTotalTransferValue(totalValue);
        } else if (period >= 1 && period <= 10 && transferDto.getTransferValue() >= 12.00) {
            percentageValue = transferDto.getTransferValue() * 0.0;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.0);
            totalValue = transferDto.getTransferValue() + percentageValue;
            transferDto.setTotalTransferValue(totalValue);
        } else if (period >= 11 && period <= 20) {
            percentageValue = transferDto.getTransferValue() * 0.082;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.082 * 100);
            totalValue = transferDto.getTransferValue() + percentageValue;
            transferDto.setTotalTransferValue(totalValue);
        } else if (period >= 21 && period <= 30) {
            percentageValue = transferDto.getTransferValue() * 0.069;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.069 * 100);
            totalValue = transferDto.getTransferValue() + percentageValue;
            transferDto.setTotalTransferValue(totalValue);
        } else if (period >= 31 && period <= 40) {
            percentageValue = transferDto.getTransferValue() * 0.047;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.047 * 100);
            totalValue = transferDto.getTransferValue() + percentageValue;
            transferDto.setTotalTransferValue(totalValue);
        } else if (period >= 41 && period <= 50) {
            percentageValue = transferDto.getTransferValue() * 0.017;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.017 * 100);
            totalValue = transferDto.getTransferValue() + percentageValue;
            transferDto.setTotalTransferValue(totalValue);
        } else {
            throw new RuntimeException("Não há taxa aplicável a esta tranferencia, tente novamente com novos parâmetros");
        }


        return transferDto;
    }
}
