package web_services.errors;

import web_services.errors.faultbeans.UnauthorizedFaultBean;

import javax.xml.ws.WebFault;
@WebFault(faultBean = "web_services.errors.faultbeans.UnauthorizedFaultBean")
public class UnauthorizedException extends Exception {

    private static final long serialVersionUID = -6647544772732631047L;
    private final UnauthorizedFaultBean fault;

    public UnauthorizedException(String message) {
        super(message);
        this.fault = new UnauthorizedFaultBean();
    }
    public UnauthorizedException(String message, UnauthorizedFaultBean fault, Throwable cause) {
        super(message, cause);
        this.fault = fault;
        this.initCause(cause);
    }
    public UnauthorizedFaultBean getFaultInfo() {
        return fault;
    }
}