package web_services.errors;

import web_services.errors.faultbeans.PersonCRUDException;
import web_services.errors.faultbeans.PersonServiceFault;

import javax.xml.ws.WebFault;


public class EmptyRequestException extends PersonCRUDException {

    public EmptyRequestException(String message) {
        super(message);
    }
}