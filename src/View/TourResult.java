package View;

import Model.Tournament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Vector;

/**
 * <h1>TourResult</h1>
 * Show the tournament result in a panel.
 *
 * @author tianshu_ji
 * @version 3.0
 * @since 2019-04-07
 */
public class TourResult extends JFrame{

    JFrame jFrame = new JFrame("Result");
    TourTable table;
    JTable jTable;

    Tournament tournament=new Tournament();

    String M;
    String[] MArray;
    int MInt;
    String P;
    int G;
    int D;

    public TourResult(Tournament tournament) {
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
        System.out.println("get tournament behaivoir "+ tournament.getBehavoirs().size());
        this.tournament = tournament;
        System.out.println("CHILEME");
        System.out.println("The behavior is in TourResult"+ tournament.getBehavoirs().size());

        tournament.runTourn();
        System.out.println("The behavior is in TourREsult"+ tournament.getBehavoirs().size());
        LinkedList<String> mapList = tournament.getMaps();
        for (int i = 0; i < mapList.size(); i++) {
            M = mapList.get(i) + " ";
        }
        M = M.substring(0, M.length()-1);

        MArray = M.split(" ");
        MInt = MArray.length;

        ArrayList pList = tournament.getBehavoirs();
        System.out.println("********** tournament.getBehavoirs ******"+tournament.getBehavoirs().size());
//        for (int i = 0; i < pList.size(); i++) {
//            P = pList.get(i) + " ";
//        }
//
//        P = P.substring(0, P.length()-1);
        StringBuffer ss=new StringBuffer();
        for(int i=0;i<tournament.getBehavoirs().size()-1;i++){
            ss.append(tournament.getBehavoirs().get(i));
            ss.append(" ");
        }
        ss.append(tournament.getBehavoirs().get(tournament.getBehavoirs().size()-1));
        P=ss.toString();
        System.out.println("PPPP" + ss.toString());
        M = "";
        for (int i = 0; i < tournament.getMaps().size()-1; i++) {
            M = M + tournament.getMaps().get(i)+" ";
        }
        M= M+tournament.getMaps().get(tournament.getMaps().size()-1);

        G = tournament.getGameTime();
        D = tournament.getGameTurn();
        jFrame.add(new TourResultPane(M, P, G, D));
        jFrame.pack();
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);
    }

    /**
     * This is a pane class to add components
     */
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
            MInt = tournament.getResultInformation().size();
            System.out.println("MINTTTTT" + MInt);

            Object[][] cellData = new Object[MInt + 1][G + 1];
            Object[] columTitle = new Object[G + 1];

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

    /**
     * This is a method to create the data in the table.
     *
     * @param cellData the data obejct in each row.
     * @param columTitle the title in each colum.
     */
    public void dataBase(Object[][] cellData, Object[] columTitle){

        LinkedList<String> resultStrings = tournament.getResultInformation();
        System.out.println("resultStrings "+tournament.getResultInformation().size());

        for(int i=0;i<resultStrings.size();i++){
            System.out.println(resultStrings.get(i));;
        }



        System.out.println("resultStrings "+resultStrings.size());
        int row=resultStrings.size();
        int colomun=resultStrings.get(0).split(" ").length;
        String[][] results=new String[row][colomun];
        //String[][] results = new String[MInt][G];
        resultStrings.get(0).split(" ");


        for (int i = 0; i < row; i++) {
            for (int j = 0; j < colomun; j++) {
                String[] temp = resultStrings.get(i).split(" ");
                results[i][j] = temp[j];
            }
        }

        String firstLine = "     ";
        //Vector v = new Vector();
        //v.add(firstLine);
        cellData[0][0] = firstLine;
        columTitle[0] = firstLine;

        for (int i = 0; i < colomun; i++) {
            int j = i + 1;
            //v.add("Game " + j);
            cellData[0][j] = "Game " + j;
            columTitle[j] = "Game " + j;
        }

        String data = "maps";
        //Vector v1 = new Vector();
        for (int i = 0; i < row; i++) {
            int k = i + 1;
            //v1.add("Map " + k);
            cellData[k][0] = "Map " + k;
            for (int j = 0; j < colomun; j++) {
                //v1.add(results[i][j]);
                cellData[k][j + 1] = results[i][j];
            }
            //model.addRow(v1);
        }
    }

}



