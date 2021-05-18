package web_services;
import javax.xml.ws.Endpoint;

public class App {
    public static void main(String[] args) {
        String url = "http://localhost:8080/PersonService";
        String catUrl = "http://localhost:8080/CatImageService";
        System.setProperty("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace",
                "false");
        Endpoint.publish(url, new PersonWebService());
        Endpoint.publish(catUrl, new CatImage());
    }
}