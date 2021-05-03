package web_services.errors;

import web_services.errors.faultbeans.PersonServiceFault;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "web_services.errors.faultbeans.PersonServiceFault")
public class SQLConvertException extends ServerException {

    private static final long serialVersionUID = -6647544772732631047L;
    private final PersonServiceFault fault;

    public SQLConvertException(String message) {
        super(message);
        this.fault = new PersonServiceFault();
    }
    public SQLConvertException(String message, PersonServiceFault fault, Throwable cause) {
        super(message);
        this.fault = fault;
    }
    public PersonServiceFault getFaultInfo() {
        return fault;
    }
}
