package telibros.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import telibros.model.Autor;
import telibros.model.Data;
import telibros.model.Pais;
import telibros.model.Sexualidad;
import telibros.model.celleditors.ComboBoxCellEditor;
import telibros.model.celleditors.SpinnerDateCellEditor;
import telibros.model.tablemodel.TModelAutor;

/**
 *
 * @author igor
 */
public class VentanaConTablaDeAutores{
    private final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
    private final int ROW_HEIGHT = 30;
    private Data data;
    private JFrame ventana;
    private Render render;
    //private List<Autor> lista;
    //private TModelAutor tableModel;
    //private final String[] sexoValores = {"M", "F"};
    //private SpinnerStringArrayEditor editorSexo;
    private ComboBoxCellEditor<String> comboEditorSexo;
    private ComboBoxCellEditor<Pais> comboPaises;
    private JTable table;

    public VentanaConTablaDeAutores(String title, Data data, List<Autor> lista) {
        ventana = new JFrame(title);
        try {
            this.data = data;
            render = new Render();
           // this.lista = lista;
            //tableModel = new TModelAutor(lista, data);
            //editorSexo = new SpinnerStringArrayEditor(sexoValores);
            Sexualidad[] values = Sexualidad.values();
            List<String> tiposDeSexo = new ArrayList<>();
            for(Sexualidad sexo : values){
                tiposDeSexo.add(sexo.toString());
            }
            comboEditorSexo = new ComboBoxCellEditor<>(String.class, tiposDeSexo);
            comboPaises = new ComboBoxCellEditor<>(Pais.class, data.getListaPaises());            
            initComponents();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaConTablaDeAutores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isVisible(){
        return ventana.isVisible();
    }
    
    public void setVisible(boolean visibility){
        ventana.setVisible(visibility);
    }
    
    public void actualizar(List<Autor> listaAutores){
        try {
            actualizarModeloDeTabla(listaAutores);
            ventana.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 1);
            ventana.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        } catch (SQLException ex) {
            Logger.getLogger(VentanaConTablaDeAutores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initComponents() throws SQLException{
        ventana.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        ventana.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        table = new JTable();
        table.setDefaultRenderer(Object.class, render);
        actualizarModeloDeTabla(data.getListaAutores());
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(ROW_HEIGHT);
        table.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = e.getY() / table.getRowHeight();
                int col = table.getColumnModel().getColumnIndexAtX(e.getX());
                if (e.getClickCount() == 1) {
                    TModelAutor model = (TModelAutor) table.getModel();
                    Autor autor = model.getAutor(row);
                    if (row >= 0 && row < table.getRowCount()
                        && col < table.getColumnCount()) {
                        Object value = table.getValueAt(row, col);
                        if (value instanceof JButton) {
                            try {
                                int id_autor = autor.getId();
                                JButton btn = (JButton) value;
                                if (btn.getName().equalsIgnoreCase("m")) {
                                    //modificamos
                                    autor.setNombre((String) table.getValueAt(row, TModelAutor.COLUMN_NOMBRE));
                                    String sexo = (String) table.getValueAt(row, TModelAutor.COLUMN_SEXO);
                                    autor.setSexo(Sexualidad.valueOf(sexo));
                                    java.util.Date fecha_n = (java.util.Date) table.getValueAt(row, TModelAutor.COLUMN_FECHA_DE_NACIMIENTO);
                                    java.sql.Date fecha_n_sql = new java.sql.Date(fecha_n.getTime());
                                    autor.setFecha_n(fecha_n_sql);
                                    java.util.Date fecha_m = (java.util.Date) table.getValueAt(row, TModelAutor.COLUMN_FECHA_DE_MUERTE);
                                    java.sql.Date fecha_m_sql = new java.sql.Date(fecha_m.getTime());
                                    autor.setFecha_m(fecha_m_sql);
                                    Pais pais = (Pais) table.getValueAt(row, TModelAutor.COLUMN_PAIS);
                                    autor.setPais(pais.getId());
                                    data.modificarAutor(autor);
                                    actualizar(data.repetirBusquedaDeAutores());
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "El autor seleccionado fué modificado",
                                            "éxito",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                } else if (btn.getName().equalsIgnoreCase("e")) {
                                    data.eliminarAutor(id_autor);
                                    actualizar(data.repetirBusquedaDeAutores());
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "El autor seleccionado fué eliminado",
                                            "éxito",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(
                                    null,
                                    ex.getMessage(),
                                    "DataBaseError",
                                     JOptionPane.ERROR_MESSAGE
                                );
                                Logger.getLogger(VentanaConTablaDeLibros.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        }
                    }
                }
            }
        });
        ventana.add(new JScrollPane(table), BorderLayout.CENTER);
        ventana.setLocationRelativeTo(null);
        //this.setVisible(true);
    }
    
    private void actualizarModeloDeTabla(List<Autor> lista) throws SQLException{
        table.setModel(new TModelAutor(lista, data));
        TableColumn columnSexo =
        table.getColumnModel().getColumn(TModelAutor.COLUMN_SEXO);
        //columnSexo.setCellEditor(editorSexo);
        columnSexo.setCellEditor(comboEditorSexo);        
        TableColumn columnFechaNacimiento = 
        table.getColumnModel().getColumn(TModelAutor.COLUMN_FECHA_DE_NACIMIENTO);
        columnFechaNacimiento.setCellEditor(new SpinnerDateCellEditor());        
        TableColumn columnFechaMuerte = 
        table.getColumnModel().getColumn(TModelAutor.COLUMN_FECHA_DE_MUERTE);
        columnFechaMuerte.setCellEditor(new SpinnerDateCellEditor());
        comboPaises = new ComboBoxCellEditor<>(Pais.class,  data.getListaPaises());        
        TableColumn columnPais = 
        table.getColumnModel().getColumn(TModelAutor.COLUMN_PAIS);
        columnPais.setCellEditor(comboPaises);        
    }
    
//    public static void main(String[] args) {
//        try {
//            Data data = new Data();
//            List<Autor> lista = data.getListaAutores();
//            VentanaConTablaDeAutores v = 
//                new VentanaConTablaDeAutores("Lista de autores", data, lista);
//        } catch (IOException | SQLException ex) {
//            Logger.getLogger(VentanaConTablaDeAutores.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
