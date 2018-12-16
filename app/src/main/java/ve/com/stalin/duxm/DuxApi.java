package ve.com.stalin.duxm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface DuxApi {

    public static final String BASE_URL = "http://192.168.0.100:9000/api/";

    @POST("patrulleros/")
    Call<Patrullero> registrarPatrullero(@Body PostPatrulleroBody postPatrulleroBody);

    @PUT("patrulleros/")
    Call<Patrullero> actualizarPatrullero(@Body PostPatrulleroBody postPatrulleroBody);

}