package Model;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import View.CardView;

/**
 * <h1>InitializePhase</h1> 
 * This is an initialized phase class.
 * 
 * @author jiamin_he chenwei_song
 * @version 3.0
 * @since 2019-03-01 13:08
 */
public class InitializePhase extends Observable {
	private int playerNum;
	private HashMap<String, Player> playerSet;
	private HashMap<String, Country> countries;
	private HashMap<String, Continent> continents;
	private ColorList cList = new ColorList();
	private ArrayList<String> behavoirs = new ArrayList<>();

	/**
	 * This is a constructor of initializePhase.
	 */
	public InitializePhase() {
		this.playerSet = new HashMap<>();
		this.playerNum = 0;
	}

	/**
	 * This method obtains a hash map which contains all player information.
	 *
	 * @return A hash map which contains all player information.
	 */
	public HashMap<String, Player> getPlayerSet() {
		return playerSet;
	}

	/**
	 * This method modifies player set.
	 *
	 * @param playerSet A hash map which contains all player information.
	 */
	public void setPlayerSet(HashMap<String, Player> playerSet) {
		this.playerSet = playerSet;
	}

	/**
	 * This method obtains a hash map storing all countries which are in the map.
	 *
	 * @return A hash map stores all countries which are in the map.
	 */
	public HashMap<String, Country> getCountries() {
		return countries;
	}

	/**
	 * This method modified a hash map storing all countries which are in the map.
	 *
	 * @param countries A new hash map storing all countries which are in the map.
	 */
	public void setCountries(HashMap<String, Country> countries) {
		this.countries = countries;
	}

	/**
	 * This method obtains a hash map storing all continents in the map.
	 *
	 * @return A hash map storing all continents in the map.
	 */
	public HashMap<String, Continent> getContinents() {
		return continents;
	}

	/**
	 * This method modified a hash map storing all continents which are in the map.
	 *
	 * @param continents A new hash map storing all continents in the map.
	 */
	public void setContinents(HashMap<String, Continent> continents) {
		this.continents = continents;
	}

	/**
	 * This method obtains the number of player.
	 *
	 * @return The number of player.
	 */
	public int getPlayerNum() {
		return playerNum;
	}

	/**
	 * This method adding data to playerNum, countries, continents.
	 *
	 * @param behavoirs  The strategy of players.
	 * @param countries  A hash map storing all countries which are in the map.
	 * @param continents A hash map storing all continents which are in the map.
	 */
	public void addData(ArrayList<String> behavoirs, HashMap<String, Country> countries, HashMap<String, Continent> continents) {
		this.countries = countries;
		this.continents = continents;
		this.playerNum = behavoirs.size();
		this.behavoirs = behavoirs;
	}

	/**
	 * This method invokes judgePlayerNum(), initializePlayerSet(),
	 * initializePlayerSet(), initializeCountries() to implement game's initializing
	 * phase.
	 */
	public void initPhase() {
		boolean judgeNum = judgePlayerNum();
		boolean initPlatyer = initializePlayerSet();
		boolean initAmry = initializeArmy();
		boolean initCount = initializeCountries();
		
		for (Map.Entry<String, Continent> cEntry : this.continents.entrySet()) {
			System.out.println("Control Value of Continent " + cEntry.getKey()+ " : " + cEntry.getValue().getConvalue());
		}

		if ((judgeNum && initPlatyer && initAmry && initCount) == true) {
			setChanged();
			notifyObservers(this);
		} else {
			System.out.println("Initialize Phase Failure");

		}
	}

	/**
	 * This method is used to make sure that the number of players not greater than
	 * the number of countries.
	 *
	 * @return true if less, otherwise is false.
	 */
	private boolean judgePlayerNum() {

		if (playerNum > countries.size()) {
			System.out.println("The number of player is greater than countries.");
			return false;
		} else {
			
			System.out.println("Get into initialized phase.");
			return true;
		}

	}

	/**
	 * This method implements player objects and stores them into player set.
	 *
	 * @return true if the size of player set is non-empty, otherwise is false.
	 */
	private boolean initializePlayerSet() {
		LinkedList<Color> colorLinkedList = cList.getColors();
		for (int i = 1; i <= playerNum; i++) {
			Player player = new Player(String.valueOf(i),behavoirs.get(i - 1));
			System.out.println("player name: " + String.valueOf(i) + ",mode: " + behavoirs.get(i - 1));
			player.setColor(colorLinkedList.get(i - 1));// set player color
			playerSet.put(player.getPlayerName(), player);// add player to playerSet
		}

		if (playerSet.size() == playerNum) {
			System.out.println("InitializePlayerSet success");
			return true;
		} else {
			System.out.println("InitializePlayerSet Failure");
			return false;
		}
	}

	/**
	 * This method bases the number of players to initialize the number of armies.
	 *
	 * @return true if initializes successful, otherwise is false.
	 */
	private boolean initializeArmy() {
		int armyDefault = 0;
		switch (playerNum) {
		case 2:
			armyDefault = 20;
			break;
		case 3:
			armyDefault = 10;
			break;
		case 4:
			armyDefault = 30;
			break;
		case 5:
			armyDefault = 25;
			break;
		default:
			break;
		}

		if (armyDefault == 0) {
			System.out.println("InitializeArmy Failure");
			return false;
		} else {
			for (Map.Entry<String, Player> entry : playerSet.entrySet()) {
				entry.getValue().setArmy(armyDefault);
			}
			System.out.println("InitializeArmy success");
			return true;
		}
	}

	/**
	 * This method implements randomly assigning countries to players and setting
	 * player's color, setting every country with one armies.
	 *
	 * @return true if initialization is succeed, otherwise is false.
	 */
	private boolean initializeCountries() {
		if (!judgePlayerNum()) {

			System.out.println("InitializeCountries Failure");
			return false;

		} else {
			LinkedList<String> countryList = new LinkedList<>();
			for (Map.Entry<String, Country> entry : countries.entrySet()) {
				countryList.addLast(entry.getKey());
			}

			while (!countryList.isEmpty()) {
				Random randomC = new Random();
				if (countryList.size() < playerNum) {
					LinkedList<String> playerList = new LinkedList<>();
					for (Map.Entry<String, Player> entry : playerSet.entrySet()) {
						playerList.addLast(entry.getKey());
					}
					for (String str : countryList) {
						Random randomP = new Random();
						int index = randomP.nextInt(playerList.size());
						String tmpP = playerList.get(index);
						Country country = countries.get(str);
						Player player = playerSet.get(tmpP);
						country.setColor(player.getColor());// set country color
						country.setArmy(1);// set country
						LinkedList<Country> pcountryList = player.getCountryList();
						player.setArmy(player.getArmy() - 1);
						pcountryList.addLast(country);
					}
					countryList.clear();

				} else {
					for (Map.Entry<String, Player> entry : playerSet.entrySet()) {

						int index = randomC.nextInt(countryList.size());
						String remove = countryList.get(index);
						Country country = countries.get(remove);
						country.setColor(entry.getValue().getColor());// set country color
						country.setArmy(1);// set country army
						LinkedList<Country> pcountryList = entry.getValue().getCountryList();
						entry.getValue().setArmy(entry.getValue().getArmy() - 1); // update player army
						pcountryList.addLast(country);
						countryList.remove(remove);
					}
				}
			}
			return true;
		}
	}

	/**
	 * This method implements player allocated the number of initial armies.
	 *
	 * @param pname   Player name.
	 * @param couname Country name.
	 */
	public void Startup(String pname, String couname) {

		int parmies = playerSet.get(pname).getArmy() - 1;
		playerSet.get(pname).setArmy(parmies);

		int carmies = countries.get(couname).getArmy() + 1;
		countries.get(couname).setArmy(carmies);
		setChanged();
		notifyObservers(this);

	}

	/**
	 * This method implements Reinforcement phase.
	 * 
	 * @param player Player name.
	 */
	public void Reinforcement(String player) {
		playerSet.get(player).reinforcement(playerSet,countries);
		setChanged();
		notifyObservers(this);
	}

	/**
     * This method is used to calculate army produced by cards.
     *
     * @param player Name of player.
     * @param reCards Cards player hold after change cards phase.
     * @param change Change cards or not.
     */
	public void cardArmy(String player, LinkedList<Card> reCards, boolean change) {
		if (!change) {

			CardView cardview = new CardView(this);
			this.addObserver(cardview);
			cardview.receive(player, playerSet.get(player).getCardList());

		}

		if (change) {

			int size = playerSet.get(player).getCardList().size();
			int resize = reCards.size();

			if (resize != size) {

				playerSet.get(player).setCardList(reCards);
				int armies = playerSet.get(player).getArmy();
				int times = playerSet.get(player).getChangeCardTime();
				playerSet.get(player).setArmy(armies + times * 5);
				playerSet.get(player).setChangeCardTime(times + 1);
				if (resize >= 3) {
					cardArmy(player, playerSet.get(player).getCardList(), false);
				}
			}

			setChanged();
			notifyObservers(this);

		}

	}

	/**
	 * This method implements attack object and invokes attack function.
	 * 
	 * @param attacker Attack country's name.
	 * @param defender Defended country's name.
	 * @param mode     Attack mode which player chooses.
	 * @param attDices The number of dices attacker chooses.
	 * @param defDices The number of dices defender chooses.
	 * @return The result of attack phase.
	 */
	public String attackPhase(String attacker, String defender, String mode,
							  int attDices, int defDices) {

		System.out.println("we at attackPhase in initialize");
		System.out.println("the attacker is :"+attacker);  //country
		System.out.println("the defender is :"+defender);  //country
		System.out.println("the playerset is :");
		String playerAttack="";
		for(String players:playerSet.keySet()){
			LinkedList<Country> countries=playerSet.get(players).getCountryList();
			for(int i=0;
				i<countries.size();i++){
				if(countries.get(i).getName()==Integer.valueOf(attacker)){
					playerAttack=players;
					break;
				}
			}
		}

		String result=playerSet.get(playerAttack).attack(attacker, defender, mode, attDices,
				defDices, playerSet, countries, continents);

		if (result != "") {
			setChanged();
			notifyObservers(this);
			return result;

		} else {
			System.out.println("Attack Failure!!!");
		}

		return "Failure";

	}
//
//		Attack attack = new Attack(this.countries, this.continents, this.playerSet, attacker, defender, mode, attDices,
//				defDices);
//
//		String result = attack.attacking();// invoking attacking function
//
//		if (result != "") {
//			setChanged();
//			notifyObservers(this);
//			return result;
//
//		} else {
//			System.out.println("Attack Failure!!!");
//		}
//
//		return "Failure";
//	}

	/**
	 * This method implements armies transfer in attack phase.
	 * 
	 * @param attacker Attack country's name.
	 * @param defender Defended country's name.
	 * @param armies   The number of armies that player want to move.
	 */
	public void attTransforArmies(String attacker, String defender, int armies) {
		Attack attack = new Attack(this.countries, this.continents, this.playerSet, attacker, defender);
		attack.transferArmy(armies);
		System.out.println(" Attack Phase: finished transfer armies.");
		setChanged();
		notifyObservers(this);

	}

	/**
	 * This method a valid fortification.
	 *
	 * @param from Starting country.
	 * @param to   Target country.
	 * @param move The number of armies to be moved.
	 */
	public void Fortification(String from, String to, int move) {
		String player="";
		Country fromc=countries.get(from);
		Country toc=countries.get(to);
		for(String players:playerSet.keySet()){
			if(playerSet.get(players).getColor().equals(fromc.getColor())){
				player=players;
			}
		}
		playerSet.get(player).fortification(fromc,toc,move,countries);
//		int start = countries.get(from).getArmy() - move;
//		countries.get(from).setArmy(start);
//
//		int end = countries.get(to).getArmy() + move;
//		countries.get(to).setArmy(end);

		setChanged();
		notifyObservers(this);

	}

	/**
     * This method is to earnCard.
     *
     * @param player Current player.
     */
	public void earnCard(String player) {
		int card = (int) (1 + Math.random() * 3);
		Card c = new Card();
		LinkedList<Card> list = new LinkedList<>();
		switch (card) {
		case 1:

			// infantry
			c.setName("i");
			list = playerSet.get(player).getCardList();
			list.add(c);
			playerSet.get(player).setCardList(list);
			JOptionPane.showMessageDialog(null, "you got infantry card");
			break;
		case 2:

			// cavalry
			c.setName("c");
			list = playerSet.get(player).getCardList();
			list.add(c);

			playerSet.get(player).setCardList(list);
			JOptionPane.showMessageDialog(null, "you got cavalry card");
			break;
		case 3:

			// artillery
			c.setName("a");
			list = playerSet.get(player).getCardList();
			list.add(c);

			playerSet.get(player).setCardList(list);
			JOptionPane.showMessageDialog(null, "you got artillery card");
			break;

		}

		setChanged();
		notifyObservers(this);
	}

	/**
	 * The method judges these countries can transfer or not.
	 * 
	 * @param player The current player.
	 * @param s The start country.
	 * @param d The destination country.
	 * @return true if these countries can transfer.
	 */
	public boolean canTransfer(String player, String s, String d) {
		int maxCountry = returnMax();
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

	/**
	 * The method is to check whether exists a path that can execute transfer method.
	 * 
	 * @param player The current player.
	 * @param Path All the path from start country to destination.
	 * @return true If there is a path that belongs to this player.
	 */
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

	/**
	 * The method checks whether current player click right country.
	 * 
	 * @param cplayer Current player.
	 * @param ccountry Current country.
	 * @return true if the player owns the country.
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
     * This method is to find the maximum of countries.
     *
     * @return maximum The max of countries.
     */
	private int returnMax() {
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

/**
 * <h1>ColorList</h1> This class defines color class, initialing all color
 * information.
 *
 * @author jiamin_he
 * @version 3.0
 * @since 2019-03-01
 */
class ColorList {

	private LinkedList<Color> colors = new LinkedList<>();

	/**
	 * This is a no-argument constructor of ColorList.
	 */
	public ColorList() {
		Color skyblue = new Color(119, 240, 228);
		Color lightgreen = new Color(91, 255, 120);
		Color ginger = new Color(217, 179, 64);
		Color pink = new Color(251, 132, 188);
		Color purple = new Color(182, 154, 228);
		colors.addLast(skyblue);
		colors.addLast(lightgreen);
		colors.addLast(ginger);
		colors.addLast(pink);
		colors.addLast(purple);
	}

	/**
	 * This method obtains a color list.
	 * 
	 * @return A linked list stores all color information.
	 */
	public LinkedList<Color> getColors() {
		return colors;
	}
}