/**
 * @author Carlos
 * @author Adrián
 * @author Miguel
 * @author Nehuén
 * This class contains the data of the persons
 */

package finalproject.glamourfx.data;

public abstract class Person
{
    protected String name;

    public Person(String name)
    {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
