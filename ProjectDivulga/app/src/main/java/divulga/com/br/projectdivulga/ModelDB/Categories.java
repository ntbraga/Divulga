package divulga.com.br.projectdivulga.ModelDB;


import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 *
 * @author PedroNeto
 */
public class Categories extends RealmObject{
	private String cat_name;
    private int id_city;
    @PrimaryKey
	private int id;
    @Ignore
    private RealmList<Establishments> establishments;

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

    public RealmList<Establishments> getEstablishments() {
        return establishments;
    }

    public void setEstablishments(RealmList<Establishments> establishments) {
        this.establishments = establishments;
    }
}
