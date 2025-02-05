package com.khband.temperature.be.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Building {

    private @Id @GeneratedValue Long id;
    private String name;
    private @OneToMany List<Room> rooms;

    public Building() {}

    public Building(String name) {
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Room> getRooms() {
        return this.rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void addRoom(Room room) {
        if (this.rooms == null) {
            this.rooms = new ArrayList<>();
        }
        this.rooms.add(room);
    }

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rooms=" + rooms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Building building = (Building) o;
        return Objects.equals(id, building.id) && Objects.equals(name, building.name) && Objects.equals(rooms, building.rooms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rooms);
    }
}
