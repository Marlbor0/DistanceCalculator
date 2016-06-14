package controllers;

import entity.City;
import services.CityService;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by Александр on 07.06.2016.
 */
@Path("city")
public class CityController {
    @Inject
    private CityService cityService;
    @Path("/all")
    @GET
    @Produces("application/json")
    public Response getAll(){
        List<City> cities = cityService.getAll();
        StringBuilder result = new StringBuilder();
        if(cities.size()>0){
            result.append("{\"cities\" : [ \n");
            for (City city : cities){
                result = result.append("{\"city\": {\"id\" : \"").append(city.getId())
                               .append("\" , \"name\" : \"").append(city.getName()).append("\" } }, \n ");
            }
            result.append("] \n }");
            return Response.ok(result.toString(), MediaType.APPLICATION_JSON).build();
        }
        return Response.noContent().build();
    }
}
