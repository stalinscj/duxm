package ve.com.stalin.duxm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TableLayout;

import com.google.firebase.iid.FirebaseInstanceId;

public class MainActivity extends AppCompatActivity {

    private TableLayout tblAlertas;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tblAlertas = (TableLayout) findViewById(R.id.tblAlertas);

        TableDynamic tableDynamic = new TableDynamic(tblAlertas, getApplicationContext());

        Log.d("Firebase", "token dddd: "+ FirebaseInstanceId.getInstance().getToken());
    }
}
