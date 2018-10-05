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

    public void execute(String sql)  {
        try {
            connection.createStatement().execute(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String sql) {
        try {
            connection.createStatement().executeUpdate(sql);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int executeUpdateGetId(String sql) {
        try {
            ResultSet res = connection.createStatement().executeQuery(sql);
            res.next();
            return res.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public ResultSet fetch(String sql) {
        try {
            return connection.createStatement().executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
