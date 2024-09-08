package com.tokiomarine.desafiotokiomarinebackend.repositories;

import com.tokiomarine.desafiotokiomarinebackend.entities.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
