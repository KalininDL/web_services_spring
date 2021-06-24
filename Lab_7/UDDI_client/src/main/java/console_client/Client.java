package console_client;

import java.util.logging.Logger;

public class Client {

    private final static Logger log = Logger.getLogger(Client.class.getName());

    public static void main(String[] args) {
        try {
            UddiService uddiService =
                    new UddiService("uddi.xml");
            String URL = "http://localhost:8081/PersonService?wsdl";
            String serviceKey = uddiService.publish("Persons", "PersonService", URL);

            System.in.read();
            uddiService.removeBusiness(serviceKey);
            uddiService.logOut();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
