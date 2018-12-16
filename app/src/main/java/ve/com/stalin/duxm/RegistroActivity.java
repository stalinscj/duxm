package ve.com.stalin.duxm;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistroActivity extends AppCompatActivity {

    private EditText txtNombre;
    private EditText txtCedula;
    private Button btnRegistrar;
    private View progressBar;

    private Retrofit restAdapter;
    private DuxApi duxApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        this.txtNombre    = (EditText) findViewById(R.id.txtNombre);
        this.txtCedula    = (EditText) findViewById(R.id.txtCedula);
        this.btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        this.progressBar  = (View) findViewById(R.id.progressBar);

        // Crear conexión al servicio REST
        restAdapter = new Retrofit.Builder()
            .baseUrl(DuxApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        // Crear conexión a la API de Dux
        duxApi = restAdapter.create(DuxApi.class);
    }

    public void registrar(View view) {
        String nombre = this.txtNombre.getText().toString().trim();
        String cedula = this.txtCedula.getText().toString().trim();
        String token  = FirebaseInstanceId.getInstance().getToken();

        if (validarCampos(nombre, cedula)) {
            this.btnRegistrar.setEnabled(false);
            showProgress(true);
            intentarRegistrar(nombre, cedula, token);
        }
    }

    private boolean validarCampos(String nombre, String cedula) {

        boolean valido = true;

        if (nombre.length()*cedula.length()==0) {
            valido = false;
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
        }

        if(!cedula.matches("\\d+")) {
            valido = false;
            Toast.makeText(this, "La cédula solo debe contener números", Toast.LENGTH_SHORT).show();
        }

        return valido;
    }

    private void intentarRegistrar(String nombre, String cedula, String token) {
        Call<Patrullero> registrarPatrulleroCall = duxApi.registrarPatrullero(new PostPatrulleroBody(nombre, cedula, token));

        registrarPatrulleroCall.enqueue(new Callback<Patrullero>() {
            @Override
            public void onResponse(Call<Patrullero> call, Response<Patrullero> response) {
                showProgress(false);

                if (response.isSuccessful()) {
                    Patrullero patrullero = response.body();

                    Configuracion config = new Configuracion(getSharedPreferences(Configuracion.PREFS_NOMBRE, Context.MODE_PRIVATE));
                    config.registrarDispositivo(patrullero.getNombre(), patrullero.getCedula(), patrullero.getToken());

                    Toast.makeText(RegistroActivity.this, "Registro Exitoso, esperando su activación", Toast.LENGTH_SHORT).show();

                    Intent main = new Intent(RegistroActivity.this, MainActivity.class);
                    startActivity(main);
                } else {

                    ApiError apiError = new ApiError(response.code(), response.errorBody());

                    for (String error: apiError.getErrors()) {
                        Toast.makeText(RegistroActivity.this, error, Toast.LENGTH_SHORT).show();
                    }
                }

                RegistroActivity.this.btnRegistrar.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Patrullero> call, Throwable t) {
                showProgress(false);
                Log.e("Error: ", t.getMessage());
                Toast.makeText(RegistroActivity.this, "Error al conectar con el servidor...", Toast.LENGTH_SHORT).show();
                RegistroActivity.this.btnRegistrar.setEnabled(true);
            }
        });
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
}
