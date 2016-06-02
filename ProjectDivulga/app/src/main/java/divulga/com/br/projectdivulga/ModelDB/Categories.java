package divulga.com.br.projectdivulga.ModelDB;


import io.realm.RealmObject;

/**
 *
 * @author PedroNeto
 */
public class Categories extends RealmObject{
	private String cat_name;
    private int id_city;
	private int id;

    public Categories(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCat_name(){
       return this.cat_name;
    }

    public void setCat_name(String cat_name){
        this.cat_name = cat_name;
    }

    public void setId_city(int id_city) {
        this.id_city = id_city;
    }

    public int getId_city() {
        return id_city;
    }
}
