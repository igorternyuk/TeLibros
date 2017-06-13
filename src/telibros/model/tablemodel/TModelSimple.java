package telibros.model.tablemodel;

import java.util.List;
import javax.swing.JButton;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import telibros.model.BasePOJO;

/**
 *
 * @author igor
 * @param <T>
 */
public class TModelSimple<T extends BasePOJO> implements TableModel{
    private static final int COLUMN_ID = 0;
    private static final int COLUMN_NOMBRE = 1;
    private static final int COLUMN_MODIFICAR = 2;
    private static final int COLUMN_ELIMINAR = 3;
    private final String[] titulos = {"ID", "Nombre", "Modificar", "Eliminar"};
    private List<T> lista;
    private final JButton btnModificar;
    private final JButton btnEliminar;

    public TModelSimple(List<T> lista) {
        this.lista = lista;
        btnModificar = new JButton("Modificar");
        btnModificar.setName("m");
        btnEliminar = new JButton("Eliminar");
        btnEliminar.setName("e");
    }
    
    public T getRowObject(int row){
        return lista.get(row);
    }

    public void setLista(List<T> lista) {
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
        return columnIndex == COLUMN_NOMBRE;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T instance = lista.get(rowIndex);
        Object result = null;
        switch(columnIndex){
            case COLUMN_ID :
                result = instance.getId();
                break;
            case COLUMN_NOMBRE :
                result = instance.getNombre();
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
        T instance = lista.get(rowIndex);
        if(columnIndex == COLUMN_NOMBRE){
            instance.setNombre((String)aValue);
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
    }
    
}
