// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package web_services.util;

import org.mariadb.jdbc.MariaDbPoolDataSource;
import web_services.App;
import web_services.MariaDBDAO;
import web_services.errors.ServerException;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionUtil{

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T obj) throws SQLException;
    }

    @FunctionalInterface
    public interface SQLFunction<T, R> {
        R apply(T obj) throws SQLException;
    }


    private static class ConnectionUtilSingletonHoolder{
        public static final ConnectionUtil instance = new ConnectionUtil();
    }

    public static ConnectionUtil getInstance(){
        return ConnectionUtilSingletonHoolder.instance;
    }

    private ConnectionUtil(){
        try {
            prop.load(App.class.getClassLoader().getResourceAsStream("DBConnection.properties"));
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Connection connection;
    private Properties prop = new Properties();


    public  Connection getConnection() throws ServerException {
        try {
            if(connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(
                        prop.getProperty("JDBC_URL"),
                        prop.getProperty("JDBC_USER"),
                        prop.getProperty("JDBC_PASSWORD"));
            }
            return connection;
        } catch (SQLException ex) {
            Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
            throw new ServerException("Unable to connect to database");
        }
    }

    public <R> R statement(SQLFunction<? super Statement, ? extends R> function) throws ServerException{
        Objects.requireNonNull(function);
        try {
            return connection(conn -> {
                try(Statement stmt = conn.createStatement()){
                    return function.apply(stmt);
                }
            });
        } catch (SQLException e){
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new ServerException(e.getMessage());
        }
    }

    public <R> R preparedStatement(String sql, SQLFunction<? super PreparedStatement, ? extends R> function) throws ServerException{
        Objects.requireNonNull(function);
        try {
            return connection(conn -> {
                try(PreparedStatement stmt = conn.prepareStatement(sql)){
                    return function.apply(stmt);
                }
            });
        } catch (SQLException e){
            Logger.getLogger(MariaDBDAO.class.getName()).log(Level.SEVERE, null, e);
            throw new ServerException(e.getMessage());
        }
    }

    public void connection(SQLConsumer<? super Connection> consumer) throws ServerException, SQLException {
        Objects.requireNonNull(consumer);
        try(Connection conn = getConnection()){
            consumer.accept(conn);
        }
    }

    public <R> R connection(SQLFunction<? super Connection, ? extends R> function) throws ServerException, SQLException {
        Objects.requireNonNull(function);
        try (Connection conn = getConnection()){
            return function.apply(conn);
        }
    }
}