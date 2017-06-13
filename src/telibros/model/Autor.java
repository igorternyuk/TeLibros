package telibros.model;

import java.sql.Date;

public class Autor {
    int id;
    String nombre;
    Sexualidad sexo;
    Date fecha_n;
    Date fecha_m;
    int pais;

    public Autor() {
    }

    public Autor(String nombre, Sexualidad sexo, Date fecha_n, Date fecha_m, int pais) {
        this.nombre = nombre;
        this.sexo = sexo;
        this.fecha_n = fecha_n;
        this.fecha_m = fecha_m;
        this.pais = pais;
    }
    
    public Autor(int id, String nombre, Sexualidad sexo, Date fecha_n, Date fecha_m, int pais) {
        this.id = id;
        this.nombre = nombre;
        this.sexo = sexo;
        this.fecha_n = fecha_n;
        this.fecha_m = fecha_m;
        this.pais = pais;
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

    public Sexualidad getSexo() {
        return sexo;
    }

    public void setSexo(Sexualidad sexo) {
        this.sexo = sexo;
    }

    public Date getFecha_n() {
        return fecha_n;
    }

    public void setFecha_n(Date fecha_n) {
        this.fecha_n = fecha_n;
    }

    public Date getFecha_m() {
        return fecha_m;
    }

    public void setFecha_m(Date fecha_m) {
        this.fecha_m = fecha_m;
    }

    public int getPais() {
        return pais;
    }

    public void setPais(int pais) {
        this.pais = pais;
    }
    
    @Override
    public String toString(){
        return nombre;
    }
}
