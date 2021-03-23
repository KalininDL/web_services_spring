package web_services;

import java.util.HashMap;
import java.util.Map;

public class SQLQueryBuilder {

    public String buildInsertQuery(Query query){
        HashMap<String, String> map = buildMap(query);

        StringBuilder sqlQuery = new StringBuilder("INSERT INTO persons (");
        int counter = 0;
        for (Map.Entry<String, String> e: map.entrySet()){
            counter++;
            sqlQuery.append(e.getKey());
            if (counter != map.size()) sqlQuery.append(", ");
        }
        counter = 0;
        sqlQuery.append(") VALUES (");
        for (Map.Entry<String, String> e: map.entrySet()){
            counter++;
            sqlQuery.append("\'"+e.getValue()+"\'");
            if (counter != map.size()) sqlQuery.append(", ");
        }
        sqlQuery.append(")");
        return sqlQuery.toString();
    }

    public String buildUpdateQuery(Query query, Query update){
        HashMap<String, String> map = buildMap(query);
        HashMap<String, String> updateMap = buildMap(update);
        StringBuilder sqlQuery = new StringBuilder("UPDATE persons set ");
        int counter = 0;
        for (Map.Entry<String, String> e: updateMap.entrySet()){
            counter++;
            sqlQuery.append(e.getKey() + " = " + "\'"+e.getValue()+"\'");
            if (counter != updateMap.size()) sqlQuery.append(", ");
        }
        counter = 0;
        sqlQuery.append(" WHERE ");
        for (Map.Entry<String, String> e: map.entrySet()){
            counter++;
            sqlQuery.append(e.getKey() + " = " + "\'"+e.getValue()+"\'");
            if (counter != map.size()) sqlQuery.append(" and ");
        }
        return sqlQuery.toString();
    }

    public String buildDeleteQuery(Query query){
        HashMap<String, String> map = buildMap(query);
        StringBuilder sqlQuery = new StringBuilder("DELETE * FROM persons WHERE ");
        int counter = 0;
        for (Map.Entry<String, String> e: map.entrySet()){
            counter++;
            sqlQuery.append(e.getKey() + " = " + "\'"+e.getValue()+"\'");
            if (counter != map.size()) sqlQuery.append(" and ");
        }
        return sqlQuery.toString();
    }

    public String buildSelectQuery(Query query){
        HashMap<String, String> map = buildMap(query);
        StringBuilder sqlQuery = new StringBuilder("select * from persons where ");
        int counter = 0;
        for (Map.Entry<String, String> e: map.entrySet()){
            counter++;
            sqlQuery.append(e.getKey() + " = " + "\'"+e.getValue()+"\'");
            if (counter != map.size()) sqlQuery.append(" and ");
        }
        return sqlQuery.toString();
    }

    private HashMap<String, String> buildMap(Query query){
        HashMap<String, String> queryMap = new HashMap<>();
        if (!query.getId().equals("")) queryMap.put("id", query.getId());
        if (!query.getName().equals("")) queryMap.put("name", query.getName());
        if (!query.getSurname().equals("")) queryMap.put("surname", query.getSurname());
        if (!query.getAge().equals("")) queryMap.put("age", query.getAge());
        if (!query.getCountry().equals("")) queryMap.put("country", query.getCountry());
        if (!query.getGender().equals("")) queryMap.put("gender", query.getGender());
        return queryMap;
    }

}
