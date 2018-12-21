package ve.com.stalin.duxm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Notificacion> notificaciones;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private NotificacionAdapter notificacionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.layoutManager = new LinearLayoutManager(this);
        this.recyclerView = findViewById(R.id.notificacionesRecyclerView);
        this.recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onResume() {
        super.onResume();

        //getApplicationContext().deleteDatabase(Configuracion.DB_NAME);
        Configuracion config = new Configuracion(getApplicationContext());

        if(!config.estaRegistrado()){
            Intent registro = new Intent(this, RegistroActivity.class);
            startActivity(registro);
        } else {
            if(!config.estaTokenActualizado()){
                Patrullero patrullero = config.getPatrullero();
                patrullero.refrescarDatos(config);
                Toast.makeText(this, "Actualizando su token...", Toast.LENGTH_SHORT).show();
            }
        }

        //        config.llenarNotificaciones();
        this.notificaciones = config.getNotificaciones(); //this.generateNotificaciones();

        this.notificacionAdapter = new NotificacionAdapter(this, this.notificaciones);

        this.recyclerView.setAdapter(notificacionAdapter);

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("Token: ", "Token actualizado: " + refreshedToken);
    }

    private ArrayList<Notificacion> generateNotificaciones() {
        ArrayList<Notificacion> notificaciones = new ArrayList<Notificacion>();

        for (int i=0; i<10; i++){
            Notificacion notificacion = new Notificacion(i, true, true, true, "11:30 am 31-12-2017", "11:30 am 31-12-2018", "11:30 am 31-12-2016", i+"ABCDEF", "Puerto Ordaz - San Félix", "img");
            notificaciones.add(notificacion);
        }

        return notificaciones;
    }


}
