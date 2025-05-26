/**
 * @author Carlos
 * @author Adrián
 * @author Miguel
 * @author Nehuén
 * This class contains the data of the hairdressers
 */

package finalproject.glamourfx.data;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Hairdresser extends Person {
    private int stars;

    public Hairdresser(String name,int stars)
    {
        super(name);
        reviseStars(stars);
    }

    /**
     * Adjusts the star rating to ensure it is within the valid range (1-5).
     * @param stars The star rating to adjust.
     */
    public void reviseStars(int stars) {
        if (stars > 5)
        {
            this.stars = 5;
        }
        else if (stars < 1)
        {
            this.stars = 1;
        }
        else
        {
            this.stars = stars;
        }
    }

    public int getStars()
    {
        return stars;
    }

    public void setStars(int stars)
    {
        this.stars = stars;
    }

    /**
     * Stores a list of hairdressers in a file.
     * @param hairdressers The list of hairdressers to store.
     */
    public static void storeInFile(List<Hairdresser> hairdressers) {
        try (PrintWriter pw=new PrintWriter("hairdressers.txt"))
        {
            for(Hairdresser h:hairdressers){
                pw.println(h.getName()+";"+h.getStars());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a list of hairdressers from a file.
     * @return A list of hairdressers.
     */
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

    @Override
    public String toString() {
        String sts = "✮".repeat(stars);
        return super.toString() + ", " + sts;
    }

}
