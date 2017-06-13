package telibros.model.tablemodel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import telibros.exceptions.AutorNotFoundException;
import telibros.exceptions.GeneroNotFoundException;
import telibros.model.Autor;
import telibros.model.BasePOJO;
import telibros.model.Data;
import telibros.model.Libro;

/**
 *
 * @author igor
 */
public class TModelLibro implements TableModel{
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_NOMBRE = 1;
    public static final int COLUMN_AUTOR = 2;
    public static final int COLUMN_GENERO = 3;
    public static final int COLUMN_FECHA = 4;
    public static final int COLUMN_PAGINAS = 5;
    public static final int COLUMN_PRECIO = 6;
    public static final int COLUMN_DISPONIBLE = 7;
    public static final int COLUMN_BOTON_MODIFICAR = 8;
    public static final int COLUMN_BOTON_ELIMINAR = 9;
    private List<Libro> lista;
    private Data data;
    private JButton btnModificar, btnEliminar;
    private final String[] titulos = {"ID", "Nombre", "Autor", "Genero", "Fecha",
        "Paginas", "Precio", "Disponible", "Modificar", "Eliminar"};
    
    public TModelLibro(List<Libro> lista, Data data) throws SQLException {
        this.lista = lista;
        this.data = data;
        btnModificar = new JButton("Modificar");
        btnModificar.setName("m");
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setName("e");
    }

    public void setLista(List<Libro> lista) {
        this.lista = lista;
    }

//    public void setData(Data data) {
//        this.data = data;
//    }
    
    public Libro getLibro(int rowIndex){
        return lista.get(rowIndex);
    }
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return titulos.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return titulos[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if(lista.isEmpty()){
            return Object.class;
        } else {
            return getValueAt(0, columnIndex).getClass();
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return !(columnIndex == COLUMN_ID ||
               columnIndex == COLUMN_BOTON_MODIFICAR ||
               columnIndex == COLUMN_BOTON_ELIMINAR);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Libro libro = getLibro(rowIndex);
        Object result = null;
        switch(columnIndex){
            case COLUMN_ID :
                result = libro.getId();
                break;
            case COLUMN_NOMBRE:
                result = libro.getNombre();
                break;
            case COLUMN_AUTOR:
                try {
                    int id_autor = libro.getAutor();
                    result = data.getAutor(id_autor);
                } catch (SQLException ex) {
                    Logger.getLogger(TModelLibro.class.getName()).log(Level.SEVERE, null, ex);
                } catch (AutorNotFoundException ex) {
                    Logger.getLogger(TModelLibro.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case COLUMN_GENERO : 
                    int id_genero = libro.getGenero();
                    try {
                        result = data.getGenero(id_genero);
                    } catch (SQLException ex) {
                        Logger.getLogger(TModelLibro.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (GeneroNotFoundException ex) {
                        Logger.getLogger(TModelLibro.class.getName()).log(Level.SEVERE, null, ex);
                    }
                break;
            case COLUMN_FECHA :
                result = new java.util.Date(libro.getFecha().getTime());
                break;
            case COLUMN_PAGINAS :
                result = libro.getPaginas();
                break;
            case COLUMN_PRECIO :
                result = libro.getPrecio();
                break;
            case COLUMN_DISPONIBLE :
                result = libro.getDisponible();
                break;
            case COLUMN_BOTON_MODIFICAR :
                result = btnModificar;
                break;
            case COLUMN_BOTON_ELIMINAR :
                result = btnEliminar;
                break;
            default :
                throw new IllegalArgumentException("Invalid column index");
        }
        return result;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Libro libro = lista.get(rowIndex);
        switch(columnIndex){
            case COLUMN_NOMBRE :
                libro.setNombre((String)aValue);
                break;
            case COLUMN_AUTOR :
                libro.setAutor(((Autor) aValue).getId());
                break;
            case COLUMN_GENERO:
                libro.setGenero(((BasePOJO) aValue).getId());
                break;
            case COLUMN_FECHA:
//              DateFormat df = new SimpleDateFormat("yyyy-mm-dd");                   
//              Date date = new Date(df.parse((String)aValue).getTime());
                java.util.Date date = (java.util.Date) aValue;
                Date sqlDate = new Date(date.getTime());
                libro.setFecha(sqlDate);
                break;
            case COLUMN_PAGINAS:
                libro.setPaginas((int)aValue);
                break;
            case COLUMN_PRECIO:
                double precio = (double) aValue;
                libro.setPrecio(precio);
                break;
            case COLUMN_DISPONIBLE :
                libro.setDisponible((Boolean)aValue);
                break;
            default :
                throw new IllegalArgumentException("Invalid column index");
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
    
}
