/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trains.model;

/**
 *
 * @author eduardo
 */
public class Track {

    private String inicio;
    private String fin;
    private Integer distancia;

    /**
     * Metodo constructor
     *
     * @param inicio
     * @param fin
     * @param distancia
     */
    public Track(String inicio, String fin, Integer distancia) {
        this.inicio = inicio;
        this.fin = fin;
        this.distancia = distancia;
    }

    @Override
    public String toString() {
        return inicio + fin + distancia;
    }

    /**
     * @return the inicio
     */
    public String getInicio() {
        return inicio;
    }

    /**
     * @param inicio the inicio to set
     */
    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    /**
     * @return the fin
     */
    public String getFin() {
        return fin;
    }

    /**
     * @param fin the fin to set
     */
    public void setFin(String fin) {
        this.fin = fin;
    }

    /**
     * @return the distancia
     */
    public Integer getDistancia() {
        return distancia;
    }

    /**
     * @param distancia the distancia to set
     */
    public void setDistancia(Integer distancia) {
        this.distancia = distancia;
    }

}
