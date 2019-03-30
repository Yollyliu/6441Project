package View;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
 









import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import Controller.LoadMap_controller;
import Model.Checkmap;
import Model.IO;
import Model.Message;

/**
 *<h1>SelectMap</h1>
 * Creating a new map or load map to edit.
 * 
 * @author chenwei_song
 * @version 3.0
 * @since 2019-02-27
 */
public class SelectMap extends JFrame{
	
	JFrame frame  = new JFrame();
	
	/**
	 * It is a constructor.
	 */
	public SelectMap(){
		EventQueue.invokeLater(new Runnable() {
			
			/**
			 * It is a thread.
			 */
			@Override
			public void run() {
				 try {
	                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
	                    ex.printStackTrace();
	                }
	                frame.add(new selectPane());
	                frame.pack();
	                frame.setLocationRelativeTo(null);
	                frame.setVisible(true);
	             
			}
		});	

}
	/**
	 * It is a panel to show buttons
	 */
	public class selectPane extends JLayeredPane{
		
		public selectPane(){
			
		JButton	select = new JButton("Select a map");
		add(select);
		select.setBounds(200, 200, 200, 100);
		
		JButton	create = new JButton("create a new map");
		add(create);
		create.setBounds(200, 300, 200, 100);
		create.setVisible(true);
	
		select.addActionListener(new ActionListener() {
			
			/**
			 * This method will be invoked when an action occurs.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser jfc=new JFileChooser();
				jfc.setCurrentDirectory(new File("mapfile/"));
				jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
				
				int returnValue = jfc.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println("File name:"+jfc.getSelectedFile().getName());
					String filename = "mapfile/"+ jfc.getSelectedFile().getName();
			
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
						}
						frame.dispose();
						LoadMap_controller lmController = new LoadMap_controller();
						lmController.sendfilename(filename);
					}
					else {
					
						String error = Message.getMessage();
						JOptionPane.showMessageDialog(null, error);
					}		
					
				}
				System.out.println(jfc.getSelectedFile().getName());
		
			}
		});
		
		
		
		create.addActionListener(new ActionListener() {
			
			/**
			 * This method will be invoked when an action occurs.
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new EditMap();
				System.out.println("create a new map");
				
			}
		});
	}
		/**
		 * It is a method to define the size of window.
		 */
		@Override
    	public Dimension getPreferredSize(){
    		return new Dimension(650,650);
    		}
		}
	}


