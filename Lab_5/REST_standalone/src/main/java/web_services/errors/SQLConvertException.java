package web_services.errors;

import web_services.errors.faultbeans.PersonCRUDException;
import web_services.errors.faultbeans.PersonServiceFault;

import javax.xml.ws.WebFault;

public class SQLConvertException extends ServerException {

    public static SQLConvertException DEFAULT_INSTANCE = new
            SQLConvertException("Internal server error");

    public SQLConvertException(String message) {
        super(message);
    }
}
