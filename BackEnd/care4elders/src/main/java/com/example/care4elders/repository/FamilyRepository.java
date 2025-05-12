package com.example.care4elders.repository;

import com.example.care4elders.model.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Long> {

    Optional<Family> findByFamilyname(String familyname);

    Optional<Family> findByRelative (String relative);

    Optional<Family> findByPassword(String password);
}
