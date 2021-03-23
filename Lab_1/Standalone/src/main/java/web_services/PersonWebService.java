package web_services;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(serviceName = "PersonService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class PersonWebService {

    //I have MariaDB deployed on my home server. Hope it's ok to use it instead of PostgreSQL
    // since JDBC allows you to change DBs without changing the behaviour
    private final MariaDBDAO dao;
    private final SQLQueryBuilder sqlQueryBuilder;

    public PersonWebService(){
        sqlQueryBuilder = new SQLQueryBuilder();
        dao = new MariaDBDAO();
    }


    @WebMethod(operationName = "getPersons")
    public Person[] getPersons() {
        Person[] persons_array = dao.getPersons();
        return persons_array;
    }

    //I had to change the method signature and return arrays of Persons.
    // JAXB is able to serialize lists in standalone app but fails with glassfish
    // and asks you to use arrays. I have no idea why
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
}