package divulga.com.br.projectdivulga.rest;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RestApi {

    public static final String BASE_URL = "http://dev.adrielov.com.br/api/";
    private static Retrofit retrofit = null;

    private static ApiInterface apiService;
    public static ApiInterface getApiInterface(){
        if(apiService == null)
        apiService =
                RestApi.getClient().create(ApiInterface.class);

        return apiService;
    }

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
