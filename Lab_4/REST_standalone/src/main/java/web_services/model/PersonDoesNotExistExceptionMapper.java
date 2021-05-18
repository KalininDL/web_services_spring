package web_services.model;

import web_services.errors.PersonDoesNotExistException;
import web_services.errors.faultbeans.PersonCRUDException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class PersonDoesNotExistExceptionMapper implements ExceptionMapper<PersonCRUDException> {

@Override
public Response toResponse(PersonCRUDException e) {
        return Response.status(404).entity(e.getMessage()).build();
        }
}
