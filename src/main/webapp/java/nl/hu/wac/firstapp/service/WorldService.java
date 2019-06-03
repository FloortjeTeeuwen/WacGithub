package nl.hu.wac.firstapp.service;

import nl.hu.wac.firstapp.domain.Country;
import nl.hu.wac.firstapp.domain.CountryDao;
import nl.hu.wac.firstapp.domain.CountryPostgresDaoImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class WorldService {

	public List<Country> getAllCountries(){
		CountryDao countryDao = new CountryPostgresDaoImpl();
		return countryDao.findAll();
	}

	public List<Country> get10LargestPopulations(){
		CountryDao countryDao = new CountryPostgresDaoImpl();
		return countryDao.find10LargestPopulations();
	}

	public List<Country> get10LargestSurfaces(){
		CountryDao countryDao = new CountryPostgresDaoImpl();
		return countryDao.find10LargestSurfaces();
	}

	public List<Country> getCountryByCode(String code){
		CountryDao countryDao = new CountryPostgresDaoImpl();
		return countryDao.findByCode(code);
	}

	public boolean save(Country country){
		CountryDao countryDao = new CountryPostgresDaoImpl();
		return countryDao.save(country);
	}

	public void deleteCountryByCode(String code){
		CountryDao countryDao = new CountryPostgresDaoImpl();

		for (Country country : countryDao.findAll()){
			if (country.getCode().equals(code)) {
				countryDao.delete(country);
				break;
			}
		}

	}
	public boolean update(Country country, String code){
		CountryDao countryDao = new CountryPostgresDaoImpl();
		return countryDao.update(country,code);
	}

}
