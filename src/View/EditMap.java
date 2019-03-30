package View;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.*;

import Model.*;

/**
 * <h1>EditMap</h1> 
 * This class show create new map View The class include method
 * add line, delete line, paint line.
 *
 * @author chenwei_song
 * @version 3.0
 * @since 2019-03-05
 */
public class EditMap extends JFrame implements Observer {
	JFrame frame = new JFrame("Edit Map");
	String currentstate;
	String ct;
	public static HashMap<String, Line> lineMap = new HashMap<>();
	public static HashMap<String, Country> countries = new HashMap<>();
	public static HashMap<String, Continent> continents = new HashMap<>();

	/**
	 * It is constructor loading the JFrame as a thread.
	 */
	public EditMap() {
		EventQueue.invokeLater(new Runnable() {

			/**
			 * This is a thread.
			 */
			@Override
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
						| UnsupportedLookAndFeelException ex) {
					ex.printStackTrace();
				}
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				frame.add(new EditPane());
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}
		});
	}

	/**
	 * EditPane. it is a JPanel to show all JLable, JButton, graphics in the JFrame.
	 * Moreover, MouseListner is applied on the panel, to process all operations on
	 * the JPanel.
	 */
	public class EditPane extends JLayeredPane {
		public EditPane() {
			File[] images = new File(System.getProperty("user.dir") + "/resource").listFiles(new FileFilter() {

				/**
				 * This method judges file name belonging to which type.
				 */
				@Override
				public boolean accept(File pathname) {
					String name = pathname.getName().toLowerCase();
					return name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".bmp")
							|| name.endsWith(".gif");
				}
			});

			MouseHandler mh = new MouseHandler();

			// create button country
			JButton country = new JButton("country");
			country.setName("country");
			country.setBackground(Color.green);
			country.setBounds(1100, 20, 80, 25);
			country.addMouseListener(mh);

			// frame.getContentPane().add(country);
			add(country);

			// create button line
			JButton line = new JButton("line");
			line.setBounds(1100, 60, 80, 25);
			line.addMouseListener(mh);
			line.setName("line");
			add(line);

			// continent name text
			JLabel continame = new JLabel("continent: ");
			continame.setBounds(1000, 100, 80, 25);
			add(continame);

			// continent name button
			JButton continent = new JButton("A");
			continent.setBounds(1100, 100, 80, 25);
			continent.setName("continent");
			ct = "A";
			continent.addMouseListener(mh);
			add(continent);

			// control value JLable
			JLabel contiJLabel = new JLabel("control value:");
			contiJLabel.setBounds(1000, 140, 80, 25);
			add(contiJLabel);

			// control value JText
			final JTextField cvtext = new JTextField(20);
			cvtext.setBounds(1100, 140, 80, 25);
			cvtext.addKeyListener(new KeyAdapter() {

				/**
				 * This method xxxxxxx
				 */
				@Override
				public void keyPressed(KeyEvent key) {
					if (key.getKeyCode() == KeyEvent.VK_ENTER) {
						System.out.println(cvtext.getText());
						Continent c = new Continent(ct, Integer.parseInt(cvtext.getText()), null);
						continents.put(c.getName(), c);
					}
				}

			});
			add(cvtext);

			// delete button
			JButton delete = new JButton("delete");
			delete.setBounds(1100, 180, 80, 25);
			delete.setName("delete");
			delete.addMouseListener(mh);
			add(delete);

			// submit button
			JButton submit = new JButton("submit");
			submit.setBounds(1100, 220, 80, 25);
			submit.setName("submit");
			submit.addMouseListener(mh);
			add(submit);

		}

		/**
		 * It is an override method in JFrame. this method could draw the connect line
		 * between countries.
		 * 
		 * @param g It is a tool that can draw any shape on the JFrame.
		 */
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			for (String key : lineMap.keySet()) {
				Point s = lineMap.get(key).getStart();
				Point e = lineMap.get(key).getEnd();
				g.drawLine(s.x + 50, s.y + 50, e.x + 50, e.y + 50);
			}
		}

		/**
		 * This method defines the size of window.
		 * 
		 * @return Window size.
		 */
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(1200, 650);
		}

		/**
		 * This class mouse listener for all buttons (country, line, delete, submit)
		 * each button could invoke related methods.
		 */
		public class MouseHandler extends MouseAdapter {
			iconHandler ih = new iconHandler();
			File image = new File(System.getProperty("user.dir") + "/resource/country.png");
			int imgname = 1;

			/**
			 * All the button would be executed as mouseClicked method mouseClicked method
			 * is an override method in JPanel.
			 * 
			 * @param e It means a component that mouse clicked.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				String lablename = e.getComponent().getName();
				if (lablename.equals("continent")) {

					// change continent name
					JButton con = (JButton) e.getComponent();
					if (!continents.containsKey(con.getText()) || continents.get(con.getText()).getConvalue() == 0) {
						JOptionPane.showMessageDialog(null, "define a control value");
					} else {
						int conname = con.getText().charAt(0) + 1;
						con.setText(Character.toString((char) conname));
						ct = Character.toString((char) conname);
					}

					System.out.println("set control value for continent" + con.getText());
				} else if (lablename.equals("country")) {
					currentstate = "country";
					try {
						BufferedImage img = ImageIO.read(image);
						JLabel label = new JLabel(new ImageIcon(img));
						label.setSize(label.getPreferredSize());
						label.setLocation(50, 50);
						label.setText(ct);
						label.setName(Integer.toString(imgname));
						label.setHorizontalTextPosition(JLabel.CENTER);
						label.setVerticalTextPosition(JLabel.CENTER);
						label.addMouseListener(ih);
						label.addMouseMotionListener(ih);
						add(label);

						// add country to hash map
						Point location = label.getLocation();

						// countryList)
						Country c = new Country(imgname, location, 0, null, ct, null);
						countries.put(Integer.toString(imgname), c);
						imgname++;
						System.out.println("country");

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else if (lablename.equals("line")) {
					currentstate = "line";
					System.out.println("line");

				} else if (lablename.equals("delete")) {
					currentstate = "delete";
					System.out.println("delete");
				} else if (lablename.equals("submit")) {
					System.out.println("submit");

					for (String key : countries.keySet()) {

						System.out.println(
								key + " " + countries.get(key).getContinent() + countries.get(key).getCountryList());
					}

					for (String key : continents.keySet()) {
						ArrayList<String> cList = new ArrayList<>();
						for (String k : countries.keySet()) {
							boolean temp = countries.get(k).getContinent().equals(key);
							if (temp) {
								cList.add(k);

							}
						}
						continents.get(key).setCountryList(cList);

					}
					for (String key : continents.keySet()) {
						System.out.print("continent " + key);
						for (int i = 0; i < continents.get(key).getCountryList().size(); i++) {
							System.out.print(continents.get(key).getCountryList().get(i) + " ");
						}

						System.out.println();
					}

					Checkmap checkmap = new Checkmap(countries, continents);

					checkmap.judge();
					boolean result = Message.isSuccess();

					System.out.println(result);

					if (result) {

						String m = JOptionPane.showInputDialog("save map?");
						if (m != null) {

							IO io = new IO(countries, continents);
							io.writeFile(m);
							System.out.println(m);
							JOptionPane.showMessageDialog(null, "save map successful");

							frame.dispose();
							new StartGame();
						} else {
							for (String key : continents.keySet()) {
								continents.get(key).setCountryList(null);
							}
						}

					} else {
						String error = Message.getMessage();
						JOptionPane.showMessageDialog(null, error);
						for (String key : continents.keySet()) {
							continents.get(key).setCountryList(null);
						}
					}

				}

			}

		}

		/**
		 * It is a mouse listener class for all country labels all country label could
		 * drag, add, delete, and be connected each other.
		 */
		public class iconHandler extends MouseAdapter {
			private Point offset;
			boolean startline = true;
			private String stcountry;

			/**
			 * 
			 */
			@Override
			public void mousePressed(MouseEvent e) {

				JLabel label = (JLabel) e.getComponent();

				moveToFront(label);
				offset = e.getPoint();

				// delete country
				if (currentstate.equals("delete")) {
					offset = e.getPoint();
					String l = countries.get(label.getName()).getCountryList();
					if (l != null) {
						deleteLine(label.getName());

					}

					frame.remove(label);
					remove(label);

					countries.remove(label.getName());
					revalidate();
				}

				// connect country
				else if (currentstate.equals("line")) {

					if (startline) {
						stcountry = label.getName();
						System.out.println("start country " + stcountry);
						startline = false;
					} else {

						System.out.println("end country " + label.getName());
						if (stcountry != null && label.getName() != null && !stcountry.equals(label.getName())) {
							addLine(stcountry, label.getName());
						}

						stcountry = null;
						startline = true;

					}

				}

			}

			/**
			 * This method could delete line when delete a country, all the line that
			 * connects the deleted country would be removed.
			 * 
			 * @param target The deleted country name.
			 */
			public void deleteLine(String target) {
				ArrayList<String> removeline = new ArrayList<>();
				for (String key : lineMap.keySet()) {
					String[] stend = key.split(" ");
					if (stend[0].equals(target) || stend[1].equals(target)) {
						removeline.add(key);

					}
				}
				for (int i = 0; i < removeline.size(); i++) {
					lineMap.remove(removeline.get(i));
				}

				// delete target in country list
				String target1 = target + " ";

				for (String key : countries.keySet()) {

					String list = countries.get(key).getCountryList();
					countries.get(key).setCountryList(list.replace(target1, ""));
				}

				repaint();

			}

			/**
			 * This method creates a line between countries.
			 * 
			 * @param start The country name of line start point.
			 * @param end   The country name of line end point.
			 */
			public void addLine(String start, String end) {

				Point st = countries.get(start).getLocation();
				Point ed = countries.get(end).getLocation();

				String newstlist;
				String newedlist;

				if (countries.get(start).getCountryList() == null) {
					newstlist = end + " ";
					countries.get(start).setCountryList(newstlist);
				} else {
					newstlist = countries.get(start).getCountryList() + end + " ";
					countries.get(start).setCountryList(newstlist);
				}
				if (countries.get(end).getCountryList() == null) {
					newedlist = start + " ";
					countries.get(end).setCountryList(newedlist);
				} else {
					newedlist = countries.get(end).getCountryList() + start + " ";
					countries.get(end).setCountryList(newedlist);
				}
				Line l = new Line(st, ed);
				lineMap.put(start + " " + end, l);
				repaint();

			}

			/**
			 * The method could drag the country on the JFrame.
			 * 
			 * @param e The component that mouse listened.
			 */
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = e.getPoint().x - offset.x;
				int y = e.getPoint().y - offset.y;
				Component component = e.getComponent();
				Point location = component.getLocation();
				if (currentstate.equals("country")) {
					location.x += x;
					location.y += y;
					countries.get(component.getName()).setLocation(location);
					component.setLocation(location);
					PaintLine(component.getName());
				}

			}

			/**
			 * When the mouse drags the country, the connected line should move at same
			 * time.
			 * 
			 * @param target The country name that mouse dragged.
			 */
			public void PaintLine(String target) {

				for (String key : lineMap.keySet()) {
					String[] stend = key.split(" ");
					if (stend[0].equals(target)) {
						Point s = countries.get(target).getLocation();
						lineMap.get(key).setStart(s);
					} else if (stend[1].equals(target)) {
						Point e = countries.get(target).getLocation();
						lineMap.get(key).setEnd(e);
					}
				}
				repaint();

			}

		}

	}

	/**
	 * It is observer override update method update countries, continents, players
	 * at the same time.
	 *
	 * @param obs The observable object.
	 * @param x   An argument passed to the notifyObservers method.
	 */
	@Override
	public void update(Observable obs, Object x) {
	}

}
