package divulga.com.br.projectdivulga.ModelDB;


import divulga.com.br.projectdivulga.Adapters.ContactAdapter;
import io.realm.RealmObject;

/**
 *
 * @author PedroNeto
 */
public class Contacts extends RealmObject{
	private int id;
	private int id_estab;
	private String contact;
    private String type;

    public Contacts(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_estab(){
       return this.id_estab;
    }

    public void setId_estab(int id_estab){
        this.id_estab = id_estab;
    }

    public String getContact(){
       return this.contact;
    }

    public void setContact(String contact){
        this.contact = contact;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
