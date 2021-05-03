// This is a personal academic project. Dear PVS-Studio, please check it.

// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com
package web_services.util;

import org.mariadb.jdbc.MariaDbPoolDataSource;
import web_services.App;
import web_services.MariaDBDAO;
import web_services.errors.ServerException;

import java.io.IOException;
import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ConnectionUtil implements AutoCloseable{

    @FunctionalInterface
    public interface SQLConsumer<T> {
        void accept(T obj) throws SQLException;
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
                    System.out.println("Getting new connection");
                connection = DriverManager.getConnection(
                        prop.getProperty("JDBC_URL"),
                        prop.getProperty("JDBC_USER"),
                        prop.getProperty("JDBC_PASSWORD"));
                } else return connection;
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionUtil.class.getName()).log(Level.SEVERE, null, ex);
                throw new ServerException("Unable to connect to database");
            }
            return connection;
    }

    public void connection(SQLConsumer<? super Connection> consumer) throws ServerException, SQLException {
        Objects.requireNonNull(consumer);
        try(Connection conn = getConnection()){
            consumer.accept(conn);
        }
    }

    @Override
    public void close() throws Exception {
        getConnection().close();
    }
}
