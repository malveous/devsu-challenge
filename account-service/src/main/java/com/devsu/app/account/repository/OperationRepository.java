package com.devsu.app.account.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsu.app.account.model.Operation;

public interface OperationRepository extends JpaRepository<Operation, Integer> {

    @Query("SELECT o from Operation o where o.account.accountNumber = ?1")
    List<Operation> findOperationsByAccountNumber(String accountNumber);

    @Query("SELECT o from Operation o where o.account.id = ?1 and o.date BETWEEN ?2 and ?3")
    List<Operation> retrieveOperationsByAccountIdAndDateRange(int id, Instant startDate, Instant endDate);

}
