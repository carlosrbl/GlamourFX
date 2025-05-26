/**
 * @author Carlos
 * @author Adrián
 * @author Miguel
 * @author Nehuén
 * This class contains the data of the appointments
 */

package finalproject.glamourfx.data;

import finalproject.glamourfx.controllers.SessionManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return hairdresser + ", " + service + ", " + time.format(formatter) + ", " + totalPrice + " €";
    }

    public static List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader("reservations.txt")))
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String line = null;
            while ((line = bf.readLine()) != null)
            {
                String[] parts = line.split(";");
                if (parts[4].equalsIgnoreCase(SessionManager.getCurrentCustomer().getName()))
                {
                    appointments.add(new Appointment(LocalDate.parse(parts[2], formatter), parts[4], parts[0], Double.parseDouble(parts[3]), parts[1]));
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return appointments;
    }
}
