package telibros.model.tablemodel;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import telibros.exceptions.PaisNotFoundException;
import telibros.model.Autor;
import telibros.model.Data;
import telibros.model.Sexualidad;

/**
 *
 * @author igor
 */
public class TModelAutor implements TableModel{
    public static final int COLUMN_ID = 0;
    public static final int COLUMN_NOMBRE = 1;
    public static final int COLUMN_SEXO = 2;
    public static final int COLUMN_FECHA_DE_NACIMIENTO = 3;
    public static final int COLUMN_FECHA_DE_MUERTE = 4;
    public static final int COLUMN_PAIS = 5;
    public static final int COLUMN_MODIFICAR = 6;
    public static final int COLUMN_ELIMINAR = 7;
    private final String[] titulos = {"ID", "Nombre", "Sexo", "Fecha_n",
        "Fecha_m", "País", "Modificar", "Eliminar"};
    private List<Autor> lista;
    private final Data data;
    private JButton btnModificar, btnEliminar;

    public TModelAutor(List<Autor> lista, Data data) {
        this.lista = lista;
        this.data = data;
        btnModificar = new JButton("Modificar");
        btnModificar.setName("m");
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setName("e");
    }
    
    public Autor getAutor(int row){
        return lista.get(row);
    }
    
    public void setLista(List<Autor> lista) {
        this.lista = lista;
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
        return !(columnIndex == COLUMN_ID || columnIndex == COLUMN_MODIFICAR ||
            columnIndex == COLUMN_ELIMINAR);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Autor autor = lista.get(rowIndex);
        Object result = null;
        switch(columnIndex){
            case COLUMN_ID :
                result = autor.getId();
                break;
            case COLUMN_NOMBRE :
                result = autor.getNombre();
                break;
            case COLUMN_SEXO :
                result = autor.getSexo().toString();
                break;
            case COLUMN_FECHA_DE_NACIMIENTO :
                //Aquií convertimos java.sql.Date a java.util.Date
                result = new java.util.Date(autor.getFecha_n().getTime());
                break;
            case COLUMN_FECHA_DE_MUERTE :
                result = new java.util.Date(autor.getFecha_m().getTime());
                break;
            case COLUMN_PAIS :
                int id_pais = autor.getPais();
                try {
                    result = data.getPais(id_pais);
                } catch (SQLException ex) {
                    Logger.getLogger(TModelAutor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PaisNotFoundException ex) {
                    Logger.getLogger(TModelAutor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case COLUMN_MODIFICAR :
                result = btnModificar;
                break;
            case COLUMN_ELIMINAR :
                result = btnEliminar;
                break;
            default :
                throw new IllegalArgumentException("Invalid column index");
        }
        return result;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Autor autor = lista.get(rowIndex);
        switch(columnIndex){
            case COLUMN_NOMBRE :
                autor.setNombre((String)aValue);
                break;
            case COLUMN_SEXO :
                autor.setSexo(Sexualidad.valueOf((String)aValue));
                break;
            case COLUMN_FECHA_DE_NACIMIENTO :
                //Aquií convertimos java.util.Date a java.sql.Date
                long timeNacimiento = ((java.util.Date)aValue).getTime();
                java.sql.Date sqlDateNacimiento = new java.sql.Date(timeNacimiento);
                autor.setFecha_n(sqlDateNacimiento);
                break;
            case COLUMN_FECHA_DE_MUERTE :
                long timeMuerte = ((java.util.Date)aValue).getTime();
                java.sql.Date sqlDateMuerte = new java.sql.Date(timeMuerte);
                autor.setFecha_m(sqlDateMuerte);
                break;
            case COLUMN_PAIS :
                int id_pais = autor.getPais();
                try {
                    data.getPais(id_pais);
                } catch (SQLException ex) {
                    Logger.getLogger(TModelAutor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (PaisNotFoundException ex) {
                    Logger.getLogger(TModelAutor.class.getName()).log(Level.SEVERE, null, ex);
                }
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
