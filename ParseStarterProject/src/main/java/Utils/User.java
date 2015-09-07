package Utils;

/**
 * Created by gonzalo on 9/7/2015.
 */
public class User {
    private static User ourInstance = new User();

    public static User getInstance() {
        return ourInstance;
    }

    private User() {
    }
}
