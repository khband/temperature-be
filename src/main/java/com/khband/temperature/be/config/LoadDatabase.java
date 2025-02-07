package com.khband.temperature.be.config;

import com.khband.temperature.be.dto.Building;
import com.khband.temperature.be.dto.Room;
import com.khband.temperature.be.repository.BuildingRepository;
import com.khband.temperature.be.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RoomRepository roomRepository, BuildingRepository buildingRepository) {

        return args -> {
            Room room1 = saveRoom(roomRepository, "Office", 21.5);
            Room room2 = saveRoom(roomRepository, "Lunch Room", 22.0);
            saveBuilding(buildingRepository, "Drottninggatan 24", Collections.singletonList(room1));
            saveBuilding(buildingRepository, "Ölandsgatan 6", Collections.singletonList(room2));
        };
    }

    private Room saveRoom(RoomRepository roomRepository, String name, Double currentTemperature) {
        Room room = roomRepository.save(new Room(name));
        room.setCurrentTemperature(currentTemperature);
        roomRepository.save(room);
        log.info("Preloaded {}", room);
        return room;
    }

    private void saveBuilding(BuildingRepository buildingRepository, String name, List<Room> rooms) {
        Building building1 = buildingRepository.save(new Building(name));
        for (Room room : rooms) {
            building1.addRoom(room);
        }
        buildingRepository.save(building1);
        log.info("Preloaded {}", building1);
    }
}
