package View;

import javax.swing.*;

import javax.swing.table.TableModel;

public class TourTable extends JTable {
    public TourTable(Object[][] a, Object[] b){
        super((TableModel) new JTable(a, b));

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
