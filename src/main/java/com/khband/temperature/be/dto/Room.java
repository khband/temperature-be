package com.khband.temperature.be.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Room {

    private @Id @GeneratedValue Long id;
    private String name;
    private Double currentTemperature;
    private Double targetTemperature;

    public Room() {}

    public Room(String name) {
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

    public Double getCurrentTemperature() {
        return this.currentTemperature;
    }

    public void setCurrentTemperature(Double currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public Double getTargetTemperature() {
        return this.targetTemperature;
    }

    public void setTargetTemperature(Double targetTemperature) {
        this.targetTemperature = targetTemperature;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", currentTemperature=" + currentTemperature +
                ", targetTemperature=" + targetTemperature +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(id, room.id) && Objects.equals(name, room.name) && Objects.equals(currentTemperature, room.currentTemperature) && Objects.equals(targetTemperature, room.targetTemperature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, currentTemperature, targetTemperature);
    }
}
