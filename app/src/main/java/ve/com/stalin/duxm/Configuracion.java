package ve.com.stalin.duxm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.iid.FirebaseInstanceId;

public class Configuracion {

    public static final String DB_NAME = "dux_db";

    private Context context;
    private SQLite sqlite;
    private SQLiteDatabase db;

    public Configuracion(Context context) {
        this.context = context;
    }

    public boolean estaRegistrado() {
        return !this.getValor("nombre").isEmpty() && !this.getValor("cedula").isEmpty();
    }

    public boolean estaTokenActualizado() {
        String tokenActual  = FirebaseInstanceId.getInstance().getToken();
        String tokenGuarado = this.getValor("token");

        return tokenActual.equals(tokenGuarado);
    }

    public void registrarDispositivo(String id, String nombre, String cedula, String token) {
        this.sqlite = new SQLite(context, Configuracion.DB_NAME, null, 1);
        this.db = this.sqlite.getWritableDatabase();

        String[] claves  = {"id", "nombre", "cedula", "token"};
        String[] valores = {id, nombre,    cedula,   token};

        for (int i=0; i<4; i++){
            this.db.delete("configuraciones", "clave=?", new String[] {claves[i]});

            ContentValues values = new ContentValues();
            values.put("clave", claves[i]);
            values.put("valor", valores[i]);

            this.db.insert("configuraciones", null, values);
        }

        this.db.close();
    }

    public String getValor(String clave){
        this.sqlite = new SQLite(context, Configuracion.DB_NAME, null, 1);
        this.db = this.sqlite.getWritableDatabase();

        String valor = "";

        Cursor fila = this.db.rawQuery("SELECT valor FROM configuraciones WHERE clave=?", new String[] {clave});

        if (fila.moveToFirst()){
            valor = fila.getString(0);
        }

        this.db.close();

        return valor;
    }

    public void setValor(String clave, String valor){
        this.sqlite = new SQLite(context, Configuracion.DB_NAME, null, 1);
        this.db = this.sqlite.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("valor", valor);

        this.db.update("configuraciones", values, "clave=?", new String[] {clave});

        this.db.close();
    }

    public Patrullero getPatrullero() {
        return new Patrullero(this.getValor("id"), this.getValor("nombre"), this.getValor("cedula"), null, this.getValor("token"));
    }
}
