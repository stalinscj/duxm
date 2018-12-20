package ve.com.stalin.duxm;

import android.util.Log;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Notificacion {

    private int id;
    private boolean entregada;
    private boolean alcanzado;
    private boolean atendida;
    private String fecha_entregada;
    private String fecha_atendida;
    private String fecha_lectura;
    private String placa;
    private String direccion;
    private String imagen_str;


    public Notificacion(int id, boolean entregada, boolean alcanzado, boolean atendida, String fecha_entregada, String fecha_atendida, String fecha_lectura, String placa, String direccion, String imagen_str) {
        this.id = id;
        this.entregada = entregada;
        this.alcanzado = alcanzado;
        this.atendida = atendida;
        this.fecha_entregada = fecha_entregada;
        this.fecha_atendida = fecha_atendida;
        this.fecha_lectura = fecha_lectura;
        this.placa = placa;
        this.direccion = direccion;
        this.imagen_str = imagen_str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEntregada() {
        return entregada;
    }

    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    public boolean isAlcanzado() {
        return alcanzado;
    }

    public void setAlcanzado(boolean alcanzado) {
        this.alcanzado = alcanzado;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }

    public String getFecha_entregada() {
        return fecha_entregada;
    }

    public void setFecha_entregada(String fecha_entregada) {
        this.fecha_entregada = fecha_entregada;
    }

    public String getFecha_atendida() {
        return fecha_atendida;
    }

    public void setFecha_atendida(String fecha_atendida) {
        this.fecha_atendida = fecha_atendida;
    }

    public String getFecha_lectura() {
        return fecha_lectura;
    }

    public void setFecha_lectura(String fecha_lectura) {
        this.fecha_lectura = fecha_lectura;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagen_str() {
        return imagen_str;
    }

    public void setImagen_str(String imagen_str) {
        this.imagen_str = imagen_str;
    }

    public static Notificacion getAlertaDetalle(int alertaId) {

        try {
            // Crear conexión al servicio REST
            Retrofit restAdapter = new Retrofit.Builder()
                    .baseUrl(DuxApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Crear conexión a la API de Dux
            DuxApi duxApi = restAdapter.create(DuxApi.class);

            Call<ResponseBody> getAlertaDetalleCall = duxApi.getAlertaDetalle(alertaId);
            ResponseBody response = getAlertaDetalleCall.execute().body();
            JsonObject json = (new JsonParser()).parse(response.string()).getAsJsonObject();

            return new Notificacion(
                alertaId,
                true,
                false,
                false,
                "",
                "",
                    json.get("fecha_lectura").getAsString(),
                    json.get("matricula").getAsString(),
                    json.get("direccion").getAsString(),
                    json.get("imagen").getAsString().replace("data:image/png;base64,", "")
            );

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int actualizarNotificacion(String alertaId, String patrulleroId, String entregada, String alcanzado, String atendida, String fecha_entregada, String fecha_atendida) {

        try {
            // Crear conexión al servicio REST
            Retrofit restAdapter = new Retrofit.Builder()
                    .baseUrl(DuxApi.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            // Crear conexión a la API de Dux
            DuxApi duxApi = restAdapter.create(DuxApi.class);

            PutNotificacionBody putNotificacionBody = new PutNotificacionBody(alertaId, patrulleroId, entregada, alcanzado, atendida, fecha_entregada, fecha_atendida);

            Call<ResponseBody> actualizarNotificacionCall = duxApi.actualizarNotificacion("0", putNotificacionBody);
            ResponseBody responseBody = actualizarNotificacionCall.execute().body();
            String response = responseBody.string();

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(response);
            JsonObject json = jsonElement.getAsJsonObject();

            return json.get("id").getAsInt();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
