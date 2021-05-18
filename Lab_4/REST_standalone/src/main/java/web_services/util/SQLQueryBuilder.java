// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package web_services.util;

import web_services.MariaDBDAO;
import web_services.errors.EmptyRequestException;
import web_services.errors.SQLConvertException;
import web_services.errors.faultbeans.PersonServiceFault;
import web_services.SQLConvertable;
import web_services.model.SQLQueryConverter;

import java.util.HashMap;
import java.util.Map;

public class SQLQueryBuilder {

    private SQLQueryConverter converter = new SQLQueryConverter();

    public String buildInsertQuery(SQLConvertable query) throws SQLConvertException {
            HashMap<String, String> map = converter.getQueryMap(query);
            StringBuilder sqlQuery = new StringBuilder("INSERT INTO persons (");
            int counter = 0;
            for (Map.Entry<String, String> e : map.entrySet()) {
                counter++;
                sqlQuery.append(e.getKey());
                if (counter != map.size()) sqlQuery.append(", ");
            }
            counter = 0;
            sqlQuery.append(") VALUES (");
            for (Map.Entry<String, String> e : map.entrySet()) {
                counter++;
                sqlQuery.append("\'" + e.getValue() + "\'");
                if (counter != map.size()) sqlQuery.append(", ");
            }
            sqlQuery.append(")");
            return sqlQuery.toString();
    }

    public String buildUpdateQuery(SQLConvertable query, SQLConvertable update) throws SQLConvertException {
        HashMap<String, String> map = converter.getQueryMap(query);
        HashMap<String, String> updateMap = converter.getQueryMap(update);
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

    public String buildDeleteQuery(SQLConvertable query) throws SQLConvertException {
        HashMap<String, String> map = converter.getQueryMap(query);
        StringBuilder sqlQuery = new StringBuilder("DELETE FROM persons WHERE ");
        int counter = 0;
        for (Map.Entry<String, String> e: map.entrySet()){
            counter++;
            sqlQuery.append(e.getKey() + " = " + "\'"+e.getValue()+"\'");
            if (counter != map.size()) sqlQuery.append(" and ");
        }
        return sqlQuery.toString();
    }

    public String buildSelectQuery(SQLConvertable query) throws EmptyRequestException, SQLConvertException {
        HashMap<String, String> map = converter.getQueryMap(query);
        PersonServiceFault fault = new PersonServiceFault();
        if(map.size() == 0) throw new EmptyRequestException("Query is empty!");
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
