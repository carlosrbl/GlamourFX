package finalproject.glamourfx.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Appointment {
    private LocalDate time;
    private String customer;
    private String hairdresser;
    private double totalPrice;
    private String service;

    public Appointment(LocalDate time, String customer,
                       String hairdresser, double totalPrice, String service) {
        this.time = time;
        this.customer = customer;
        this.hairdresser = hairdresser;
        this.totalPrice = totalPrice;
        this.service = service;
    }


    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
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

    public String getService() {return service;}

    public void setService(String service) {this.service = service;}

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return "Service "+ service + ", date: " + time.format(formatter) + ", client: " + customer + ", hairdresser: " + hairdresser + ",total price: " + totalPrice + " €, ";
    }
}
