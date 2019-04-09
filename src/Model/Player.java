package Model;

import PlayerStrategy.Strategy;
import PlayerStrategy.strategyFactory;

import java.awt.*;
import java.util.*;

/**
 * <h1>Player</h1> 
 * This class for defining a player. It contains all
 * information.
 *
 * @author jiamin_he
 * @version 3.0
 * @since 2019-03-01
 */
public class Player {

	private String playerName;
	private Color color;
	private int army;
	private LinkedList<Country> countryList = new LinkedList<>();
	private LinkedList<Card> cardList = new LinkedList<>();
	private int changeCardTime = 1;
	private Strategy strategy;


	/**
	 * This is a default constructor.
	 */
	public Player() {

	}

	/**
	 * This is a constructor and initializes player attribute.
	 *
	 * @param playerName player name.
	 */
	public Player(String playerName,String behavoir) {
		this.playerName = playerName;
		this.color = null;
		this.army = 0;

		this.strategy= strategyFactory.getBehavior(behavoir,this);
	}

	/**
	 * This method obtains player name.
	 *
	 * @return player name.
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * This method modifies player name.
	 *
	 * @param playerName player name.
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * This method obtains player's color.
	 *
	 * @return player's color.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * This method modified player's color.
	 *
	 * @param color player's color.
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * This method obtains the number of armies player having.
	 *
	 * @return the number of armies.
	 */
	public int getArmy() {
		return army;
	}

	/**
	 * This method modifies the number of armies player having.
	 *
	 * @param army the number of armies.
	 * 
	 */
	public void setArmy(int army) {
		this.army = army;
	}

	/**
	 * This method obtains all countries which player has.
	 *
	 * @return all countries which player has.
	 */
	public LinkedList<Country> getCountryList() {
		return countryList;
	}

	/**
	 * This method modifies the countries which player has.
	 *
	 * @param countryList a list stores all countries which player has.
	 */
	public void setCountryList(LinkedList<Country> countryList) {
		this.countryList = countryList;
	}

	/**
	 * This method obtains all cards which player has.
	 *
	 * @return a list stores all cards which player has.
	 */
	public LinkedList<Card> getCardList() {
		return cardList;
	}

	/**
	 * This method modifies cards which player has.
	 *
	 * @param cardList a list stores all cards which player has.
	 */
	public void setCardList(LinkedList<Card> cardList) {
		this.cardList = cardList;
	}

	/**
	 * This method obtains the times of changing card.
	 * 
	 * @return The times of changing card.
	 */
	public int getChangeCardTime() {
		return changeCardTime;
	}

	/**
	 * This method modifies the times of changing card.
	 * 
	 * @param changeCardTime The times of changing card.
	 */
	public void setChangeCardTime(int changeCardTime) {
		this.changeCardTime = changeCardTime;
	}


	//////////////////////// Reinforcement /////////////////////////////////////

	public void reinforcement(){
		this.strategy.Reinforcement();
	}



	public void getAllArmies() {

		// update armies number for each player
		int system = SystemArmy();
		int continent = ContinentArmy();
		int card = 0;
		setArmy(system + continent + card);
	}

	public int SystemArmy() {

		if (getCountryList().size() <= 9) {
			return 3;
		}
		// countries # >9
		else {
			int n = getCountryList().size() / 3;
			n = (int) Math.floor(n);
			return n;
		}
	}



	public int ContinentArmy() {     //暂时想不出来怎么改
		int n = 0;
		LinkedList<Country> captital =getCountryList();
		HashMap<String, Integer> cal = new HashMap<>();
		LinkedList<String> listB = new LinkedList<>();
		for (int i = 0; i < captital.size(); i++) {
			String c = captital.get(i).getContinent();

			listB.add(c);
		}
//		for (int i = 0; i < listB.size(); i++) {
//			int temp = Collections.frequency(listB, listB.get(i));
//			if (continents.get(listB.get(i)).getCountryList().size() == temp) {
//
//				cal.put(listB.get(i), continents.get(listB.get(i)).getConvalue());
//			}
//		}
		for (String key : cal.keySet()) {
			n = n + cal.get(key);
		}
		return n;
	}



	/////////////////// Attack //////////////////////////////////////


	public void attack(String attacker, String defender, String mode,
					   int attDices, int defDices,
					   HashMap<String, Player> playerSet,
					   HashMap<String, Country> countries,
					   HashMap<String, Continent> continents){

		this.strategy.Attack(attacker, defender, mode,
				attDices, defDices,playerSet,countries,continents);
	}

	public String attackPhase(String attacker, String defender, String mode,
							  int attDices, int defDices,
							  HashMap<String, Player> playerSet,
							  HashMap<String, Country> countries,
							  HashMap<String, Continent> continents) {

		Attack attack = new Attack(countries, continents, playerSet, attacker,
				defender, mode, attDices, defDices);

		String result = attack.attacking();// invoking attacking function

		if (result != "") {

			return result;

		} else {
			System.out.println("Attack Failure!!!");
		}

		return "Failure";
	}

	////////////////////////  Fortification /////////////////////////
	public void fortification(Country from, Country to, int move,
							  HashMap<String, Country> countries){
		this.strategy.Fortification(from,  to, move,countries);

	}

	public boolean canTransfer(String player, String s, String d,
							   HashMap<String, Country> countries) {
		int maxCountry = returnMax(countries);
		FindPath fp = new FindPath(maxCountry);
		String p = player;
		Iterator<Map.Entry<String, Country>> iterator = countries.entrySet().iterator();

		// build graph
		while (iterator.hasNext()) {
			Map.Entry<String, Country> entry = iterator.next();
			int from = Integer.valueOf(entry.getKey());
			String[] clist = entry.getValue().getCountryList().split(" ");
			for (int i = 0; i < clist.length; i++) {
				fp.addEdge(from, Integer.valueOf(clist[i]));
			}

		}

		int start = Integer.valueOf(s);
		int end = Integer.valueOf(d);

		fp.printAllPaths(start, end);

		ArrayList<ArrayList<Integer>> allpath = new ArrayList<>();

		String[] paths = fp.allpath.split("#");
		for (int i = 0; i < paths.length; i++) {
			ArrayList<Integer> onepath = new ArrayList<>();
			String[] line = paths[i].split(" ");
			for (int j = 0; j < line.length; j++) {
				onepath.add(Integer.valueOf(line[j]));
			}
			allpath.add(onepath);
		}
		System.out.println(allpath);
		boolean result = checkPath(p, allpath);
		return result;
	}



	private boolean checkPath(String player, ArrayList<ArrayList<Integer>> Path) {
		boolean isOwn = false;

		String p = player;
		for (int i = 0; i < Path.size(); i++) {
			boolean temp = true;
			for (int j = 0; j < Path.get(i).size(); j++) {
				boolean isMatch = rightcountry(p, String.valueOf(Path.get(i).get(j)));

				if (!isMatch) {
					temp = false;

					break;
				}
			}
			if (temp) {
				isOwn = true;
				break;

			}

		}

		return isOwn;
	}

	public boolean rightcountry(String cplayer, String ccountry) {
		boolean match = false;
		LinkedList<Country> findCountries = this.getCountryList();
		for (Iterator<Country> iterator = findCountries.iterator(); iterator.hasNext();) {
			String s = String.valueOf(iterator.next().getName());
			if (ccountry.equals(s)) {
				match = true;
			}

		}
		return match;
	}


	private int returnMax(HashMap<String, Country> countries) {
		int max = 0;
		for (String m : countries.keySet()) {
			int temp = countries.get(m).getName();
			if (temp > max) {
				max = temp;
			}
		}
		return max + 1;
	}


}
