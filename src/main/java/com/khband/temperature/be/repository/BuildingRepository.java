package com.khband.temperature.be.repository;

import com.khband.temperature.be.dto.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {}