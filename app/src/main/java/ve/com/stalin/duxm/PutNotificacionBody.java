package ve.com.stalin.duxm;

public class PutNotificacionBody {

    private String alerta_id;
    private String patrullero_id;
    private String entregada;
    private String alcanzado;
    private String atendida;
    private String fecha_entregada;
    private String fecha_atendida;

    public PutNotificacionBody(String alerta_id, String patrullero_id, String entregada, String alcanzado, String atendida, String fecha_entregada, String fecha_atendida) {
        this.alerta_id = alerta_id;
        this.patrullero_id = patrullero_id;
        this.entregada = entregada;
        this.alcanzado = alcanzado;
        this.atendida = atendida;
        this.fecha_entregada = fecha_entregada;
        this.fecha_atendida = fecha_atendida;
    }

    public String getAlerta_id() {
        return alerta_id;
    }

    public void setAlerta_id(String alerta_id) {
        this.alerta_id = alerta_id;
    }

    public String getPatrullero_id() {
        return patrullero_id;
    }

    public void setPatrullero_id(String patrullero_id) {
        this.patrullero_id = patrullero_id;
    }

    public String getEntregada() {
        return entregada;
    }

    public void setEntregada(String entregada) {
        this.entregada = entregada;
    }

    public String getAlcanzado() {
        return alcanzado;
    }

    public void setAlcanzado(String alcanzado) {
        this.alcanzado = alcanzado;
    }

    public String getAtendida() {
        return atendida;
    }

    public void setAtendida(String atendida) {
        this.atendida = atendida;
    }

    public String getFecha_entregada() {
        return fecha_entregada;
    }

    public void setFecha_entregada(String fecha_entregada) {
        this.fecha_entregada = fecha_entregada;
    }

    public String getFecha_atendida() {
        return fecha_atendida;
    }

    public void setFecha_atendida(String fecha_atendida) {
        this.fecha_atendida = fecha_atendida;
    }
}
