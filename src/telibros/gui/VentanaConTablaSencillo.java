package telibros.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import telibros.model.BasePOJO;
import telibros.model.Data;
import telibros.model.Genero;
import telibros.model.Pais;
import telibros.model.tablemodel.TModelSimple;

/**
 *
 * @author igor
 */
public class VentanaConTablaSencillo {

    private static final int WINDOW_WIDTH = 500, WINDOW_HEIGHT = 600;
    private final int ROW_HEIGHT = 30;
    private final Data data;
   // private List<? extends BasePOJO> lista;
    private final Render render;
    private final JFrame ventana;
    private JTable table;
    private JScrollPane scrollPane;

    public VentanaConTablaSencillo(String title, Data data, List<? extends BasePOJO> lista) {
        ventana = new JFrame(title);
        this.data = data;
        //this.lista = lista;
        render = new Render();
        initComponents(lista);
    }

    public void setLista(List<? extends BasePOJO> lista) {
        //this.lista = lista;
        //tableModel.setLista(lista);
        table.setModel(new TModelSimple(lista));
    }
    
    private void initComponents(List<? extends BasePOJO> lista) {
        ventana.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        ventana.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        table = new JTable(new TModelSimple(lista));
        table.setDefaultRenderer(Object.class, render);
        table.setAutoCreateRowSorter(true);
        table.setRowHeight(ROW_HEIGHT);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int row = e.getY() / table.getRowHeight();
                int col = table.getColumnModel().getColumnIndexAtX(e.getX());
                if (e.getClickCount() == 1) {
                    TModelSimple model = (TModelSimple) table.getModel();
                    BasePOJO base = model.getRowObject(row);
                    if (row >= 0 && row < table.getRowCount() && col < table.getColumnCount()) {
                        Object value = table.getValueAt(row, col);
                        if (value instanceof JButton) {
                            JButton btn = (JButton) value;
                            int id = (int) table.getValueAt(row, 0);
                            String newName = (String) table.getValueAt(row, 1);
                            try {
                                if (btn.getName().equalsIgnoreCase("m")) {
                                    if (base instanceof Pais) {
                                        Pais pais = new Pais(id, newName);
                                        data.modificarPais(pais);
                                        setLista(data.getListaPaises());
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "El país seleccionado fué modificado",
                                                "éxito",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                    } else if (base instanceof Genero) {
                                        Genero genero = new Genero(id, newName);
                                        data.modificarGenero(genero);
                                        setLista(data.getListaGeneros());
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "El género seleccionado fué modificado",
                                                "éxito",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                    }
                                } else if (btn.getName().equalsIgnoreCase("e")) {
                                    if (base instanceof Pais) {
                                        data.eleminarPais(id);
                                        setLista(data.getListaPaises());
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "El país seleccionado fué eliminado",
                                                "éxito",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                    } else if (base instanceof Genero) {
                                        data.eliminarGenero(id);
                                        setLista(data.getListaGeneros());                                        
                                        JOptionPane.showMessageDialog(
                                                null,
                                                "El género seleccionado fué eliminado",
                                                "éxito",
                                                JOptionPane.INFORMATION_MESSAGE
                                        );
                                    }
                                }
                            } catch (SQLException ex) {
                                JOptionPane.showMessageDialog(
                                    null,
                                    ex.getMessage(),
                                    "DataBaseError",
                                     JOptionPane.ERROR_MESSAGE
                                );
                                Logger.getLogger(VentanaConTablaSencillo.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }
        }
        );
        this.scrollPane = new JScrollPane(table);
        ventana.add(scrollPane, BorderLayout.CENTER);        
        ventana.setLocationRelativeTo(null);
    }
    
    public boolean isVisible(){
        return ventana.isVisible();
    }
    
    public void setVisible(boolean visibility){
        ventana.setVisible(visibility);
    }
//    public static void main(String[] args) {
//        try {
//            Data data = new Data();
//            List<Pais> lista = data.getListaPaises();
//            VentanaConTablaSencillo v = new VentanaConTablaSencillo("Lista de paises", data, lista);
//            List<Genero> lista2 = data.getListaGeneros();
//            VentanaConTablaSencillo v2 = new VentanaConTablaSencillo("Lista de generos", data, lista2);
//        } catch (IOException | SQLException ex) {
//            Logger.getLogger(VentanaConTablaSencillo.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
