package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Controller.InitGame_controller;
import Model.*;

/**
 * <h1>TourInitGame</h1>
 * Input the player types, game number, turn number and select map.
 *
 * @author tianshu_ji
 * @version 3.0
 * @since 2019-04-07
 */
public class TourInitGame extends JFrame {

    ArrayList<String> behaviors = new ArrayList<>();
    //	int n;
    JFrame frame = new JFrame();
    int gameNumber = 0;
    int turnNumber = 0;
    int mapNumber = 0;
    LinkedList<String> maps = new LinkedList<>();


    public HashMap<String, Country> countries = new HashMap<>();
    public HashMap<String, Continent> continents = new HashMap<>();

    /**
     * It is a constructor that creates a thread of JFrame.
     */
    public TourInitGame() {
        EventQueue.invokeLater(new Runnable() {

            /**
             * It is a thread that upload a thread
             */
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException ex) {
                    ex.printStackTrace();
                }
                frame.add(new TourInitPane());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    /**
     * It is a initPane to add buttons on the JFrame.
     *
     */
    public class TourInitPane extends JLayeredPane {

        /**
         * It is a constructor to add buttons on the panel.
         */
        public TourInitPane() {

            JLabel label = new JLabel("Please select player: ");
            add(label);
            label.setBounds(50,150,200,25);

            JButton aggressive = new JButton("Aggressive");
            add(aggressive);
            aggressive.setBounds(50,200,80,50);

            JButton benevolent = new JButton("Benevolent");
            add(benevolent);
            benevolent.setBounds(150,200,80,50);

            JButton random = new JButton("Random");
            add(random);
            random.setBounds(250,200,80,50);

            JButton cheater = new JButton("Cheater");
            add(cheater);
            cheater.setBounds(350,200,80,50);

            JLabel player_num = new JLabel("Player number:");
            add(player_num);
            player_num.setBounds(50,250,100,25);

            JLabel numText = new JLabel("0");
            add(numText);
            numText.setBounds(150,250,80,25);

            JButton select = new JButton("Select a map");
            add(select);
            select.setBounds(200,300 , 200, 100);

            JButton gamesNum = new JButton("Games number");
            add(gamesNum);
            gamesNum.setBounds(200, 400, 150, 50);

            JButton turnsNum = new JButton("Turns number");
            add(turnsNum);
            turnsNum.setBounds(200, 450, 150, 50);

            JButton okButton = new JButton("OK");
            add(okButton);
            okButton.setBounds(200, 500, 80, 50);




            aggressive.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    behaviors.add("aggressive");
                    numText.setText(Integer.toString(behaviors.size()));
                }
            });

            benevolent.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    behaviors.add("benevolent");
                    numText.setText(Integer.toString(behaviors.size()));
                }
            });

            random.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    behaviors.add("random");
                    numText.setText(Integer.toString(behaviors.size()));
                }
            });

            cheater.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    behaviors.add("cheater");
                    numText.setText(Integer.toString(behaviors.size()));
                }
            });

            select.addActionListener(new ActionListener() {

                /**
                 * This is an actionPerformed function.
                 */
                @Override
                public void actionPerformed(ActionEvent e) {

                    JFileChooser jfc = new JFileChooser();
                    jfc.setCurrentDirectory(new File("mapfile/"));
                    jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                    jfc.setMultiSelectionEnabled(true);

                    int returnValue = jfc.showOpenDialog(null);
                    File[] files = jfc.getSelectedFiles();
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                        System.out.println(files.length);

                        if (files.length > 5){
                            JOptionPane.showMessageDialog(null, "Please select maps less than 6");
                        } else {
                            for (int i = 0; i < files.length; i++) {
                                System.out.println("File name:" + files[i].getName());
                                String filename = "mapfile/" + files[i].getName();

                                IO io = new IO();
                                io.readFile(filename);
                                boolean isMap = Message.isSuccess();
                                if (isMap) {
                                    Checkmap checkmap = new Checkmap(io.getCountries(), io.getContinents());
                                    checkmap.judge();
                                    boolean result = Message.isSuccess();
                                    if (!result) {
                                        String error = Message.getMessage();
                                        JOptionPane.showMessageDialog(null, error);
                                    } else {

                                        //frame.dispose();

//                                        InitGame_controller controller = new InitGame_controller();
//                                        controller.receive(behaviors, filename);

                                        maps.add(filename);

                                    }

                                } else {

                                    String error = Message.getMessage();
                                    JOptionPane.showMessageDialog(null, error);
                                }
                            }

                        }

                    }

                }
            });

            gamesNum.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gameNumber = slider(1, 5, "games");
                }
            });

            turnsNum.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    turnNumber = slider(10, 50, "turns");
                }
            });

            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (behaviors.size() < 2 && maps.size() < 1 && gameNumber == 0 && turnNumber == 0){
                        JOptionPane.showMessageDialog(null, "Please Fill Right");
                    } else {
                        Tournament tournament = new Tournament(behaviors, maps, gameNumber, turnNumber);

                        System.out.println("Get behavoir in TourInitGame "+tournament.getBehavoirs().size());
                        frame.dispose();
                        new TourResult(tournament);
                    }
                }
            });

        }

        /**
         * It is an override method in JPanel it defines the window size.
         *
         * @return window size the data type is dimension.
         */
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(650, 650);
        }

        public int slider(int min, int max, String string){
            final JFrame frame = new JFrame();
            JDialog dialog = new JDialog(frame, true);
            JPanel panel = new JPanel();
            panel.setSize(300, 300);
            final JLabel sliderLabel = new JLabel(string + "         ", JLabel.CENTER);
            sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            final JSlider framesPerSecond = new JSlider(min, max);
            framesPerSecond.setMinorTickSpacing(1);
            framesPerSecond.setPaintTicks(true);
            framesPerSecond.setPaintLabels(true);
            framesPerSecond.setBorder(BorderFactory.createEmptyBorder(0,0,10,0));
            Font font = new Font("Serif", Font.ITALIC, 15);
            framesPerSecond.setFont(font);
            framesPerSecond.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    int fps = (int) source.getValue();
                    sliderLabel.setText(string + ":" + String.valueOf(fps));
                }
            });
            panel.add(sliderLabel);
            panel.add(framesPerSecond);

            JButton button = new JButton("OK");
            button.setBounds(150, 150, 25, 30);
            panel.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(framesPerSecond.getValue());
                    frame.dispose();
                }
            });

            dialog.add(panel);
            dialog.setLocationRelativeTo(null);
            dialog.setUndecorated(true);
            dialog.setSize(500, 100);
            dialog.setVisible(true);
            int result = (int) framesPerSecond.getValue();
            return result;
        }
    }
}
