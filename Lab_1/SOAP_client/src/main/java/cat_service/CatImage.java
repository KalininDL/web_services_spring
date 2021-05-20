
package cat_service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CatImage", targetNamespace = "http://web_services/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CatImage {


    /**
     * 
     * @return
     *     returns javax.activation.DataHandler
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "fileDownload", targetNamespace = "http://web_services/", className = "cat_service.FileDownload")
    @ResponseWrapper(localName = "fileDownloadResponse", targetNamespace = "http://web_services/", className = "cat_service.FileDownloadResponse")
    public DataHandler fileDownload();

}