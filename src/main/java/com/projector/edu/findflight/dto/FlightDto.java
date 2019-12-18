package com.projector.edu.findflight.dto;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public class FlightDto {

    private String originSpot;

    private String destinationSpot;

    private LocalDate departureDate;

    private LocalTime departureTime;

    private LocalDate arrivalDate;

    private LocalTime arrivalTime;

    private Duration duration;

    private int price;

    public String getOriginSpot() {
        return originSpot;
    }

    public void setOriginSpot(String originSpot) {
        this.originSpot = originSpot;
    }

    public String getDestinationSpot() {
        return destinationSpot;
    }

    public void setDestinationSpot(String destinationSpot) {
        this.destinationSpot = destinationSpot;
    }

    public LocalDate getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(LocalDate departureDate) {
        this.departureDate = departureDate;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
