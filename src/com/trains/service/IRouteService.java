/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trains.service;

import com.trains.model.Route;
import java.util.List;

/**
 *
 * @author eduardo
 */
public interface IRouteService {

    void agregarRoute(String route);

    int calcularDistanciaRuta(String ruta);

    List<Route> consultaRutasParadasMenorIgualA(String inicio, String fin, Integer paradas);

    Route consultaRutasMasCorta(String inicio, String fin);

    List<Route> consultaRutasMenoresA(String inicio, String fin, Integer distancia);

    List<Route> consultaRutasParadasIgualA(String inicio, String fin, Integer paradas);

    void cargarRutasPorDefecto();

    String mostrarRutas();

    Boolean getOk();

    String getMensaje();
}
