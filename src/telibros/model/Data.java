package telibros.model;

import java.io.IOException;
import telibros.conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import telibros.exceptions.AutorNotFoundException;
import telibros.exceptions.GeneroNotFoundException;
import telibros.exceptions.LibroNotFoundException;
import telibros.exceptions.PaisNotFoundException;

/**
 *
 * @author igor
 */
public class Data {
    Conexion conexion;
    Connection con; 
    PreparedStatement sql;
    String busquedaAutorReciente = "select * from autores;";
    String busquedaLibroReciente = "select * from libros;";
    ResultSet resultSet;
    
    public Data() throws IOException, SQLException{
        conexion = new Conexion();
        con = conexion.getConection();
    }
    
    public void meterPais(Pais p) throws SQLException{
        String nombre = p.getNombre();
        sql = con.prepareStatement("insert into paises values(default,?);");
        sql.setString(1, nombre);
        sql.executeUpdate();
        sql.close();
    }
    
    public void modificarPais(Pais p) throws SQLException{
        int id = p.getId();
        String nombre = p.getNombre();
        sql = con.prepareStatement("update paises set nombre = ? where id = ?;");
        sql.setString(1, nombre);
        sql.setInt(2, id);
        sql.executeUpdate();
        sql.close();        
    }
    
    public void eleminarPais(int id_pais) throws SQLException{
        sql = con.prepareStatement("delete from paises where id = " + id_pais);
        sql.executeUpdate();
        sql.close();
    }
    
    public Pais getPais(int id_pais) throws SQLException, PaisNotFoundException{
        sql = con.prepareStatement("select * from paises where id =" + id_pais);
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            Pais p = new Pais();
            p.setId(resultSet.getInt(1));
            p.setNombre(resultSet.getString(2));
            sql.close();
            return p;
        } else {
            sql.close();
            throw new PaisNotFoundException("El país con el id = " + id_pais + 
                    " no se encontró");
        }
    }
    
    public List<Pais> getListaPaises() throws SQLException{
        sql = con.prepareStatement("select * from paises;");
        resultSet = sql.executeQuery();
        List<Pais> lista = new ArrayList<>();
        while (resultSet.next()) {
            lista.add(new Pais(resultSet.getInt(1), resultSet.getString(2)));
        }
        sql.close();
        return lista;
    }
    
    public int countPaises() throws SQLException{
        int contador = 0;
        sql = con.prepareStatement("select count(*) from paises;");
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            contador = resultSet.getInt(1);
        }
        sql.close();
        return contador;
    }
    
    public void meterGenero(Genero g) throws SQLException{
        sql = con.prepareStatement("insert into generos values(default,?);");
        sql.setString(1, g.getNombre());
        sql.executeUpdate();
        sql.close();
    }
    
    public void modificarGenero(Genero g) throws SQLException{
        sql = con.prepareStatement("update generos set nombre= ? where id= ?;");
        sql.setString(1, g.getNombre());
        sql.setInt(2, g.getId());
        sql.executeUpdate();
        sql.close();
    }
    
    public void eliminarGenero(int id_genero) throws SQLException{
        sql = con.prepareStatement("delete from generos where id=" + id_genero);
        sql.executeUpdate();
        sql.close();
    }
    
    public Genero getGenero(int id_genero) throws SQLException, GeneroNotFoundException{
        sql = con.prepareStatement("select * from generos where id=" + id_genero);
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            Genero g = new Genero();
            g.setId(resultSet.getInt(1));
            g.setNombre(resultSet.getString(2));
            sql.close();
            return g;
        } else {
            sql.close();
            throw new GeneroNotFoundException("El genero con el id = " + id_genero +
                "no se encontró.");
        }
    }
    
    public List<Genero> getListaGeneros() throws SQLException{
        sql = con.prepareStatement("select * from generos;");
        resultSet = sql.executeQuery();
        List<Genero> lista = new ArrayList<>();
        while(resultSet.next()){
            lista.add(new Genero(resultSet.getInt(1), resultSet.getString(2)));
        }
        return lista;
    }
    
    public int countGeneros() throws SQLException{
        int contador = 0;
        sql = con.prepareStatement("select count(*) from generos;");
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            contador = resultSet.getInt(1);
        }
        sql.close();
        return contador;
    }
    
    public void meterAutor(Autor a) throws SQLException{
        String nombre = a.getNombre();
        Sexualidad sexo = a.getSexo();
        Date fecha_n = a.getFecha_n();
        Date fecha_m = a.getFecha_m();
        int pais = a.getPais();
        sql = con.prepareCall("insert into autores values(default,?,?,?,?,?);");
        sql.setString(1, nombre);
        sql.setString(2, sexo.toString());
        sql.setDate(3, fecha_n);
        sql.setDate(4, fecha_m);
        sql.setInt(5, pais);
        sql.executeUpdate();
        sql.close();
    }
    
    public void modificarAutor(Autor a) throws SQLException{
        String nombre = a.getNombre();
        Sexualidad sexo = a.getSexo();
        Date fecha_n = a.getFecha_n();
        Date fecha_m = a.getFecha_m();
        int pais = a.getPais();
        sql = con.prepareStatement("update autores set nombre = ?, sexo = ?,"
                + "fecha_n = ?, fecha_m = ?, pais = ? where id = ?;");
        sql.setString(1, nombre);
        sql.setString(2, sexo.toString());
        sql.setDate(3, fecha_n);
        sql.setDate(4, fecha_m);
        sql.setInt(5, pais);
        sql.setInt(6, a.getId());
        sql.executeUpdate();
        sql.close();
    }
    
    public void eliminarAutor(int id_autor) throws SQLException{
        sql = con.prepareStatement("delete from autores where id=" + id_autor);
        sql.executeUpdate();
        sql.close();
    }
    
    private Autor rescatarAutorDesdeResultSet(ResultSet resultSet) throws SQLException{
        Autor a = new Autor();
        a.setId(resultSet.getInt(1));
        a.setNombre(resultSet.getString(2));
        a.setSexo(Sexualidad.valueOf(resultSet.getString(3)));
        a.setFecha_n(resultSet.getDate(4));
        a.setFecha_m(resultSet.getDate(5));
        a.setPais(resultSet.getInt(6));
        return a;
    }
    
    public Autor getAutor(int id_autor) throws SQLException, AutorNotFoundException{
        sql = con.prepareStatement("select * from autores where id = " +
            id_autor + ";");
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            Autor a = rescatarAutorDesdeResultSet(resultSet);
            sql.close();
            return a;
        } else {
            sql.close();
            throw new AutorNotFoundException("El autor con el id = " + id_autor +
                    " no se encontró.");
        }
    }
    
    public List<Autor> getListaAutores() throws SQLException{
        busquedaAutorReciente = "select * from autores";
        sql = con.prepareStatement(busquedaAutorReciente);
        resultSet = sql.executeQuery();
        List<Autor> lista = new ArrayList<>();
        while(resultSet.next()){
            lista.add(rescatarAutorDesdeResultSet(resultSet));
        }
        sql.close();
        return lista ;
    }
    
    private int getDiffYears(java.util.Date first, java.util.Date second) {
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(first);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(second);
        int result = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
        if(cal1.get(Calendar.MONTH) > cal2.get(Calendar.MONTH) ||
          ((cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
          (cal1.get(Calendar.DAY_OF_MONTH) > cal2.get(Calendar.DAY_OF_MONTH))))){
            --result;
        }
        return result;
    }
    
    public List<Autor> buscarAutores(String regExp, boolean considerarSexo,
            Sexualidad sexo, boolean considerarEdad, int edadMin, int edadMax,
            boolean considerarPais, int id_pais) throws SQLException {

        StringBuilder sb = new StringBuilder("select * from autores where"
                + " autores.nombre like '%" + regExp + "%'");
        if(considerarSexo){
            sb.append(" and autores.sexo = '");
            sb.append(sexo.toString());
            sb.append("'");
        }
        
        if(considerarPais){
            sb.append(" and autores.pais =");
            sb.append(id_pais);
        }

        if(considerarEdad){
            sb.append(" and (calc_age(autores.fecha_n) >= ");
            sb.append(edadMin);
            sb.append(" and calc_age(autores.fecha_n) <= ");
            sb.append(edadMax);
            sb.append(")");
        }        

        sb.append(";");
        sql = con.prepareStatement(sb.toString());
        busquedaAutorReciente = sb.toString();
        System.out.println("SQL = " + sb.toString());
        resultSet = sql.executeQuery();
        List<Autor> lista = new ArrayList<>();
        while(resultSet.next()){
            Autor a = rescatarAutorDesdeResultSet(resultSet);
            lista.add(a);
        }
        sql.close();
        return lista;
    }
    
    public List<Autor> repetirBusquedaDeAutores() throws SQLException{
        sql = con.prepareStatement(busquedaAutorReciente);
        resultSet = sql.executeQuery();
        List<Autor> lista = new ArrayList<>();
        while (resultSet.next()) {            
            lista.add(rescatarAutorDesdeResultSet(resultSet));
        }
        sql.close();
        return lista;
    }
    
    public int countAutores() throws SQLException{
        int contador = 0;
        sql = con.prepareStatement("select count(*) from autores;");
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            contador = resultSet.getInt(1);
        }
        sql.close();
        return contador;
    }
    
    public void meterLibro(Libro l) throws SQLException{
        sql = con.prepareStatement("insert into libros values(default,?,?,?,?,?,?,?);");
        sql.setString(1, l.getNombre());
        sql.setInt(2, l.getAutor());
        sql.setInt(3, l.getGenero());
        sql.setDate(4, l.getFecha());
        sql.setInt(5, l.getPaginas());
        sql.setDouble(6, l.getPrecio());
        sql.setBoolean(7, l.getDisponible());
        sql.executeUpdate();
        sql.close();
    }
    
    public void modificarLibro(Libro l) throws SQLException{
        sql = con.prepareStatement("update libros set nombre = ?, autor = ?,"
            + "genero = ?, fecha = ?, paginas = ?, precio = ?, disponible = ? where id = ?;");
        sql.setString(1, l.getNombre());
        sql.setInt(2, l.getAutor());
        sql.setInt(3, l.getGenero());
        sql.setDate(4, l.getFecha());
        sql.setInt(5, l.getPaginas());
        sql.setDouble(6, l.getPrecio());
        sql.setBoolean(7, l.getDisponible());
        sql.setInt(8, l.getId());
        sql.executeUpdate();
        sql.close();
    }
    
    public void eliminarLibro(int id_libro) throws SQLException{
        sql = con.prepareStatement("delete from libros where id=" + id_libro + ";");
        sql.executeUpdate();
        sql.close();
    }
    
    private Libro rescatarLibroDesdeResultSet(ResultSet resultSet) throws SQLException {
        Libro l = new Libro();
        l.setId(resultSet.getInt(1)); //ID
        l.setNombre(resultSet.getString(2)); //Nombre
        l.setAutor(resultSet.getInt(3)); //Autor
        l.setGenero(resultSet.getInt(4)); //Genero
        l.setFecha(resultSet.getDate(5)); //Fecha
        l.setPaginas(resultSet.getInt(6)); //Cantidad de paginas
        l.setPrecio(resultSet.getDouble(7)); //precio
        l.setDisponible(resultSet.getBoolean(8)); //Disponible
        return l;
    }
    
    public Libro getLibro(int id_libro) throws SQLException, LibroNotFoundException{
        sql = con.prepareStatement("select * from libros where id= " + id_libro);
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            Libro l = rescatarLibroDesdeResultSet(resultSet);
            sql.close();
            return l;
        } else {
            sql.close();
            throw new LibroNotFoundException("El libro con el id = " + id_libro +
                    " no se encontró");
        }
    }
    
    public List<Libro> getListaLibros() throws SQLException{
        busquedaLibroReciente = "select * from libros;";
        sql = con.prepareStatement(busquedaLibroReciente);
        resultSet = sql.executeQuery();
        List<Libro> lista = new ArrayList<>();
        while(resultSet.next()){
            lista.add(rescatarLibroDesdeResultSet(resultSet));
        }
        return lista;
    }
    
    public List<Libro> buscarLibros(String regExp, boolean considerarGenero,
        int id_genero, boolean considerarAutor, int id_autor,
        boolean considerarPais, int id_pais, 
        boolean considerarFecha, Date fechaMin, Date fechaMax,
        boolean considerarPaginas, int paginasMin, int paginasMax,
        boolean considerarPrecio, double precioMin, double precioMax,
        boolean soloDisponibles) throws SQLException{
        StringBuilder sb = new StringBuilder(
            "select libros.id, libros.nombre, libros.autor, libros.genero,"
            + "libros.fecha, libros.paginas, libros.precio, libros.disponible"
            + " from libros, autores, paises where libros.autor = autores.id "
            + " and autores.pais = paises.id"
            + " and libros.nombre like '%" + regExp + "%' ");
        if(considerarGenero){
            sb.append(" and libros.genero = ");
            sb.append(id_genero);
        }
        if(considerarAutor){
            sb.append(" and libros.autor = ");
            sb.append(id_autor);
        }
        if(considerarPais){
            sb.append(" and paises.id = ");
            sb.append(id_pais);
        }
        if(considerarFecha){
            sb.append(" and (libros.fecha >= '");
            sb.append(fechaMin);
            sb.append("' and libros.fecha <= '");
            sb.append(fechaMax);
            sb.append("')");
        }
        if(considerarPaginas){
            sb.append(" and (libros.paginas >= ");
            sb.append(paginasMin);
            sb.append(" and libros.paginas <=");
            sb.append(paginasMax);
            sb.append(")");
        }
        if(considerarPrecio){
            sb.append(" and (libros.precio >= ");
            sb.append(precioMin);
            sb.append(" and libros.precio <=");
            sb.append(precioMax);
            sb.append(")");
        }
        if(soloDisponibles){
            sb.append(" and libros.disponible = true");
        }
        sb.append(";");
        System.out.println("SQL = " + sb.toString());
        busquedaLibroReciente = sb.toString();
        sql = con.prepareStatement(sb.toString());
        resultSet = sql.executeQuery();
        List<Libro> lista = new ArrayList<>();
        while (resultSet.next()) {            
            lista.add(rescatarLibroDesdeResultSet(resultSet));
        }
        sql.close();
        return lista;
    }
    
    public List<Libro> repetirBusquedaDeLibros() throws SQLException{
        sql = con.prepareStatement(busquedaLibroReciente);
        resultSet = sql.executeQuery();
        List<Libro> lista = new ArrayList<>();
        while (resultSet.next()) {            
            lista.add(rescatarLibroDesdeResultSet(resultSet));
        }
        sql.close();
        return lista;
    }
    
    public int countLibros() throws SQLException{
        int contador = 0;
        sql = con.prepareStatement("select count(*) from libros;");
        resultSet = sql.executeQuery();
        if(resultSet.next()){
            contador = resultSet.getInt(1);
        }
        sql.close();
        return contador;
    }
    
//    public static void main(String[] args) {
//        try {
//            Data data = new Data();
//            Calendar cal1 = Calendar.getInstance();
//            cal1.set(1800, 1, 1);
//            Date fecha1 = new Date(cal1.getTimeInMillis());
//            cal1.set(2015, 1, 1);
//            Date fecha2 = new Date(cal1.getTimeInMillis());
//            List<Libro> lista = data.buscarLibros("", true, 4, true, 1, true,
//                fecha1, fecha2, true, 0, 1000, true, 0, 1000, true);
//            System.out.println("Imprimimos la lista de libros ...");
//            for(Libro lib : lista){
//                System.out.println(lib);
//            }            
//            List<Autor> autores = data.buscarAutores("", true, Sexualidad.M,
//                true, 20, 100, true, 10);
//             System.out.println("Imprimimos la lista de autores...");
//            for(Autor autor : autores){
//                System.out.println(autor);
//            }   
//        } catch (IOException | SQLException ex) {
//            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
