package ve.com.stalin.duxm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.firebase.iid.FirebaseInstanceId;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Random;

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

    public void llenarNotificaciones () {

        this.sqlite = new SQLite(context, Configuracion.DB_NAME, null, 1);
        this.db = this.sqlite.getWritableDatabase();
        this.db.execSQL("DELETE FROM notificaciones");

        String[] direcciones = {"Puerto Ordaz - San Félix", "San Félix - Puerto Ordaz", "Ciudad Bolívar - Puerto Ordaz", "Puerto Ordaz - Ciudad Bolívar"};

        for (int i = 0; i < 0; i++) {
            int id = i;

            Random random = new Random();

            boolean entregada = random.nextBoolean();
            boolean alcanzado = random.nextBoolean();
            boolean atendida  = random.nextBoolean();

            long rangebegin = Timestamp.valueOf("2010-02-08 00:00:00").getTime();
            long rangeend = Timestamp.valueOf("2018-12-31 23:59:59").getTime();
            long diff = rangeend - rangebegin + 1;

            String fecha_entregada = new Timestamp(rangebegin + (long)(Math.random() * diff)).toString();
            String fecha_atendida = new Timestamp(rangebegin + (long)(Math.random() * diff)).toString();
            String fecha_lectura = new Timestamp(rangebegin + (long)(Math.random() * diff)).toString();

            byte[] array = new byte[6];
            new Random().nextBytes(array);

            String placa = i+new String(array, Charset.forName("UTF-8"));

            Random r = new Random();
            int randomNumber=r.nextInt(direcciones.length);

            String direccion = direcciones[randomNumber];

            String imagen_str = "----"+placa+"----";

            Notificacion notificacion = new Notificacion(id, entregada, alcanzado, atendida, fecha_entregada, fecha_atendida, fecha_lectura, placa, direccion, imagen_str);

            ContentValues values = new ContentValues();

            values.put("id", id);
            values.put("entregada", entregada);
            values.put("alcanzado", alcanzado);
            values.put("atendida", atendida);
            values.put("fecha_entregada", fecha_entregada);
            values.put("fecha_atendida", fecha_atendida);
            values.put("fecha_lectura", fecha_lectura);
            values.put("placa", placa);
            values.put("direccion", direccion);
            values.put("imagen_str", imagen_str);

            this.db.insert("notificaciones", null, values);

        }

        this.db.close();

    }

    public ArrayList<Notificacion> getNotificaciones () {
        this.sqlite = new SQLite(context, Configuracion.DB_NAME, null, 1);
        this.db = this.sqlite.getWritableDatabase();

        ArrayList<Notificacion> notificaciones = new ArrayList<Notificacion>();

        Cursor fila = this.db.rawQuery("SELECT * FROM notificaciones ORDER BY id DESC", null);

        while (fila.moveToNext()){

            int id = fila.getInt(0);
            boolean entregada = fila.getInt(1)==1 ? true:false;
            boolean alcanzado = fila.getInt(2)==1 ? true:false;
            boolean atendida = fila.getInt(3)==1 ? true:false;
            String fecha_entregada = fila.getString(4);
            String fecha_atendida = fila.getString(5);
            String fecha_lectura = fila.getString(6);
            String placa = fila.getString(7);
            String direccion = fila.getString(8);
            String imagen_str = fila.getString(9);

            Notificacion notificacion = new Notificacion(id, entregada, alcanzado, atendida, fecha_entregada, fecha_atendida, fecha_lectura, placa, direccion, imagen_str);

            notificaciones.add(notificacion);
        }

        this.db.close();

        return notificaciones;
    }

    public void guardarNotificacion(Notificacion notificacion) {
        this.sqlite = new SQLite(context, Configuracion.DB_NAME, null, 1);
        this.db = this.sqlite.getWritableDatabase();

        this.db.execSQL("DELETE FROM notificaciones WHERE id="+notificacion.getId());

        ContentValues values = new ContentValues();
        values.put("id", notificacion.getId());
        values.put("entregada", notificacion.isEntregada());
        values.put("alcanzado", notificacion.isAlcanzado());
        values.put("atendida", notificacion.isAtendida());
        values.put("fecha_entregada", notificacion.getFecha_entregada());
        values.put("fecha_atendida", notificacion.getFecha_atendida());
        values.put("fecha_lectura", notificacion.getFecha_lectura());
        values.put("placa", notificacion.getPlaca());
        values.put("direccion", notificacion.getDireccion());
        values.put("imagen_str", notificacion.getImagen_str());

        this.db.insert("notificaciones", null, values);

        this.db.close();

    }

    public Notificacion getNotificacion (int idNotificacion) {
        this.sqlite = new SQLite(context, Configuracion.DB_NAME, null, 1);
        this.db = this.sqlite.getWritableDatabase();

        Notificacion notificacion = null;

        Cursor fila = this.db.rawQuery("SELECT * FROM notificaciones WHERE id="+idNotificacion, null);

        if (fila.moveToFirst()){

            int id = fila.getInt(0);
            boolean entregada = fila.getInt(1)==1 ? true:false;
            boolean alcanzado = fila.getInt(2)==1 ? true:false;
            boolean atendida = fila.getInt(3)==1 ? true:false;
            String fecha_entregada = fila.getString(4);
            String fecha_atendida = fila.getString(5);
            String fecha_lectura = fila.getString(6);
            String placa = fila.getString(7);
            String direccion = fila.getString(8);
            String imagen_str = fila.getString(9);

            notificacion = new Notificacion(id, entregada, alcanzado, atendida, fecha_entregada, fecha_atendida, fecha_lectura, placa, direccion, imagen_str);
        }

        this.db.close();

        return notificacion;
    }

    public void eliminarNotificacion(int id) {
        this.sqlite = new SQLite(context, Configuracion.DB_NAME, null, 1);
        this.db = this.sqlite.getWritableDatabase();

        this.db.execSQL("DELETE FROM notificaciones WHERE id="+id);

        this.db.close();
    }
}
