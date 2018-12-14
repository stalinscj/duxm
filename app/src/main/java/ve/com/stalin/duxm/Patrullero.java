package ve.com.stalin.duxm;

public class Patrullero {

    private String id;
    private String nombre;
    private String cedula;
    private String activo;
    private String token;

    public Patrullero(String id, String nombre, String cedula, String activo, String token) {
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.activo = activo;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
