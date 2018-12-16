package ve.com.stalin.duxm;

public class PutPatrulleroBody extends PostPatrulleroBody {

    private String id;

    public PutPatrulleroBody(String id, String nombre, String cedula, String token) {
        super(nombre, cedula, token);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
