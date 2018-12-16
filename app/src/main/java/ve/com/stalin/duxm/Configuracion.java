package ve.com.stalin.duxm;

//import android.content.SharedPreferences;

import android.content.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences;

public class Configuracion {

    public static final String PREFS_NOMBRE = "datos_dux";
    private SharedPreferences preferencias;
    private SharedPreferences.Editor editor;


    public Configuracion(SharedPreferences preferencias) {

        this.preferencias = preferencias;
        this.editor = preferencias.edit();
    }

    public boolean estaRegistrado() {
        return !(this.preferencias.getString("cedula", "")=="" || this.preferencias.getString("nombre", "")=="");
    }

    public void registrarDispositivo(String nombre, String cedula, String token) {
        this.editor.putString("cedula", cedula);
        this.editor.putString("nombre", nombre);
        this.editor.putString("token", token);
        this.editor.putBoolean("tokenActivo", true);
        this.editor.commit();
    }

    public boolean estaTokenActivo() {
        return this.preferencias.getBoolean("tokenActivo", false);
    }

    public void desactivarToken() {
        this.editor.putBoolean("tokenActivo", false);
        this.editor.commit();
    }

    public void activarToken() {
        this.editor.putBoolean("tokenActivo", true);
        this.editor.commit();
    }

    public void setToken(String token) {
        this.editor.putString("token", token);
        this.editor.commit();
    }

    public String getNombre(){
        return this.preferencias.getString("nombre", "");
    }

    public String getCedula(){
        return this.preferencias.getString("cedula", "");
    }
}
