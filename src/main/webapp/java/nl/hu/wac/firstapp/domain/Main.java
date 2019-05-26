package nl.hu.wac.firstapp.domain;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException{

        CountryDao countryDao = new CountryPostgresDaoImpl();
        List<Country> countries = countryDao.findAll();
    }
}
