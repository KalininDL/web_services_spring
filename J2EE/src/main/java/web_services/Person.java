package web_services;

public class Person {
    private String name;
    private String surname;
    private int age;
    private int id;
    private String country;
    private String gender;

    public Person() {
    }
    public Person(String name, String surname, int age, String country, String gender) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.country = country;
        this.gender = gender;
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