package divulga.com.br.projectdivulga.ModelDB;


/**
 *
 * @author PedroNeto
 */
public class Contacts{
	private int id;
	private String contact_phone;
	private int id_estab;
	private String contact_email;


    public String getContact_phone(){
       return this.contact_phone;
    }


    public void setContact_phone(String contact_phone){
        this.contact_phone = contact_phone;
    }


    public int getId_estab(){
       return this.id_estab;
    }


    public void setId_estab(int id_estab){
        this.id_estab = id_estab;
    }


    public String getContact_email(){
       return this.contact_email;
    }


    public void setContact_email(String contact_email){
        this.contact_email = contact_email;
    }


    public String getPk() {
        return "id";
    }

    public String getTable() {
        return "contacts";
    }
}
