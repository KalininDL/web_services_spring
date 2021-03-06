
package wsdl_autogenerated;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "UnauthorizedException", targetNamespace = "http://web_services/")
public class UnauthorizedException
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private UnauthorizedFaultBean faultInfo;

    /**
     * 
     * @param message
     * @param faultInfo
     */
    public UnauthorizedException(String message, UnauthorizedFaultBean faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param message
     * @param faultInfo
     * @param cause
     */
    public UnauthorizedException(String message, UnauthorizedFaultBean faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: wsdl_autogenerated.UnauthorizedFaultBean
     */
    public UnauthorizedFaultBean getFaultInfo() {
        return faultInfo;
    }

}
