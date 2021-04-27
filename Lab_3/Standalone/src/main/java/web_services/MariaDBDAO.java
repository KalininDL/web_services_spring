package web_services;

import org.mariadb.jdbc.internal.util.exceptions.MariaDbSqlException;
import web_services.errors.ServerException;
import web_services.model.Person;
import web_services.util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MariaDBDAO {


    private Person[] processQuery(ResultSet rs){
        Person[] persons_array = new Person[0];
        List<Person> persons = new ArrayList<>();
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                int age = rs.getInt("age");
                String country = rs.getString("country");
                String gender = rs.getString("gender");
                Person person = new Person(name, surname, age, country, gender);
                persons.add(person);
            }
            persons_array = new Person[persons.size()];
            persons.toArray(persons_array);
            return persons_array;
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons_array;
    }

    public Person[] getPersons() throws ServerException {
        Person[] persons_array = new Person[0];
        try (Statement stmt = ConnectionUtil.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from persons");
            persons_array = processQuery(rs);
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
        return persons_array;
    }

    public String executeUpdateQuery(String sqlQuery) throws ServerException {
        try (Statement stmt = ConnectionUtil.getConnection().createStatement()) {
            int res = stmt.executeUpdate(sqlQuery);
            String result = "Query affected " + res + " rows";
            System.out.println(result);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
    }

    public Person[] getPersonsBySqlQuery(String sqlQuery) throws ServerException {
        Person[] persons_array = new Person[0];
        try (Statement stmt = ConnectionUtil.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            persons_array = processQuery(rs);
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
        return persons_array;
    }

    public Boolean checkIfPersonExists(String sqlQuery) throws ServerException {
        try (Statement stmt = ConnectionUtil.getConnection().createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return processQuery(rs).length > 0;
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException(ex.getMessage());
        }
    }

}