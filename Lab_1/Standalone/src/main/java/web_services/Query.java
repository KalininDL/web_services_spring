package web_services;

import java.util.HashMap;
import java.util.Map;

public class Query {

    private String id = "";
    private String age = "";
    private String name = "";
    private String surname = "";
    private String country = "";
    private String gender = "";


    public Query(){
    }

    public Query(String age, String name, String surname,  String country, String gender) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.surname = surname;
        this.country = country;
        this.gender = gender;
    }

    public String getAge() { return age; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getCountry() { return country; }
    public String getGender() { return gender; }
    public String getId() { return id; }

    public void setId(String id) { this.id = id; }
    public void setAge(String age) { this.age = age; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setCountry(String country) { this.country = country; }
    public void setGender(String gender) { this.gender = gender; }

    @Override
    public String toString(){
        return "";
    }
}
