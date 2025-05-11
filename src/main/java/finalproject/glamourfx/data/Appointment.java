package finalproject.glamourfx.data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class Appointment {
    private String name;
    private LocalDateTime time;
    private Customer customer;
    private Hairdresser hairdresser;
    private List<Service> services;
    private double totalPrice;
    private int totalDuration;

    public Appointment(String name, LocalDateTime time, Customer customer,
                       Hairdresser hairdresser, List<Service> services, double totalPrice, int totalDuration) {
        this.name = name;
        this.time = time;
        this.customer = customer;
        this.hairdresser = hairdresser;
        this.services = services;
        this.totalPrice = totalPrice;
        this.totalDuration = totalDuration;
    }

    private String showServices()
    {
        String[] listServices = new String[services.size()];
        for (int i = 0; i < listServices.length; i++)
        {
            listServices[i] = services.get(i).getName();
        }
        return Arrays.toString(listServices);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public Hairdresser getHairdresser() {
        return hairdresser;
    }

    public void setHairdresser(Hairdresser hairdresser) {
        this.hairdresser = hairdresser;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(int totalDuration) {
        this.totalDuration = totalDuration;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return name + ", Fecha y hora: " + time.format(formatter) + ", " + customer.getName() + ", " + hairdresser.getName() + ", Services: " + showServices() + ", " + totalPrice + " €, " + totalDuration + " min";
    }
}
