package finalproject.glamourfx.data;

public class Customer extends User {
    private String email;
    private String phoneNumber;

    public Customer(String name, String password, String email, String phoneNumber) {
        super(name, password);
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String toString() {
        return super.toString() + ", " + email + ", " + phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
