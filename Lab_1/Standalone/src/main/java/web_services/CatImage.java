package web_services;

import com.sun.xml.ws.developer.StreamingAttachment;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.jws.Oneway;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.soap.MTOM;

@StreamingAttachment(parseEagerly=true, memoryThreshold=40000L)
@MTOM
@WebService(name="CatImage")
public class CatImage {

        @XmlMimeType("application/octet-stream")
        @WebMethod
        public DataHandler fileDownload()
        {
            return  new DataHandler(
                    new FileDataSource("src/main/resources/cat.jpg"));
        }
}
