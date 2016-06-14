package controllers;

import entity.City;
import services.CityService;
import services.DistanceService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Александр on 09.06.2016.
 */
@Path("calculate")
public class Calculator {
    @Inject
    private CityService cityService;
    @Inject
    private DistanceService distanceService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response calculate(@QueryParam("type") String type, @QueryParam("cityfrom") String name1, @QueryParam("cityto") String name2) {
        String result = "";
        City cityFrom = cityService.getCityByName(name1);
        City cityTo = cityService.getCityByName(name2);
        Double res = null;
        if (cityFrom != null && cityTo != null) {
            if (type.equalsIgnoreCase("Crowflight")) {
                res = getDistanceByCoordinates(cityFrom.getLatitude(), cityFrom.getLongitude(), cityTo.getLatitude(), cityTo.getLongitude());
                result = "{Crowflight:" + res + "}";
                return Response.ok(result, MediaType.APPLICATION_JSON_TYPE).build();
            } else if (type.equalsIgnoreCase("Distance Matrix")) {
                res = distanceService.getDistance(cityFrom, cityTo);
                result = "{DistanceMatrix : " + String.valueOf(res) + "}";
                return Response.ok(result, MediaType.APPLICATION_JSON_TYPE).build();
            } else if (type.equalsIgnoreCase("all")) {
                res = getDistanceByCoordinates(cityFrom.getLatitude(), cityFrom.getLongitude(), cityTo.getLatitude(), cityTo.getLongitude());
                Double res2 = distanceService.getDistance(cityFrom, cityTo);
                result = "{Crowflight : " + res + "} \n{DistanceMatrix : " + res2 + "}";
                return Response.ok(result, MediaType.APPLICATION_JSON_TYPE).build();
            }
        }
        return Response.noContent().build();
    }

    /**
     * Calculate crow flight distance between two points
     */
    public double getDistanceByCoordinates(double startLat, double startLong, double endLat, double endLong) {
        /**
         * Earth radius in kilometers
         */
        final int EARTH_RADIUS = 6371;
        double dLat = Math.toRadians(endLat - startLat);
        double dLon = Math.toRadians(endLong - startLong);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return EARTH_RADIUS * c;
    }

}
