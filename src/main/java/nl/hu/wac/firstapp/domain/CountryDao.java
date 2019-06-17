package nl.hu.wac.firstapp.domain;

import java.util.List;

public interface CountryDao {
    boolean save(Country country);
    List<Country> findAll();
    List<Country> findByCode(String code);
    List<Country> find10LargestPopulations();
    List<Country> find10LargestSurfaces();
    boolean update(Country country, String code);
    boolean delete(Country country);
}
