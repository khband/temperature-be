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

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(RoomRepository roomRepository, BuildingRepository buildingRepository) {

        return args -> {
            Room room1 = roomRepository.save(new Room("Office"));
            log.info("Preloaded {}", room1);
            Room room2 = roomRepository.save(new Room("Lunch Room"));
            log.info("Preloaded {}", room2);
            Building building1 = buildingRepository.save(new Building("Drottninggatan 24"));
            building1.addRoom(room1);
            buildingRepository.save(building1);
            log.info("Preloaded {}", building1);
            Building building2 = buildingRepository.save(new Building("Ölandsgatan 6"));
            building2.addRoom(room2);
            buildingRepository.save(building2);
            log.info("Preloaded {}", building2);
        };
    }
}
