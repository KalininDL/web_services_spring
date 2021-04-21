package web_services.util;

import web_services.errors.EmptyRequestException;
import web_services.errors.faultbeans.PersonServiceFault;
import web_services.model.Query;
import web_services.SQLConvertable;

import java.util.HashMap;
import java.util.Map;

public class SQLQueryBuilder {

    public String buildInsertQuery(SQLConvertable query){
        HashMap<String, String> map = query.buildMap();
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

    public String buildUpdateQuery(SQLConvertable query, Query update){
        HashMap<String, String> map = query.buildMap();
        HashMap<String, String> updateMap = update.buildMap();
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

    public String buildDeleteQuery(SQLConvertable query){
        HashMap<String, String> map = query.buildMap();
        StringBuilder sqlQuery = new StringBuilder("DELETE FROM persons WHERE ");
        int counter = 0;
        for (Map.Entry<String, String> e: map.entrySet()){
            counter++;
            sqlQuery.append(e.getKey() + " = " + "\'"+e.getValue()+"\'");
            if (counter != map.size()) sqlQuery.append(" and ");
        }
        return sqlQuery.toString();
    }

    public String buildSelectQuery(SQLConvertable query) throws EmptyRequestException {
        HashMap<String, String> map = query.buildMap();
        PersonServiceFault fault = new PersonServiceFault();
        if(map.size() == 0) throw new EmptyRequestException("Query is empty!", fault);
        StringBuilder sqlQuery = new StringBuilder("SELECT * FROM persons WHERE ");
        int counter = 0;
        for (Map.Entry<String, String> e: map.entrySet()){
            counter++;
            sqlQuery.append(e.getKey() + " = " + "\'"+e.getValue()+"\'");
            if (counter != map.size()) sqlQuery.append(" and ");
        }
        return sqlQuery.toString();
    }

}
