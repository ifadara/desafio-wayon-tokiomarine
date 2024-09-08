package com.tokiomarine.desafiotokiomarinebackend.controllers;

import com.tokiomarine.desafiotokiomarinebackend.dto.TransferDto;
import com.tokiomarine.desafiotokiomarinebackend.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "v1/transfer")
public class TransferController {

    @Autowired
    private TransferService transferService;

    @GetMapping
    public Page<TransferDto> findAll(Pageable pageable){
        return transferService.findAll(pageable);
    }

    @PostMapping
    public TransferDto createTransfer(@RequestBody TransferDto transferDto){
        return transferService.save(transferDto);
    }
}
