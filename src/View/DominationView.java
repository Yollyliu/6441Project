package View;

import Model.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

/**
 * <h1>DominationView</h1> 
 * It is a JLayeredPane Domination View with labels have
 * three function (reinforcement, attack, fortification).
 *
 * @author youlin_liu, shuo_chi
 * @version 3.0
 * @since 2019-03-20
 */
public class DominationView extends JLayeredPane implements Observer {

	public HashMap<String, Player> playerSet = new HashMap<>();
	public HashMap<String, Country> countries = new HashMap<>();
	public HashMap<String, Continent> continents = new HashMap<>();
	LinkedList<String> labels = new LinkedList<>();
	InitializePhase observable;
	LinkedList<JLabel> allLables = new LinkedList<>();

	/**
	 * This observer override update method update countries, continents, players
	 * and JPanel JLabels at the same time.
	 */
	@Override
	public void update(Observable obs, Object x) {
		countries = ((InitializePhase) obs).getCountries();
		continents = ((InitializePhase) obs).getContinents();
		playerSet = ((InitializePhase) obs).getPlayerSet();
		UpdateDomain();

	}

	/**
	 * This is a constructor method for DominationView.
	 *
	 * @param model InitializePharse_model.
	 */
	public DominationView(InitializePhase model) {
		observable = model;
		playerSet = observable.getPlayerSet();
		countries = observable.getCountries();
		continents = observable.getContinents();
		Dimension size = new Dimension(500, 450);
		setPreferredSize(size);

		JLabel label1 = new JLabel();
		label1.setBounds(130, 300, 80, 25);
		label1.setText("Percent");
		add(label1);

		JLabel label2 = new JLabel();
		label2.setBounds(185, 300, 80, 25);
		label2.setText("ownedConti");
		add(label2);

		JLabel label3 = new JLabel();
		label3.setBounds(270, 300, 80, 25);
		label3.setText("TotalArmy");
		add(label3);

		if (playerSet.size() > 0) {

			for (int i = 0; i < playerSet.size(); i++) {
				JLabel label = new JLabel();
				label.setText(percentCountry(Integer.toString(i + 1)));
				label.setName(Integer.toString(i + 1));
				label.setBounds(80, 340 + 30 * i, 400, 25);
				add(label);
				allLables.add(label);
			}
		} else {
			System.out.println("player set is 0");
		}
	}

	/**
	 * This is an update method updating all JLabels.
	 */
	public void UpdateDomain() {

		boolean noPlayer = true;
		for (JLabel la : allLables) {
			for (Map.Entry<String, Player> entry : playerSet.entrySet()) {
				if (la.getName().equals(entry.getKey())) {
					la.setText(percentCountry(entry.getKey()));
					noPlayer = false;
				}

			}
			if (noPlayer) {
				StringBuffer sb = new StringBuffer();
				sb.append("Player");
				sb.append(la.getName());
				sb.append("       0");
				la.setText(sb.toString());

			}
			noPlayer = true;
		}
	}

	/**
	 * This is a method receive current information for players, countries and
	 * continents.
	 *
	 * @param players    information which maybe changed.
	 * @param Countries  information which maybe changed.
	 * @param continents which information maybe changed.
	 */
	public void receive(HashMap<String, Player> players, HashMap<String, Country> Countries,
			HashMap<String, Continent> continents) {
		// flag=true;
		this.playerSet = players;
		System.out.println("This is in the receive function");
		this.continents = continents;
		this.countries = Countries;

	}

	/**
	 * This method gets all information of a player.
	 *
	 * @param key It is a player name.
	 * @return String Player information for all needed.
	 */
	public String percentCountry(String key) {

		StringBuffer sb = new StringBuffer();
		sb.append("Player");
		sb.append(key);
		sb.append("       ");
		int countryNum = playerSet.get(key).getCountryList().size();
		float perct = (float) countryNum / countries.size();
		perct = perct * 100;
		sb.append(perct);
		sb.append("%           ");
		String s = ownContinent(key);
		if (!s.equals("")) {
			sb.append(s);
		} else {
			sb.append("   ");
		}
		sb.append("            ");
		sb.append(getTotalArmy(key));
		String allInfor = sb.toString();
		System.out.println("Information in for " + allInfor);
		return allInfor;
	}

	/**
	 * This method gets owned continents of a player.
	 *
	 * @param player A player name.
	 * @return String Player information for owned continents.
	 */
	public String ownContinent(String player) {
		String continent = "";
		LinkedList<Country> captital = playerSet.get(player).getCountryList();
		HashMap<String, Integer> cal = new HashMap<>();
		LinkedList<String> listB = new LinkedList<>();
		for (int i = 0; i < captital.size(); i++) {
			String c = captital.get(i).getContinent();
			listB.add(c);
		}
		for (int i = 0; i < listB.size(); i++) {
			int temp = Collections.frequency(listB, listB.get(i));
			if (continents.get(listB.get(i)).getCountryList().size() == temp) {
				cal.put(listB.get(i), continents.get(listB.get(i)).getConvalue());
			}
		}
		for (String key : cal.keySet()) {
			continent = continent + key + " ";
		}
		System.out.println("The continent owned is " + continent);
		return continent;
	}

	/**
	 * This method gets total armies of a player.
	 *
	 * @param key A player name
	 * @return String Player information for total armies.
	 */
	public String getTotalArmy(String key) {
		String armies = "";
		int total = 0;
		Player player = playerSet.get(key);
		LinkedList<Country> countries = player.getCountryList();
		for (Country curCoun : countries) {
			total += curCoun.getArmy();
		}
		total += player.getArmy();
		armies = String.valueOf(total);
		return armies;
	}

}
