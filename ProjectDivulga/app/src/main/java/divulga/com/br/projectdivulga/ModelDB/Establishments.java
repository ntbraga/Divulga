package divulga.com.br.projectdivulga.ModelDB;


import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 *
 * @author PedroNeto
 */
public class Establishments extends RealmObject{
	private int id_city;
	private String address_complement;
	private String estab_name;
	private String address_street;
	private int id_cat;
    @PrimaryKey
	private int id;
	private String address_zip;
	private String address_district;
	private String estab_description;
	private String address_number;
    private String opening_time;
    private String closing_time;
    private RealmList<Contacts> contacts;

    @com.google.gson.annotations.Expose(deserialize = false, serialize = false)
    private boolean favorite;

    public Establishments(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_city(){
       return this.id_city;
    }


    public void setId_city(int id_city){
        this.id_city = id_city;
    }


    public String getAddress_complement(){
       return this.address_complement;
    }


    public void setAddress_complement(String address_complement){
        this.address_complement = address_complement;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getEstab_name(){
       return this.estab_name;
    }


    public void setEstab_name(String estab_name){
        this.estab_name = estab_name;
    }


    public String getAddress_street(){
       return this.address_street;
    }


    public void setAddress_street(String address_street){
        this.address_street = address_street;
    }


    public int getId_cat(){
       return this.id_cat;
    }


    public void setId_cat(int id_cat){
        this.id_cat = id_cat;
    }


    public String getAddress_zip(){
       return this.address_zip;
    }


    public void setAddress_zip(String address_zip){
        this.address_zip = address_zip;
    }


    public String getAddress_district(){
       return this.address_district;
    }


    public void setAddress_district(String address_district){
        this.address_district = address_district;
    }


    public String getEstab_description(){
       return this.estab_description;
    }


    public void setEstab_description(String estab_description){
        this.estab_description = estab_description;
    }


    public String getAddress_number(){
       return this.address_number;
    }


    public void setAddress_number(String address_number){
        this.address_number = address_number;
    }

    public RealmList<Contacts> getContacts() {
        return contacts;
    }

    public void setContacts(RealmList<Contacts> contacts) {
        this.contacts = contacts;
    }

    public String getOpening_time() {
        return opening_time;
    }

    public void setOpening_time(String opening_time) {
        this.opening_time = opening_time;
    }

    public void setClosing_time(String closing_time) {
        this.closing_time = closing_time;
    }

    public String getClosing_time() {
        return closing_time;
    }
}
