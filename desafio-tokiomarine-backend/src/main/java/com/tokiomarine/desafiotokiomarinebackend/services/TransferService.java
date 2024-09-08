package com.tokiomarine.desafiotokiomarinebackend.services;

import com.tokiomarine.desafiotokiomarinebackend.dto.TransferDto;
import com.tokiomarine.desafiotokiomarinebackend.entities.Transfer;
import com.tokiomarine.desafiotokiomarinebackend.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.time.Period;

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
        TransferDto completeTransfer = calculateFeeAmountAndTotalTransferValue(transferDto);
        Transfer transfer = new Transfer(completeTransfer);
        transfer = transferRepository.save(transfer);

        return new TransferDto(transfer);
    }

    private TransferDto calculateFeeAmountAndTotalTransferValue(TransferDto transferDto){
        Period period = Period.between(transferDto.getTransferDate(), transferDto.getScheduledDate());
        double percentageValue = 0.0;


        if (period.isZero() && transferDto.getTotalTransferValue() > 3.00) {
            percentageValue = transferDto.getTransferValue() * 0.025;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.025 * 100);
            transferDto.setTotalTransferValue(transferDto.getTransferValue() + transferDto.getFeeAmount());
        } else if (period.getDays() >= 1 && period.getDays() <= 10 && transferDto.getTotalTransferValue() > 12.00) {
            percentageValue = transferDto.getTransferValue() * 0.0;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.0 * 100);
            transferDto.setTotalTransferValue(transferDto.getTransferValue() + transferDto.getFeeAmount());
        } else if (period.getDays() >= 11 && period.getDays() <= 20) {
            percentageValue = transferDto.getTransferValue() * 0.082;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.082 * 100);
            transferDto.setTotalTransferValue(transferDto.getTransferValue() + transferDto.getFeeAmount());
        } else if (period.getDays() >= 21 && period.getDays() <= 30) {
            percentageValue = transferDto.getTransferValue() * 0.069;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.069 * 100);
            transferDto.setTotalTransferValue(transferDto.getTransferValue() + transferDto.getFeeAmount());
        } else if (period.getDays() >= 31 && period.getDays() <= 40) {
            percentageValue = transferDto.getTransferValue() * 0.047;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.047 * 100);
            transferDto.setTotalTransferValue(transferDto.getTransferValue() + transferDto.getFeeAmount());
        } else if (period.getDays() >= 41 && period.getDays() <= 50) {
            percentageValue = transferDto.getTransferValue() * 0.017;
            transferDto.setFeeAmount(percentageValue);
            transferDto.setTransferPercentage(0.017 * 100);
            transferDto.setTotalTransferValue(transferDto.getTransferValue() + transferDto.getFeeAmount());
        } else {
            throw new RuntimeException("Não há taxa aplicável a esta tranferencia, tente novamente com novos parâmetros");
        }


        return transferDto;
    }
}
