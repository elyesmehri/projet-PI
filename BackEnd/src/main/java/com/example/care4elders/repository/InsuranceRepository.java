package com.example.care4elders.repository;

import com.example.care4elders.model.Family;
import com.example.care4elders.model.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InsuranceRepository extends JpaRepository<Insurance, Long>  {

    Optional<Insurance> findByProviderName(String providerName);
    Optional<Insurance> findByPolicyNumber(String policyNumber);

}
