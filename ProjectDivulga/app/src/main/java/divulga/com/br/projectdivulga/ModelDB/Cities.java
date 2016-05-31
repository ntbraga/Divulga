package divulga.com.br.projectdivulga.ModelDB;


import io.realm.RealmObject;

/**
 *
 * @author PedroNeto
 */
public class Cities extends RealmObject{
	private String city_name;
	private int id;
	private String city_state;

    public Cities(){}

    public Cities(int id, String city_name, String city_state){
        this.id = id;
        this.city_name = city_name;
        this.city_state = city_state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity_name(){
       return this.city_name;
    }


    public void setCity_name(String city_name){
        this.city_name = city_name;
    }


    public String getCity_state(){
       return this.city_state;
    }


    public void setCity_state(String city_state){
        this.city_state = city_state;
    }


}
