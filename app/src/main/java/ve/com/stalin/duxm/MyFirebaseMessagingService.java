package ve.com.stalin.duxm;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String LOGTAG = "android-fcm";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {

            String titulo = remoteMessage.getNotification().getTitle();
            String texto = remoteMessage.getNotification().getBody();
//            String msj = remoteMessage.getData().toString();

            Log.d(LOGTAG, "NOTIFICACION RECIBIDA");
            Log.d(LOGTAG, "Título: " + titulo);
            Log.d(LOGTAG, "Texto: " + texto);
//            Log.d(LOGTAG, "Msj: " + msj);

        }else{
            Map<String, String> data = remoteMessage.getData();
            int alertaId = Integer.parseInt(data.get("alerta_id"));

            Notificacion notificacion = null;

            boolean estaEnElPerimetro = true;

            if(estaEnElPerimetro){
                notificacion = Notificacion.getAlertaDetalle(alertaId);
            }

            Configuracion config = new Configuracion(getApplicationContext());

            String fechaEntregada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            int idNotificacion = Notificacion.actualizarNotificacion(String.valueOf(alertaId), config.getValor("id"), Boolean.toString(true), Boolean.toString(estaEnElPerimetro), Boolean.toString(false), fechaEntregada, null);

            if (notificacion!=null){
                notificacion.setId(idNotificacion);
                notificacion.setAlcanzado(estaEnElPerimetro);
                notificacion.setFecha_entregada(fechaEntregada);

                config.guardarNotificacion(notificacion);

                String titulo = String.format("Matrícula %s Solicitada", notificacion.getPlaca());
                String texto = String.format("Dirección: %s", notificacion.getDireccion());

                showNotification(titulo, texto);
            }
        }
    }

    private void showNotification(String title, String text) {
        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(android.R.drawable.stat_sys_warning)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setSound(sound);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, notificationBuilder.build());
    }
}
