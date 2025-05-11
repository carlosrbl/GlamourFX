package finalproject.glamourfx.data;

public class Hairdresser extends Person {
    private int stars;

    public Hairdresser(String name,int stars)
    {
        super(name);
        if (stars > 5 || stars < 0)
        {
            this.stars = 1;
        }
        else {
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

    @Override
    public String toString() {
        String sts = "✮".repeat(stars);
        return super.toString() + ", " + sts;
    }

}
