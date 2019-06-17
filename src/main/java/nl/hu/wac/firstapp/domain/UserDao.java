package nl.hu.wac.firstapp.domain;

public interface UserDao {

    String findRoleForUser(String name, String pass);
}
