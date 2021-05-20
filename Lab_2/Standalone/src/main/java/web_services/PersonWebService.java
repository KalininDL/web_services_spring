package web_services;

import web_services.errors.EmptyRequestException;
import web_services.errors.PersonDoesNotExistException;
import web_services.errors.SQLConvertException;
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
    public Person[] getPersons() {
        return dao.getPersons();
    }

    //I had to change the method signature and return arrays of Persons.
    // JAXB is able to serialize lists in standalone app but fails with glassfish
    // and asks you to use arrays. I have no idea why
    @WebMethod(operationName = "selectPersons")
    public Person[] selectPersonsByQueryClass(Person query) throws PersonDoesNotExistException, EmptyRequestException, SQLConvertException {
        try {
            Person[] persons_array = dao.getPersonsBySqlQuery(sqlQueryBuilder.buildSelectQuery(query));
            if(persons_array.length == 0) throw new PersonDoesNotExistException("No persons found by given parameters", fault);
            return persons_array;
        }
        catch (EmptyRequestException | SQLConvertException e){
            throw e;
        }
    }

    @WebMethod(operationName = "deletePersonByID")
    public String deletePersonByID(int id) throws PersonDoesNotExistException, EmptyRequestException, SQLConvertException {
        try {
            if (!dao.checkIfPersonExists(sqlQueryBuilder.buildSelectQuery(new Person(id))))
                throw new PersonDoesNotExistException("Person with specified parameters does not exist!", fault);
            return dao.executeUpdateQuery(sqlQueryBuilder.buildDeleteQuery(id), "deleted");
        } catch (EmptyRequestException | SQLConvertException e){
            throw e;
        }
    }

    @WebMethod(operationName = "insertPerson")
    public String insertPerson(Person person) throws SQLConvertException, EmptyRequestException {
        try {
            String result = dao.executeUpdateQuery(sqlQueryBuilder.buildInsertQuery(person), "added");
            Person[] created = dao.getPersonsBySqlQuery(sqlQueryBuilder.buildSelectQuery(person));
            return "New person with id: " + created[0].getId() + " was successfully created!";
        } catch (SQLConvertException | EmptyRequestException e){
            throw e;
        }
    }

    @WebMethod(operationName = "updatePersonByID")
    public String updatePersonByID(int id, Person updated) throws PersonDoesNotExistException, EmptyRequestException, SQLConvertException {
        try {
            if (!dao.checkIfPersonExists(sqlQueryBuilder.buildSelectQuery(new Person(id))))
                throw new PersonDoesNotExistException("Person with specified parameters does not exist!", fault);
            return dao.executeUpdateQuery(sqlQueryBuilder.buildUpdateQuery(id, updated), "updated");
        } catch (EmptyRequestException | SQLConvertException e){
            throw e;
        }
    }
}