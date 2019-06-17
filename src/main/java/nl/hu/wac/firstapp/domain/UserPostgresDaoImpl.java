package nl.hu.wac.firstapp.domain;

import java.sql.*;

public class UserPostgresDaoImpl extends PostgresBaseDao implements UserDao {

    @Override
    public String findRoleForUser(String username, String password) {
        String query = ("select role from useraccount where username = '" + username
        + "' and password = '" + password + "'");
        String rol = null;
        try (Connection con = super.getConnection()){
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while (rs.next()){
                String role = rs.getString("ROLE");
                rol = role;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        System.out.println(rol);
        return rol;
        }
    }
