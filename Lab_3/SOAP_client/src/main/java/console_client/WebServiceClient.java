package console_client;

import com.sun.xml.internal.ws.developer.JAXWSProperties;
import com.sun.xml.internal.ws.developer.StreamingDataHandler;
import wsdl_autogenerated.PersonService;
import wsdl_autogenerated.ServerException;

import javax.activation.DataHandler;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.MTOMFeature;
import javax.xml.ws.soap.SOAPFaultException;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

public class WebServiceClient {

    private final static Logger log = Logger.getLogger(WebServiceClient.class.getName());

    public static void main(String[] args) throws MalformedURLException {
        try {
            URL url = new URL("http://localhost:8080/PersonService?wsdl");
            wsdl_autogenerated.PersonService personService = new PersonService(url);
            OperationHandler operationHandler = new OperationHandler(personService);
            operationHandler.operationSelectDialog();
        } catch (ServerException | SOAPFaultException sfe){
            System.out.println("Server returned error: " + sfe.getMessage());
        }
    }


}
