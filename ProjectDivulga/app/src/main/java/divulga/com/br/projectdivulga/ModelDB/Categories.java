package divulga.com.br.projectdivulga.ModelDB;


/**
 *
 * @author PedroNeto
 */
public class Categories{
	private String cat_name;
	private int id;


    public String getCat_name(){
       return this.cat_name;
    }


    public void setCat_name(String cat_name){
        this.cat_name = cat_name;
    }


    public String getPk() {
        return "id";
    }

    public String getTable() {
        return "categories";
    }
}
