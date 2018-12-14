package ve.com.stalin.duxm;

public class PostPatrulleroBody {
    private String nombre;
    private String cedula;
    private String token;

    public PostPatrulleroBody(String nombre, String cedula, String token) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
