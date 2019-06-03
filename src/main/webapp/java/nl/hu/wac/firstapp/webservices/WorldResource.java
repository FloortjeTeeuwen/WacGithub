package nl.hu.wac.firstapp.webservices;

import nl.hu.wac.firstapp.domain.Country;
import nl.hu.wac.firstapp.service.ServiceProvider;
import nl.hu.wac.firstapp.service.WorldService;

import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/countries")
public class WorldResource {
    @GET
    @Produces("application/json")
    public String getWorldservice() {
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jsonCountriesBuilder = Json.createArrayBuilder();

        for (Country c : service.getAllCountries()) {
            JsonObjectBuilder jsonCountry = convertCountry(c);

            jsonCountriesBuilder.add(jsonCountry);
        }
        JsonArray array = jsonCountriesBuilder.build();

        return array.toString();
    }

    @GET
    @Path("{code}")
    @Produces("application/json")
    public String getWorldserviceInfo(@PathParam("code") String code) {
        WorldService service = ServiceProvider.getWorldService();
        Country country = (Country) service.getCountryByCode(code);

        JsonObjectBuilder countryBuilder = convertCountry(country);

        return countryBuilder.build().toString();
    }

    @GET
    @Path("/largestsurface")
    @Produces("application/json")
    public String getWorldserviceInfo1() {
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jsonCountriesBuilder = Json.createArrayBuilder();

        for (Country c : service.get10LargestSurfaces()) {
            JsonObjectBuilder jsonCountry = convertCountry(c);

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
    @Produces("application/json")
    public String getWorldserviceInfo2() {
        WorldService service = ServiceProvider.getWorldService();
        JsonArrayBuilder jsonCountriesBuilder = Json.createArrayBuilder();

        for (Country c : service.get10LargestPopulations()) {
            JsonObjectBuilder jsonCountry = convertCountry(c);

            jsonCountriesBuilder.add(jsonCountry);
        }

        JsonArray array = jsonCountriesBuilder.build();

        return array.toString();
    }

    @DELETE
    @Path("/{code}")
    @Produces("application/json")
    public Response deleteCountry(@PathParam("code") String code) {
        WorldService service = ServiceProvider.getWorldService();

        service.deleteCountryByCode(code);

        return Response.ok().build();
    }


    private JsonObjectBuilder convertCountry(Country country) {
        JsonObjectBuilder countryBuilder = Json.createObjectBuilder();
        countryBuilder.add("code", country.getCode());
        countryBuilder.add("name", country.getName());
        countryBuilder.add("capital", country.getCapital());
        countryBuilder.add("surface", country.getSurface());
        countryBuilder.add("government", country.getGovernment());
        countryBuilder.add("iso3", country.getIso3());
        countryBuilder.add("continent", country.getContinent());
        countryBuilder.add("region", country.getRegion());
        countryBuilder.add("population", country.getPopulation());
        countryBuilder.add("lat", country.getLatitude());
        countryBuilder.add("lng", country.getLongitude());

        return countryBuilder;
    }

    @PUT
    @Path("/{code}")
    @Produces("application/json")
    public Response updateCountry(@PathParam("code") String code,
                                  @FormParam("name") String name, @FormParam("continent") String con,
                                  @FormParam("region") String reg, @FormParam("surface") double sur,
                                  @FormParam("population") int pop, @FormParam("government") String gov) {
        WorldService service = ServiceProvider.getWorldService();
        Country newCountry = new Country(code, name, con, reg, sur, pop, gov);
        if (service.update(newCountry, code)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/newcountry")
    @Produces("application/json")
    public Response saveCountry(@FormParam("Landcode") String code,
                                @FormParam("Landnaam") String name, @FormParam("Continent") String con,
                                @FormParam("Regio") String reg, @FormParam("Oppervlakte") double sur,
                                @FormParam("Populatie") int pop, @FormParam("Regering") String gov) {
        WorldService service = ServiceProvider.getWorldService();
        Country newCountry = new Country(code, name, con, reg, sur, pop, gov);
        if (service.save(newCountry)) {
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

}}


