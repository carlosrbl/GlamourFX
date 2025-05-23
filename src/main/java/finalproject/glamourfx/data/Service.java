package finalproject.glamourfx.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Service {
    private String name;
    private double price;
    private int duration;

    public Service(String name, double price, int duration) {
        this.name = name;
        this.price = price;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static ArrayList<Service> getServices() {
        ArrayList<Service> services = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader("services.txt")))
        {
            String line = "";
            String[] parts;
            while ((line = bf.readLine()) != null)
             {
                   parts = line.split(";");
                   services.add(new Service(parts[0], Double.parseDouble(parts[1]),
                           Integer.parseInt(parts[2])));
             }

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return services;
    }

    public static void storeInFile(List<Service> services) {
        try (PrintWriter pw = new PrintWriter("services.txt")) {

            for (Service s : services)
            {
                pw.println(s.getName()+";"+s.getPrice()+";"+s.getDuration());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return name + ", " + price + " €, " + duration + " min";
    }
}
