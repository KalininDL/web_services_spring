package web_services.errors;

import web_services.errors.faultbeans.PersonServiceFault;

import javax.xml.ws.WebFault;

public class ServerException extends Exception {

    public static ServerException DEFAULT_INSTANCE = new
            ServerException("Internal server error");

    public ServerException(String message) {
        super(message);
    }
}
