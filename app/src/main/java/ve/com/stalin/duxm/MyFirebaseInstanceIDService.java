package ve.com.stalin.duxm;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String LOGTAG = "android-fcm";

    @Override
    public void onTokenRefresh() {

        Configuracion config = new Configuracion(getSharedPreferences(Configuracion.PREFS_NOMBRE, Context.MODE_PRIVATE));

        if(config.estaRegistrado()){
            //Se obtiene el token actualizado
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            config.setToken(refreshedToken);
            config.desactivarToken();

            Patrullero patrullero = new Patrullero(null, config.getNombre(), config.getCedula(), null, refreshedToken );
            patrullero.refrescarDatos(config);

            Log.e(LOGTAG, "Token actualizado: " + refreshedToken);
        }
    }

}
