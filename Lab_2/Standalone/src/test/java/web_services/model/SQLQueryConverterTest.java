package web_services.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import web_services.errors.SQLConvertException;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class SQLQueryConverterTest {

    private static SQLQueryConverter converter;

    @BeforeAll
    private static void init(){
        converter = new SQLQueryConverter();
    }


    @Test
    void getQueryMapTest() {
        Person p = new Person();
        p.setCountry("Russia");
        p.setName("Name");
        p.setAge(99);
        try {
            HashMap<String, String> map = converter.getQueryMap(p);
            map.forEach((k, v) -> {
                System.out.println(k + " : " + v);
            });
        }
        catch (SQLConvertException sce){
            System.out.println(sce.getMessage());
        }
    }
}