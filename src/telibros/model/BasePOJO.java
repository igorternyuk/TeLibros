package telibros.model;

/**
 *
 * @author igor
 */
public class BasePOJO {
    protected int id;
    protected String nombre;

    public BasePOJO() {
    }

    public BasePOJO(String nombre) {
        this.nombre = nombre;
    }

    public BasePOJO(int id, String nombre) {
        this(nombre);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
