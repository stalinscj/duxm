package ve.com.stalin.duxm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLite extends SQLiteOpenHelper {

    public SQLite(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE configuraciones (" +
                    "id    INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "clave TEXT NOT NULL UNIQUE," +
                    "valor TEXT NOT NULL" +
                ")");

        db.execSQL("CREATE TABLE notificaciones (" +
                    "id              INTEGER PRIMARY KEY," +
                    "entregada       TEXT NOT NULL CHECK (entregada IN ('0','1'))," +
                    "alcanzado       TEXT NOT NULL CHECK (alcanzado IN ('0','1'))," +
                    "atendida        TEXT NOT NULL CHECK (atendida IN ('0','1'))," +
                    "fecha_entregada TEXT," +
                    "fecha_atendida  TEXT," +
                    "fecha_lectura   TEXT," +
                    "placa           TEXT," +
                    "direccion       TEXT," +
                    "imagen_str      TEXT"  +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
