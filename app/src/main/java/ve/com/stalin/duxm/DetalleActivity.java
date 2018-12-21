package ve.com.stalin.duxm;

import android.annotation.SuppressLint;
import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Date;

public class DetalleActivity extends AppCompatActivity {

    private ImageView imagen;
    private TextView imagenDireccion;
    private TextView imagenFechaLectura;
    private TextView imagenFechaEntregada;
    private TextView imagenFechaAtendida;
    private CheckBox chkAtendida;
    private Button btnGuardarNotificacion;
    private Button btnEliminarNotificacion;
    private Notificacion notificacion;
    Configuracion config;

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
        this.btnEliminarNotificacion = (Button) findViewById(R.id.btnEliminarNotificacion);

        int idNotificacion = getIntent().getIntExtra("idNotificacion", 0);

        this.config = new Configuracion(this);

        this.notificacion = config.getNotificacion(idNotificacion);

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
            this.btnGuardarNotificacion.setVisibility(View.INVISIBLE);
        } else {
            this.chkAtendida.setVisibility(View.GONE);
            this.btnGuardarNotificacion.setVisibility(View.GONE);
        }

        this.imagenFechaAtendida.setText(String.format("Atendida: %s",fechaAtendida));
        this.btnEliminarNotificacion.setText("ELIMINAR ["+this.notificacion.getId()+"]");
    }

    public void guardarNotificacion(View view) {
        String fechaAtendida = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        this.notificacion.setAtendida(true);
        this.notificacion.setFecha_atendida(fechaAtendida);

        int res = Notificacion.actualizarNotificacion(notificacion, false);

        if(res!=0) {
            this.config.guardarNotificacion(notificacion);
            this.chkAtendida.setVisibility(View.GONE);
            this.btnGuardarNotificacion.setVisibility(View.GONE);
            this.imagenFechaAtendida.setText(String.format("Atendida: %s",fechaAtendida));
        }
    }

    public void eliminarNotificacion(View view) {
        this.config.eliminarNotificacion(this.notificacion.getId());
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    public void toggleBtnGuardar(View view) {
        if (this.btnGuardarNotificacion.getVisibility()==View.INVISIBLE || this.btnGuardarNotificacion.getVisibility()==View.GONE ) {
            this.btnGuardarNotificacion.setVisibility(View.VISIBLE);
        } else {
            this.btnGuardarNotificacion.setVisibility(View.INVISIBLE);
        }
    }
}
