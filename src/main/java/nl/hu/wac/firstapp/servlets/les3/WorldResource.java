package nl.hu.wac.firstapp.servlets.les3;


import nl.hu.wac.firstapp.domain.Country;
import nl.hu.wac.firstapp.service.ServiceProvider;
import nl.hu.wac.firstapp.service.WorldService;

import javax.json.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("/countries")
public class WorldResource {
    @GET
    @Produces("application/json")
    public String getWorldservice(){
       WorldService service = ServiceProvider.getWorldService();
       JsonArrayBuilder jsonCountriesBuilder = Json.createArrayBuilder();

       for(Country c : service.getAllCountries()){
           JsonObject jsonCountry = buildJsonCountryFrom(c);

           jsonCountriesBuilder.add(jsonCountry);
        }

       JsonArray array = jsonCountriesBuilder.build();
       return array.toString();
    }


    private JsonObject buildJsonCountryFrom(Country country) {
        JsonObjectBuilder countryBuilder = Json.createObjectBuilder();

        countryBuilder.add("country", country.getName());
        countryBuilder.add("code", country.getCode());

        return countryBuilder.build();
    }

    @GET
    @Path("{code}")
    @Produces ("application/json")
    public String getWorldserviceInfo(@PathParam("code") String code){
        WorldService service = ServiceProvider.getWorldService();
        Country country = service.getCountryByCode(code);

        JsonObjectBuilder countryBuilder = Json.createObjectBuilder();
        countryBuilder.add("code", country.getCode());
        countryBuilder.add("name", country.getName());
        countryBuilder.add("Capital", country.getCapital());
        countryBuilder.add("Surface", country.getSurface());
        countryBuilder.add("Goverment", country.getGovernment());
        countryBuilder.add("Iso3", country.getIso3());
        countryBuilder.add("continent", country.getContinent());
        countryBuilder.add("region", country.getRegion());
        countryBuilder.add("population", country.getPopulation());
        countryBuilder.add("lat", country.getLatitude());
        countryBuilder.add("lng", country.getLongitude());

        return countryBuilder.build().toString();
    }
    @GET
    @Path("/largestsurface")
    @Produces ("application/json")
    public String getWorldserviceInfo1(){
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jsonCountriesBuilder = Json.createArrayBuilder();

        for(Country c : service.get10LargestSurfaces()){
            JsonObject jsonCountry = buildJsonCountryFrom(c);

            jsonCountriesBuilder.add(jsonCountry);
        }

        JsonArray array = jsonCountriesBuilder.build();
        return array.toString();
    }


    private JsonObject buildJsonCountryFrom1(Country country) {
        JsonObjectBuilder countryBuilder = Json.createObjectBuilder();

        countryBuilder.add("country", country.getName());
        countryBuilder.add("code", country.getCode());

        return countryBuilder.build();

    }
    @GET
    @Path("/largestpopulations")
    @Produces ("application/json")
    public String getWorldserviceInfo2(){
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jsonCountriesBuilder = Json.createArrayBuilder();

        for(Country c : service.get10LargestPopulations()){
            JsonObject jsonCountry = buildJsonCountryFrom(c);

            jsonCountriesBuilder.add(jsonCountry);
        }

        JsonArray array = jsonCountriesBuilder.build();
        return array.toString();
    }


    private JsonObject buildJsonCountryFrom2(Country country) {
        JsonObjectBuilder countryBuilder = Json.createObjectBuilder();

        countryBuilder.add("country", country.getName());
        countryBuilder.add("code", country.getCode());

        return countryBuilder.build();


}
}
