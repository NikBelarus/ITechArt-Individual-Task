package dao;

import org.apache.commons.dbcp.BasicDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PoolConnection{
    private Properties mainProperties = new Properties();
    private InputStream mainPropStream = ContactDAOImpl.class.getClassLoader().getResourceAsStream("main.properties");
    private BasicDataSource ds;
    private static PoolConnection instance = new PoolConnection();

    public static PoolConnection getInstance() {
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public void init() throws IOException {
        mainProperties.load(mainPropStream);
        ds = new BasicDataSource();
        ds.setDriverClassName(mainProperties.getProperty("db.driver"));
        ds.setUrl(mainProperties.getProperty("db.connectionUrl"));
        ds.setUsername(mainProperties.getProperty("db.user.name"));
        ds.setPassword(mainProperties.getProperty("db.user.password"));
    }
}