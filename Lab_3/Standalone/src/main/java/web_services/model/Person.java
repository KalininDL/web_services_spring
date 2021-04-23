package web_services.model;

import web_services.SQLConvertable;
import web_services.annotations.QueryClass;
import web_services.annotations.QueryField;

@QueryClass
public class Person implements SQLConvertable {

    @QueryField
    private String name;

    @QueryField
    private String surname;

    @QueryField
    private int age;

    @QueryField
    private int id;

    @QueryField
    private String country;

    @QueryField
    private String gender;

    public int getId() {
        return id;
    }

    public Person(String name, String surname, int age, String country, String gender) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.country = country;
        this.gender = gender;
    }

    public Person(){

    }


    public String getName() {
        return name;
    }
    public String getSurname() {
        return surname;
    }
    public String getCountry() { return country;}
    public String getGender() { return gender;}
    public int getAge() {
        return age;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setCountry(String country) {this.country = country;}
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Person{" + "name=" + name + ", surname=" + surname + ", age=" + age + ", country=" + country + ", gender=" + gender + '}';
    }
}