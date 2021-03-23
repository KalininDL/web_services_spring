package web_services;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService(serviceName = "PersonService")
@SOAPBinding(style = Style.RPC)
public class PersonWebService {

    @Resource(lookup = "jdbc/web_services")
    private DataSource dataSource;

    private MariaDBDAO dao;
    private SQLQueryBuilder sqlQueryBuilder;

    public PersonWebService(){
        sqlQueryBuilder = new SQLQueryBuilder();
        dao = new MariaDBDAO(this.getConnection());
    }


    @WebMethod(operationName = "getPersons")
    public Person[] getPersons() {
        Person[] persons_array = dao.getPersons();
        return persons_array;
    }

    @WebMethod(operationName = "selectPersonsByQueryClass")
    public Person[] selectPersonsByQueryClass(Query query) {
        Person[] persons_array = dao.getPersonsBySqlQuery(sqlQueryBuilder.buildSelectQuery(query));
        return persons_array;
    }

    @WebMethod(operationName = "deletePersonByQueryClass")
    public String deletePersonByQueryClass(Query query) {
        String result = dao.executeUpdateQuery(sqlQueryBuilder.buildDeleteQuery(query));
        return result;
    }

    @WebMethod(operationName = "insertPersonByQueryClass")
    public String insertPersonByQueryClass(Query query) {
        String result = dao.executeUpdateQuery(sqlQueryBuilder.buildInsertQuery(query));
        return result;
    }

    @WebMethod(operationName = "updatePersonByQueryClass")
    public String updatePersonByQueryClass(Query query, Query updated) {
        String result = dao.executeUpdateQuery(sqlQueryBuilder.buildUpdateQuery(query, updated));
        return result;
    }

    private Connection getConnection() {
        Connection result = null;
        try {
            result = dataSource.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(PersonWebService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

}