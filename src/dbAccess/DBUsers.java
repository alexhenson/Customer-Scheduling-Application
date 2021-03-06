package dbAccess;

import dbConnection.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;

import java.sql.*;

/** This class is responsible for the SQL functionality for Appointment objects. */
public class DBUsers {
    /** This method uses SQL specific methods to access a database and create an ObservableList of User objects.
     *  @return ObservableList of User objects
     */
    public static ObservableList<User> getAllUsers() {
        ObservableList<User> ulist =FXCollections.observableArrayList();

        try {
            String sql = "SELECT User_ID, User_Name, Password FROM client_schedule.users;";
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
