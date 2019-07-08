package dao;

import org.apache.log4j.Logger;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

public class BaseImpl {
    protected Connection connection;
    private static final Logger log = Logger.getLogger(BaseImpl.class);

    public void setConnection() {
        try {
            this.connection = PoolConnection.getInstance().getConnection();
        } catch (SQLException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
        }
    }

    public void setCurrentConnection(Connection connection){
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
        }
    }
}
