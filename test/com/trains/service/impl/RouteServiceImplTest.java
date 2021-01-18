/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trains.service.impl;

import com.trains.model.Route;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Edu
 */
public class RouteServiceImplTest {

    private static RouteServiceImpl instance;

    public RouteServiceImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
        instance = new RouteServiceImpl();
        instance.cargarRutasPorDefecto();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void distance_of_the_route_abc() {
        int result = instance.calcularDistanciaRuta("A-B-C");
        assertEquals(9, result);
    }

    @Test
    public void distance_of_the_route_ad() {
        int result = instance.calcularDistanciaRuta("A-D");
        assertEquals(5, result);
    }

    @Test
    public void distance_of_the_route_adc() {
        int result = instance.calcularDistanciaRuta("A-D-C");
        assertEquals(13, result);
    }

    @Test
    public void distance_of_the_route_aebcd() {
        int result = instance.calcularDistanciaRuta("A-E-B-C-D");
        assertEquals(22, result);
    }

    @Test
    public void distance_of_the_route_aed() {
        int result = instance.calcularDistanciaRuta("A-E-D");
        assertEquals("Ruta no existe", instance.getMensaje());
    }

    @Test
    public void number_of_trips_starting_at_c_and_ending_at_c_with_a_maximum_of_3_stops() {
        List<Route> routes = instance.consultaRutasParadasMenorIgualA("C", "C", 3);
        assertEquals(2, routes.size());
    }

    @Test
    public void number_of_trips_starting_at_a_and_ending_at_c_with_exactly_4_stops() {
        List<Route> routes = instance.consultaRutasParadasIgualA("A", "C", 4);
        assertEquals(3, routes.size());
    }

    @Test
    public void length_of_the_shortest_route_from_a_to_c() {
        Route route = instance.consultaRutasMasCorta("A", "C");
        int length = instance.calcularDistanciaRuta(route.toString());
        assertEquals(9, length);
    }
    
    @Test
    public void length_of_the_shortest_route_from_b_to_b() {
        Route route = instance.consultaRutasMasCorta("B", "B");
        int length = instance.calcularDistanciaRuta(route.toString());
        assertEquals(9, length);
    }
    
    @Test
    public void number_of_different_routes_from_c_to_c_with_a_distance_of_less_than_30() {
        List<Route> routes = instance.consultaRutasMenoresA("C", "C", 30);
        assertEquals(7, routes.size());
    }
}
