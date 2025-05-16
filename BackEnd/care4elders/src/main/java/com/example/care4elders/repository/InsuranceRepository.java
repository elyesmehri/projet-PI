package com.example.care4elders.repository;
import com.example.care4elders.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Family, Long>  {


}
