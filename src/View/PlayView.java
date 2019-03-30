package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;
import Model.Attack;
import Model.Continent;
import Model.Country;
import Model.InitializePhase;
import Model.Line;
import Model.Player;

/**
 * <h1>PlayView</h1> It is Play Game main View have three function
 * (reinforcement, attack, fortification)
 *
 * @author chenwei_song
 * @version 3.0
 * @since 2019-03-01
 */
public class PlayView extends JFrame implements Observer {

	public HashMap<String, Line> lineMap = new HashMap<>();
	public HashMap<String, Country> countries = new HashMap<>();
	public HashMap<String, Continent> continents = new HashMap<>();
	public HashMap<String, Player> playerSet = new HashMap<>();
	public JLabel name;
	public JLabel color;
	public JLabel armies;
	public String currentPhase;
	public JButton phase;
	private boolean WIN = false;
	BackEnd b;
	InitializePhase observable = new InitializePhase();

	DominationView dominationView;

	private static final long serialVersionUID = 1L;
	JFrame frame = new JFrame("Risk Game");

	/**
	 * It is observer override update method update countries, continents, players
	 * at the same time
	 */

	@Override
	public void update(Observable obs, Object x) {
		countries = ((InitializePhase) obs).getCountries();
		continents = ((InitializePhase) obs).getContinents();
		playerSet = ((InitializePhase) obs).getPlayerSet();

	}

	/**
	 * It is constructor of play view to upload a thread of JFrame show all buttons
	 * ,labels, texts.
	 */
	public PlayView() {
		b = new BackEnd();
		observable.addObserver(b);
		observable.addObserver(this);
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

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				PlayPane playPane = new PlayPane();
				frame.add(playPane, BorderLayout.WEST);
				observable.addObserver(dominationView);
				frame.add(dominationView, BorderLayout.EAST);
				observable.addObserver(dominationView);

				frame.setSize(1300, 650);
				frame.pack();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);

			}
		});

	}

	/**
	 * It is a JLayeredPane to add buttons, labels, texts. it can receive mouse
	 * listener component.
	 */
	public class PlayPane extends JLayeredPane {

		iconHandler ih = new iconHandler();

		public PlayPane() {
			observable.setCountries(countries);
			observable.setContinents(continents);
			observable.setPlayerSet(playerSet);
			dominationView = new DominationView(observable);
			observable.addObserver(dominationView);
			File image = new File("resource/tower.png");

			for (String key : countries.keySet()) {

				Point start = countries.get(key).getLocation();
				String continent = countries.get(key).getContinent() + " " + countries.get(key).getArmy();

				String countryList = countries.get(key).getCountryList();
				countries.get(key).setCountryList(countryList + " ");
				String[] link = countryList.split(" ");
				try {
					BufferedImage img = ImageIO.read(image);

					int width = img.getWidth();
					int height = img.getHeight();

					WritableRaster raster = img.getRaster();
					for (int xx = 0; xx < width; xx++) {
						for (int yy = 0; yy < height; yy++) {
							int[] pixels = raster.getPixel(xx, yy, (int[]) null);
							pixels[0] = countries.get(key).getColor().getRed();
							pixels[1] = countries.get(key).getColor().getGreen();
							pixels[2] = countries.get(key).getColor().getBlue();
							raster.setPixel(xx, yy, pixels);
						}

					}

					JLabel label = new JLabel(new ImageIcon(img));
					label.setSize(label.getPreferredSize());
					label.setLocation(start);
					label.setText(continent);
					label.setName(key);
					label.setHorizontalTextPosition(JLabel.CENTER);
					label.setVerticalTextPosition(JLabel.CENTER);
					label.addMouseListener(ih);
					label.addMouseMotionListener(ih);
					add(label);

				} catch (IOException e1) {
					e1.printStackTrace();
				}

				for (int i = 0; i < link.length; i++) {
					if (countries.containsKey(link[i])) {
						Point end = countries.get(link[i]).getLocation();
						lineMap.put(key + " " + link[i], new Line(start, end));
					}
				}
			}

			repaint();

			// create button country
			phase = new JButton("start up phase");
			currentPhase = "start up";
			phase.setName("phase");
			phase.setBackground(Color.green);
			phase.setBounds(400, 600, 200, 50);
			phase.addMouseListener(ih);
			add(phase);

			// player name color label
			JLabel player = new JLabel("Player: ");
			player.setBounds(1000, 20, 80, 25);
			player.setName("player");
			add(player);
			name = new JLabel();
			name.setText("1");
			name.setName("player");
			name.setBounds(1060, 20, 20, 25);
			add(name);
			color = new JLabel("");
			color.setBounds(1110, 20, 25, 25);
			color.setBackground(playerSet.get("1").getColor());
			color.setOpaque(true);
			add(color);

			// receive armies number
			JLabel army = new JLabel();
			army.setText("Army: ");
			army.setBounds(1000, 60, 80, 25);
			add(army);
			String n = String.valueOf(playerSet.get("1").getArmy());
			armies = new JLabel(n);
			armies.setName("armies");
			armies.setBounds(1100, 70, 80, 25);
			add(armies);

		}

		/**
		 * This is a paint function.
		 * @param g is object of swing drawing tool it can draw any shapes in this project, it is used to draw line between countries.
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
		 * It is an override method of JFrame it defines the size of window.
		 * 
		 * @return The size of JFrame.
		 */
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(1200, 650);
		}

		/**
		 * It is an mouse adapter class to process the mouse operation we only use mouse
		 * click.
		 */
		public class iconHandler extends MouseAdapter {
			JLabel from;
			boolean start = true;

			/**
			 * It is a mouse clicked method.
			 * 
			 * @param e The component of mouse click.
			 */
			@Override
			public void mouseClicked(MouseEvent e) {

				String click = e.getComponent().getName();
				if (click.equals("phase")) {

					JButton phase = (JButton) e.getComponent();
					if (phase.getText().equals("Reinforcement")) {
						System.out.println("Reinforcement");
						armies.setText(String.valueOf(playerSet.get(name.getText()).getArmy()));

					} else if (phase.getText().equals("Attack")) {
						start = true;
						from = null;
						JOptionPane.showMessageDialog(null, "enter Fortification phase");
						phase.setText("Fortification");
						currentPhase = "Fortification";
					} else if (phase.getText().equals("Fortification")) {
						
						// earn card
						if (WIN) {
							observable.earnCard(name.getText());
						}
						WIN = false;
						System.out.println("Fortification, enter next phase Reinforcement, Change Player");
						String nextP = b.findnext(name.getText());

						phase.setText("Reinforcement");
						currentPhase = "Reinforcement";
						observable.Reinforcement(nextP);
						if (playerSet.get(nextP).getCardList().size() != 0) {
							observable.cardArmy(nextP, playerSet.get(nextP).getCardList(), false);
							armies.setText(
									"<html><body><p align=\"center\">calculating...<br/>press&nbsp;reinforcement</p></body></html>");

						} else {
							armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
						}
						name.setText(nextP);
						color.setBackground(playerSet.get(nextP).getColor());

					}

				}
				
////////////////////////////////////////////////				
				else {
					if (currentPhase.equals("start up")) {

						JLabel c = (JLabel) e.getComponent();
						
						// check whether the player click own country
						boolean match = rightcountry(name.getText(), c.getName());

						if (match) {
							observable.Startup(name.getText(), click);
							
							// update country army number
							String[] old = c.getText().split(" ");
							String now = old[0] + " " + countries.get(c.getName()).getArmy();
							c.setText(now);

							// change player
							String nextP = b.nextplayer(name.getText());
							if (nextP == "") {
								
								// get into reinforcement phase
								JOptionPane.showMessageDialog(null, "enter reinforcement phase");
								System.out.println("enter reinforcement phase");
								phase.setText("Reinforcement");
								currentPhase = "Reinforcement";
								observable.Reinforcement("1");
								name.setText("1");
								armies.setText(String.valueOf(playerSet.get("1").getArmy()));
								color.setBackground(playerSet.get("1").getColor());

							} else {
								name.setText(nextP);
								armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
								color.setBackground(playerSet.get(nextP).getColor());
							}

						}

						else {
							JOptionPane.showMessageDialog(null, "please select your own countries");
						}

					}
					
					/////////////////////////////////////////
					else if (currentPhase.equals("Reinforcement")) {

						JLabel c = (JLabel) e.getComponent();
						reinforcement(c, click);

					}
					
					//////////////////////////////////
					else if (currentPhase.equals("Attack")) {

						JLabel c = (JLabel) e.getComponent();
						boolean match = rightcountry(name.getText(), c.getName());
						boolean isOne = b.isOne(c.getName());
						if (start && match && isOne) {
							from = c;
							start = false;
							from.setBorder(new LineBorder(Color.ORANGE));
							System.out.println("you choose" + from.getText() + " as attack country");
						} else {
							if (start && !isOne) {
								JOptionPane.showMessageDialog(null, "this country cannot attack others");
							}
							if (start && !match) {
								JOptionPane.showMessageDialog(null, "please select your own countries");
							}
							if (!start && match) {
								JOptionPane.showMessageDialog(null,
										"please select country that belongs to other players");
								from.setBorder(null);
								from = null;
								start = true;
							}

						}

						if (!start && !match) {
							boolean isNeighbour = b.Isneighbour(from.getName(), c.getName());
							boolean self = rightcountry(name.getText(), c.getName());
							if (isNeighbour && !self) {
								
								// calculate disc number
								c.setBorder(new LineBorder(Color.ORANGE));
								attackerView(from, c);
								from = null;
								start = true;

							} else {
								from.setBorder(null);
								from = null;
								start = true;
								if (!isNeighbour) {
									JOptionPane.showMessageDialog(null, "please select your neighbour countries");
								}
								if (self) {
									JOptionPane.showMessageDialog(null,
											"please select country that belongs to other players");
								}
							}
						}
						if (Attack.isWIN()) {
							String winner = "";
							for (String key : playerSet.keySet()) {
								winner = key;
							}
							JOptionPane.showMessageDialog(null,
									"Congradulation!!!!player " + winner + " is winnner!!!");
							frame.dispose();
							new StartGame();

						}
						boolean canAttack = b.canAttack(name.getText());
						if (!canAttack && !Attack.isWIN()) {// cannot attack enter fortification phase
							JOptionPane.showMessageDialog(null, "you cannot attack,enter fortification phase");
							System.out.println("enter fortification phase");
							phase.setText("Fortification");
							currentPhase = "Fortification";
						}
						Attack.WIN = false;

					}

					////////////////////////////////////////
					else if (currentPhase.equals("Fortification")) {
						JLabel c = (JLabel) e.getComponent();
						boolean match = rightcountry(name.getText(), c.getName());
						if (match) {
							if (start) {
								from = c;
								from.setBorder(new LineBorder(Color.ORANGE));
								start = false;

								System.out.println("move army from " + from.getText());
							} else {
								c.setBorder(new LineBorder(Color.ORANGE));
								String to = c.getName();
								fortification(from, c, to);

								from.setBorder(null);
								c.setBorder(null);
								start = true;
								from = null;

							}

						} else {
							JOptionPane.showMessageDialog(null, "please select your own countries");
							if (from != null) {
								from.setBorder(null);
							}
						//	from.setBorder(null);
							start = true;
							from = null;
						}

					}
				}

			}

			/**
			 *
			 * Check whether current player click right country or not.
			 * 
			 * @param cplayer  Who wants to operate this country
			 * @param ccountry Which country would be operated
			 * @return true if the player owns this country otherwise is false.
			 */
			public boolean rightcountry(String cplayer, String ccountry) {
				boolean match = false;
				LinkedList<Country> findCountries = playerSet.get(cplayer).getCountryList();
				for (Iterator<Country> iterator = findCountries.iterator(); iterator.hasNext();) {
					String s = String.valueOf(iterator.next().getName());
					if (ccountry.equals(s)) {
						match = true;
					}

				}
				return match;
			}

			/**
			 * This method executes reinforcement.
			 *
			 * @param c     The country that is chosen.
			 * @param click The country name.
			 */
			public void reinforcement(JLabel c, String click) {
				boolean match = rightcountry(name.getText(), c.getName());

				if (match) {
					observable.Startup(name.getText(), click);
					
					// update country army number
					String[] old = c.getText().split(" ");
					String now = old[0] + " " + countries.get(c.getName()).getArmy();
					c.setText(now);
					armies.setText(String.valueOf(playerSet.get(name.getText()).getArmy()));
					if (playerSet.get(name.getText()).getArmy() == 0) {
						boolean canAttack = b.canAttack(name.getText());
						if (canAttack) {
							
							// enter attack phase
							JOptionPane.showMessageDialog(null, "enter attack phase");
							System.out.println("enter Attack phase");
							currentPhase = "Attack";
							phase.setText("Attack");
						} else { 
							
							// cannot attack enter fortification phase
							JOptionPane.showMessageDialog(null, "you cannot attack,enter fortification phase");
							System.out.println("enter fortification phase");
							phase.setText("Fortification");
							currentPhase = "Fortification";
						}

					}

				}

				else {
					JOptionPane.showMessageDialog(null, "please select your own countries");
				}
			}

			/**
			 * This method check whether current player click right country or not.
			 *
			 * @param attak  Who wants to operate this country.
			 * @param defend Which country would be operated.
			 */
			public void attackerView(JLabel attak, JLabel defend) {
				
				// select mode
				String mode = b.chooseMode();
				String defender = b.findPlayer(defend.getName());
				String[] atcoun = attak.getText().split(" ");
				String[] decoun = defend.getText().split(" ");
				if (mode != "") {
					if (mode.equals("All_Out")) {
						String canTransfer = observable.attackPhase(attak.getName(), defend.getName(), mode, 0, 0);
						Transfer(canTransfer, attak, defend);
					} else {
						
						// one - time
						String dicses = b.dicsnumber(name.getText(), atcoun[1], "at", "");
						if (dicses != "") {
							
							// defender choose disc
							String de = b.dicsnumber(defender, decoun[1], "de", dicses);
							if (de != "") {
								
								// one_time
								String canTransfer = observable.attackPhase(attak.getName(), defend.getName(), mode,
										Integer.valueOf(dicses), Integer.valueOf(de));
								Transfer(canTransfer, attak, defend);
							}

						}

					}
				}

			}

			/**
			 * This method updates attack and defender JLabel.
			 *
			 * @param record A String shows transfer army information.
			 * @param att    A JLabel shows attack country army.
			 * @param def    A JLabel shows defender country army.
			 */
			public void Transfer(String record, JLabel att, JLabel def) {
				String[] readrecord = record.split(" ");
				if (readrecord[1].equals("0")) {//update countries information
					updateCountries(att);
					updateCountries(def);
					att.setBorder(null);
					def.setBorder(null);
					if (readrecord[0].equals(name.getText())) {
						JOptionPane.showMessageDialog(null, "attacker Player"+ name.getText()+" win");
						if (!readrecord[2].equals("0")) {
							WIN = true;
						}
						
					}
					else if (readrecord[0].equals("-1")) {
						
						JOptionPane.showMessageDialog(null, "This is a draw.");
					}
					else {
						String defender = b.findPlayer(def.getName());
						JOptionPane.showMessageDialog(null, "defender Player"+ defender+" win");
					}
					
				}
				else {
					WIN = true;
					updateCountries(att);
					updateCountries(def);
					int move = b.moveArmies(Integer.valueOf(readrecord[1]), Integer.valueOf(readrecord[2]));
					observable.Fortification(att.getName(), def.getName(), move);
					updateCountries(att);
					updateCountries(def);
					att.setBorder(null);
					def.setBorder(null);
				}
			}

			/**
			 * This method updates JLabel information of countries.
			 *
			 * @param label A JLabel shows a country information.
			 */
			public void updateCountries(JLabel label) {
				
				// update country army number
				String[] old = label.getText().split(" ");
				String now = old[0] + " " + countries.get(label.getName()).getArmy();
				label.setText(now);
				
				// update country color
				ImageIcon imageIcon = (ImageIcon) label.getIcon();
				Image image = imageIcon.getImage();

				BufferedImage img = (BufferedImage) image;
				int width = img.getWidth();
				int height = img.getHeight();

				WritableRaster raster = img.getRaster();
				for (int xx = 0; xx < width; xx++) {
					for (int yy = 0; yy < height; yy++) {
						int[] pixels = raster.getPixel(xx, yy, (int[]) null);
						pixels[0] = countries.get(label.getName()).getColor().getRed();
						pixels[1] = countries.get(label.getName()).getColor().getGreen();
						pixels[2] = countries.get(label.getName()).getColor().getBlue();
						raster.setPixel(xx, yy, pixels);
					}

				}

			}

			/**
			 * This method implements fortification.
			 *
			 * @param from A label that is a start country.
			 * @param c    A label that is a end country.
			 * @param to   A string that is name of end country.
			 */
			public void fortification(JLabel from, JLabel c, String to) {

				boolean canTransfer = observable.canTransfer(name.getText(), from.getName(), to);

				// input how many armies you want to move
				String question = "how many armies you want to move from" + from.getName() + "to " + to;
				String str = JOptionPane.showInputDialog(null, question, "input armies number",
						JOptionPane.PLAIN_MESSAGE);

				// whether country armies number is zero<= 0
				boolean iszero = b.Iszero(from.getName(), Integer.valueOf(str));

				if (!canTransfer) {
					JOptionPane.showMessageDialog(null, "no path can transfer your armies");
				}
				if (!iszero) {
					JOptionPane.showMessageDialog(null, "too many armies to move ");
				}
				if (canTransfer && iszero) {

					observable.Fortification(from.getName(), to, Integer.valueOf(str));
					
					// update country army number
					String[] old = from.getText().split(" ");
					String now = old[0] + " " + countries.get(from.getName()).getArmy();
					from.setText(now);

					String[] toold = c.getText().split(" ");
					String tonow = toold[0] + " " + countries.get(c.getName()).getArmy();
					c.setText(tonow);
					
					// occupy a territory then obtain a card
					if (WIN) {
						observable.earnCard(name.getText());
					}
					WIN = false;
					
					// fortification only one time enter reinforcement
					currentPhase = "Reinforcement";
					phase.setText("Reinforcement");

					// find next player
					String nextP = b.findnext(name.getText());

					// update next player armies
					System.out.println(playerSet.get(nextP).getArmy());
					observable.Reinforcement(nextP);
					
					// change player
					name.setText(nextP);

					color.setBackground(playerSet.get(nextP).getColor());

					if (playerSet.get(nextP).getCardList().size() != 0) {
						observable.cardArmy(nextP, playerSet.get(nextP).getCardList(), false);
						armies.setText(
								"<html><body><p align=\"center\">calculating...<br/>press&nbsp;reinforcement</p></body></html>");

					} else {
						armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
					}

				}

			}

		}

	}

}
