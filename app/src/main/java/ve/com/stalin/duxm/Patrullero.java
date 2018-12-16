package ve.com.stalin.duxm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Patrullero {

    private String id;
    private String nombre;
    private String cedula;
    private String activo;
    private String token;

    public Patrullero(String id, String nombre, String cedula, String activo, String token) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.activo = activo;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void refrescarDatos(final Configuracion config) {

        // Crear conexión al servicio REST
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(DuxApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear conexión a la API de Dux
        DuxApi duxApi = restAdapter.create(DuxApi.class);

        Call<Patrullero> actualizarPatrulleroCall = duxApi.actualizarPatrullero(new PostPatrulleroBody(nombre, cedula, token));

        actualizarPatrulleroCall.enqueue(new Callback<Patrullero>() {
            @Override
            public void onResponse(Call<Patrullero> call, Response<Patrullero> response) {

                if (response.isSuccessful()) {

                    config.activarToken();

                } else {

                    ApiError apiError = new ApiError(response.code(), response.errorBody());

                    for (String error: apiError.getErrors()) {
                        Log.e("Error: ", error);
                    }
                }
            }

            @Override
            public void onFailure(Call<Patrullero> call, Throwable t) {
                Log.e("Error: ", t.getMessage());
            }
        });
    }
}
