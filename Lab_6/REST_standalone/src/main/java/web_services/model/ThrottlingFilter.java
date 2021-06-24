package web_services.model;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.util.concurrent.TimeUnit;

@Provider
public class ThrottlingFilter implements ContainerRequestFilter {

    int counter = 0;
    long startTime = System.nanoTime();

    private final long timePeriod = 1L;
    private final int maxRequestPerTimerPeriod = 10;


    private final static WebApplicationException throttlingException =
            new WebApplicationException(
                    Response.status(Response.Status.SERVICE_UNAVAILABLE)
                            .entity("Throttling exception").build());

    @Override
    public ContainerRequest filter(ContainerRequest containerRequest) {
        if((System.nanoTime() - startTime) >  timePeriod * 1_000_000_000){
            startTime = System.nanoTime();
            counter = 0;
        } else {
            counter++;
            if(counter > maxRequestPerTimerPeriod){
                throw throttlingException;
            }
        }
        return containerRequest;
    }
}
