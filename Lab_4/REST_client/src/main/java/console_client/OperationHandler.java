package console_client;


import javax.xml.ws.soap.SOAPFaultException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.List;

public class OperationHandler {

    public OperationHandler(RestClient restClient) {
        this.client = restClient;
    }

    ConsoleInputReader inputReader = new ConsoleInputReader();
    RestClient client;

    public void operationSelectDialog(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Select operation: \n"
        + "\t1 - search\n" +
        "\t2 - add\n" +
        "\t3 - delete\n" +
        "\t4 - update\n" +
        "\t5 - exit\n");
        try {
            int choice = Integer.parseInt(reader.readLine());
            switch (choice){
                case 1:
                    selectQuery();
                    break;
                case 2:
                    insertQuery();
                    break;
                case 3:
                    deleteQuery();
                    break;
                case 4:
                    updateQuery();
                    break;
                case 5:
                    System.exit(0);
                default:
                    throw new IllegalStateException("Unexpected value: " + choice);
            }
        } catch (IOException ioException){
            System.out.println("IO Exception!");
            System.exit(-1);
        } catch (NumberFormatException |
        IllegalStateException illegalStateException){
            System.out.println("Incorrect choice! Please try again");
            operationSelectDialog();
        }
    }

    public void selectQuery()  {
        try {
            Person query = inputReader.readSearchInput();
            List<Person> persons = client.getPersons(query);
            for (Person person : persons) {
                System.out.println("name: " + person.getName() +
                        ", surname: " + person.getSurname() + ", age: " + person.getAge() +
                        ", country: " + person.getCountry() + ", gender: " + person.getGender());
            }
            System.out.println("Total persons: " + persons.size());
        } catch (IllegalStateException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertQuery()  {
        Person query = inputReader.readInsertInput();
        boolean result = client.addPerson(query);
        if (result)
            System.out.println("New person added successfully");
        else
            System.out.println("Server returned error");
    }

    public void updateQuery() {
        int id = inputReader.readNum("Enter the ID of the person you want to change");
        System.out.println("Insert updated information");
        Person newPerson = inputReader.readInsertInput();
        boolean result = client.updatePerson(id, newPerson);
        if (result)
            System.out.println("Person successfully updated");
        else
            System.out.println("Server returned error");
    }

    public void deleteQuery() throws SOAPFaultException {
        int id = inputReader.readNum("Enter the ID of the person you want to change");
        boolean result = client.deletePerson(id);
        if (result)
            System.out.println("Person was successfully deleted");
        else
            System.out.println("Server returned error");
    }

}
