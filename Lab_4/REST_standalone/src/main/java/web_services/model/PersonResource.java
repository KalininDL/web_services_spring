package web_services.model;

import web_services.MariaDBDAO;
import web_services.errors.ServerException;
import web_services.util.ConnectionUtil;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
@Path("/persons")
@Produces({MediaType.APPLICATION_JSON})
public class PersonResource {



    private MariaDBDAO mdb = MariaDBDAO.getInstance();

    @GET
    public List<Person> getPersons(@QueryParam("name") String name) throws ServerException {
        List<Person> persons = mdb.getPersonsByName(name);
        return persons;
    }
}