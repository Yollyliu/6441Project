package View;

import javax.swing.*;

import javax.swing.table.TableModel;

/**
 * <h1>TourTable</h1>
 * the class TourTable is for override the JTable to let the table to be unchangeable.
 *
 * @author tianshu_ji
 * @version 3.0
 * @since 2019-04-07
 */
public class TourTable extends JTable {
    public TourTable(Object[][] a, Object[] b){
        super((TableModel) new JTable(a, b));

    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
