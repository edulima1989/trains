/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trains;

import com.trains.model.Route;
import com.trains.service.IRouteService;
import com.trains.service.impl.RouteServiceImpl;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author eduardo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IRouteService rc = new RouteServiceImpl();
        Boolean continua = true;
        String cadena;
        while (continua) {
            System.out.println("Menú:\n(1) Ingresar rutas.\n(2) Cargar rutas por defecto.\n(3) Mostrar resultados.\n(4) Mostrar Rutas.\n(5) Salir");
            cadena = sc.nextLine();
            switch (cadena) {
                case "1":
                    Boolean continua2 = true;
                    while (continua2) {
                        System.out.println("###############################################################");
                        System.out.println("Ingreso de rutas:\n(1) Ingresar ruta.\n(2) Salir\n");
                        cadena = sc.nextLine();
                        switch (cadena) {
                            case "1":
                                System.out.println("###############################################################");
                                System.out.println("Ingrese la ruta. Ejemplo: AB5");
                                cadena = sc.nextLine();
                                rc.agregarRoute(cadena);
                                if (!rc.getOk()) {
                                    System.out.println(rc.getMensaje());
                                    continue;
                                }
                                System.out.println("Ruta ingresada correctamente!\n");
                                break;
                            case "2":
                                continua2 = false;
                                break;
                            default:
                                System.out.println("Por favor ingrese una opción del menú..");
                        }
                    }
                    break;
                case "2":
                    System.out.println("###############################################################");
                    rc.cargarRutasPorDefecto();
                    break;
                case "3":
                    System.out.println("###############################################################");
                    System.out.println("1. The distance of the route A-B-C.");
                    String resp = "" + rc.calcularDistanciaRuta("A-B-C");
                    if (!rc.getOk()) {
                        resp = rc.getMensaje();
                    }
                    System.out.println("Respuesta: " + resp);
                    System.out.println("2. The distance of the route A-D.");
                    resp = "" + rc.calcularDistanciaRuta("A-D");
                    if (!rc.getOk()) {
                        resp = rc.getMensaje();
                    }
                    System.out.println("Respuesta: " + resp);
                    System.out.println("3. The distance of the route A-D-C.");
                    resp = "" + rc.calcularDistanciaRuta("A-D-C");
                    if (!rc.getOk()) {
                        resp = rc.getMensaje();
                    }
                    System.out.println("Respuesta: " + resp);
                    System.out.println("4. The distance of the route A-E-B-C-D.");
                    resp = "" + rc.calcularDistanciaRuta("A-E-B-C-D");
                    if (!rc.getOk()) {
                        resp = rc.getMensaje();
                    }
                    System.out.println("Respuesta: " + resp);
                    System.out.println("5. The distance of the route A-E-D.");
                    resp = "" + rc.calcularDistanciaRuta("A-E-D");
                    if (!rc.getOk()) {
                        resp = rc.getMensaje();
                    }
                    System.out.println("Respuesta: " + resp);

                    System.out.println("6. The number of trips starting at C and ending at C with a maximum of 3 stops.");
                    List<Route> routes = rc.consultaRutasParadasMenorIgualA("C", "C", 3);
                    System.out.println("Respuesta: " + routes.size() + " => " + routes);

                    System.out.println("7. The number of trips starting at A and ending at C with exactly 4 stops.");
                    routes = rc.consultaRutasParadasIgualA("A", "C", 4);
                    System.out.println("Respuesta: " + routes.size() + " => " + routes);

                    System.out.println("8. The length of the shortest route (in terms of distance to travel) from A to C.");
                    Route route = rc.consultaRutasMasCorta("A", "C");
                    System.out.println("Respuesta: " + rc.calcularDistanciaRuta(route.toString()) + " => " + route);

                    System.out.println("9. The length of the shortest route (in terms of distance to travel) from B to B.");
                    route = rc.consultaRutasMasCorta("B", "B");
                    System.out.println("Respuesta: " + rc.calcularDistanciaRuta(route.toString()) + " => " + route);

                    System.out.println("10. The number of different routes from C to C with a distance of less than 30.");
                    routes = rc.consultaRutasMenoresA("C", "C", 30);
                    System.out.println("Respuesta: " + routes.size() + " => " + routes);
                    System.out.println("###############################################################");
                    break;
                case "4":
                    System.out.println("###############################################################");
                    System.out.println(rc.mostrarRutas());
                    break;
                case "5":
                    continua = false;
                    break;
                default:
                    System.out.println("Por favor ingrese una opción del menú..");
            }

        }
    }

}
