package com.example.care4elders.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Setter
@Entity
public class Insurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insurance_provider_name", nullable = false)
    private String providerName;

    @Column(name = "policy_number", unique = true, nullable = false)
    private String policyNumber;

    @Column(name = "coverage_details", length = 1000)
    private String coverageDetails;

    @Column(name = "ceo", length = 100)
    private String CEO;

    @JsonIgnore
    @OneToMany(mappedBy = "insurance", cascade = CascadeType.ALL)
    private List<Pillular> linkedPrescriptions = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "insurance_supported_medication", // Junction table name
            joinColumns = @JoinColumn(name = "insurance_id"), // FK to Insurance
            inverseJoinColumns = @JoinColumn(name = "medication_id") // FK to Medication
    )
    private List<Medication> supportedMedications = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "insurance_supported_operation", // Junction table name
            joinColumns = @JoinColumn(name = "insurance_id"), // FK to Insurance
            inverseJoinColumns = @JoinColumn(name = "operation_id") // FK to Operation
    )
    private List<Operation> supportedOperations = new ArrayList<>();

    public Insurance(Long id, String CEO, String providerName, String policyNumber, String coverageDetails, List<Pillular> linkedPrescriptions, List<Medication> supportedMedications, List<Operation> supportedOperations) {
        this.id = id;
        this.CEO = CEO;
        this.providerName = providerName;
        this.policyNumber = policyNumber;
        this.coverageDetails = coverageDetails;
        this.linkedPrescriptions = linkedPrescriptions;
        this.supportedMedications = supportedMedications;
        this.supportedOperations = supportedOperations;
    }

    public Insurance() {

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getCoverageDetails() {
        return coverageDetails;
    }

    public void setCoverageDetails(String coverageDetails) {
        this.coverageDetails = coverageDetails;
    }

    public List<Pillular> getLinkedPrescriptions() {
        return linkedPrescriptions;
    }

    public void setLinkedPrescriptions(List<Pillular> linkedPrescriptions) {
        this.linkedPrescriptions = linkedPrescriptions;
    }

    public List<Medication> getSupportedMedications() {
        return supportedMedications;
    }

    public void setSupportedMedications(List<Medication> supportedMedications) {
        this.supportedMedications = supportedMedications;
    }

    public List<Operation> getSupportedOperations() {
        return supportedOperations;
    }

    public void setSupportedOperations(List<Operation> supportedOperations) {
        this.supportedOperations = supportedOperations;
    }

    public String getCEO() {
        return CEO;
    }

    public void setCEO(String CEO) {
        this.CEO = CEO;
    }
}


