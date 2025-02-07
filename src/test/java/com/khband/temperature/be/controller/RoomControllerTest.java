package com.khband.temperature.be.controller;

import com.khband.temperature.be.dto.Room;
import com.khband.temperature.be.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomControllerTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomController roomController;

    private Room room;

    @BeforeEach
    void setUp() {
        room = new Room();
        room.setId(1L);
        room.setCurrentTemperature(21.5);
        room.setTargetTemperature(21.5);
    }

    @Test
    void getAllRooms() {
        List<Room> rooms = Collections.singletonList(room);
        when(roomRepository.findAll()).thenReturn(rooms);

        List<Room> result = roomController.getAllRooms();

        verify(roomRepository, times(1)).findAll();
        assertEquals(1, result.size());
        assertEquals(room, result.getFirst());
    }

    @Test
    void getRoomById_found() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        ResponseEntity<Room> response = roomController.getRoomById(1L);

        verify(roomRepository, times(1)).findById(1L);
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(room, response.getBody());
    }

    @Test
    void getRoomById_notFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Room> response = roomController.getRoomById(1L);

        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertNull(response.getBody());
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void setCurrentTemperature_found() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        ResponseEntity<Room> response = roomController.setCurrentTemperature(1L, 23.0);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(23.0, response.getBody().getCurrentTemperature());
        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void setCurrentTemperature_notFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Room> response = roomController.setCurrentTemperature(1L, 23.0);

        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertNull(response.getBody());
        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    void setTargetTemperature_found() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        ResponseEntity<Room> response = roomController.setTargetTemperature(1L, 23.0);

        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        assertEquals(23.0, response.getBody().getTargetTemperature());
        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void setTargetTemperature_notFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Room> response = roomController.setTargetTemperature(1L, 23.0);

        assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
        assertNull(response.getBody());
        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, never()).save(any(Room.class));
    }
}

