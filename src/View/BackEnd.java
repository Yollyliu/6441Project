package View;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Model.Continent;
import Model.Country;
import Model.InitializePhase;
import Model.Player;

/**
 * <h1>BackEnd</h1> 
 * The BackEnd class implements the logical operations of all
 * front ends.
 *
 * @author chenwei_song
 * @version 3.0
 * @since 2019-03-03
 */
public class BackEnd implements Observer {

	public HashMap<String, Country> countries = new HashMap<>();
	public HashMap<String, Continent> continents = new HashMap<>();
	public HashMap<String, Player> playerSet = new HashMap<>();
	PlayView p;

	/**
	 * It is observer override update method update countries, continents, players
	 * at the same time.
	 *
	 * @param obs The observable object.
	 * @param x   An argument passed to the notifyObservers method.
	 */
	@Override
	public void update(Observable obs, Object x) {
		countries = ((InitializePhase) obs).getCountries();
		continents = ((InitializePhase) obs).getContinents();
		playerSet = ((InitializePhase) obs).getPlayerSet();

	}

	/**
	 * This is a no-argument constructor of BackEnd.
	 */
	public BackEnd() {

	}

	/**
	 * The method judges the number of armies whether is greater than one or not.
	 * 
	 * @param country Current country name.
	 * @return true if the number is more than 1, otherwise false.
	 */
	public boolean isOne(String country) {
		if (countries.get(country).getArmy() > 1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * This method judges that after moving armies, the number of armies of country
	 * hold whether is zero or not.
	 *
	 * @param from Country name.
	 * @param move The number of armies moved.
	 * @return true if the remain armies isn't zero, otherwise false.
	 */
	public boolean Iszero(String from, int move) {
		int zero = countries.get(from).getArmy() - move;
		if (zero > 0) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * This method obtains the number of armies that player want to move.
	 *
	 * @param min The minimum number of armies must be moved.
	 * @param max The maximum number of armies can be moved.
	 * @return The number of armies that player want to move.
	 */
	public int moveArmies(int min, int max) {

		final JFrame frame = new JFrame();
		JDialog dialog = new JDialog(frame, true);
		JPanel panel = new JPanel();
		panel.setSize(300, 300);
		final JLabel sliderLabel = new JLabel("armies         ", JLabel.CENTER);
		sliderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		final JSlider framesPerSecond = new JSlider(min, max);
		framesPerSecond.setMinorTickSpacing(1);
		framesPerSecond.setPaintTicks(true);
		framesPerSecond.setPaintLabels(true);
		framesPerSecond.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		Font font = new Font("Serif", Font.ITALIC, 15);
		framesPerSecond.setFont(font);
		framesPerSecond.addChangeListener(new ChangeListener() {

			/**
			 * This is stateChanged function.
			 */
			@Override
			public void stateChanged(ChangeEvent e) {
				JSlider source = (JSlider) e.getSource();
				int fps = (int) source.getValue();
				sliderLabel.setText("armies:" + String.valueOf(fps));

			}
		});
		panel.add(sliderLabel);
		panel.add(framesPerSecond);

		JButton button = new JButton("OK");
		button.setBounds(150, 150, 25, 30);
		panel.add(button);

		button.addActionListener(new ActionListener() {

			/**
			 * This method will be invoked when an action occurs.
			 */
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
		int move = (int) framesPerSecond.getValue();
		return move;
	}

	/**
	 * This method is to calculate disc number.
	 * 
	 * @param p1 Current player.
	 * @param armies Country armies.
	 * @param who Attacker or defender.
	 * @param de Defender armies.
	 * @return Disc number.
	 */
	public String dicsnumber(String p1, String armies, String who, String de) {
		int dics = 0;
		if (who.equals("at")) {
			dics = atdics(Integer.valueOf(armies));
		} else {
			// attacker defender
			dics = dedics(Integer.valueOf(de), Integer.valueOf(armies));
		}
		String m = "";
		JPanel panel = new JPanel(new GridLayout(1, 4));
		JLabel player = new JLabel("Player" + p1);
		panel.add(player);
		ButtonGroup group = new ButtonGroup();

		for (int i = 1; i <= dics; i++) {
			JRadioButton button = new JRadioButton(String.valueOf(i));
			panel.add(button);
			group.add(button);
		}
		int result = JOptionPane.showConfirmDialog(null, panel, "Please select your dics", JOptionPane.DEFAULT_OPTION);
		if (result == 0) {
			for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();

				if (button.isSelected()) {
					m = button.getText();
					System.out.println("you choose " + button.getText() + " mode");
				}
			}
		}

		return m;
	}

	/**
	 * This method finds who is next player.
	 *
	 * @param currentplayer Current playerInitializePhase.java
	 * @return Next player.
	 */
	public String nextplayer(String currentplayer) {

		String next = "";
		int size = playerSet.size();
		int[] a = new int[size];
		int cup = Integer.valueOf(currentplayer);
		int temp;
		if (cup == size) {
			temp = 1;
		} else {
			temp = cup + 1;
		}

		for (int i = 0; i < a.length; i++) {
			if (temp == a.length + 1) {
				temp = 1;
			}
			a[i] = temp;
			temp++;

		}
		for (int i = 0; i < a.length; i++) {

			if (playerSet.get(String.valueOf(a[i])).getArmy() != 0) {
				next = String.valueOf(a[i]);
				break;
			}

		}

		return next;

	}

	/**
	 * This method finds the max number of player.
	 * 
	 * @return Player name.
	 */
	public int maxplayer() {
		int max = 0;
		for (String key : playerSet.keySet()) {
			int temp = Integer.valueOf(key);
			if (temp > max) {
				max = temp;
			}
		}
		return max;
	}

	/**
	 * This method finds who is next player.
	 * 
	 * @param current Current player.
	 * @return Next player.
	 */
	public String findnext(String current) {

		int max = maxplayer();
		String next = String.valueOf(Integer.valueOf(current) + 1);
		if (Integer.valueOf(current) == max) {
			next = "1";
		}
		if (playerSet.containsKey(next)) {
			return next;
		} else {
			return findnext(next);
		}
	}

	/**
	 * This method judges whether the countries is neighbor or not
	 *
	 * @param from Countries 1
	 * @param to   Countries 2
	 * @return true if the two countries are neighbor otherwise is false.
	 */
	public boolean Isneighbour(String from, String to) {
		boolean is = false;
		String[] list = countries.get(from).getCountryList().split(" ");
		for (int i = 0; i < list.length; i++) {
			if (to.equals(list[i])) {
				is = true;
				break;
			}
		}
		return is;
	}

	/**
	 * This method implements finding a player who owns current country.
	 *
	 * @param countryName Current country name.
	 * @return The player who owns current country.
	 */
	public String findPlayer(String countryName) {

		Country country = countries.get(countryName);
		Color color = country.getColor();
		for (Map.Entry<String, Player> entry : playerSet.entrySet()) {
			if (entry.getValue().getColor().equals(color)) {
				return entry.getKey();
			}
		}
		System.out.println("Cannot find the player!!!");
		return "";
	}

	/**
	 * This method finds the number of dices that current attacker can choose.
	 *
	 * @param armies The number of armies of current country holding.
	 * @return The number of dices that current attacker can choose.
	 */
	public int atdics(int armies) {
		return Math.min(3, armies - 1);

	}

	/**
	 * This method finds the number of dices that current defender can choose.
	 *
	 * @param at The number of dices attacker chooses.
	 * @param df The number of armies of defended country holding.
	 * @return The number of dices that current defender can choose.
	 */
	public int dedics(int at, int df) {
		int d = Math.min(2, df);
		int result = Math.min(d, at);
		return result;
	}

	/**
	 * This method obtain which attack mode player want to choose.
	 *
	 * @return Attack mode.
	 */
	public String chooseMode() {
		String m = "";
		JPanel mode = new JPanel(new GridLayout(1, 2));
		JRadioButton allOutButton = new JRadioButton("All_Out");

		allOutButton.setSelected(true);
		JRadioButton oneTimeButton = new JRadioButton("One_Time");

		mode.add(allOutButton);
		mode.add(oneTimeButton);

		ButtonGroup group = new ButtonGroup();
		group.add(allOutButton);
		group.add(oneTimeButton);
		int result = JOptionPane.showConfirmDialog(null, mode, "Please select your mode", JOptionPane.DEFAULT_OPTION);
		if (result == 0) {
			for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
				AbstractButton button = buttons.nextElement();

				if (button.isSelected()) {
					m = button.getText();
					System.out.println("you choose " + button.getText() + " mode");
				}
			}
		}
		return m;

	}

	/**
	 * This method judges whether the player can attack other countries or not.
	 * 
	 * @param player Current player.
	 * @return true if the player can attack.
	 */
	public boolean canAttack(String player) {
		System.out.println("**************Begin of canAttack " +
				" in BackEnd *************************");

		boolean canAttack = false;
		LinkedList<Country> countrylist = playerSet.get(player).getCountryList();
		System.out.println("********* Begin canAttack BendEnd *********");
		for (int i = 0; i < countrylist.size(); i++) {
			String attcoun = String.valueOf(countrylist.get(i).getName());
			String[] surround = countries.get(attcoun).getCountryList().split(" ");
			System.out.println("Country : "+ attcoun+ " Army: "+countries.get(attcoun).getArmy());
			for (int j = 0; j < surround.length; j++) {
				Color attColor = playerSet.get(player).getColor();
				Color defColor = countries.get(surround[j]).getColor();
				//System.out.println("Find a different player country :");
				System.out.println("Country : "+ attcoun+ " has neib: "+surround.length);
				//if (attColor != defColor && countrylist.get(i).getArmy() > 1) {
				if (attColor != defColor && countries.get(attcoun).getArmy() > 1) {

					System.out.println("Definder is "+surround[j]);
					canAttack = true;
				}
			}
		}
		System.out.println();
		System.out.println("The result of attack is "+ canAttack);
		System.out.println();
		System.out.println("**************End of canAttack " +
				" in BackEnd *************************");

		return canAttack;
	}

}
