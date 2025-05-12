package com.example.care4elders.repository;

import com.example.care4elders.model.Posology;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PosologyRepository extends JpaRepository<Posology, Long> {

    List<Posology> findByPillularId(Long pillularId);
}
