package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class TourResult extends JFrame{

    JFrame jFrame = new JFrame("Result");
    TourTable table;
    JTable jTable;

    String M = "Map1 Map2 Map3";
    String[] MArray = M.split(" ");
    int MInt = MArray.length;
    String P = "Aggressive Benevolent Random Cheater";
    int G = 4;
    int D = 30;
    String result1 = "Aggressive Random Cheater Cheater";
    String result2 = "Cheater Draw Cheater Aggressive";
    String result3 = "Cheater Aggressive Cheater Draw";
    String[] result1Array = result1.split(" ");
    String[] result2Array = result2.split(" ");
    String[] result3Array = result3.split(" ");
    String[][] results = new String[][]{result1Array, result2Array, result3Array};

    public TourResult() {
//        EventQueue.invokeLater(new Runnable() {
//
//            /**
//             * It is a thread that upload a thread
//             */
//            @Override
//            public void run() {
//                try {
//                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
//                        | UnsupportedLookAndFeelException ex) {
//                    ex.printStackTrace();
//                    jFrame.add(new TourResultPane("M: Map1, Map2, Map3", "P: Aggressive, Benevolent, Random, Cheater", 4, 30));
//                    jFrame.pack();
//                    jFrame.setLocationRelativeTo(null);
//                    jFrame.setVisible(true);
//                }
//            }
//        });

        jFrame.add(new TourResultPane(M, P, G, D));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    public class TourResultPane extends JLayeredPane{
        public TourResultPane(String M, String P, int G, int D) {
            JLabel map = new JLabel("M: " + M);
            add(map);
            map.setBounds(50, 150, 400, 25);

            JLabel person = new JLabel("P: " + P);
            add(person);
            person.setBounds(50, 175, 400, 25);

            JLabel game = new JLabel("G: " + G);
            add(game);
            game.setBounds(50, 200, 400, 25);

            JLabel round = new JLabel("D: " + D);
            add(round);
            round.setBounds(50, 225, 400, 25);

            String[] maps = M.split(" ");

            Object[][] tableData = {
                    new Object[]{"     ", "Game" + "1", "Game" + "2", "Game" + "3", "Game" + "4"},
                    new Object[]{maps[0], "Aggressive", "Random", "Cheater", "Cheater"},
                    new Object[]{maps[1], "Cheater", "Draw", "Cheater", "Aggressive"},
                    new Object[]{maps[2], "Cheater", "Aggressive", "Cheater", "Draw"}
            };

            Object[] columnTilte = {"     ", "Game1", "Game2", "Game3", "Game4"};
            //DefaultTableModel model = new DefaultTableModel();

            Object[][] cellData = new Object[4][5];
            Object[] columTitle = new Object[5];

            dataBase(cellData, columTitle);
            //table = new TourTable(cellData, columnTilte);
            jTable = new JTable(cellData, columTitle);
            //add(table);
            add(jTable);
            jTable.setBounds(50, 300, 600, 150);
            jFrame.pack();
            jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            jFrame.setVisible(true);



        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 650);
        }
    }

    public void dataBase(Object[][] cellData, Object[] columTitle){
        String firstLine = "     ";
        //Vector v = new Vector();
        //v.add(firstLine);
        cellData[0][0] = firstLine;
        columTitle[0] = firstLine;

        for (int i = 0; i < G; i++) {
            int j = i + 1;
            //v.add("Game " + j);
            cellData[0][j] = "Game " + j;
            columTitle[j] = "Game " + j;
        }

        String data = "maps";
        //Vector v1 = new Vector();
        for (int i = 0; i < MInt; i++) {
            int k = i + 1;
            //v1.add("Map " + k);
            cellData[k][0] = "Map " + k;
            for (int j = 0; j < G; j++) {
                //v1.add(results[i][j]);
                cellData[k][j + 1] = results[i][j];
            }
            //model.addRow(v1);
        }
    }

    public static void main(String[]args){
            new TourResult();
        }


}



