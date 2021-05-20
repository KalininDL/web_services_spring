package console_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleInputReader {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    public int readNum(String message){
        System.out.println(message);
        try {
            String age = reader.readLine();
            if (age.equals("")) return -1;
            else {
                try {
                    return Integer.parseInt(age);
                } catch (NumberFormatException e) {
                    System.out.println("Number is incorrect!");
                    return readNum(message);
                }
            }
        } catch (IOException e){
            System.out.println("Something went wrong, please try again");
            return readNum(message);
        }
    }

    public Person readInsertInput() {
        Person query = new Person();
        try {
            System.out.println("Enter name: ");
            query.setName(reader.readLine());
            System.out.println("Enter surname: ");
            query.setSurname(reader.readLine());
            query.setAge(readNum("Enter age: "));
            System.out.println("Enter country: ");
            query.setCountry(reader.readLine());
            System.out.println("Enter gender: ");
            query.setGender(reader.readLine());
            return query;
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
        return query;
    }

    public Person readSearchInput() {
        Person query = new Person();
        try {
            query.setId(readNum("Enter ID: "));
            System.out.println("Enter name: ");
            query.setName(reader.readLine());
            System.out.println("Enter surname: ");
            query.setSurname(reader.readLine());
            query.setAge(readNum("Enter age: "));
            System.out.println("Enter country: ");
            query.setCountry(reader.readLine());
            System.out.println("Enter gender: ");
            query.setGender(reader.readLine());
            return query;
        }
        catch (IOException e) {
            System.out.println(e.toString());
        }
        return query;
    }
}
