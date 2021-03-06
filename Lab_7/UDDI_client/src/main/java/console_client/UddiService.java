package console_client;

import org.apache.juddi.api_v3.AccessPointType;
import org.apache.juddi.v3.client.config.UDDIClerk;
import org.apache.juddi.v3.client.config.UDDIClient;
import org.uddi.api_v3.*;
import org.uddi.v3_service.UDDIInquiryPortType;
import org.uddi.v3_service.UDDISecurityPortType;
import org.w3c.dom.Document;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import static java.lang.System.exit;

public class UddiService {
    private UDDIClerk clerk;

    private UDDISecurityPortType security;
    private UDDIInquiryPortType inquiry;
    private BusinessService businessService;

    public UddiService(String pathToUddiSettings) {
        try {
            UDDIClient uddiClient = new UDDIClient(pathToUddiSettings);
            this.clerk = uddiClient.getClerk("default");
            security = uddiClient.getTransport("default").getUDDISecurityService();
            inquiry = uddiClient.getTransport("default").getUDDIInquiryService();
            if (clerk == null)
                throw new Exception("the clerk wasn't found, check the config file!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Name getName(String name,
                         String lang) {
        Name busName = new Name();
        busName.setValue(name);
        busName.setLang(lang);
        return busName;
    }

    private String ListToString(List<Name> name) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < name.size(); i++) {
            sb.append(name.get(i).getValue()).append(" ");
        }
        return sb.toString();
    }

    private Description getDescription(String descr) {
        Description description = new Description();
        description.setValue(descr);
        description.setLang("ru-ru");
        return description;
    }

    private String ListToDescString(List<Description> name) {
        StringBuilder sb = new StringBuilder();
        for (Description description : name) {
            sb.append(description.getValue()).append(" ");
        }
        return sb.toString();
    }

    private BusinessEntity registerBusiness(String businessName) {
        BusinessEntity businessEntity = new BusinessEntity();

        businessEntity.getName().add(getName(businessName, "ru-ru"));
        businessEntity.getDescription().add(getDescription(businessName));
        BusinessEntity register = clerk.register(businessEntity);
        if (register == null) {
            System.out.println("???????????? ??????????????????????...");
            return null;
        }
        String businessKey = register.getBusinessKey();
        System.out.println("Business key:  " + businessKey);
        return register;
    }

    private BusinessService registerService(String businessKey,
                                            String servName,
                                            String URL) {
        BindingTemplate bindingTemplate = new BindingTemplate();
        AccessPoint accessPoint = new AccessPoint();
        accessPoint.setUseType(AccessPointType.WSDL_DEPLOYMENT.toString());
        accessPoint.setValue(URL);
        bindingTemplate.setAccessPoint(accessPoint);

        BindingTemplates bindingTemplates = new BindingTemplates();
        bindingTemplates.getBindingTemplate().add(bindingTemplate);

        BusinessService businessService = new BusinessService();
        businessService.getName().add(getName(servName, "ru-ru"));
        businessService.getDescription().add(getDescription(servName));
        businessService.setBusinessKey(businessKey);
        businessService.setBindingTemplates(bindingTemplates);
        BusinessService svc = clerk.register(businessService);

        return svc;
    }

    public String publish(String businessName,
                          String serviceName,
                          String URL) {
        try {
            BusinessEntity businessEntity = this.registerBusiness(businessName);
            if (businessEntity == null) {
                System.out.println("???????????? ?????????????????????? ??????????????...");
                exit(1);
            }

            BusinessService svc = registerService(businessEntity.getBusinessKey(),
                    serviceName,
                    URL);
            if (svc == null) {
                System.out.println("???????????????????????????? ???????????? ?????????????????????? ??????-??????????????.");
                exit(1);
            }

            businessService = svc;
            String serviceKey = svc.getServiceKey();
            System.out.println(String.format("ServiceKey %s ?????? ?????????????? %s", serviceKey, serviceName));

            System.out.println("?????????????????????? ?????????????? ?????????????????????? ??????????????.");
            return serviceKey;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void printServiceWSDLINFOToConsole(String WSDL_url) throws Exception {
        URL url = new URL(WSDL_url);
        URLConnection conn = url.openConnection();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(conn.getInputStream());

        TransformerFactory factory1 = TransformerFactory.newInstance();
        Transformer xform = factory1.newTransformer();

        File file = new File("write.xml");
        if (!file.exists()) {
            file.createNewFile();
        }

        StreamResult streamResult = new StreamResult();
        streamResult.setSystemId(file);

        xform.transform(new DOMSource(document), streamResult);
        xform.transform(new DOMSource(document), new StreamResult(System.out));
    }

    public void printServiceDetail(BusinessService businessService) throws Exception {
        if (businessService == null) {
            return;
        }
        System.out.println("Name: " + ListToString(businessService.getName()));
        System.out.println("Desc: " + ListToDescString(businessService.getDescription()));
        System.out.println("Key: " + (businessService.getServiceKey()));

        JAXB.marshal(businessService, System.out);

        printServiceWSDLINFOToConsole(businessService.getBindingTemplates().getBindingTemplate().get(0).getAccessPoint().getValue());

    }

    public void removeBusiness(String businessKey) {
        clerk.unRegisterBusiness(businessKey);
    }


    public void logOut() {
        clerk.discardAuthToken();
    }

    public BusinessService getBusinessService() {
        return businessService;
    }
}
