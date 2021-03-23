package web_services;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MariaDBDAO {
    Connection connection;

    public MariaDBDAO(Connection connection){
        this.connection = connection;
    }

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
                int i = 0;
                for (Person p :persons) {
                    persons_array[i] = p;
                    i++;
                }
                return persons_array;
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons_array;
    }

    public Person[] getPersons() {
        Person[] persons_array = new Person[0];
        try  {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from persons");
            persons_array = processQuery(rs);
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons_array;
    }

    public String executeUpdateQuery(String sqlQuery){
        try  {
            Statement stmt = connection.createStatement();
            int res = stmt.executeUpdate(sqlQuery);
            String result = new String("Query affected " + res + " rows");
            System.out.println(result);
            return result;
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ("Error!");
    }

    public Person[] getPersonsBySqlQuery(String sqlQuery) {
        Person[] persons_array = new Person[0];
        try  {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery);
            persons_array = processQuery(rs);
        } catch (SQLException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return persons_array;
    }
}