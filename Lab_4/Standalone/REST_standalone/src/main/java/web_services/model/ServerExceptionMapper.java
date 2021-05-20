package web_services.model;

import web_services.errors.EmptyRequestException;
import web_services.errors.SQLConvertException;
import web_services.errors.ServerException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ServerExceptionMapper implements ExceptionMapper<ServerException> {

    @Override
    public Response toResponse(ServerException e) {
        return Response.status(500).entity(e.getMessage()).build();
    }
}
