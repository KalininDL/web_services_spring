package web_services.model;

import web_services.MariaDBDAO;
import web_services.errors.EmptyRequestException;
import web_services.errors.PersonDoesNotExistException;
import web_services.errors.SQLConvertException;
import web_services.errors.ServerException;
import web_services.util.SQLQueryBuilder;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})
public class PersonResource {

    private final MariaDBDAO mdb = new MariaDBDAO();
    private final SQLQueryBuilder sqlQueryBuilder = new SQLQueryBuilder();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Person> getPersons(@QueryParam("name") String name,
                                   @QueryParam("id") int id,
                                   @QueryParam("surname") String surname,
                                   @QueryParam("country") String country,
                                   @QueryParam("gender") String gender,
                                   @QueryParam("age") int age) throws PersonDoesNotExistException, ServerException, EmptyRequestException {
        Person p = new Person();
        p.setId(id);
        p.setName(name);
        p.setAge(age);
        p.setSurname(surname);
        p.setCountry(country);
        p.setGender(gender);
            List<Person> result = mdb.getPersonsBySqlQuery(sqlQueryBuilder.buildSelectQuery(p));
            if (result.size() == 0) {
                throw PersonDoesNotExistException.DEFAULT_INSTANCE;
            } else return result;
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public Response updatePersons(Person newPerson, @QueryParam("id") int id) throws PersonDoesNotExistException, ServerException, EmptyRequestException {
        if(!mdb.checkIfPersonExists(sqlQueryBuilder.buildSelectQuery(new Person(id))))
            throw new PersonDoesNotExistException("Person you trying to change was not found!");
        String result =  mdb.executeUpdateQuery(sqlQueryBuilder.buildUpdateQuery(id, newPerson));
        return Response.status(200).entity(result).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person) throws SQLConvertException, ServerException, EmptyRequestException {
            mdb.executeUpdateQuery(sqlQueryBuilder.buildInsertQuery(person));
            List<Person> added = mdb.getPersonsBySqlQuery(sqlQueryBuilder.buildSelectQuery(person));
            return Response.status(200).entity(
                    "Person added. id : " + added.get(added.size()-1).getId()).build();
    }


    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePersons(@QueryParam("id") int id) throws ServerException, EmptyRequestException, PersonDoesNotExistException {
            if(!mdb.checkIfPersonExists(sqlQueryBuilder.buildSelectQuery(new Person(id))))
                throw new PersonDoesNotExistException("Person you trying to change was not found!");
            String result = mdb.executeUpdateQuery(sqlQueryBuilder.buildDeleteQuery(id));
            return Response.status(200).entity(result).build();
    }
}