package web_services.model;

import web_services.MariaDBDAO;
import web_services.errors.EmptyRequestException;
import web_services.errors.PersonDoesNotExistException;
import web_services.errors.ServerException;
import web_services.util.SQLQueryBuilder;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})
public class PersonResource {

    private final MariaDBDAO mdb = MariaDBDAO.getInstance();
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
            System.out.println("Sending back!");
            List<Person> result = mdb.getPersonsBySqlQuery(sqlQueryBuilder.buildSelectQuery(p));
            if (result.size() == 0) {
                throw PersonDoesNotExistException.DEFAULT_INSTANCE;
            } else return result;
    }

    @PUT
    @Produces({MediaType.APPLICATION_JSON})
    public String updatePersons(Person newPerson, @QueryParam("name") String name,
                                   @QueryParam("id") int id,
                                   @QueryParam("surname") String surname,
                                   @QueryParam("country") String country,
                                   @QueryParam("gender") String gender,
                                   @QueryParam("age") int age) throws PersonDoesNotExistException, ServerException, EmptyRequestException {
        Person oldPerson = new Person();
        oldPerson.setId(id);
        oldPerson.setName(name);
        oldPerson.setAge(age);
        oldPerson.setSurname(surname);
        oldPerson.setCountry(country);
        oldPerson.setGender(gender);
        System.out.println("Sending back!");
        if(!mdb.checkIfPersonExists(sqlQueryBuilder.buildSelectQuery(oldPerson)))
            throw new PersonDoesNotExistException("Person you trying to change was not found!");
        return mdb.executeUpdateQuery(sqlQueryBuilder.buildUpdateQuery(oldPerson, newPerson));
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPerson(Person person){
        try {
            mdb.executeUpdateQuery(sqlQueryBuilder.buildInsertQuery(person));
            return Response.status(200).build();
        } catch (ServerException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


    @DELETE
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePersons(@QueryParam("name") String name,
                                   @QueryParam("id") int id,
                                   @QueryParam("surname") String surname,
                                   @QueryParam("country") String country,
                                   @QueryParam("gender") String gender,
                                   @QueryParam("age") int age) throws ServerException {
        Person p = new Person();
        p.setId(id);
        p.setName(name);
        p.setAge(age);
        p.setSurname(surname);
        p.setCountry(country);
        p.setGender(gender);
        try {
            mdb.executeUpdateQuery(sqlQueryBuilder.buildDeleteQuery(p));
            return Response.status(200).build();
        } catch (ServerException e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}