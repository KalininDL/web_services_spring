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
import java.util.ArrayList;
import java.util.List;

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
        return (Person[]) dao.getPersons().toArray();
    }

    //I had to change the method signature and return arrays of Persons.
    // JAXB is able to serialize lists in standalone app but fails with glassfish
    // and asks you to use arrays. I have no idea why
    @WebMethod(operationName = "selectPersons")
    public Person[] selectPersonsByQueryClass(Person query) throws ServerException {
            List<Person> persons = dao.getPersonsBySqlQuery(sqlQueryBuilder.buildSelectQuery(query));
            if(persons.size() == 0) throw new PersonDoesNotExistException("No persons found by given parameters", fault);
            return (Person[]) persons.toArray();
    }


    @WebMethod(operationName = "deletePersonByID")
    public String deletePersonByID(int id) throws ServerException {
        try {
            if (!dao.checkIfPersonExists(sqlQueryBuilder.buildSelectQuery(new Person(id))))
                throw new PersonDoesNotExistException("Person with specified parameters does not exist!", fault);
            return dao.executeUpdateQuery(sqlQueryBuilder.buildDeleteQuery(id), "deleted");
        } catch (EmptyRequestException | SQLConvertException e){
            throw e;
        }
    }

    @WebMethod(operationName = "insertPerson")
    public String insertPerson(Person person) throws ServerException {
        try {
            dao.executeUpdateQuery(sqlQueryBuilder.buildInsertQuery(person), "added");
            List<Person> created = dao.getPersonsBySqlQuery(sqlQueryBuilder.buildSelectQuery(person));
            return "New person with id: " + created.get(0).getId() + " was successfully created!";
        } catch (SQLConvertException | EmptyRequestException e){
            throw e;
        }
    }

    @WebMethod(operationName = "updatePersonByID")
    public String updatePersonByID(int id, Person updated) throws  ServerException {
            if (!dao.checkIfPersonExists(sqlQueryBuilder.buildSelectQuery(new Person(id))))
                throw new PersonDoesNotExistException("Person with specified parameters does not exist!", fault);
            return dao.executeUpdateQuery(sqlQueryBuilder.buildUpdateQuery(id, updated), "updated");
    }
}