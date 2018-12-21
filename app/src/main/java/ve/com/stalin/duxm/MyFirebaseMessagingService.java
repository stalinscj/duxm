package ve.com.stalin.duxm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String LOGTAG = "android-fcm";
    private int alertaId;
    private double latitudPeaje;
    private double longitudPeaje;
    private double radio;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Map<String, String> data = remoteMessage.getData();
        
        this.alertaId = Integer.parseInt(data.get("alerta_id"));
        this.latitudPeaje = Double.parseDouble(data.get("latitud"));
        this.longitudPeaje = Double.parseDouble(data.get("longitud"));
        this.radio = Double.parseDouble(data.get("radio"));

        Handler h = new Handler(Looper.getMainLooper());
        h.post(new Runnable() {
            public void run() {
                Localizacion localizacion = new Localizacion(MyFirebaseMessagingService.this, MyFirebaseMessagingService.this);

                localizacion.verificarUbicacion(latitudPeaje, longitudPeaje, radio);
            }
        });
    }

    public void checkNotificacionPush(boolean estaEnElPerimetro){

        Notificacion notificacion = null;

        if(estaEnElPerimetro){
            notificacion = Notificacion.getAlertaDetalle(alertaId);
        }

        Configuracion config = new Configuracion(getApplicationContext());

        String fechaEntregada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        int idNotificacion = Notificacion.actualizarNotificacion(String.valueOf(alertaId), config.getValor("id"), Boolean.toString(true), Boolean.toString(estaEnElPerimetro), Boolean.toString(false), fechaEntregada, null, false);

        if (notificacion!=null){
            notificacion.setId(idNotificacion);
            notificacion.setAlcanzado(estaEnElPerimetro);
            notificacion.setFecha_entregada(fechaEntregada);

            config.guardarNotificacion(notificacion);

            String titulo = String.format("Matrícula %s Solicitada", notificacion.getPlaca());
            String texto = String.format("Dirección: %s", notificacion.getDireccion());

            showNotificacionPush(titulo, texto, idNotificacion);
        }

    }

    private void showNotificacionPush(String title, String text, int idNotificacion) {

        Intent intent = new Intent(this, DetalleActivity.class);
        intent.putExtra("idNotificacion", idNotificacion);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, intent,0);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
            .setContentIntent(pendingIntent).setAutoCancel(true)
            .setSmallIcon(android.R.drawable.stat_sys_warning)
            .setContentTitle(title)
            .setContentText(text)
            .setSound(sound);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
