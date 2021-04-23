
package wsdl_autogenerated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the wsdl_autogenerated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SQLConvertException_QNAME = new QName("http://web_services/", "SQLConvertException");
    private final static QName _PersonDoesNotExistException_QNAME = new QName("http://web_services/", "PersonDoesNotExistException");
    private final static QName _EmptyRequestException_QNAME = new QName("http://web_services/", "EmptyRequestException");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: wsdl_autogenerated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PersonServiceFault }
     * 
     */
    public PersonServiceFault createPersonServiceFault() {
        return new PersonServiceFault();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link PersonArray }
     * 
     */
    public PersonArray createPersonArray() {
        return new PersonArray();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonServiceFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web_services/", name = "SQLConvertException")
    public JAXBElement<PersonServiceFault> createSQLConvertException(PersonServiceFault value) {
        return new JAXBElement<PersonServiceFault>(_SQLConvertException_QNAME, PersonServiceFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonServiceFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web_services/", name = "PersonDoesNotExistException")
    public JAXBElement<PersonServiceFault> createPersonDoesNotExistException(PersonServiceFault value) {
        return new JAXBElement<PersonServiceFault>(_PersonDoesNotExistException_QNAME, PersonServiceFault.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PersonServiceFault }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web_services/", name = "EmptyRequestException")
    public JAXBElement<PersonServiceFault> createEmptyRequestException(PersonServiceFault value) {
        return new JAXBElement<PersonServiceFault>(_EmptyRequestException_QNAME, PersonServiceFault.class, null, value);
    }

}
