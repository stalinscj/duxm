package ve.com.stalin.duxm;

public class Notificacion {

    private int id;
    private boolean entregada;
    private boolean alcanzado;
    private boolean atendida;
    private String fecha_entregada;
    private String fecha_atendida;
    private String fecha_lectura;
    private String placa;
    private String direccion;
    private String imagen_str;


    public Notificacion(int id, boolean entregada, boolean alcanzado, boolean atendida, String fecha_entregada, String fecha_atendida, String fecha_lectura, String placa, String direccion, String imagen_str) {
        this.id = id;
        this.entregada = entregada;
        this.alcanzado = alcanzado;
        this.atendida = atendida;
        this.fecha_entregada = fecha_entregada;
        this.fecha_atendida = fecha_atendida;
        this.fecha_lectura = fecha_lectura;
        this.placa = placa;
        this.direccion = direccion;
        this.imagen_str = imagen_str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isEntregada() {
        return entregada;
    }

    public void setEntregada(boolean entregada) {
        this.entregada = entregada;
    }

    public boolean isAlcanzado() {
        return alcanzado;
    }

    public void setAlcanzado(boolean alcanzado) {
        this.alcanzado = alcanzado;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
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

    public String getFecha_lectura() {
        return fecha_lectura;
    }

    public void setFecha_lectura(String fecha_lectura) {
        this.fecha_lectura = fecha_lectura;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getImagen_str() {
        return imagen_str;
    }

    public void setImagen_str(String imagen_str) {
        this.imagen_str = imagen_str;
    }
}
