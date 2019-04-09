package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
/**
 *<h1>StartGame</h1>
 * Creating play game or edit game.
 * 
 * @author chenwei_song,tianshu_ji
 * @version 3.0
 * @since 2019-02-27
 */
public class StartGame extends JFrame{

	
	
	private static final long serialVersionUID = 1L;
	JFrame frame  = new JFrame();
	
	/**
	 * It is a main method to run the project.
	 * 
	 * @param args It is a main method.
	 */
	public static void main(String[] args) {
		new StartGame();
		
	}
	
	/**
	 *  It is a constructor.
	 */
	public StartGame(){
		EventQueue.invokeLater(new Runnable() {
			
			/**
			 * This is a thread.
			 */
			@Override
			public void run() {
				 try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                    ex.printStackTrace();
	                }
				 
	                frame.add(new StartPane());
	                frame.pack();
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
	             
			}
		});
	}
	/**
	 * It is a panel to add buttons.
	 */
	public class StartPane extends JLayeredPane{
		/**
		 * It is a constructor function.
		 */
		public StartPane(){

			JButton single = new JButton("Singe Game");
			add(single);
			single.setBounds(200, 100, 200, 100);
			single.setVisible(true);

			JButton tournament = new JButton("Tournament");
			add(tournament);
			tournament.setBounds(200, 200, 200, 100);
			tournament.setVisible(true);

			JButton Edit = new JButton("Edit Map");
			add(Edit);
			Edit.setBounds(200, 300, 200, 100);
			Edit.setVisible(true);

			single.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					new InitGame();
					System.out.println("Init Game");
				}
			});

			tournament.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					new TourInitGame();
					System.out.println("Init Game");
				}
			});

			Edit.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					frame.dispose();
					new SelectMap();
					System.out.println("Select map");

				}
			});
		}

		/**
		 * It defines the window size.
		 */
		@Override
    	public Dimension getPreferredSize(){
    		return new Dimension(650,650);
    		}
		}	

}
