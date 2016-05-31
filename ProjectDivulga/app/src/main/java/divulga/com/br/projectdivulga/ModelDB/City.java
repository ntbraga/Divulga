package divulga.com.br.projectdivulga.ModelDB;

/**
 * Created by Taty Braga on 30/05/2016.
 */
public class City {
    int id;
    String name;
    String state;

    public City(int id, String name, String state){
        this.id = id;
        this.name = name;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }
}
