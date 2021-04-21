package web_services;

import javax.xml.ws.Endpoint;
public class App {
    public static void main(String[] args) {
        String url = "http://localhost:8080/PersonService";
        System.setProperty("com.sun.xml.ws.fault.SOAPFaultBuilder.disableCaptureStackTrace",
                "false");
        Endpoint.publish(url, new PersonWebService());
    }
}