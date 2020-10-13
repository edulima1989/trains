/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trains.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eduardo
 */
public class Route {

    private List<Track> route;

    /**
     * Metodo constructor
     */
    public Route() {
        route = new ArrayList<>();
    }

    @Override
    public String toString() {
        String ruta = route.get(0).getInicio();
        for (Track t : route) {
            ruta += "-" + t.getFin();
        }
        return ruta;
    }

    /**
     * @return the route
     */
    public List<Track> getRoute() {
        return route;
    }

    /**
     * @param route the route to set
     */
    public void setRoute(List<Track> route) {
        this.route = route;
    }

}
