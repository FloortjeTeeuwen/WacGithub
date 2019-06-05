package nl.hu.wac.firstapp.webservices;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import nl.hu.wac.firstapp.domain.Country;
import nl.hu.wac.firstapp.domain.UserDao;
import nl.hu.wac.firstapp.domain.UserPostgresDaoImpl;
import nl.hu.wac.firstapp.service.ServiceProvider;
import nl.hu.wac.firstapp.service.WorldService;

import javax.annotation.Priority;
import javax.annotation.security.RolesAllowed;
import javax.json.*;
import javax.ws.rs.*;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Key;
import java.util.AbstractMap;
import java.util.Calendar;

@Path("/countries")
@RolesAllowed("user")
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
    @RolesAllowed("user")
    @Path("{code}")
    @Produces("application/json")
    public String getWorldserviceInfo(@PathParam("code") String code) {
        WorldService service = ServiceProvider.getWorldService();
        Country country = (Country) service.getCountryByCode(code);

        JsonObjectBuilder countryBuilder = convertCountry(country);

        return countryBuilder.build().toString();
    }

    @GET
    @RolesAllowed("user")
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
    @RolesAllowed("user")
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
    @RolesAllowed("user")
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
    @RolesAllowed("user")
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
    @RolesAllowed("user")
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

    }

    @Path("/authentication")
    public static class AuthenticationResource {
        final static public Key key = MacProvider.generateKey();

        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
        public Response authenticateUser(@FormParam("username") String username,
                                         @FormParam("password") String password) {
            try {
                UserDao dao = new UserPostgresDaoImpl();
                String role = dao.findRoleForUser(username, password);

                if (role == null) {
                    throw new IllegalArgumentException("No user found!");
                }

                String token = createToken(username, role);

                AbstractMap.SimpleEntry<String, String> JWT = new AbstractMap.SimpleEntry<String, String>("JWT", token);

                return Response.ok(JWT).build();
            } catch (JwtException | IllegalArgumentException e) {
                return Response.status(Response.Status.UNAUTHORIZED).build();
            }
        }

        private String createToken(String username, String role) throws JwtException {
            Calendar expiration = Calendar.getInstance();
            expiration.add(Calendar.MINUTE, 30);

            return Jwts.builder()
                    .setSubject(username)
                    .setExpiration(expiration.getTime())
                    .claim("role", role)
                    .signWith(SignatureAlgorithm.HS512, key)
                    .compact();
        }
    }
    @Provider
    @Priority(Priorities.AUTHENTICATION)
    public class AuthenticationFilter implements ContainerRequestFilter{
        @Override
        public void filter(ContainerRequestContext requestCtx)
    throws IOException{
        boolean isSecure = requestCtx.getSecurityContext().isSecure();
        MySecurityContext msc = new MySecurityContext("Unknown", "guest", isSecure);

        String authHeader = requestCtx.getHeaderString(HttpHeaders.AUTHORIZATION);
        if( authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring("Bearer".length()).trim();

        try {
            JwtParser parser = Jwts.parser().setSigningKey(AuthenticationResource.key);
            Claims claims = parser.parseClaimsJws(token).getBody();
            String user = claims.getSubject();
            String role = claims.get("role").toString();
            msc = new MySecurityContext(user, role, isSecure);
        } catch (JwtException | IllegalArgumentException e) {
            System.out.println("Invalid JWT, processing as guest!");
        }
        }
        requestCtx.setSecurityContext(msc);

}
    }
}
