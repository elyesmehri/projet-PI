package com.example.care4elders.repository;

import com.example.care4elders.model.Family;
import com.fasterxml.jackson.databind.introspect.AnnotationCollector;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface FamilyRepository extends JpaRepository<Family, Long> {

    Optional<Family> findByFamilyname(String familyname);

    Optional<Family> findByRelative (String relative);

    Optional<Family> findByPassword(String password);

    Optional <Family> getFamilyById(Long id);

}
