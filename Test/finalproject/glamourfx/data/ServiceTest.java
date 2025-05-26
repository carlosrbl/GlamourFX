package finalproject.glamourfx.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    protected Service s;

    @BeforeEach
    void setUp() {
        s = new Service("Corte de pelo + tinte", 20.4);
    }

    @Test
    void getName() {
        assertEquals("Corte de pelo + tinte", s.getName());
    }

    @Test
    void setName() {
        s.setName("Corte de pelo + perfilado");
        assertEquals("Corte de pelo + perfilado", s.getName());
    }

    @Test
    void getPrice() {
        assertEquals(20.4, s.getPrice());
    }

    @Test
    void setPrice() {
        s.setPrice(19.2);
        assertEquals(19.2, s.getPrice());
    }

    @Test
    void testToString() {
        assertEquals("Corte de pelo + tinte, 20.4 €", s.toString());
    }
}