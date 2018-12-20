package ve.com.stalin.duxm;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleActivity extends AppCompatActivity {

    private ImageView imagen;
    private TextView imagenDireccion;
    private TextView imagenFechaLectura;
    private TextView imagenFechaEntregada;
    private TextView imagenFechaAtendida;
    private CheckBox chkAtendida;
    private Button btnGuardarNotificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        this.imagen = (ImageView) findViewById(R.id.imagen);
        this.imagenDireccion = (TextView) findViewById(R.id.imagenDireccion);
        this.imagenFechaLectura = (TextView) findViewById(R.id.imagenFechaLectura);
        this.imagenFechaEntregada = (TextView) findViewById(R.id.imagenFechaEntregada);
        this.imagenFechaAtendida = (TextView) findViewById(R.id.imagenFechaAtendida);
        this.chkAtendida = (CheckBox) findViewById(R.id.chkAtendida);
        this.btnGuardarNotificacion = (Button) findViewById(R.id.btnGuardarNotificacion);

        int idNotificacion = getIntent().getIntExtra("idNotificacion", 0);

        Configuracion config = new Configuracion(this);

        Notificacion notificacion = config.getNotificacion(idNotificacion);

        String imagenStr = notificacion.getImagen_str();

        byte[] decodedString = Base64.decode(imagenStr, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        this.imagen.setImageBitmap(decodedByte);

        this.imagenDireccion.setText(String.format("Dirección: %s", notificacion.getDireccion()));
        this.imagenFechaLectura.setText(String.format("Fecha: %s", notificacion.getFecha_lectura()));
        this.imagenFechaEntregada.setText(String.format("Entregada: %s", notificacion.getFecha_entregada()));

        String fechaAtendida = notificacion.getFecha_atendida();

        if(fechaAtendida.equals("")){
            fechaAtendida = "----/--/-- --/--/--";
        } else {
            this.chkAtendida.setVisibility(View.GONE);
            this.btnGuardarNotificacion.setVisibility(View.GONE);
        }

        this.imagenFechaAtendida.setText(String.format("Atendida: %s",fechaAtendida));

    }
}
