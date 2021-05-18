
package cat_service;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cat_service package. 
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

    private final static QName _FileDownloadResponse_QNAME = new QName("http://web_services/", "fileDownloadResponse");
    private final static QName _FileDownload_QNAME = new QName("http://web_services/", "fileDownload");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cat_service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FileDownload }
     * 
     */
    public FileDownload createFileDownload() {
        return new FileDownload();
    }

    /**
     * Create an instance of {@link FileDownloadResponse }
     * 
     */
    public FileDownloadResponse createFileDownloadResponse() {
        return new FileDownloadResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileDownloadResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web_services/", name = "fileDownloadResponse")
    public JAXBElement<FileDownloadResponse> createFileDownloadResponse(FileDownloadResponse value) {
        return new JAXBElement<FileDownloadResponse>(_FileDownloadResponse_QNAME, FileDownloadResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FileDownload }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://web_services/", name = "fileDownload")
    public JAXBElement<FileDownload> createFileDownload(FileDownload value) {
        return new JAXBElement<FileDownload>(_FileDownload_QNAME, FileDownload.class, null, value);
    }

}
