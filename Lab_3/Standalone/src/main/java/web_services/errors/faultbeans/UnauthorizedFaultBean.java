package web_services.errors.faultbeans;

public class UnauthorizedFaultBean {
    private static final String DEFAULT_MESSAGE = "You should be authorized to use this method";

    protected String message;
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public static UnauthorizedFaultBean defaultInstance() {
        UnauthorizedFaultBean fault = new UnauthorizedFaultBean();
        fault.message = DEFAULT_MESSAGE;
        return fault;
    }
}
