package ve.com.stalin.duxm;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NotificacionAdapter extends RecyclerView.Adapter<NotificacionAdapter.NotificacionViewHolder> {

    private ArrayList<Notificacion> notificaciones;
    private Context context;

    public NotificacionAdapter(Context context, ArrayList<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificacionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.card_view_notificacion, null, false);

        return new NotificacionViewHolder(view, this.context, this.notificaciones);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificacionViewHolder viewHolder, int i) {
        viewHolder.setNotificacion(notificaciones.get(i));
    }

    @Override
    public int getItemCount() {
        return this.notificaciones.size();
    }

    public ArrayList<Notificacion> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(ArrayList<Notificacion> notificaciones) {
        this.notificaciones = notificaciones;
    }


    public class NotificacionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgAtendida;
        private TextView txtPlaca;
        private TextView txtDireccion;
        private TextView txtFecha;
        private ImageView imgClick;

        private ArrayList<Notificacion> notificaciones;


        public NotificacionViewHolder(@NonNull View itemView, Context context, ArrayList<Notificacion> notificaciones) {
            super(itemView);
            this.imgAtendida = itemView.findViewById(R.id.imgAtendida);
            this.txtPlaca = itemView.findViewById(R.id.txtPlaca);
            this.txtDireccion = itemView.findViewById(R.id.txtDireccion);
            this.txtFecha = itemView.findViewById(R.id.txtFecha);
            this.imgClick = itemView.findViewById(R.id.imgClick);

            this.notificaciones = notificaciones;


            itemView.setOnClickListener(this);
        }

        public void setNotificacion(Notificacion notificacion) {
            txtPlaca.setText(notificacion.getPlaca());
            txtDireccion.setText(notificacion.getDireccion());
            txtFecha.setText(notificacion.getFecha_lectura());
        }

        @Override
        public void onClick(View v) {
            int i = getAdapterPosition();
            Notificacion notificacion = this.notificaciones.get(i);

            Intent intent = new Intent(context, DetalleActivity.class);
            intent.putExtra("idNotificacion", notificacion.getId());
            context.startActivity(intent);
        }
    }
}
