package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.*;
import java.util.*;
import java.util.Timer;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import Model.Attack;
import Model.Continent;
import Model.Country;
import Model.InitializePhase;
import Model.Line;
import Model.Player;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

/**
 * <h1>PlayView</h1> It is Play Game main View have three function
 * (reinforcement, attack, fortification)
 *
 * @author chenwei_song,youlin_liu,shuo_chi,tianshu_ji
 * @version 3.0
 * @since 2019-03-01
 */
public class PlayView extends JFrame implements Observer  {

	public HashMap<String, Line> lineMap = new HashMap<>();
	public HashMap<String, Country> countries = new HashMap<>();
	public HashMap<String, Continent> continents = new HashMap<>();
	public HashMap<String, Player> playerSet = new HashMap<>();
	public JLabel name;
	public JLabel color;
	public JLabel armies;
	public String currentPhase;
	public JButton phase;
	public JLabel mode;
	private boolean WIN = false;
	BackEnd b;
	InitializePhase observable = new InitializePhase();
	LinkedList<JLabel> labelsCountry=new LinkedList <>();

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

				System.out.println("This is in constructor of PlayView, mode is "+ mode.getText());
				if(!mode.getText().equalsIgnoreCase("human")) {
					playPane.noHuman();
				}


				JMenuBar menuBar = new JMenuBar();
				frame.setJMenuBar(menuBar);
				JMenu menu1 = new JMenu("File");
				menuBar.add(menu1);
				JMenuItem saveMI = new JMenuItem("Save");
				JMenuItem loadMI = new JMenuItem("Load");
				menu1.add(saveMI);
				menu1.add(loadMI);

				loadMI.addActionListener(new LoadAction(playPane));
				saveMI.addActionListener(new SaveAction(playPane));
			}
		});

	}

	/**
	 * It is load action listener
	 */
	private class LoadAction implements ActionListener, Serializable{
		PlayPane playPane;

		public LoadAction(PlayPane playPane) {
			this.playPane = playPane;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			FileDialog fileDialog = new FileDialog(PlayView.this, "Open", FileDialog.LOAD);
			fileDialog.setVisible(true);
			String dir = fileDialog.getDirectory();
			String fileName = fileDialog.getFile();
			String filePath = dir + fileName;

			if (fileName != null && fileName.trim().length() != 0){
				File file = new File(filePath);
				playPane.loadGame(file);
			} else {
				JOptionPane.showConfirmDialog(PlayView.this, "Failed", "Risk Game", JOptionPane.DEFAULT_OPTION);
			}
		}
	}

	/**
	 * It is save action listener
	 */
	private class SaveAction implements ActionListener, Serializable {
		PlayPane playPane;

		public SaveAction(PlayPane playPane) {
			this.playPane = playPane;
		}



		@Override
		public void actionPerformed(ActionEvent e) {
			FileDialog fileDialog = new FileDialog(PlayView.this, "Save", FileDialog.SAVE);
			fileDialog.setVisible(true);
			String dir = fileDialog.getDirectory();
			String fileName = fileDialog.getFile();
			String filePath = dir + fileName;

			if (fileName != null && fileName.trim().length() != 0){
				File file = new File(filePath);
				playPane.saveGame(file);
			} else {
				JOptionPane.showConfirmDialog(PlayView.this, "Failed", "Risk Game", JOptionPane.DEFAULT_OPTION);
			}
		}
	}

	/**
	 * It is a JLayeredPane to add buttons, labels, texts. it can receive mouse
	 * listener component.
	 */
	public class PlayPane extends JLayeredPane {

		iconHandler ih = new iconHandler();

		/**
		 * This method is to save components into file.
		 *
		 * @param file The file to save
		 */
		public void saveGame(File file){
			try{
				FileOutputStream fileOutputStream= new FileOutputStream(file);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

				objectOutputStream.writeObject(lineMap);
				objectOutputStream.writeObject(countries);
				objectOutputStream.writeObject(continents);
				objectOutputStream.writeObject(playerSet);
				objectOutputStream.writeObject(name);
				objectOutputStream.writeObject(color);
				objectOutputStream.writeObject(armies);
				objectOutputStream.writeObject(currentPhase);
				objectOutputStream.writeObject(phase);
				objectOutputStream.writeObject(labelsCountry);
				objectOutputStream.writeObject(b);
				objectOutputStream.writeObject(dominationView);
				objectOutputStream.writeObject(mode);

				objectOutputStream.close();
				fileOutputStream.close();

				JOptionPane.showConfirmDialog(frame, "Save Successfully", "RiskGame",JOptionPane.DEFAULT_OPTION);
			} catch (Exception e){
				JOptionPane.showConfirmDialog(frame, e.toString() + "\nSave Failed", "RiskGame", JOptionPane.DEFAULT_OPTION);
			}
		}
		/**
		 * This method is to load components from file.
		 *
		 * @param file The file to read
		 */

		public void loadGame(File file){
			try{
				HashMap<String, Line> saveLineMap = new HashMap<>();
				HashMap<String, Country> saveCountries = new HashMap<>();
				HashMap<String, Continent> saveContinents = new HashMap<>();
				HashMap<String, Player> savePlayerSet = new HashMap<>();
				JLabel saveName;
				JLabel saveColor;
				JLabel saveArmies;
				String saveCurrentPhase;
				JButton savePhase;
				LinkedList<JLabel> saveLabelsCountry;
				BackEnd saveB;
				DominationView saveDominationView;
				JLabel saveMode;



				FileInputStream fileInputStream = new FileInputStream(file);
				ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);

				saveLineMap = (HashMap<String, Line>) objectInputStream.readObject();
				saveCountries = (HashMap<String, Country>) objectInputStream.readObject();
				saveContinents = (HashMap<String, Continent>) objectInputStream.readObject();
				savePlayerSet = (HashMap<String, Player>) objectInputStream.readObject();
				saveName = (JLabel) objectInputStream.readObject();
				saveColor = (JLabel) objectInputStream.readObject();
				saveArmies = (JLabel) objectInputStream.readObject();
				saveCurrentPhase = (String) objectInputStream.readObject();
				savePhase = (JButton) objectInputStream.readObject();
				saveLabelsCountry = (LinkedList<JLabel>) objectInputStream.readObject();
				saveB = (BackEnd) objectInputStream.readObject();
				saveDominationView = (DominationView) objectInputStream.readObject();
				saveMode = (JLabel) objectInputStream.readObject();

				objectInputStream.close();
				fileInputStream.close();

				if (lineMap != null && countries != null && continents != null && playerSet != null && name != null && color != null && armies != null && currentPhase != null && phase != null && labelsCountry != null){
					removeAll();

					lineMap = saveLineMap;
					countries = saveCountries;
					continents = saveContinents;
					playerSet = savePlayerSet;
					name = saveName;
					color = saveColor;
					armies = saveArmies;
					currentPhase = saveCurrentPhase;
					phase = savePhase;
					labelsCountry = saveLabelsCountry;
					b = saveB;
					dominationView = saveDominationView;
					mode = saveMode;

					frame.setEnabled(true);
//					for (JLabel country : labelsCountry) {
//						frame.remove(country);
//					}
					observable.setCountries(countries);
					observable.setContinents(continents);
					observable.setPlayerSet(playerSet);
					dominationView = new DominationView(observable);
					observable.addObserver(dominationView);

					paintCountry(countries);

					repaint();

					// create button country
					//JButton phase1 = phase;
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

					JLabel strategy = new JLabel("Strategy: ");
					add(strategy);
					strategy.setBounds(1000, 70, 80, 25);

					mode = new JLabel();
					mode.setText(playerSet.get("1").getMode());
					add(mode);
					mode.setBounds(1070, 70, 80, 25);

					// receive armies number
					JLabel army = new JLabel();
					army.setText("Army: ");
					army.setBounds(1000, 120, 80, 25);
					add(army);
					String n = String.valueOf(playerSet.get("1").getArmy());
					armies = new JLabel(n);
					armies.setName("armies");
					armies.setBounds(1100, 120, 80, 25);
					add(armies);

					JOptionPane.showConfirmDialog(frame,"Load Successfully", "RiskGame",JOptionPane.DEFAULT_OPTION);
				}
			} catch (Exception e){
				JOptionPane.showConfirmDialog(frame, e.toString() + "\nLoad Failed", "RiskGame", JOptionPane.DEFAULT_OPTION);
			}
		}

		/**
		 * This method is constructor of PlayPane.
		 *
		 */
		public PlayPane() {
			observable.setCountries(countries);
			observable.setContinents(continents);
			observable.setPlayerSet(playerSet);
			dominationView = new DominationView(observable);
			observable.addObserver(dominationView);
			File image = new File("resource/tower.png");
//			LinkedList<JLabel> labelsCountry=new LinkedList <>();

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
					String labelInfor=key+":"+" "+continent;
					label.setText(labelInfor);
					label.setName(key);
					label.setHorizontalTextPosition(JLabel.CENTER);
					label.setVerticalTextPosition(JLabel.CENTER);
					label.addMouseListener(ih);
					label.addMouseMotionListener(ih);
					add(label);
					labelsCountry.add(label);

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

			JLabel strategy = new JLabel("Strategy: ");
			add(strategy);
			strategy.setBounds(1000, 70, 80, 25);

			mode = new JLabel();
			mode.setText(playerSet.get("1").getMode());
			add(mode);
			mode.setBounds(1070, 70, 80, 25);

			// receive armies number
			JLabel army = new JLabel();
			army.setText("Army: ");
			army.setBounds(1000, 120, 80, 25);
			add(army);
			String n = String.valueOf(playerSet.get("1").getArmy());
			armies = new JLabel(n);
			armies.setName("armies");
			armies.setBounds(1100, 120, 80, 25);
			add(armies);

		}


		/**
		 * This is a paint function.
		 *
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
		 * This method is to paint country
		 *
		 * @param countries A hash map stores all countries.
		 */
		public void paintCountry(HashMap<String, Country> countries){

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
					labelsCountry.add(label);

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
		}


		/**
		 * This method is to change states fot different kinds of players.
		 *
		 * @param state a state to store one of phases (reinforcement, attack, fortification).
		 */
		public void stateChangeStartUp(String state){

			System.out.println("************** Begin of stateChangeStartUp ****************");

			String nextP="";
			if(state.equalsIgnoreCase("start up")) {

				//两个玩家的时候是 start up阶段，但是还是自己
				nextP = b.nextplayer(name.getText());
				System.out.println("This is start up state, next player is "+ nextP);
			}else{
				nextP=b.findnext(name.getText());
				System.out.println("This is not start up state, next player is "+ nextP);

			}
			System.out.println("The state is "+state);
			if (nextP == "") {
				//当nextP为空时，只有当从start Up 到 Reinforcement
				System.out.println("nextp is null , enter new phase");
				state="Reinforcement";
				System.out.println("enter "+state+" phase in nohuman");
				phase.setText(state);
				currentPhase = state;
				deleteBorder();
				System.out.println();
				name.setText("1");
				color.setBackground(playerSet.get("1").getColor());
				mode.setText(playerSet.get("1").getMode());

				//当没有之后，全部跳转到1,更新国家的label

				if(!mode.getText().equalsIgnoreCase("human")) {
					String reinforceINf=observable.Reinforcement("1");
					String[] inf=reinforceINf.split(" ");
					String one="Add "+inf[1]+" armies to "+inf[0]+" countries";
					showInformation(one,"Reinforcement 1");
					armies.setText("0");
					noHuman();
				} else{
					observable.Reinforcement("1");
					JOptionPane.showMessageDialog(null,
							"enter reinforcement phase in human");
					//System.out.println("The player1 army is "+playerSet.get(nextP).get);
					armies.setText(String.valueOf(playerSet.get("1").getArmy()));
				}
				updateLabelsCountry();
				deleteBorder();

			} else {

				name.setText(nextP);
				color.setBackground(playerSet.get(nextP).getColor());
				mode.setText(playerSet.get(nextP).getMode());

				//这个是从fortification到reinforcement
				if (state.equalsIgnoreCase("reinforcement")) {
					currentPhase="Reinforcement";
					phase.setText("Reinforcement");

					String ans = observable.Reinforcement(nextP);
					//updateLabelsCountry();
					if (!playerSet.get(nextP).getMode().equalsIgnoreCase("human") &&
							!playerSet.get(nextP).getMode().equalsIgnoreCase("cheater")) {

						String army = ans.split(" ")[0];
						String strongest = ans.split(" ")[1];
						String infomation = "Add " + army + " armies to " + strongest + " country";
						String s=currentPhase+" "+nextP;
						showInformation(infomation,s);
						LinkedList <String> delete = observable.autoChangeCard(nextP);
						if(delete.size()>0) {
							for (int i = 0; i < delete.size(); i++) {
								String sc = "Change Card " + nextP;
								showInformation(delete.get(i), sc);
							}
							String curInf = "Add " + playerSet.get(nextP).getArmy() +
									" armies to " + strongest + " country";
							showInformation(curInf, "Card army "+nextP);
						}

						while (playerSet.get(nextP).getArmy() > 0) {
							observable.Startup(nextP, strongest);
						}
						armies.setText("0");
						updateLabelsCountry();
						noHuman();
					} else if(playerSet.get(nextP).getMode().equalsIgnoreCase("cheater")){
						showInformation("Cheater: Double armies to every country!", "Reinforcement "+nextP);
						armies.setText("0");
						updateLabelsCountry();
						noHuman();

					}
					else {

						System.out.println("The mode is : " + mode.getText() + " Player is " + name.getText());
						JOptionPane.showMessageDialog(null, "enter reinforcement phase in human");
						if (playerSet.get(nextP).getCountryList().size() > 0) {
							observable.cardArmy(nextP, playerSet.get(nextP).getCardList(), false);
							armies.setText("<html><body><p align=\"center\">calculating...<br/>press&nbsp;reinforcement</p></body></html>");

						} else {
							armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
						}
					}
				}
//
					else {
						armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
						phase.setText(state);
						currentPhase = state;
						updateLabelsCountry();
						if(!playerSet.get(nextP).getMode().equalsIgnoreCase("human")){
							noHuman();
						}else {

							return;
						}
					}

				}



			System.out.println("************** End of stateChangeStartUp ****************");

		}

		/**
		 * This method is to get next state.
		 *
		 * @return The name of next state.
		 */
		public String getNextState(){
			String state="";
			switch (currentPhase) {
//				case "start up":
//					state = "Reinforcement";
//					break;
				case "Reinforcement":
					state = "Attack";
					break;
				case "Attack":
					state = "Fortification";
					break;
				case "Fortification":
					state = "Reinforcement";
					break;
				default:
					break;
			}
			return state;

		}

		/**
		 * THis method is to automatically show the information of playing ever 3 seconds.
		 *
		 * @param information A String to store the information.
		 * @param name A string to store the name of the player.
		 */
		public void showInformation(String information,String name){
			JOptionPane op = new JOptionPane(information, JOptionPane.INFORMATION_MESSAGE);
			JDialog dialog = op.createDialog(information);
			dialog.setTitle(name);
			javax.swing.Timer timer = new javax.swing.Timer(2000, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.setVisible(false);
					dialog.dispose();
					dialog.setLocation(10, 20);
				}
			});

			timer.setRepeats(false);
			timer.start();

			dialog.setVisible(true);
		}


		/**
		 * This is a method for three phases of no human players.
		 */
		public void noHuman() {

			isGetWin();

			int flag = -100;
			System.out.println();
			System.out.println();
			System.out.println("**************Begin  nohuman function*****************");


			///////////////////////start up phase///////////////////

			if (currentPhase.equals("start up")) {
				System.out.println(" ******** Begin of start up in noHuman **********");
				String cd = observable.randomSelect(name.getText());

				observable.Startup(name.getText(), cd);
				updateLabelsCountry();

				String nextp = b.nextplayer(name.getText());
				if (nextp.equalsIgnoreCase(name.getText())) {
					String cc = observable.randomSelect(name.getText());
					observable.Startup(name.getText(), cc);
					//4.6 add
					String curSelect = "Add One Army to " + cc;
					String phasePlayer = currentPhase + " " + name.getText();
					showInformation(curSelect, phasePlayer);
					updateLabelsCountry();
				}

				updateLabelsCountry();
				System.out.println();
				System.out.println();
				updateLabelsCountry();


				armies.setText(String.valueOf(playerSet.get(name.getText()).getArmy()));

				//4.6 add
				String s = currentPhase + " " + name.getText();
				//String s=currentPhase+" "+nextp;
				String countrys = "Add One Army to " + cd;
				showInformation(countrys, s);

				deleteBorder();
				stateChangeStartUp("start up");
				System.out.println(" ******** End of start up in noHuman **********");

				return;

			}


			///////////////////////reinforcement ///////////////////

			if (currentPhase.equalsIgnoreCase("reinforcement")) {

				System.out.println(" ******** Begin of reinforcement in noHuman **********");

				if (!mode.getText().equalsIgnoreCase("human")) {

					//observable.Reinforcement(name.getText()); //------->10。50 shan
					//System.out.println("in noHuman reinforcement");
					reinforcementNoHuman();

					//observable.attackPhase("0","0","All_out",0,0);

				} else {

					return;
				}

			}
			System.out.println(" ******** End of reinforcement in noHuman **********");

			///////////////////////attack///////////////////

			if (currentPhase.equalsIgnoreCase("attack")) {
				System.out.println(" ******** Begin of attack in noHuman **********");

				LinkedList <String> ans = new LinkedList <>();
				ans = observable.attackPhase(name.getText(), "0",
						"All_out", 0, 0, mode.getText());
				//observable.Fortification("0","0",0);
				updateLabelsCountry();
				if(!mode.getText().equalsIgnoreCase("benevolent")) {
					showInformation("Player" + name.getText() + " is attacking ", "Attack " + name.getText());
				}
				System.out.println(" attack information is in PlayView");
				if (!mode.getText().equalsIgnoreCase("cheater")) {

					if (ans.size() > 0) {

						for (int i = 0; i < ans.size(); i++) {
							System.out.println(ans.get(i));
							String[] attackResult = ans.get(i).split(" ");
							if (attackResult[0].equalsIgnoreCase(name.getText())) {
								if (!attackResult[1].equalsIgnoreCase(attackResult[2])
										&& !attackResult[2].equalsIgnoreCase("0")) {
									System.out.println("********** change Win== true **********");
									WIN = true;
									break;
								}

							}
						}


						if (WIN) {
							showInformation("Player"+name.getText() + " conquers! ", "Attack "+name.getText());
						} else {
							showInformation("Player"+name.getText() + " not conquers! ", "Attack "+name.getText());
						}
					}
				}
				if(mode.getText().equalsIgnoreCase("cheater")){
					updateLabelsCountry();
					showInformation("Cheater: auto conquer neighbor countries!", "Attack "+name.getText());
				}



				attackNoHuman();
				updateLabelsCountry();
				isGetWin();
				System.out.println(" ******** End of attack in noHuman **********");

			}

			///////////////////////fortification///////////////////
			if (currentPhase.equalsIgnoreCase("fortificaiton")) {


				isGetWin();


				fortificationNoHuman();
				updateLabelsCountry();
			}

			System.out.println("**************End nohuman function*****************");
			return;

		}

		/**
		 * This is the mathod to delete the border of the country.
		 */

		public void deleteBorder() {
			for (int i = 0; i < labelsCountry.size(); i++) {
				labelsCountry.get(i).setBorder(null);
			}
		}

		/**
		 * This is the method to show the final winner.
		 */
		public void isGetWin(){
			if (Attack.isWIN()) {
				String winner = "";
				for (String key : playerSet.keySet()) {
					winner = key;
				}
				//showInformation("Congradulation!!! Winner: ",winner);
				JOptionPane.showMessageDialog(null,
						"Congradulation!!!!player " + winner + " is winnner!!!");
				frame.dispose();

				new StartGame();
				exit(0);

			}
		}


		/**
		 * This method executes reinforcement for no human.
		 */
		public void reinforcementNoHuman() {
			System.out.println("************Begin reinforcementNoHuman *********");
			updateLabelsCountry();

			boolean canAttack = b.canAttack(name.getText());


			System.out.println("The result of canAttack in reinforcement " +
					" is "+canAttack);
			deleteBorder();
			if (canAttack) {

				System.out.println("Attack, enter Attack phase");
				System.out.println("enter Attack phase");
				currentPhase = "Attack";
				phase.setText("Attack");

			} else {
				System.out.println("Cannot Attack, enter fortification phase.");
				phase.setText("Fortification");
				currentPhase = "Fortification";
				if(observable.canFortification(name.getText())) {
					observable.Fortification(name.getText(), "0", 0,
							playerSet.get(String.valueOf(name.getText())).getMode());

					showInformation("Player"+name.getText()+" Fortificaiton ","Fortificaiton "+name.getText());
					updateLabelsCountry();
					fortificationNoHuman();
					noHuman();
				}else{
					phase.setText("Reinforcement");
					currentPhase="Reinforcement";
					stateChangeStartUp("Reinforcement");
				}

			}
			System.out.println("************End reinforcementNoHuman *********");

		}

		/**
		 * This is the method to update the countries' labels.
		 */
		public void updateLabelsCountry(){


			for (int i = 0; i < labelsCountry.size(); i++) {
				//if (labelsCountry.get(i).getName().equalsIgnoreCase(cc)) {

				boolean flag = false;
				String oldInf = labelsCountry.get(i).getText();
				String[] old = oldInf.split(" ");
				//System.out.println("current army in reinforcement is " + countries.get(labelsCountry.get(i).getName()).getArmy());
				String now = old[0] + " " + old[1] + " " + countries.get(labelsCountry.get(i).getName()).getArmy();
				if (!now.equals(oldInf)) {
					flag = true;
				}

				labelsCountry.get(i).setText(now);
				//updateCountriesColor(labelsCountry.get(i));
				//System.out.println("we are updating "+i+ " country, it belongs to ");
				if (flag) {
					for (String key : playerSet.keySet()) {
						if (countries.get(labelsCountry.get(i).getName()).getColor() == playerSet.get(key).getColor()) {
							System.out.println("The label " + labelsCountry.get(i).getText() +
									" belongs to Player: " + key);
						}
					}
					labelsCountry.get(i).setBorder(new LineBorder(Color.RED));


				}
			}

			for(int j=0;j<labelsCountry.size();j++){
				updateCountriesColor(labelsCountry.get(j));
			}

			armies.setText(String.valueOf(playerSet.get(name.getText()).getArmy()));

			System.out.println("update labelsCountry finish");
			System.out.println();

		}


		/**
		 * This is the method for no human attacking.
		 */
		public void attackNoHuman() {
			System.out.println("******* Begin of attackNoHuman **********");
			updateLabelsCountry();


			if (Attack.isWIN()) {
				String winner = "";
				for (String key : playerSet.keySet()) {
					winner = key;
				}
				System.out.println("Congradulation!!!!player " + winner + " is winnner!!!");
				frame.dispose();
				new StartGame();
			}else {

				deleteBorder();
				System.out.println("enter fortification phase in no Human");
				phase.setText("Fortification");
				currentPhase = "Fortification";
				observable.Fortification(name.getText(),"0",0,
						playerSet.get(String.valueOf(name.getText())).getMode());
				if(!mode.getText().equalsIgnoreCase("cheater")) {
					showInformation("Player"+name.getText() + " Fortificaiton ", "Fortificaiton "+name.getText());
				}else{
					showInformation("Player"+name.getText() + "Cheater: double armies in front country ", "Fortificaiton "+name.getText());

				}
				updateLabelsCountry();
				if (WIN) {

					String s=observable.earnCard(name.getText(),mode.getText());
					System.out.println("Player "+name.getText()+" get one Card");
					showInformation("Player "+name.getText()+" get "+s+" Card","Get Card "+name.getText());
				}
				WIN = false;
//				phase.setText("Reinforcement");
//				currentPhase="Reinforcement";
//
				stateChangeStartUp(getNextState());
				deleteBorder();
			}

			System.out.println("******* End of attackNoHuman **********");

		}


		/**
		 * This is the method for no human fortification.
		 */
		public void fortificationNoHuman() {

			System.out.println("***************** begin of fortificationNoHuman**************  ");
			updateLabelsCountry();

//			if (WIN) {
//				observable.earnCard(name.getText());
//			}
//			WIN = false;

			System.out.println();
			System.out.println();

			System.out.println(" we will find next player after fortification in fortificationNoHuman");
			String nextP = b.findnext(name.getText());
			System.out.println("The next player is " + nextP);

			// fortification only one time enter reinforcement
			name.setText(nextP);
			color.setBackground(playerSet.get(name.getText()).getColor());
			mode.setText(playerSet.get(nextP).getMode());
			currentPhase = "Reinforcement";
			phase.setText("Reinforcement");
			System.out.println("Update information is : ");
			System.out.println("Player : " + name.getText() +
					" Mode : " + mode.getText());

			/////////////// Next player reinforcement////////////

			if(!playerSet.get(nextP).getMode().equalsIgnoreCase("human")
					&& !playerSet.get(nextP).getMode().equalsIgnoreCase("cheater")) {
				System.out.println(" we are in nohuman no cheater in PlayView");
				String ans=observable.Reinforcement(nextP);

					String army=ans.split(" ")[0];
					String strongest=ans.split(" ")[1];
					String infomation="Add "+ army+" armies to "+strongest+" country";
					String s=currentPhase+" "+nextP;

					showInformation(infomation,s);

				updateLabelsCountry();
				LinkedList<String> ansCard=observable.autoChangeCard(nextP);
				if(ansCard.size()>0) {
					for (int i = 0; i < ansCard.size(); i++) {
						String sc = "Change Card " + nextP;
						showInformation(ansCard.get(i), sc);
					}
					String curInf = "Add " + playerSet.get(nextP).getArmy() +
							" armies to " + strongest + " country";
					showInformation(curInf, "Card army "+nextP);

				}
				while(playerSet.get(nextP).getArmy()>0){
					observable.Startup(nextP,strongest);
				}
				armies.setText("0");
				noHuman();
			}else if(playerSet.get(nextP).getMode().equalsIgnoreCase("cheater")){
				String ans=observable.Reinforcement(nextP);

					String infomation="Cheater: auto double armies in front country";
					String s=currentPhase+" "+nextP;

					showInformation(infomation,s);

				updateLabelsCountry();
				armies.setText("0");
				noHuman();
			}

			else if(mode.getText().equalsIgnoreCase("human")){
				updateLabelsCountry();
				observable.Reinforcement(nextP);

				if (playerSet.get(nextP).getCountryList().size() > 0) {
					observable.cardArmy(nextP, playerSet.get(nextP).getCardList(), false);
					armies.setText("<html><body><p align=\"center\">calculating...<br/>press&nbsp;reinforcement</p></body></html>");

				} else {
					armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
				}
			}

				// update next player armies
			System.out.println("************** end of fortificationNoHuman ************");

		}





		/**
		 * This method updates JLabel information of countries.
		 *
		 * @param label A JLabel shows a country information.
		 */
		public void updateCountriesColor(JLabel label) {

			ImageIcon imageIcon = (ImageIcon) label.getIcon();
			Image image = imageIcon.getImage();

			BufferedImage img = (BufferedImage) image;
			int width = img.getWidth();
			int height = img.getHeight();

			WritableRaster raster = img.getRaster();
//


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
				//选择的是phase
				if (click.equals("phase")) {


					JButton phase = (JButton) e.getComponent();


					if (phase.getText().equals("Reinforcement")) {
						System.out.println("Reinforcement in mouseClick in playView");
						armies.setText(String.valueOf(playerSet.get(name.getText()).getArmy()));
					}


					else if (phase.getText().equals("Attack")) {

						System.out.println("**************Begin of phase " +
								"= Attack in MouseClick in PlayView*************************");

						start = true;
						from = null;
						JOptionPane.showMessageDialog(null, "enter Fortification phase");
						phase.setText("Fortification");
						currentPhase = "Fortification";

						System.out.println("**************End of phase " +
								"= Attack in MouseClick in PlayView*************************");

						System.out.println();

					}


					else if (phase.getText().equals("Fortification")) {
						System.out.println();
						System.out.println("**************Begin of phase " +
								"= Fortification in MouseClick in PlayView*************************");

						// earn card--->not this phase in nohuman
						if (WIN) {
							observable.earnCard(name.getText(),mode.getText());
						}
						WIN = false;
						System.out.println("Fortification, enter next phase Reinforcement, Change Player");
						String nextP = b.findnext(name.getText());

						phase.setText("Reinforcement");
						currentPhase = "Reinforcement";
						enterReinforcement(nextP,"fortificaiton");
						System.out.println("From fortification to reinforcement, the player is : " + name.getText()
								+ " Army is " + armies.getText() + " mode is : " + mode.getText());

						System.out.println("**************End of phase " +
								"= Fortification in MouseClick in PlayView*************************");
						System.out.println();
						System.out.println();
					}

					}



////////////////////////////////////////////////	选择的是国家

				else {
					if (currentPhase.equals("start up")) {
						System.out.println("**************Begin of currentPhase " +
								"= start up in MouseClick in PlayView*************************");


						JLabel c = (JLabel) e.getComponent();

						// check whether the player click own country
						boolean match = rightcountry(name.getText(), c.getName());

						if (match) {
							observable.Startup(name.getText(), click);

							System.out.println("This is start up phases in human");
							// update country army number
							String[] old = c.getText().split(" ");
							String now = old[0] + " " +old[1] + " " + countries.get(c.getName()).getArmy();
							c.setText(now);

							// change player
							String nextP = b.nextplayer(name.getText());
							if (nextP == "") {
								enterReinforcement("1","start up");
							} else {
								System.out.println("Human previous player: " + name.getText() + " Mode : " + mode.getText());
								name.setText(nextP);
								armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
								color.setBackground(playerSet.get(nextP).getColor());
								mode.setText(playerSet.get(nextP).getMode());
								System.out.println("Human current player: " + nextP + " Mode : " + playerSet.get(nextP).getMode());
								//调用他致使human 向 nohuman转变
								if (!playerSet.get(nextP).getMode().equalsIgnoreCase("human")) {
									noHuman();
								}
							}

						} else {
							JOptionPane.showMessageDialog(null, "please select your own countries");
						}
						System.out.println("**************End of currentPhase " +
								"= start up in MouseClick in PlayView*************************");
						System.out.println();


					}

					/////////////////////////////////////////
					else if (currentPhase.equals("Reinforcement")) {

						System.out.println("**************Begin of currentPhase " +
								"= Reinforcement in MouseClick in PlayView*************************");

						if (!mode.getText().equalsIgnoreCase("human")) {
							noHuman();
						} else {
							JLabel c = (JLabel) e.getComponent();
							reinforcement(c, click);
						}
						System.out.println("**************End of currentPhase " +
								"= Reinforcement in MouseClick in PlayView*************************");


					}

					//////////////////////////////////   Attack ///////////////////
					else if (currentPhase.equals("Attack")) {

						System.out.println("**************Begin of currentPhase " +
								"= Attack in MouseClick in PlayView*************************");


						JLabel c = (JLabel) e.getComponent();
						boolean match = rightcountry(name.getText(), c.getName());
						boolean isOne = b.isOne(c.getName());
						if (start && match && isOne) {
							from = c;
							start = false;
							from.setBorder(new LineBorder(Color.ORANGE));
							System.out.println("you choose " + from.getText() + " as attack country");
						} else {
							if (start && !isOne) {
								JOptionPane.showMessageDialog(null, "this country cannot attack others in human");
							}
							if (start && !match) {
								JOptionPane.showMessageDialog(null, "please select your own countries in human ");
							}
							if (!start && match) {
								JOptionPane.showMessageDialog(null,
										"please select country that belongs to other players in human");
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
						System.out.println("**************End of currentPhase " +
								"= Attack in MouseClick in PlayView*************************");


					}

					////////////////////////////////////////Fortification ////////////////
					else if (currentPhase.equals("Fortification")) {

						System.out.println("**************Begin of currentPhase " +
								"= Fortification in MouseClick in PlayView*************************");
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
						System.out.println("**************End of currentPhase " +
								"= Fortification in MouseClick in PlayView*************************");


					}

				}

			}

			/**
			 * This is the method to determine different strategies players can enter their particular reinforcement.
			 *
			 * @param nextP the name of next player.
			 * @param previous the previous phase.
			 */

			public void enterReinforcement(String nextP,String previous) {

					// get into reinforcement phase
					System.out.println();
					System.out.println("enter reinforcement phase in start up phase ");
					phase.setText("Reinforcement");
					currentPhase = "Reinforcement";
					color.setBackground(playerSet.get(nextP).getColor());
					mode.setText(playerSet.get(nextP).getMode());
					name.setText(nextP);
					String ans = observable.Reinforcement(nextP);


					if (mode.getText().equalsIgnoreCase("human")) {
						System.out.println("The mode is : " + mode.getText() + " Player is " + name.getText());
						JOptionPane.showMessageDialog(null, "enter reinforcement phase in human");
						if(previous.equalsIgnoreCase("fortificaiton")) {
							if (playerSet.get(nextP).getCountryList().size() > 0) {
								observable.cardArmy(nextP, playerSet.get(nextP).getCardList(), false);
								armies.setText("<html><body><p align=\"center\">calculating...<br/>press&nbsp;reinforcement</p></body></html>");

							} else {
								armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
							}
						}else{
							armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
						}
						updateLabelsCountry();
					} else if(mode.getText().equalsIgnoreCase("cheater")){
						System.out.println("The mode is : " + mode.getText() + " Player is " + name.getText());

						String infomation = "Cheater: auto double armies to all countries";
						String s=currentPhase+" "+nextP;
						showInformation(infomation,s);
						System.out.println();
						updateLabelsCountry();
						armies.setText("0");
						noHuman();
					}else {
						System.out.println("The mode is : " + mode.getText() + " Player is " + name.getText());
						String army = ans.split(" ")[0];
						String strongest = ans.split(" ")[1];
						String infomation = "Add " + army + " armies to " + strongest + " country";
						String s=currentPhase+" "+nextP;
						showInformation(infomation,s);
						LinkedList <String> delete = observable.autoChangeCard(nextP);
						if (delete.size() > 0) {
							for (int i = 0; i < delete.size(); i++) {

								String sc="Change Card "+nextP;
								showInformation(delete.get(i),sc);

							}
							String curInf= "Add " + playerSet.get(nextP).getArmy() + " armies to " + strongest + " country";
							showInformation(curInf, "Card Army "+nextP);
						}
						//String cc = observable.randomSelect(nextP);

						while (playerSet.get(nextP).getArmy() > 0) {
							observable.Startup(nextP, strongest);
						}
						System.out.println();
						updateLabelsCountry();
						armies.setText("0");
						noHuman();
					}
			}


			/**
			 * Check whether current player click right country or not.
			 *
			 * @param cplayer  Who wants to operate this country
			 * @param ccountry Which country would be operated
			 * @return true if the player owns this country otherwise is false.
			 */
			public boolean rightcountry(String cplayer, String ccountry) {
				boolean match = false;
				LinkedList <Country> findCountries = playerSet.get(cplayer).getCountryList();
				for (Iterator <Country> iterator = findCountries.iterator(); iterator.hasNext(); ) {
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
					String now = old[0] + " "+old[1] + " " + countries.get(c.getName()).getArmy();
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

				} else {
					JOptionPane.showMessageDialog(null, "please select your own countries");
				}
			}
//
//


			/**
			 * This method check whether current player click right country or not.
			 *
			 * @param attak  Who wants to operate this country.
			 * @param defend Which country would be operated.
			 */
			public void attackerView(JLabel attak, JLabel defend) {

				System.out.println("********* Begin of attackView **********");
				// select mode
				String mode = b.chooseMode();
				String defender = b.findPlayer(defend.getName());
				String[] atcoun = attak.getText().split(" ");
				String[] decoun = defend.getText().split(" ");
				LinkedList <String> ans = new LinkedList <>();
				if (mode != "") {
					if (mode.equals("All_Out")) {
//						String canTransfer = observable.attackPhase(attak.getName(), defend.getName(),
//								mode, 0, 0, "Human");
						ans = observable.attackPhase(attak.getName(), defend.getName(),
								mode, 0, 0, "Human");
						String canTransfer = ans.getFirst();     //对于human 来说，他的第一个就是。
						Transfer(canTransfer, attak, defend);
					} else {

						// one - time
						System.out.println(" enter attackView with mode == one_time ");
						System.out.println();
						String dicses = b.dicsnumber(name.getText(), atcoun[2], "at", "");
						if (!dicses.equalsIgnoreCase("")) {

							System.out.println();
							// defender choose disc
							String de = b.dicsnumber(defender, decoun[2], "de", dicses);
							if (!de.equalsIgnoreCase("")) {

								// one_time
//								ans = observable.attackPhase(attak.getName(), defend.getName(),
//										mode, 0, 0, "Human");
								//String canTransfer = ans.getFirst();
								String canTransfer = observable.attackPhase(attak.getName(), defend.getName(), mode,
										Integer.valueOf(dicses), Integer.valueOf(de),"Human").getFirst();
								Transfer(canTransfer, attak, defend);
							}

						}

					}
				}
				System.out.println("********* End of attackView **********");

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
						JOptionPane.showMessageDialog(null, "attacker Player" + name.getText() + " win");
						if (!readrecord[2].equals("0")) {
							WIN = true;
						}

					} else if (readrecord[0].equals("-1")) {

						JOptionPane.showMessageDialog(null, "This is a draw.");
					} else {
						String defender = b.findPlayer(def.getName());
						JOptionPane.showMessageDialog(null, "defender Player" + defender + " win");
					}

				} else {
					WIN = true;
					updateCountries(att);
					updateCountries(def);
					int move = b.moveArmies(Integer.valueOf(readrecord[1]), Integer.valueOf(readrecord[2]));
					observable.Fortification(att.getName(), def.getName(), move, "Human");
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
				String now = old[0] + " "+old[1] + " " + countries.get(label.getName()).getArmy();
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

					observable.Fortification(from.getName(), to, Integer.valueOf(str), "Human");

					// update country army number
					String[] old = from.getText().split(" ");
					String now = old[0] +" "+old[1]+" " + countries.get(from.getName()).getArmy();
					from.setText(now);

					String[] toold = c.getText().split(" ");
					String tonow = toold[0] +" " +toold[1] + " "+ countries.get(c.getName()).getArmy();
					c.setText(tonow);

					// occupy a territory then obtain a card
					if (WIN) {
						observable.earnCard(name.getText(),mode.getText());
					}
					WIN = false;


					// fortification only one time enter reinforcement
					currentPhase = "Reinforcement";
					phase.setText("Reinforcement");



					// find next player
					String nextP = b.findnext(name.getText());
					enterReinforcement(nextP,"Fortification");

					// update next player armies
//					System.out.println(playerSet.get(nextP).getArmy());
//					name.setText(nextP);
//					mode.setText(playerSet.get(nextP).getMode());
//
//					color.setBackground(playerSet.get(nextP).getColor());
//					if (!playerSet.get(nextP).getMode().equalsIgnoreCase("cheater")
//							&& ! playerSet.get(nextP).getMode().equalsIgnoreCase("human")) {
//						String ans=observable.Reinforcement(nextP);
//						String army = ans.split(" ")[0];
//						String strongest = ans.split(" ")[1];
//						String infomation = "Add " + army + " armies to " + strongest + " country";
//						String s=currentPhase+" "+nextP;
//						showInformation(infomation,s);
//						LinkedList <String> delete = observable.autoChangeCard("1");
//						if (delete.size() > 0) {
//							for (int i = 0; i < delete.size(); i++) {
//
//								String sc="Change Card "+nextP;
//								showInformation(delete.get(i),sc);
//							}
//						}
//						//String cc = observable.randomSelect(nextP);
//						String curInf="Add " + playerSet.get(nextP).getArmy() + " armies to " + strongest + " country";
//						showInformation(curInf,strongest);
//						while (playerSet.get(nextP).getArmy() > 0) {
//							observable.Startup(nextP, strongest);
//						}
//						//armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
//						System.out.println();
//						updateLabelsCountry();
//						armies.setText("0");
//						noHuman();
//
//
//
//						observable.autoChangeCard(nextP);
//						updateLabelsCountry();
//						noHuman();
//					}else if(playerSet.get(nextP).getMode().equalsIgnoreCase("cheater")){
//						observable.Reinforcement(nextP);
//						showInformation("Cheater: auto double armies to every country!",
//								"Reinforcement "+nextP);
//						System.out.println();
//						updateLabelsCountry();
//						armies.setText("0");
//						noHuman();
//
//
//					}
//
//
//
//					else {
////
//						JOptionPane.showMessageDialog(null, "enter reinforcement phase in human");
//						observable.Reinforcement(nextP);
//						if (playerSet.get(nextP).getCountryList().size() > 0) {
//							observable.cardArmy(nextP, playerSet.get(nextP).getCardList(), false);
//							armies.setText("<html><body><p align=\"center\">calculating...<br/>press&nbsp;reinforcement</p></body></html>");
//
//						} else {
//							armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
//						}
//
////						if (playerSet.get(nextP).getCardList().size() != 0) {
////							System.out.println(" Card Army is not zero ");
////							observable.cardArmy(nextP, playerSet.get(nextP).getCardList(), false);
////							armies.setText(" ? ");
////
////						} else {
////							System.out.println(" Card Army is zero");
////							armies.setText(String.valueOf(playerSet.get(nextP).getArmy()));
////						}
//
//					}
//
				}

			}
		}

	}


}


