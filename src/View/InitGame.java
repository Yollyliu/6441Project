package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Controller.InitGame_controller;
import Controller.LoadMap_controller;
import Model.Checkmap;
import Model.Continent;
import Model.Country;
import Model.IO;
import Model.Message;

/**
 * <h1>InitGame</h1> 
 * Input the player number and select map.
 *
 * @author chenwei_song
 * @version 3.0
 * @since 2019-03-01
 */
public class InitGame extends JFrame {

	int n;
	JFrame frame = new JFrame();

	public HashMap<String, Country> countries = new HashMap<>();
	public HashMap<String, Continent> continents = new HashMap<>();

	/**
	 * It is a constructor that creates a thread of JFrame.
	 */
	public InitGame() {
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
				frame.add(new initPane());
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
	public class initPane extends JLayeredPane {
		
		/**
		 * It is a constructor to add buttons on the panel.
		 */
		public initPane() {

			JLabel palyernum = new JLabel("player number :");
			palyernum.setBounds(150, 100, 100, 30);
			add(palyernum);

			final JTextField num = new JTextField(20);
			num.setBounds(250, 100, 80, 25);
			add(num);
			num.addKeyListener(new KeyAdapter() {
				
				/**
				 * This is a keyPressed function.
				 */
				@Override
				public void keyPressed(KeyEvent key) {
					if (key.getKeyCode() == KeyEvent.VK_ENTER) {
						System.out.println(num.getText());
						n = Integer.parseInt(num.getText());
					}
				}

			});

			JButton select = new JButton("Select a map");
			add(select);
			select.setBounds(200, 200, 200, 100);

			select.addActionListener(new ActionListener() {

				/**
				 * This is an actionPerformed function.
				 */
				@Override
				public void actionPerformed(ActionEvent e) {

					JFileChooser jfc = new JFileChooser();
					jfc.setCurrentDirectory(new File("mapfile/"));
					jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

					int returnValue = jfc.showOpenDialog(null);

					if (returnValue == JFileChooser.APPROVE_OPTION) {
						System.out.println("File name:" + jfc.getSelectedFile().getName());
						String filename = "mapfile/" + jfc.getSelectedFile().getName();

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

								frame.dispose();

								InitGame_controller controller = new InitGame_controller();
								controller.receive(n, filename);
							}

						} else {

							String error = Message.getMessage();
							JOptionPane.showMessageDialog(null, error);
						}

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
	}

}
