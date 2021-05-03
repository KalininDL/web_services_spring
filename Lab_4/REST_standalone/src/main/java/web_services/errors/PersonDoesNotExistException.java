package web_services.errors;
import web_services.errors.faultbeans.PersonServiceFault;

import javax.xml.ws.WebFault;
@WebFault(faultBean = "web_services.errors.faultbeans.PersonServiceFault")
public class PersonDoesNotExistException extends ServerException {

    private static final long serialVersionUID = -6647544772732631047L;
    private final PersonServiceFault fault;

    public PersonDoesNotExistException(String message, PersonServiceFault fault) {
        super(message);
        this.fault = fault;
    }
    public PersonDoesNotExistException(String message, PersonServiceFault fault, Throwable cause) {
        super(message);
        this.fault = fault;
    }
    public PersonServiceFault getFaultInfo() {
        return fault;
    }
}