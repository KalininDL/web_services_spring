package web_services.model;

import com.sun.jersey.core.util.Base64;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.*;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthFilter implements ContainerRequestFilter {

    private int counter;

    private final static WebApplicationException unauthorized =
            new WebApplicationException(
                    Response.status(Response.Status.UNAUTHORIZED)
                            .header(HttpHeaders.WWW_AUTHENTICATE, "Basic realm=\"realm\"")
                            .entity("You should be logged in to access this method").build());

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) throws WebApplicationException {
        counter++;
        System.out.println("Counter is " +counter);
        String method = containerRequest.getMethod();
        String path = containerRequest.getPath(true);
        if (method.equals("GET") && path.equals("persons"))
            return containerRequest;
        String authHeader = containerRequest.getHeaderValue("authorization");
        if (authHeader == null)
            throw unauthorized;
        authHeader = authHeader.replaceFirst("[Bb]asic ", "");
        String userColonPass = Base64.base64Decode(authHeader);
        if (!userColonPass.equals("user:itmo"))
            throw unauthorized;
        return containerRequest;
    }
}