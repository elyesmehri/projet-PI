package com.example.care4elders.services;

import com.example.care4elders.controllers.DTO.OperationRequest;
import com.example.care4elders.controllers.DTO.OperationResponse;
import com.example.care4elders.model.Operation;

import java.util.List;

public interface OperationService {

    OperationResponse convertToDto(Operation operation);

    OperationResponse createOperation(OperationRequest request);

    OperationResponse getOperationById(Long id);

    List<OperationResponse> getAllOperations();

    OperationResponse updateOperation(Long id, OperationRequest request);

    void deleteOperation(Long id);
}
