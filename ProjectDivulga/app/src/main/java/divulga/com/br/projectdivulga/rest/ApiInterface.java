package divulga.com.br.projectdivulga.rest;

import java.util.List;

import divulga.com.br.projectdivulga.ModelDB.Categories;
import divulga.com.br.projectdivulga.ModelDB.Cities;
import divulga.com.br.projectdivulga.ModelDB.Establishments;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by ntbra on 04/06/2016.
 */
public interface ApiInterface {


    @GET("cities")
    Call<List<Cities>> getAllCities();

    @GET("cities/{id}")
    Call<Cities> getCity(@Path("id") int id);

    @GET("categories/{id}")
    Call<Cities> getCategories(@Path("id") int id);

    @GET("establishments/{id}")
    Call<Establishments> getEstablishment(@Path("id") int id);
}
