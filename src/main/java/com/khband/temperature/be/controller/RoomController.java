package com.khband.temperature.be.controller;

import com.khband.temperature.be.dto.Room;
import com.khband.temperature.be.repository.RoomRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepository;
    private final ExecutorService executorService;
    private final Map<Long, Future<?>> runningTasks;

    RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
        this.executorService = Executors.newCachedThreadPool();
        this.runningTasks = new ConcurrentHashMap<>();
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

    @PatchMapping("/{id}/currentTemperature")
    public ResponseEntity<Room> setCurrentTemperature(@PathVariable Long id, @RequestParam Double currentTemperature) {
        return roomRepository.findById(id)
                .map(room -> {
                    room.setCurrentTemperature(currentTemperature);
                    roomRepository.save(room);
                    return ResponseEntity.ok(room);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}/targetTemperature")
    public ResponseEntity<Room> setTargetTemperature(@PathVariable Long id, @RequestParam Double targetTemperature) {
        return roomRepository.findById(id)
                .map(room -> {
                    if (room.getTargetTemperature() != null && room.getTargetTemperature().equals(targetTemperature)) {
                        return ResponseEntity.ok(room);
                    }

                    Future<?> existingTask = runningTasks.remove(id);
                    if (existingTask != null) {
                        existingTask.cancel(true);
                    }

                    room.setTargetTemperature(targetTemperature);
                    roomRepository.save(room);

                    Future<?> newTask = executorService.submit(() -> adjustTemperatureGradually(room));
                    runningTasks.put(id, newTask);

                    return ResponseEntity.ok(room);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Async
    public void adjustTemperatureGradually(Room room) {
        try {
            while (!Objects.equals(roundToOneDecimal(room.getCurrentTemperature()), roundToOneDecimal(room.getTargetTemperature()))) {
                Thread.sleep(1000);
                if (room.getTargetTemperature() > room.getCurrentTemperature()) {
                    room.setCurrentTemperature(roundToOneDecimal(room.getCurrentTemperature() + 0.1));
                }
                if (room.getTargetTemperature() < room.getCurrentTemperature()) {
                    room.setCurrentTemperature(roundToOneDecimal(room.getCurrentTemperature() - 0.1));
                }
                roomRepository.save(room);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private static double roundToOneDecimal (double value) {
        int scale = (int) Math.pow(10, 1);
        return (double) Math.round(value * scale) / scale;
    }
}
