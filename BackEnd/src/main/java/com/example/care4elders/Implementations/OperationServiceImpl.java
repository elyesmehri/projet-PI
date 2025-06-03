package com.example.care4elders.Implementations;

import com.example.care4elders.controllers.DTO.OperationRequest;
import com.example.care4elders.controllers.DTO.OperationResponse;
import com.example.care4elders.model.Operation;
import com.example.care4elders.repository.OperationRepository;
import com.example.care4elders.services.OperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;


    @Override
    public OperationResponse convertToDto(Operation operation) {
        OperationResponse dto = new OperationResponse();
        dto.setId(operation.getId());
        dto.setOperationName(operation.getOperationName());
        dto.setOperationCode(operation.getOperationCode());
        dto.setDescription(operation.getDescription());
        dto.setStandardPrice(operation.getStandardPrice());
        dto.setCategory(operation.getCategory());
        // dto.setNumberOfSupportingInsurances(operation.getSupportedByInsurances().size()); // Uncomment if needed
        return dto;
    }

    @Override
    public OperationResponse createOperation(OperationRequest request) {
        Operation operation = new Operation();
        operation.setOperationName(request.getOperationName());
        operation.setOperationCode(request.getOperationCode());
        operation.setDescription(request.getDescription());
        operation.setStandardPrice(request.getStandardPrice());
        operation.setCategory(request.getCategory());

        Operation savedOperation = operationRepository.save(operation);
        return convertToDto(savedOperation);
    }

    @Override
    public OperationResponse getOperationById(Long id) {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operation not found with ID: " + id));
        return convertToDto(operation);
    }

    @Override
    public List<OperationResponse> getAllOperations() {
        return operationRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public OperationResponse updateOperation(Long id, OperationRequest request) {
        Operation operation = operationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Operation not found with ID: " + id));

        operation.setOperationName(request.getOperationName());
        operation.setOperationCode(request.getOperationCode());
        operation.setDescription(request.getDescription());
        operation.setStandardPrice(request.getStandardPrice());
        operation.setCategory(request.getCategory());

        Operation updatedOperation = operationRepository.save(operation);
        return convertToDto(updatedOperation);
    }

    @Override
    public void deleteOperation(Long id) {
        if (!operationRepository.existsById(id)) {
            throw new EntityNotFoundException("Operation not found with ID: " + id);
        }
        // When deleting an operation, its links in the join table (insurance_supported_operation) will be removed.
        // No associated Insurances will be deleted.
        operationRepository.deleteById(id);
    }
}
