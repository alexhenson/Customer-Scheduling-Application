package dbAccess;

import database.JDBC;
import javafx.beans.Observable;
import model.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.*;

public class DBUsers {
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> ulist =FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                User u = new User(userId, userName, password);
                ulist.add(u);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return ulist;
    }
}