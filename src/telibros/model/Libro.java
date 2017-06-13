package telibros.model;

import java.sql.Date;

public class Libro {
    private int id;
    private String nombre;
    private int autor, genero;
    private Date fecha;
    private int paginas;
    private double precio;
    private boolean disponible;
    
    public Libro() {
    }

    public Libro(String nombre, int autor, int genero, Date fecha, int paginas, double precio, boolean disponible) {
        this.nombre = nombre;
        this.autor = autor;
        this.genero = genero;
        this.fecha = fecha;
        this.paginas = paginas;
        this.precio = precio;
        this.disponible = disponible;
    }
    
    public Libro(int id, String nombre, int autor, int genero, Date fecha, int paginas, double precio, boolean disponible) {
        this(nombre, autor, genero, fecha, paginas, precio, disponible);
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

    public int getAutor() {
        return autor;
    }

    public void setAutor(int autor) {
        this.autor = autor;
    }

    public int getGenero() {
        return genero;
    }

    public void setGenero(int genero) {
        this.genero = genero;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getPaginas() {
        return paginas;
    }

    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
