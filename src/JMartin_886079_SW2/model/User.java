package JMartin_886079_SW2.model;

/**
 * Represents a User.
 */
public class User {
    /** the username of the user */
    private static String username;
    /** the user id of the user */
    private static int userID;

    /**
     * Creates a user object.
     * @param userID The user id of the logged in user.
     * @param username The user name of the logged in user.
     */
    public User(int userID, String username) {
        User.userID = userID;
        User.username = username;
    }

    /**
     * Sets the user id of the logged in user.
     * @param userID The user id of the logged in user.
     */
    public void setUserID(int userID) { User.userID = userID; }
    /**
     * Sets the username of the logged in user.
     * @param username The user name of the logged in user.
     */
    public void setUsername(String username) { User.username = username; }

    /**
     * Returns the username of the logged in user.
     * @return username of the logged in user.
     */
    public static String getUsername() {
        return User.username;
    }

    /**
     * Returns the user id of the logged in user
     * @return user id of the logged in user.
     */
    public static int getUserID() { return User.userID;}
}
