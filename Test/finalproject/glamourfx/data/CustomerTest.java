package finalproject.glamourfx.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    protected Customer c;
    @BeforeEach
    public void setUp() {
        c = new Customer("Marc", "1234", "email.com", "666");
    }

    @Test
    void testToString() {
        assertEquals("Marc, email.com, 666", c.toString());
        assertNotNull(c.toString());
    }

    @Test
    void getEmail() {
        assertEquals("email.com", c.getEmail());
        assertNotNull(c.getEmail());
    }

    @Test
    void setEmail() {
        c.setEmail("email2.com");
        assertNotSame("email.com", c.getEmail());
    }

    @Test
    void getPhoneNumber() {
        assertEquals("666", c.getPhoneNumber());
        assertNotNull(c.getPhoneNumber());
    }

    @Test
    void setPhoneNumber() {
        c.setPhoneNumber("787");
        assertNotSame("666", c.getPhoneNumber());
    }
}