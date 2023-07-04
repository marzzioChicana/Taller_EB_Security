package com.upc.taller_eb_sec.repository;

import com.upc.taller_eb_sec.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByDriverId(Long driverId);
}
