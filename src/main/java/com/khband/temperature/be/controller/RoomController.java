package com.khband.temperature.be.controller;

import com.khband.temperature.be.dto.Room;
import com.khband.temperature.be.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepository;

    RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomRepository.findById(id);
        return room.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/targetTemperature")
    public ResponseEntity<Room> updateTargetTemperature(@PathVariable Long id, @RequestParam Double targetTemperature) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setTargetTemperature(targetTemperature);
                    roomRepository.save(room);
                    return ResponseEntity.ok(room);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
