package model;

/** This class is responsible for the functionality of the User class. */
public class User {
    private int userId;
    private String userName;
    private String password;

    /** This constructor initializes the fields from the three parameters.
     *   @param userId to set userId
     *   @param userName to set userName
     *   @param password to set password
     */
    public User(int userId, String userName, String password) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the object as a String
     */
    @Override
    public String toString() {
        return "ID: " + userId + ", Name: " + userName;
    }
}
