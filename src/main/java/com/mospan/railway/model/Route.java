package com.mospan.railway.model;


import java.time.LocalTime;

public class Route extends Entity{

    private long startStationId;
    private long endStationId;

    private LocalTime departTime;
    private LocalTime arrivalTime;

    public long getStartStationId() {
        return startStationId;
    }

    public void setStartStationId(long startStationId) {
        this.startStationId = startStationId;
    }

    public long getEndStationId() {
        return endStationId;
    }

    public void setEndStationId(long endStationId) {
        this.endStationId = endStationId;
    }

    public LocalTime getDepartTime() {
        return departTime;
    }

    public void setDepartTime(LocalTime departTime) {
        this.departTime = departTime;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }
}
