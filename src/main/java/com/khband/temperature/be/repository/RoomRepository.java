package com.khband.temperature.be.repository;

import com.khband.temperature.be.dto.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {}