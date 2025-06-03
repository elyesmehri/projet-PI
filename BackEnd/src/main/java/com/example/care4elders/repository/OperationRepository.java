package com.example.care4elders.repository;

import com.example.care4elders.model.Medication;
import com.example.care4elders.model.Operation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long> {

    Optional<Operation> findByOperationName(String operationName);
    Optional<Operation> findByOperationCode(String operationCode);
}
