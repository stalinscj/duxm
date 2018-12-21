package ve.com.stalin.duxm;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;


public class Localizacion {

    Context context;

    private LocationManager locationManager;
    private double latitudPeaje, longitudPeaje;
    private double radio;
    private MyFirebaseMessagingService myFirebaseMessagingService;

    public Localizacion(Context context, MyFirebaseMessagingService myFirebaseMessagingService) {
        this.context = context;
        this.myFirebaseMessagingService = myFirebaseMessagingService;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    private boolean isUbicacionActiva() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showAlerta() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this.context);
        dialog.setTitle("Active la Ubicación")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    public void verificarUbicacion(double latitudPeaje, double longitudPeaje, double radio) {
        if (!this.isUbicacionActiva()) {
            showAlerta();
            return;
        }

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        this.latitudPeaje = latitudPeaje;
        this.longitudPeaje = longitudPeaje;
        this.radio = radio;

        this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 20 * 1000, 10, locationListener);
    }

    private final LocationListener locationListener = new LocationListener() {

        public void onLocationChanged(Location location) {
            double latitudDispotivo = location.getLatitude();
            double longitudDispositivo = location.getLongitude();

            double distancia = calcularDistancia(latitudDispotivo, longitudDispositivo);

            boolean estaEnElPerimetro = distancia <= radio;

            Toast.makeText(context, String.format("Distacia: %f Radio: %f Está: %d", distancia, radio, (estaEnElPerimetro==true?1:0) ), Toast.LENGTH_LONG).show();
            Log.e("Res: ", String.format("Distacia: %f Radio: %f Está: %d", distancia, radio, (estaEnElPerimetro==true?1:0)));
            locationManager.removeUpdates(locationListener);

            myFirebaseMessagingService.checkNotificacionPush(estaEnElPerimetro);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private double calcularDistancia(double latitudDispositivo, double longitudDispositivo){

        Location locationPeaje = new Location("Peaje");

        locationPeaje.setLatitude(this.latitudPeaje);
        locationPeaje.setLongitude(this.longitudPeaje);

        Location locationDispositivo = new Location("Dispositivo");

        locationDispositivo.setLatitude(latitudDispositivo);
        locationDispositivo.setLongitude(longitudDispositivo);

        float distancia = locationPeaje.distanceTo(locationDispositivo);

        return distancia;
    }

}
