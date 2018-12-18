package ve.com.stalin.duxm;

public class Notificacion {

    private int id;
    private String placa;
    private String direccion;
    private String fecha_lectura;
    //Herencia Detalle
//    private String fecha_entregada;
    private boolean atendida;
//    private String fecha_atendida;
//    private String imgStr;


    public Notificacion(int id, String placa, String direccion, String fecha_lectura, boolean atendida) {
        this.id = id;
        this.placa = placa;
        this.direccion = direccion;
        this.fecha_lectura = fecha_lectura;
        this.atendida = atendida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getFecha_lectura() {
        return fecha_lectura;
    }

    public void setFecha_lectura(String fecha_lectura) {
        this.fecha_lectura = fecha_lectura;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public void setAtendida(boolean atendida) {
        this.atendida = atendida;
    }
}
