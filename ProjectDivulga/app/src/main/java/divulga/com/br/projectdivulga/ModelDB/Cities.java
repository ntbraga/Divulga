package divulga.com.br.projectdivulga.ModelDB;


import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 *
 * @author PedroNeto
 */
public class Cities extends RealmObject{
	private String city_name;
	private int id;
	private String city_state;
    private RealmList<Categories> categories;

    public Cities(){}

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

    public RealmList<Categories> getCategories() {
        return categories;
    }

    public void setCategories(RealmList<Categories> categories) {
        this.categories = categories;
    }
}
