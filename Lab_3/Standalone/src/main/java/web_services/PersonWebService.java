package web_services;

import web_services.errors.EmptyRequestException;
import web_services.errors.PersonDoesNotExistException;
import web_services.errors.SQLConvertException;
import web_services.errors.ServerException;
import web_services.errors.faultbeans.PersonServiceFault;
import web_services.model.Person;
import web_services.util.SQLQueryBuilder;

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
    private final PersonServiceFault fault;

    public PersonWebService(){
        sqlQueryBuilder = new SQLQueryBuilder();
        dao = new MariaDBDAO();
        fault = new PersonServiceFault();
    }


    @WebMethod(operationName = "getPersons")
    public Person[] getPersons() throws ServerException {
        Person[] persons_array = dao.getPersons();
        return persons_array;
    }

    //I had to change the method signature and return arrays of Persons.
    // JAXB is able to serialize lists in standalone app but fails with glassfish
    // and asks you to use arrays. I have no idea why
    @WebMethod(operationName = "selectPersonsByQueryClass")
    public Person[] selectPersonsByQueryClass(Person query) throws ServerException {
            Person[] persons_array = dao.getPersonsBySqlQuery(sqlQueryBuilder.buildSelectQuery(query));
            if(persons_array.length == 0) throw new PersonDoesNotExistException("No persons found by given parameters", fault);
            return persons_array;
    }

    @WebMethod(operationName = "deletePersonByQueryClass")
    public String deletePersonByQueryClass(Person query) throws ServerException {
            if (!dao.checkIfPersonExists(sqlQueryBuilder.buildSelectQuery(query)))
                throw new PersonDoesNotExistException("Person with specified parameters does not exist!", fault);
            return dao.executeUpdateQuery(sqlQueryBuilder.buildDeleteQuery(query));
    }

    @WebMethod(operationName = "insertPersonByQueryClass")
    public String insertPersonByQueryClass(Person query) throws ServerException {
            String result = dao.executeUpdateQuery(sqlQueryBuilder.buildInsertQuery(query));
            return result;
    }

    @WebMethod(operationName = "updatePersonByQueryClass")
    public String updatePersonByQueryClass(Person query, Person updated) throws  ServerException {
            if (!dao.checkIfPersonExists(sqlQueryBuilder.buildSelectQuery(query)))
                throw new PersonDoesNotExistException("Person with specified parameters does not exist!", fault);

            return dao.executeUpdateQuery(sqlQueryBuilder.buildUpdateQuery(query, updated));
    }
}