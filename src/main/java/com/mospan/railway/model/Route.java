package com.mospan.railway.model;


import java.time.Duration;
import java.time.LocalTime;

public class Route extends Entity{

    private double price;

    private Station startStation;
    private Station endStation;

    private LocalTime departTime;
    private LocalTime time;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Station getStartStation() {
        return startStation;
    }

    public void setStartStation(Station startStation) {
        this.startStation = startStation;
    }

    public Station getEndStation() {
        return endStation;
    }

    public void setEndStation(Station endStation) {
        this.endStation = endStation;
    }

    public LocalTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalTime departTime) {
        this.departTime = departTime;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalTime getArrivalTime() {
        return departTime.plus(Duration.between(LocalTime.MIDNIGHT, time));
    }
}
