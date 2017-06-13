package telibros.gui;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
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
import telibros.model.celleditors.ComboBoxCellEditor;
import telibros.model.Data;
import telibros.model.Genero;
import telibros.model.Libro;
import telibros.model.celleditors.SpinnerDateCellEditor;
import telibros.model.celleditors.SpinnerNumericCellEditor;
import telibros.model.tablemodel.TModelLibro;

/**
 *
 * @author igor
 */
public class VentanaConTablaDeLibros{

    private static final int WINDOW_WIDTH = 800, WINDOW_HEIGHT = 600;
    private Data data;
    private JFrame ventana;
    private Render render;
    private ComboBoxCellEditor<Genero> comboGeneros;
    private ComboBoxCellEditor<Autor> comboAutores;
    private JTable table;

    public VentanaConTablaDeLibros(String title, Data data, List<Libro> lista)
            throws HeadlessException {
        ventana = new JFrame(title);
        this.data = data;
        try {
            initComponents(lista);
        } catch (IOException ex) {
            Logger.getLogger(VentanaConTablaDeLibros.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(VentanaConTablaDeLibros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean isVisible(){
        return ventana.isVisible();
    }
    
    public void setVisible(boolean visibility){
        ventana.setVisible(visibility);
    }
    
    public void actualizar(List<Libro> lista) throws SQLException {
        actualizarModeloDeTabla(lista);
        ventana.setSize(WINDOW_WIDTH, WINDOW_HEIGHT + 1);
        ventana.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
    }


    private void initComponents(List<Libro> lista) throws SQLException, IOException {
        ventana.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        ventana.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        ventana.setResizable(true);
        table = new JTable();
        table.setAutoCreateRowSorter(true);
        render = new Render();
        table.setDefaultRenderer(Object.class, render);
        table.setRowHeight(30);
        actualizarModeloDeTabla(lista);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = e.getY() / table.getRowHeight();
                int col = table.getColumnModel().getColumnIndexAtX(e.getX());
                if (e.getClickCount() == 1) {
                    TModelLibro model = (TModelLibro) table.getModel();
                    Libro lib = model.getLibro(row);
                    if (row >= 0 && row < table.getRowCount()
                            && col < table.getColumnCount()) {
                        Object value = table.getValueAt(row, col);
                        if (value instanceof JButton) {
                            try {
                                int id_libro = lib.getId();
                                JButton btn = (JButton) value;
                                if (btn.getName().equalsIgnoreCase("m")) {
                                    //modificamos
                                    lib.setNombre((String) table.getValueAt(row, TModelLibro.COLUMN_NOMBRE));
                                    Autor autor = (Autor) table.getValueAt(row, TModelLibro.COLUMN_AUTOR);
                                    lib.setAutor(autor.getId());
                                    Genero genero = (Genero) table.getValueAt(row, TModelLibro.COLUMN_GENERO);
                                    lib.setGenero(genero.getId());
                                    java.util.Date fecha = (java.util.Date) table.getValueAt(row, TModelLibro.COLUMN_FECHA);
                                    java.sql.Date fecha_sql = new java.sql.Date(fecha.getTime());
                                    lib.setFecha(fecha_sql);
                                    lib.setPaginas((int) table.getValueAt(row, TModelLibro.COLUMN_PAGINAS));
                                    lib.setPrecio((double) table.getValueAt(row, TModelLibro.COLUMN_PRECIO));
                                    lib.setDisponible((boolean) table.getValueAt(row, TModelLibro.COLUMN_DISPONIBLE));
                                    data.modificarLibro(lib);
                                    actualizar(data.repetirBusquedaDeLibros());
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "El libro seleccionado fué modificado",
                                            "éxito",
                                            JOptionPane.INFORMATION_MESSAGE
                                    );
                                } else if (btn.getName().equalsIgnoreCase("e")) {
                                    data.eliminarLibro(id_libro);
                                    actualizar(data.repetirBusquedaDeLibros());
                                    JOptionPane.showMessageDialog(
                                            null,
                                            "El libro seleccionado fué eliminado",
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
    }

    private void actualizarModeloDeTabla(List<Libro> lista) throws SQLException {
        table.setModel(new TModelLibro(lista, data));
        comboGeneros = new ComboBoxCellEditor(Genero.class, data.getListaGeneros());
        comboAutores = new ComboBoxCellEditor(Autor.class, data.getListaAutores());
        table.setDefaultEditor(Genero.class, comboGeneros);
        table.setDefaultEditor(Autor.class, comboAutores);
        TableColumn tc4 = table.getColumnModel().getColumn(4);
        tc4.setCellEditor(new SpinnerDateCellEditor());
        TableColumn tc5 = table.getColumnModel().getColumn(5);
        tc5.setCellEditor(new SpinnerNumericCellEditor(0, 0, 1000000, 1));
        TableColumn tc6 = table.getColumnModel().getColumn(6);
        tc6.setCellEditor(new SpinnerNumericCellEditor(0.f, 0.f, 1000000000.f, 0.1f));
    }
    


//    public static void main(String[] args) {
//        try {
//            Data data = new Data();
//            List<Libro> lista = data.getListaLibros();
//            VentanaConTablaDeLibros v = new VentanaConTablaDeLibros("Results", lista, data);
//        } catch (IOException | SQLException ex) {
//            Logger.getLogger(VentanaConTablaDeLibros.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
