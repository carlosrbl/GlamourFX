package finalproject.glamourfx.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
    protected Admin a;

    @BeforeEach
    void setUp() {
        a = new Admin("admin", "1234");
    }

    @Test
    void testToString() {
        assertEquals("admin", a.toString());
    }
}