
package wsdl_autogenerated;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "PersonWebService", targetNamespace = "http://web_services/")
@SOAPBinding(style = SOAPBinding.Style.RPC)
@XmlSeeAlso({
    ObjectFactory.class
})
public interface PersonWebService {


    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws ServerException
     */
    @WebMethod
    @WebResult(partName = "return")
    public String insertPersonByQueryClass(
        @WebParam(name = "arg0", partName = "arg0")
        Person arg0)
        throws ServerException
    ;

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws ServerException
     */
    @WebMethod
    @WebResult(partName = "return")
    public String updatePersonByQueryClass(
        @WebParam(name = "arg0", partName = "arg0")
        Person arg0,
        @WebParam(name = "arg1", partName = "arg1")
        Person arg1)
        throws ServerException
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns wsdl_autogenerated.PersonArray
     * @throws ServerException
     */
    @WebMethod
    @WebResult(partName = "return")
    public PersonArray selectPersonsByQueryClass(
        @WebParam(name = "arg0", partName = "arg0")
        Person arg0)
        throws ServerException
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns java.lang.String
     * @throws ServerException
     */
    @WebMethod
    @WebResult(partName = "return")
    public String deletePersonByQueryClass(
        @WebParam(name = "arg0", partName = "arg0")
        Person arg0)
        throws ServerException
    ;

    /**
     * 
     * @return
     *     returns wsdl_autogenerated.PersonArray
     * @throws ServerException
     */
    @WebMethod
    @WebResult(partName = "return")
    public PersonArray getPersons()
        throws ServerException
    ;

}
