
package wsdl_autogenerated;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "EmptyRequestException", targetNamespace = "http://web_services/")
public class EmptyRequestException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private PersonServiceFault faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public EmptyRequestException(String message, PersonServiceFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public EmptyRequestException(String message, PersonServiceFault faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: wsdl_autogenerated.PersonServiceFault
     */
    public PersonServiceFault getFaultInfo() {
        return faultInfo;
    }

}
