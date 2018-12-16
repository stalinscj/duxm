package ve.com.stalin.duxm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private TableLayout tblAlertas;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Configuracion config = new Configuracion(getSharedPreferences(Configuracion.PREFS_NOMBRE, Context.MODE_PRIVATE));

        if(!config.estaRegistrado()){
            Intent registro = new Intent(this, RegistroActivity.class);
            startActivity(registro);
        } else {
            if(!config.estaTokenActivo()){
                MyFirebaseInstanceIDService myFirebaseInstanceIDService = new MyFirebaseInstanceIDService();
                myFirebaseInstanceIDService.onTokenRefresh();
                Toast.makeText(this, "Actualizando su token id...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        setContentView(R.layout.activity_main);
        tblAlertas = (TableLayout) findViewById(R.id.tblAlertas);

        TableDynamic tableDynamic = new TableDynamic(tblAlertas, getApplicationContext());

        Log.d("Firebase", "token dddd: "+ FirebaseInstanceId.getInstance().getToken());

    }
}
