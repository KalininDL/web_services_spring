package web_services.errors;
import web_services.errors.faultbeans.PersonCRUDException;
import web_services.errors.faultbeans.PersonServiceFault;

import javax.xml.ws.WebFault;

public class PersonDoesNotExistException extends PersonCRUDException {

    public static PersonDoesNotExistException DEFAULT_INSTANCE = new
            PersonDoesNotExistException("Person with given parameters does not exist");

    public PersonDoesNotExistException(String message) {
        super(message);
    }
}