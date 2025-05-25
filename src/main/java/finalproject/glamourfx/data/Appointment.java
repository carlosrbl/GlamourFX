package finalproject.glamourfx.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Appointment {
    private LocalDateTime time;
    private String customer;
    private String hairdresser;
    private double totalPrice;

    public Appointment(LocalDateTime time, String customer,
                       String hairdresser,double totalPrice) {
        this.time = time;
        this.customer = customer;
        this.hairdresser = hairdresser;
        this.totalPrice = totalPrice;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getHairdresser() {
        return hairdresser;
    }

    public void setHairdresser(String hairdresser) {
        this.hairdresser = hairdresser;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return " Fecha y hora: " + time.format(formatter) + ", " + customer + ", " + hairdresser + ", " + totalPrice + " €, ";
    }
}
