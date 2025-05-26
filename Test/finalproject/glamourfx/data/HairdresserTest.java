package finalproject.glamourfx.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HairdresserTest {
    protected Hairdresser hairdresser;


    @BeforeEach
    void setUp() {
        hairdresser = new Hairdresser("Manolo",2);
    }

    @Test
    void reviseStarsTest() {

        hairdresser.reviseStars(-1);
        assertNotEquals(-1,hairdresser.getStars());
    }

    @Test
    void getStars() {
        assertEquals(2,hairdresser.getStars());
    }

    @Test
    void setStars() {
        hairdresser.setStars(4);
        assertEquals(4, hairdresser.getStars());
    }

}