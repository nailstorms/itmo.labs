package lab8.orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {


    private Connection connection;

    public DB(String url, String login, String password) {
        try {
            connection = DriverManager.getConnection(url, login, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void execute(String sql) throws SQLException {
        connection.createStatement().execute(sql);
    }

    public void executeUpdate(String sql) throws SQLException {
        connection.createStatement().executeUpdate(sql);
    }

    public ResultSet fetch(String sql) throws SQLException {
        return connection.createStatement().executeQuery(sql);
    }

    public void close() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAutoCommit(Boolean b) {
        try {
            connection.setAutoCommit(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void commit() {
        try {
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
