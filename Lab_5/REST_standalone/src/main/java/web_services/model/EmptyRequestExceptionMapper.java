package web_services.model;

import web_services.errors.EmptyRequestException;
import web_services.errors.faultbeans.PersonCRUDException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EmptyRequestExceptionMapper implements ExceptionMapper<EmptyRequestException> {

    @Override
    public Response toResponse(EmptyRequestException e) {
        return Response.status(400).entity(e.getMessage()).build();
    }
}
