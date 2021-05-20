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

    private ConnectionUtil connectionUtil = ConnectionUtil.getInstance();

    private List<Person> processQuery(ResultSet rs){
        Person[] persons_array = new Person[0];
        List<Person> persons = new ArrayList<>();
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                String surname = rs.getString("surname");
                int age = rs.getInt("age");
                String country = rs.getString("country");
                String gender = rs.getString("gender");
                int id = rs.getInt("id");
                Person person = new Person(name, surname, age, country, gender);
                person.setId(id);
                persons.add(person);
            }
            return persons;
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons;
    }

    public List<Person> getPersons() throws ServerException {
        return connectionUtil.statement(stmt -> {
            ResultSet rs = stmt.executeQuery("select * from persons");
            return processQuery(rs);
        });
    }

    public String executeUpdateQuery(String sqlQuery, String message) throws ServerException {
        return connectionUtil.statement(stmt -> {
            int affected  = stmt.executeUpdate(sqlQuery);
            return  "Successfully " + message + " " + affected + " person(s)";
        });
    }

    public List<Person> getPersonsBySqlQuery(String sqlQuery) throws ServerException {
        return connectionUtil.statement(stmt -> {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return processQuery(rs);
        });
    }

    public Boolean checkIfPersonExists(String sqlQuery) throws ServerException {
        return connectionUtil.statement(stmt -> {
            ResultSet rs = stmt.executeQuery(sqlQuery);
            return processQuery(rs).size() > 0;
        });
    }

    public List<Person> getPersonsByName(String name) throws ServerException {
        return connectionUtil.preparedStatement("SELECT * FROM persons where name = ?", stmt -> {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            return processQuery(rs);
        });
    }
}