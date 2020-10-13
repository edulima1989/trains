/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trains.service.impl;

import com.trains.model.Route;
import com.trains.model.Track;
import com.trains.service.IRouteService;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author eduardo
 */
public class RouteServiceImpl implements IRouteService {

    private List<Track> tracks;
    private final String matches = "[A-D]{2}+[\\d]+";

    private Boolean ok;
    private String mensaje;

    /**
     * Metodo constructor
     */
    public RouteServiceImpl() {
        this.tracks = new ArrayList<>();
        this.ok = true;
        this.mensaje = "";
    }

    /**
     * Permite agregar una ruta
     *
     * @param route
     */
    @Override
    public void agregarRoute(String route) {
        setOk(true);
        route = route.toUpperCase();
        if (!route.matches(matches)) {
            setError("Ruta ingresada no es valida " + route);
            return;
        }
        String inicio = route.substring(0, 1);
        String fin = route.substring(1, 2);

        if (inicio.equals(fin)) {
            setError("La ciudad de inicio no puede ser la misma que el destino");
            return;
        }

        for (Track r : tracks) {
            if (r.getInicio().equals(inicio) && r.getFin().equals(fin)) {
                setError("La ruta ingresada ya existe");
                return;
            }
        }

        Integer distancia = Integer.parseInt(route.substring(2));
        tracks.add(new Track(inicio, fin, distancia));

    }

    /**
     * Calcula la distancia del recorrido de una ruta
     *
     * @param ruta
     * @return
     */
    @Override
    public int calcularDistanciaRuta(String ruta) {
        setOk(true);
        int distancia = 0;
        ruta = ruta.toUpperCase();
        String[] ciudades = ruta.split("-");
        Boolean existeRuta;
        for (int i = 0; i < ciudades.length - 1; i++) {
            existeRuta = false;
            for (Track r : tracks) {
                if (ciudades[i].equals(r.getInicio()) && ciudades[i + 1].equals(r.getFin())) {
                    distancia += r.getDistancia();
                    existeRuta = true;
                    break;
                }
            }
            if (!existeRuta) {
                setError("Ruta no existe");
                return 0;
            }
        }
        return distancia;
    }

    /**
     * Consulta las diferentes rutas de [inicio] a [fin] donde el número de
     * paradas sea menor o igual a [paradas]
     *
     * @param inicio
     * @param fin
     * @param paradas
     * @return
     */
    @Override
    public List<Route> consultaRutasParadasMenorIgualA(String inicio, String fin, Integer paradas) {
        List<Route> routes = new ArrayList<>();
        Route route;
        for (Track r : tracks) {
            if (r.getInicio().equals(inicio)) {
                route = new Route();
                route.getRoute().add(r);
                routes.addAll(consultaRutasParadasMenorIgualA(fin, route, paradas));
            }
        }

        return routes;
    }

    /**
     * Metodo recursivo para consultar las diferentes rutas de [inicio] a [fin]
     * donde el número de paradas sea menor o igual a [paradas]
     *
     * @param fin
     * @param ruta
     * @param paradas
     * @return
     */
    private List<Route> consultaRutasParadasMenorIgualA(String fin, Route ruta, Integer paradas) {
        List<Route> rutasOut = new ArrayList<>();
        if (ruta.getRoute().size() == paradas) {
            if (!fin.equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
                return null;
            }
            rutasOut.add(ruta);
            return rutasOut;
        }
        if (ruta.getRoute().size() > 1 && fin.equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
            rutasOut.add(ruta);
            return rutasOut;
        }

        Route aux;
        List<Route> rutas2;
        for (Track r : tracks) {
            if (r.getInicio().equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
                aux = new Route();
                aux.getRoute().addAll(ruta.getRoute());
                aux.getRoute().add(r);

                rutas2 = consultaRutasParadasMenorIgualA(fin, aux, paradas);
                if (rutas2 != null) {
                    rutasOut.addAll(rutas2);
                }
            }
        }
        return rutasOut;
    }

    /**
     * Consulta la ruta más corta de [inicio] a [fin]
     *
     * @param inicio
     * @param fin
     * @return
     */
    @Override
    public Route consultaRutasMasCorta(String inicio, String fin) {
        List<Route> routes = new ArrayList<>();
        Route route;
        for (Track r : tracks) {
            if (r.getInicio().equals(inicio)) {
                route = new Route();
                route.getRoute().add(r);
                routes.addAll(consultaRutasMasCorta(fin, route));
            }
        }
        Route aux = null;
        for (Route r : routes) {
            if (aux == null) {
                aux = r;
                continue;
            }
            if (calcularDistanciaRuta(r.toString()) < calcularDistanciaRuta(aux.toString())) {
                aux = r;
            }
        }

        return aux;
    }

    /**
     * Metodo recursivo para consultar la ruta más corta de [inicio] a [fin]
     *
     * @param fin
     * @param ruta
     * @return
     */
    private List<Route> consultaRutasMasCorta(String fin, Route ruta) {
        List<Route> rutasOut = new ArrayList<>();
        if (fin.equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
            rutasOut.add(ruta);
            return rutasOut;
        }
        if (ruta.getRoute().size() > 1 && ruta.getRoute().get(ruta.getRoute().size() - 2).getInicio().equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
            return null;
        }

        Route aux;
        List<Route> rutas2;
        for (Track r : tracks) {
            if (r.getInicio().equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
                aux = new Route();
                aux.getRoute().addAll(ruta.getRoute());
                aux.getRoute().add(r);
                rutas2 = consultaRutasMasCorta(fin, aux);
                if (rutas2 != null) {
                    rutasOut.addAll(rutas2);
                }
            }
        }
        return rutasOut;
    }

    /**
     * Consulta las rutas desde [inicio] a [fin] donde la distancia recorrida
     * sea menor a [distancia]
     *
     * @param inicio
     * @param fin
     * @param distancia
     * @return
     */
    @Override
    public List<Route> consultaRutasMenoresA(String inicio, String fin, Integer distancia) {
        List<Route> routes = new ArrayList<>();
        Route route;
        for (Track r : tracks) {
            if (r.getInicio().equals(inicio)) {
                route = new Route();
                route.getRoute().add(r);
                routes.addAll(consultaRutasMenoresA(fin, route, distancia));
            }
        }
        Route aux = null;
        for (Route r : routes) {
            if (aux == null) {
                aux = r;
                continue;
            }
            if (calcularDistanciaRuta(r.toString()) < calcularDistanciaRuta(aux.toString())) {
                aux = r;
            }
        }

        return routes;
    }

    /**
     * Metodo recursivo para consultar las rutas desde [inicio] a [fin] donde la
     * distancia recorrida sea menor a [distancia]
     *
     * @param fin
     * @param ruta
     * @param distancia
     * @return
     */
    private List<Route> consultaRutasMenoresA(String fin, Route ruta, Integer distancia) {
        List<Route> rutasOut = new ArrayList<>();
        if (fin.equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
            rutasOut.add(ruta);
        }
        if (calcularDistanciaRuta(ruta.toString()) >= distancia) {
            return null;
        }

        Route aux;
        List<Route> rutas2;
        for (Track r : tracks) {
            if (r.getInicio().equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
                aux = new Route();
                aux.getRoute().addAll(ruta.getRoute());
                aux.getRoute().add(r);
                rutas2 = consultaRutasMenoresA(fin, aux, distancia);
                if (rutas2 != null) {
                    rutasOut.addAll(rutas2);
                }
            }
        }
        return rutasOut;
    }

    /**
     * Consulta las rutas desde [inicio] a [fin] donde el número de paradas sea
     * igual a [paradas]
     *
     * @param inicio
     * @param fin
     * @param paradas
     * @return
     */
    @Override
    public List<Route> consultaRutasParadasIgualA(String inicio, String fin, Integer paradas) {
        List<Route> routes = new ArrayList<>();
        Route route;
        for (Track r : tracks) {
            if (r.getInicio().equals(inicio)) {
                route = new Route();
                route.getRoute().add(r);
                routes.addAll(consultaRutasParadasIgualA(fin, route, paradas));
            }
        }

        return routes;
    }

    /**
     * Metodo recursivo para consultar las rutas desde [inicio] a [fin] donde el
     * número de paradas sea igual a [paradas]
     *
     * @param fin
     * @param ruta
     * @param paradas
     * @return
     */
    private List<Route> consultaRutasParadasIgualA(String fin, Route ruta, Integer paradas) {
        List<Route> rutasOut = new ArrayList<>();
        if (ruta.getRoute().size() > paradas) {
            return null;
        }
        if (ruta.getRoute().size() == paradas && fin.equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
            rutasOut.add(ruta);
            return rutasOut;
        }

        Route aux;
        List<Route> rutas2;
        for (Track r : tracks) {
            if (r.getInicio().equals(ruta.getRoute().get(ruta.getRoute().size() - 1).getFin())) {
                aux = new Route();
                aux.getRoute().addAll(ruta.getRoute());
                aux.getRoute().add(r);

                rutas2 = consultaRutasParadasIgualA(fin, aux, paradas);
                if (rutas2 != null) {
                    rutasOut.addAll(rutas2);
                }
            }
        }
        return rutasOut;
    }

    /**
     * Carga las rutas para Test [AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7]
     */
    @Override
    public void cargarRutasPorDefecto() {
        tracks.clear();
        tracks.add(new Track("A", "B", 5));
        tracks.add(new Track("B", "C", 4));
        tracks.add(new Track("C", "D", 8));
        tracks.add(new Track("D", "C", 8));
        tracks.add(new Track("D", "E", 6));
        tracks.add(new Track("A", "D", 5));
        tracks.add(new Track("C", "E", 2));
        tracks.add(new Track("E", "B", 3));
        tracks.add(new Track("A", "E", 7));
    }

    /**
     * Retorna todas las rutas ingresadas
     *
     * @return
     */
    @Override
    public String mostrarRutas() {
        return tracks.toString();
    }

    /**
     * Fija un error
     *
     * @param mensaje
     */
    private void setError(String mensaje) {
        this.setOk(Boolean.FALSE);
        this.setMensaje(mensaje);
    }

    /**
     * @return the ok
     */
    public Boolean getOk() {
        return ok;
    }

    /**
     * @param ok the ok to set
     */
    public void setOk(Boolean ok) {
        this.ok = ok;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
