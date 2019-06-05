package nl.hu.wac.firstapp.service;

import nl.hu.wac.firstapp.domain.*;

import java.util.List;

public class WorldService {

	public WorldService() {
		countrydao = new CountryPostgresDaoImpl();
		userDao = new UserPostgresDaoImpl();

	}
	private CountryDao countrydao;
	private UserDao userDao;

	public List<Country> getAllCountries(){
		return countrydao.findAll();
	}

	public List<Country> get10LargestPopulations(){
		return countrydao.find10LargestPopulations();
	}

	public List<Country> get10LargestSurfaces(){
		return countrydao.find10LargestSurfaces();
	}

	public List<Country> getCountryByCode(String code){
		return countrydao.findByCode(code);
	}

	public boolean save(Country country){
		return countrydao.save(country);
	}

	public void deleteCountryByCode(String code){

		for (Country country : countrydao.findAll()){
			if (country.getCode().equals(code)) {
				countrydao.delete(country);
				break;
			}
		}

	}
	public boolean update(Country country, String code){
		return countrydao.update(country,code);
	}

	public String findRoleForUser(String name, String pass){return userDao.findRoleForUser(name, pass);}
}
