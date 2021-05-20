package console_client;

public class App {
    private static final String URL = "http://localhost:8080/rest/persons";

    public static void main(String[] args) {
        OperationHandler operationHandler = new OperationHandler(new RestClient(URL));
        operationHandler.operationSelectDialog();
    }
}