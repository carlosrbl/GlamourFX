package finalproject.glamourfx.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Hairdresser extends Person {
    private int stars;

    public Hairdresser(String name,int stars)
    {
        super(name);

    }

    public void reviseStars(int stars) {

    }

    public static List<Hairdresser> getHairdressers() {
        List<Hairdresser> hairdressers = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader("hairdressers.txt")))
        {
            String line = null;
            while ((line = bf.readLine()) != null)
            {
                String[] parts = line.split(";");
                hairdressers.add(new Hairdresser(parts[0], Integer.parseInt(parts[1])));
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return hairdressers;
    }


    public int getStars()
    {
        return stars;
    }

    public void setStars(int stars)
    {
        this.stars = stars;
    }


    public void storeInFile() {

    }


    @Override
    public String toString() {
        String sts = "✮".repeat(stars);
        return super.toString() + ", " + sts;
    }

}
