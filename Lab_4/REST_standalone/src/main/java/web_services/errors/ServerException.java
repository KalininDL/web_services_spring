package web_services.errors;

import web_services.errors.faultbeans.PersonServiceFault;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "web_services.errors.faultbeans.PersonServiceFault")
public class ServerException extends Exception {

    private static final long serialVersionUID = -6647544772732631047L;
    private final PersonServiceFault fault;

    public ServerException(String message) {
        super(message);
        this.fault = new PersonServiceFault();
    }
    public ServerException(String message, PersonServiceFault fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
        this.initCause(cause);
    }
    public PersonServiceFault getFaultInfo() {
        return fault;
    }
}
