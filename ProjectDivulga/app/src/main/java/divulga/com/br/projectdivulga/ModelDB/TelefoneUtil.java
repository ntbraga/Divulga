package divulga.com.br.projectdivulga.ModelDB;

import android.graphics.drawable.Drawable;

/**
 * Created by Taty Braga on 31/05/2016.
 */
public class TelefoneUtil {
    private String name;
    private String tel;
    private Drawable icon;

    public TelefoneUtil(String name, String tel, Drawable icon){
        this.name = name;
        this.tel = tel;
        this.icon = icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public String getTel() {
        return tel;
    }
}
