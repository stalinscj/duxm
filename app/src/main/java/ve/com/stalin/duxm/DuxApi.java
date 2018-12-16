package ve.com.stalin.duxm;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DuxApi {

    public static final String BASE_URL = "http://192.168.0.100:9000/api/";

    @POST("patrulleros/")
    Call<Patrullero> registrarPatrullero(@Body PostPatrulleroBody postPatrulleroBody);

    @PUT("patrulleros/{id}/")
    Call<Patrullero> actualizarPatrullero(@Path("id") String id, @Body PutPatrulleroBody putPatrulleroBody);

}
