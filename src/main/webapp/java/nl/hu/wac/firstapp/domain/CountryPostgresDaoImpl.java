package nl.hu.wac.firstapp.domain;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CountryPostgresDaoImpl extends PostgresBaseDao implements CountryDao {
    private Connection myConn;

    public CountryPostgresDaoImpl(){
        this.myConn = super.getConnection();
    }
    public boolean save(Country country){
        try {
            PreparedStatement mySt = myConn.prepareStatement("insert into public.\"Country\" values (?,?,?,?,?,?,?)");
            mySt.setString(1, country.getCode());
            mySt.setString(2, country.getName());
            mySt.setString(3, country.getContinent());
            mySt.setString(4, country.getRegion());
            mySt.setDouble(5, country.getSurface());
            mySt.setInt(6, country.getPopulation());
            mySt.setString(7, country.getGovernment());
            mySt.executeQuery();
            System.out.println("Land toegevoegd");
        }
        catch (SQLException e){
            System.out.println("FOUT");
        }
        return false;
    }

    public List<Country> findAll(){
        List<Country> countries = new ArrayList<Country>();
        try {
            PreparedStatement mySt = myConn.prepareStatement("select * from public.\"Country\"");
            ResultSet RS = mySt.executeQuery();
            Country c = null;
            while (RS.next()) {
                System.out.println("Landcode: " + RS.getString("code"));
                System.out.println("Afkorting: " + RS.getString("iso3"));
                System.out.println("Landnaam: " + RS.getString("name"));
                System.out.println("Hoofdstad: " + RS.getString("capital"));
                System.out.println("Contrinent: " + RS.getString("continent"));
                System.out.println("Regio: " + RS.getString("region"));
                System.out.println("Oppervlakte: " + RS.getDouble("surfacearea"));
                System.out.println("Aantal inwoners: " + RS.getInt("population"));
                System.out.println("Regering: " + RS.getString("governmentform"));
                System.out.println("Lat: " + RS.getDouble("latitude"));
                System.out.println("Lon: " + RS.getDouble("longitude"));
                System.out.println("\n");

                c = new Country(
                        RS.getString("code"),
                        RS.getString("iso3"),
                        RS.getString("name"),
                        RS.getString("capital"),
                        RS.getString("continent"),
                        RS.getString("region"),
                        RS.getDouble("surfacearea"),
                        RS.getInt("population"),
                        RS.getString("governmentform"),
                        RS.getDouble("latitude"),
                        RS.getDouble("longitude"));
                countries.add(c);
            }
        }
        catch (SQLException e){
            return null;
        }
        return countries;
    }

    public List<Country> findByCode(String code){
        List<Country> countries = new ArrayList<Country>();
        try {
            PreparedStatement mySt = myConn.prepareStatement("select * from public.\"Country\" where code=?");
            mySt.setString(1, code);
            ResultSet RS = mySt.executeQuery();
            Country c = null;
            while (RS.next()) {
                System.out.println("Landcode: " + RS.getString("code"));
                System.out.println("Afkorting: " + RS.getString("iso3"));
                System.out.println("Landnaam: " + RS.getString("name"));
                System.out.println("Hoofdstad: " + RS.getString("capital"));
                System.out.println("Contrinent: " + RS.getString("continent"));
                System.out.println("Regio: " + RS.getString("region"));
                System.out.println("Oppervlakte: " + RS.getDouble("surfacearea"));
                System.out.println("Aantal inwoners: " + RS.getInt("population"));
                System.out.println("Regering: " + RS.getString("governmentform"));
                System.out.println("Lat: " + RS.getDouble("latitude"));
                System.out.println("Lon: " + RS.getDouble("longitude"));
                System.out.println("\n");

                c = new Country(
                        RS.getString("code"),
                        RS.getString("iso3"),
                        RS.getString("name"),
                        RS.getString("capital"),
                        RS.getString("continent"),
                        RS.getString("region"),
                        RS.getDouble("surfacearea"),
                        RS.getInt("population"),
                        RS.getString("governmentform"),
                        RS.getDouble("latitude"),
                        RS.getDouble("longitude"));
                countries.add(c);
            }
        }
        catch (SQLException e){
            return null;
        }
        return countries;
    }

    public List<Country> find10LargestPopulations(){
        List<Country> countries = new ArrayList<Country>();
        try{
            PreparedStatement mySt = myConn.prepareStatement("SELECT * FROM public.\"Country\"  ORDER BY population DESC limit 5; ");
            ResultSet RS = mySt.executeQuery();
            Country c = null;
            while (RS.next()) {
                System.out.println("Landcode: " + RS.getString("code"));
                System.out.println("Afkorting: " + RS.getString("iso3"));
                System.out.println("Landnaam: " + RS.getString("name"));
                System.out.println("Hoofdstad: " + RS.getString("capital"));
                System.out.println("Contrinent: " + RS.getString("continent"));
                System.out.println("Regio: " + RS.getString("region"));
                System.out.println("Oppervlakte: " + RS.getDouble("surfacearea"));
                System.out.println("Aantal inwoners: " + RS.getInt("population"));
                System.out.println("Regering: " + RS.getString("governmentform"));
                System.out.println("Lat: " + RS.getDouble("latitude"));
                System.out.println("Lon: " + RS.getDouble("longitude"));
                System.out.println("\n");

                c = new Country(
                        RS.getString("code"),
                        RS.getString("iso3"),
                        RS.getString("name"),
                        RS.getString("capital"),
                        RS.getString("continent"),
                        RS.getString("region"),
                        RS.getDouble("surfacearea"),
                        RS.getInt("population"),
                        RS.getString("governmentform"),
                        RS.getDouble("latitude"),
                        RS.getDouble("longitude"));
                countries.add(c);
            }
        }
        catch (SQLException e){
            return null;
        }
        return countries;
    }

    public List<Country> find10LargestSurfaces(){
        List<Country> countries = new ArrayList<Country>();
        try{
            PreparedStatement mySt = myConn.prepareStatement("SELECT * FROM public.\"Country\"  ORDER BY surfacearea DESC limit 5; ");
            ResultSet RS = mySt.executeQuery();
            Country c = null;
            while (RS.next()) {
                System.out.println("Landcode: " + RS.getString("code"));
                System.out.println("Afkorting: " + RS.getString("iso3"));
                System.out.println("Landnaam: " + RS.getString("name"));
                System.out.println("Hoofdstad: " + RS.getString("capital"));
                System.out.println("Contrinent: " + RS.getString("continent"));
                System.out.println("Regio: " + RS.getString("region"));
                System.out.println("Oppervlakte: " + RS.getDouble("surfacearea"));
                System.out.println("Aantal inwoners: " + RS.getInt("population"));
                System.out.println("Regering: " + RS.getString("governmentform"));
                System.out.println("Lat: " + RS.getDouble("latitude"));
                System.out.println("Lon: " + RS.getDouble("longitude"));
                System.out.println("\n");

                c = new Country(
                        RS.getString("code"),
                        RS.getString("iso3"),
                        RS.getString("name"),
                        RS.getString("capital"),
                        RS.getString("continent"),
                        RS.getString("region"),
                        RS.getDouble("surfacearea"),
                        RS.getInt("population"),
                        RS.getString("governmentform"),
                        RS.getDouble("latitude"),
                        RS.getDouble("longitude"));
                countries.add(c);
            }
        }
        catch (SQLException e){
            return null;
        }
        return countries;
    }

    public boolean update(Country country){
        try {
            PreparedStatement mySt = myConn.prepareStatement("update public.\"Country\" set values (?,?,?,?,?,?,?)");
            mySt.setString(1, country.getCode());
            mySt.setString(2, country.getName());
            mySt.setString(3, country.getContinent());
            mySt.setString(4, country.getRegion());
            mySt.setDouble(5, country.getSurface());
            mySt.setInt(6, country.getPopulation());
            mySt.setString(7, country.getGovernment());
            mySt.executeQuery();
            System.out.println("Land geupdate");
        }
        catch (SQLException e){
            System.out.println("FOUT");
        }
        return false;
    }

    public boolean delete(Country country){
        try {
            PreparedStatement mySt = myConn.prepareStatement("delete from public.\"Country\" where ?");
            mySt.setString(1, country.getCode());
            ResultSet RS = mySt.executeQuery();
            while (RS.next()) {
                System.out.println("Land verwijdert");
            }
        } catch (SQLException e){
        }
        return false;
    }
}

