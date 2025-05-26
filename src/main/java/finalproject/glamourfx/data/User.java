/**
 * @author Carlos
 * @author Adrián
 * @author Miguel
 * @author Nehuén
 * This class contains the user data
 */

package finalproject.glamourfx.data;

public abstract class User extends Person {
    protected String password;

    public User(String name, String password) {
        super(name);
        this.password = password;
    }

    @Override
    public String toString() {
        return super.toString() + ", " + password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
