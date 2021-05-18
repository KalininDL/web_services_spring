package web_services;

import org.junit.jupiter.api.Test;
import web_services.errors.SQLConvertException;
import web_services.model.Person;
import web_services.util.SQLQueryBuilder;

import static org.junit.jupiter.api.Assertions.*;

class SQLQueryBuilderTest {

    @Test
    void buildInsertQuery() throws SQLConvertException {
        Person query = new Person();
        SQLQueryBuilder sqlQueryBuilder =new SQLQueryBuilder();
        query.setAge(18);
        query.setName("Иван");
        query.setSurname("Михайлов");
        String sqlQuery = sqlQueryBuilder.buildInsertQuery(query);
        System.out.println(sqlQuery);
        assertEquals("INSERT INTO persons (surname, name, age) VALUES ('Михайлов', 'Иван', '18')", sqlQuery);
    }

    @Test
    void buildUpdateQuery() throws SQLConvertException {
        Person query = new Person();
        SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder();
        query.setAge(18);
        query.setName("Иван");
        query.setSurname("Михайлов");
        Person queryUpdate = new Person();
        queryUpdate.setAge(18);
        queryUpdate.setName("Максим");
        queryUpdate.setSurname("Михайлов");
        queryUpdate.setGender("М");
        String sqlQuery = sqlQueryBuilder.buildUpdateQuery(query, queryUpdate);
        System.out.println(sqlQuery);
        //assertEquals("DELETE * FROM persons WHERE surname = 'Михайлов' and name = 'Иван' and age = '18'", sqlQuery);
    }

    @Test
    void buildDeleteQuery() throws SQLConvertException {
        Person query = new Person();
        SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder();
        query.setAge(18);
        query.setName("Иван");
        query.setSurname("Михайлов");
        String sqlQuery = sqlQueryBuilder.buildDeleteQuery(query);
        System.out.println(sqlQuery);
        assertEquals("DELETE FROM persons WHERE surname = 'Михайлов' and name = 'Иван' and age = '18'", sqlQuery);
    }

    @Test
    void buildSelectQuery() {

    }
}