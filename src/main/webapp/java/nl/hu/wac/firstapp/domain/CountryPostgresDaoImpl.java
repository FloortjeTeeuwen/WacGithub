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
            PreparedStatement mySt = myConn.prepareStatement("insert into country (code, name, continent, region, surfacearea, population, governmentform, capital )values (?,?,?,?,?,?,?,?)");
            mySt.setString(1, country.getCode());
            mySt.setString(2, country.getName());
            mySt.setString(3, country.getContinent());
            mySt.setString(4, country.getRegion());
            mySt.setDouble(5, country.getSurface());
            mySt.setInt(6, country.getPopulation());
            mySt.setString(7, country.getGovernment());
            mySt.setString(8, "");
            mySt.executeLargeUpdate();
            return true;
        }
        catch (SQLException e){
            System.out.println("FOUT");
            e.printStackTrace();
        }
        return false;
    }

    public List<Country> findAll(){
        List<Country> countries;

        try {
            PreparedStatement mySt = myConn.prepareStatement("select * from country order by name");
            ResultSet resultSet = mySt.executeQuery();

            countries = buildCountries(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return countries;
    }

    private List<Country> buildCountries(ResultSet resultSet) throws SQLException {
        List<Country> countries = new ArrayList<Country>();

        while (resultSet.next()) {
            Country country = new Country(
                    resultSet.getString("code"),
                    resultSet.getString("iso3"),
                    resultSet.getString("name"),
                    resultSet.getString("capital"),
                    resultSet.getString("continent"),
                    resultSet.getString("region"),
                    resultSet.getDouble("surfacearea"),
                    resultSet.getInt("population"),
                    resultSet.getString("governmentform"),
                    resultSet.getDouble("latitude"),
                    resultSet.getDouble("longitude"));
            countries.add(country);
        }

        return countries;
    }

    public List<Country> findByCode(String code){
        List<Country> countries;
        try {
            PreparedStatement mySt = myConn.prepareStatement("select * from country where code=?");
            mySt.setString(1, code);
            ResultSet resultSet = mySt.executeQuery();

            countries = buildCountries(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return countries;
    }

    public List<Country> find10LargestPopulations(){
        List<Country> countries;
        try{
            PreparedStatement mySt = myConn.prepareStatement("SELECT * FROM country  ORDER BY population DESC limit 5; ");
            ResultSet resultSet = mySt.executeQuery();

            countries = buildCountries(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return countries;
    }

    public List<Country> find10LargestSurfaces(){
        List<Country> countries;
        try{
            PreparedStatement mySt = myConn.prepareStatement("SELECT * FROM country  ORDER BY surfacearea DESC limit 5; ");
            ResultSet resultSet = mySt.executeQuery();

            countries = buildCountries(resultSet);
        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }

        return countries;
    }

    public boolean update(Country country, String code){
        try {
            PreparedStatement mySt = myConn.prepareStatement("update country set code=?, name=?, continent=?, region=?, surfacearea=?, population=?, governmentform=? where code = ?");
            mySt.setString(1, country.getCode());
            mySt.setString(2, country.getName());
            mySt.setString(3, country.getContinent());
            mySt.setString(4, country.getRegion());
            mySt.setDouble(5, country.getSurface());
            mySt.setInt(6, country.getPopulation());
            mySt.setString(7, country.getGovernment());
            mySt.setString(8, country.getCode());
            mySt.executeUpdate();
            return true;
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Country country){
        try {
            PreparedStatement mySt = myConn.prepareStatement("delete from country where code = ?");
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

