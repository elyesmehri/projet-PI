package com.example.care4elders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "operation")
@Getter
@Setter
public class Operation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "operation_name", nullable = false, unique = true)
    private String operationName;

    @Column(name = "operation_code", unique = true) // e.g., CPT code, ICD-10 PCS code
    private String operationCode;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "standard_price") // Base price of the operation
    private Float standardPrice;

    @Column(name = "category") // e.g., "Surgery", "Diagnostic", "Therapy", "Consultation"
    private String category;

    // --- Many-to-Many Relationship with Insurance ---
    // An operation can be supported by many insurance policies.
    // An insurance policy can support many operations.
    @JsonIgnore // Prevent infinite recursion
    @ManyToMany(mappedBy = "supportedOperations", fetch = FetchType.LAZY)
    private List<Insurance> supportedByInsurances = new ArrayList<>();

    public Operation() {
    }

    public Operation(Long id, String operationName, String operationCode, String description,
                     Float standardPrice, String category, List<Insurance> supportedByInsurances) {

        this.id = id;
        this.operationName = operationName;
        this.operationCode = operationCode;
        this.description = description;
        this.standardPrice = standardPrice;
        this.category = category;
        this.supportedByInsurances = supportedByInsurances;
    }

    public Float getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(Float standardPrice) {
        this.standardPrice = standardPrice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public String getOperationCode() {
        return operationCode;
    }

    public void setOperationCode(String operationCode) {
        this.operationCode = operationCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<Insurance> getSupportedByInsurances() {
        return supportedByInsurances;
    }

    public void setSupportedByInsurances(List<Insurance> supportedByInsurances) {
        this.supportedByInsurances = supportedByInsurances;
    }
}
