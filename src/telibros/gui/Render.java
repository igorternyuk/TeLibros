package telibros.gui;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import telibros.model.Autor;
import telibros.model.BasePOJO;

/**
 *
 * @author igor
 */
public class Render extends DefaultTableCellRenderer{
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column){
        if(value instanceof JButton){
            JButton btn = (JButton)value;
            if(isSelected){
                btn.setForeground(Color.black);
                btn.setBackground(btn.getName().equalsIgnoreCase("e") ? Color.RED : Color.GREEN);
            } else {
                btn.setForeground(table.getForeground());
                btn.setBackground(UIManager.getColor("Button.background"));
            }
            return btn;
        } else if(value instanceof JCheckBox){
            JCheckBox checkBox = (JCheckBox)value;
            return checkBox;
        } else if(value instanceof Autor){
            Autor autor = (Autor)value;
            setText(autor.getNombre());
            return this;
        } else if(value instanceof BasePOJO){
            BasePOJO basePOJO = (BasePOJO)value;
            setText(basePOJO.getNombre());
            return this;            
        }
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
