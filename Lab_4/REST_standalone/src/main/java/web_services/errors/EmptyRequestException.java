package web_services.errors;

import web_services.errors.faultbeans.PersonCRUDException;
import web_services.errors.faultbeans.PersonServiceFault;

import javax.xml.ws.WebFault;
@WebFault(faultBean = "web_services.errors.faultbeans.PersonServiceFault")
public class EmptyRequestException extends PersonCRUDException {

    public EmptyRequestException(String message) {
        super(message);
    }
}