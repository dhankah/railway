package com.mospan.railway.model;


public class Train extends Entity{



    private Route route;

    private int places = 50;


    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }
}
