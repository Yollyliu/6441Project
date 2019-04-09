package Model;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.*;
import java.util.List;
import java.util.Map.Entry;

import javax.swing.*;

import View.CardView;

/**
 * <h1>InitializePhase</h1> 
 * This is an initialized phase class.
 * 
 * @author jiamin_he chenwei_song
 * @version 3.0
 * @since 2019-03-01 13:08
 */
public class InitializePhase extends Observable implements Serializable {
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

	public ArrayList <String> getBehavoirs() {
		return behavoirs;
	}

	public void setBehavoirs(ArrayList <String> behavoirs) {
		this.behavoirs = behavoirs;
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

	public void clearAll(){
		this.countries = new HashMap <>();
		this.continents = new HashMap <>();
		this.playerNum = 0;
		this.behavoirs = new ArrayList <>();
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
	 * This method invokes judgePlayerNum(), initializePlayerSet(),
	 * initializePlayerSet(), initializeCountries() to implement game's initializing
	 * phase.
	 */
	public void initPhase() {
		boolean judgeNum = judgePlayerNum();
		boolean initPlatyer = initializePlayerSet();
		boolean initAmry = initializeArmy();
		boolean initCount = initializeCountries();
		
		for (Entry<String, Continent> cEntry : this.continents.entrySet()) {
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
			armyDefault = 13;
			break;
		case 4:
			armyDefault = 13;
			break;
		case 5:
			armyDefault = 13;
			break;
		default:
			break;
		}

		if (armyDefault == 0) {
			System.out.println("InitializeArmy Failure");
			return false;
		} else {
			for (Entry<String, Player> entry : playerSet.entrySet()) {
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
			LinkedList <String> countryList = new LinkedList <>();
			for (Entry <String, Country> entry : countries.entrySet()) {
				countryList.addLast(entry.getKey());
			}

			while (!countryList.isEmpty()) {
				Random randomC = new Random();
				if (countryList.size() < playerNum) {
					LinkedList <String> playerList = new LinkedList <>();
					for (Entry <String, Player> entry : playerSet.entrySet()) {
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
						LinkedList <Country> pcountryList = player.getCountryList();
						player.setArmy(player.getArmy() - 1);
						pcountryList.addLast(country);
					}
					countryList.clear();

				} else {
					for (Entry <String, Player> entry : playerSet.entrySet()) {

						int index = randomC.nextInt(countryList.size());
						String remove = countryList.get(index);
						Country country = countries.get(remove);
						country.setColor(entry.getValue().getColor());// set country color
						country.setArmy(1);// set country army
						LinkedList <Country> pcountryList = entry.getValue().getCountryList();
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
	 * This method is to random select a country of a player.
	 *
	 * @param key The name of the player.
	 * @return the name of the random country selected.
	 */

	public String randomSelect(String key){

		System.out.println(" In random select Initialize: the player is "+key);
				Random randomC = new Random();

				int index = randomC.nextInt(playerSet.get(key).getCountryList().size());
				int find=playerSet.get(key).getCountryList().get(index).getName();
				String findc=String.valueOf(find);

		System.out.println(" the select country is "+ findc);
		return findc;

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
		System.out.println("finish start up, play army -1, country army +1");


		setChanged();
		notifyObservers(this);

	}

	/**
	 * This method implements Reinforcement phase.
	 * 
	 * @param player Player name.
	 * @return the name of country to be reinforced.
	 */
	public String Reinforcement(String player) {
		System.out.println();
		System.out.println("in initialize phase reinforcement");
		String c=playerSet.get(player).reinforcement(playerSet,countries,continents);
		//System.out.println("the lucky country is "+c);
		System.out.println("done well in Reinforcement");
		System.out.println();
		System.out.println();


		setChanged();
		notifyObservers(this);
		return c;
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
	 * @param strategy The strategy of current player.
	 * @return The result of attack phase.
	 */
	public LinkedList<String> attackPhase(String attacker, String defender, String mode,
							  int attDices, int defDices,String strategy) {

		System.out.println("we at attackPhase in initialize");
		System.out.println("Attacker is :"+attacker);  //country
		System.out.println("Defender is :"+defender);  //country
		System.out.println("Strategy is :"+strategy);
		System.out.println();
		System.out.println();
		System.out.println();
		String playerAttack="";
		String result="";
		LinkedList<String> ans=new LinkedList <>();
		if(strategy.equalsIgnoreCase("human")) {
			for (String players : playerSet.keySet()) {
				LinkedList <Country> countries = playerSet.get(players).getCountryList();
				for (int i = 0; i < countries.size(); i++) {
					if (countries.get(i).getName() == Integer.valueOf(attacker)) {
						playerAttack = players;
						break;
					}
				}
			}

			System.out.println("the playerAttack : " + playerAttack);

			ans = playerSet.get(playerAttack).attack(attacker, defender, mode, attDices,
					defDices, playerSet, countries, continents);
		}else {

			if(strategy.equalsIgnoreCase("cheater")) {
				ans=playerSet.get(attacker).attack(attacker, defender, mode, attDices,
						defDices, playerSet, countries, continents);
				changeCheater(ans,attacker);
			}else{
				ans=playerSet.get(attacker).attack(attacker, defender, mode, attDices,
						defDices, playerSet, countries, continents);
			}



		}

		if(ans.size()>0){
		//if (result != "") {
			setChanged();
			notifyObservers(this);

			return ans;

		} else {
			System.out.println("Attack Failure!!!");
		}


		return ans;

	}


	public void changeCheater(LinkedList<String> ans,String player){
		for(int i=0;i<ans.size();i++){
			System.out.println("update "+ans.get(i)+" information in countries and playerSet");
			countries.get(ans.get(i)).setColor(playerSet.get(player).getColor());
			countries.get(ans.get(i)).setArmy(1);
			LinkedList<Country> cou=playerSet.get(player).getCountryList();
			cou.add(countries.get(ans.get(i)));
			playerSet.get(player).setCountryList(cou);

		}
		System.out.println(" finish updating");
		setChanged();
		notifyObservers();
	}
//


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
	 * This method is used to justify can fortification or not.
	 *
	 * @param player Player's name.
	 * @return true,if can fortification,otherwise is false.
	 */
	public boolean canFortification(String player){
		boolean flag=false;
		LinkedList<Country> playerCountry=playerSet.get(player).getCountryList();
		for(int i=0;i<playerCountry.size();i++){
			if(countries.get(String.valueOf(playerCountry.get(i).getName())).getArmy()>1){
				flag=true;
			}
		}
		return flag;
	}
	/**
	 * This method a valid fortification.
	 *
	 * @param from Starting country.
	 * @param to   Target country.
	 * @param move The number of armies to be moved.
	 * @param mode The fortification mode to be chosen.
	 */
	public void Fortification(String from, String to, int move,String mode) {



		if(mode.equalsIgnoreCase("human")) {
			System.out.println("InitializePhase human Fortificaiton");
			String player="";
			for (String players : playerSet.keySet()) {
				if (playerSet.get(players).getColor().equals(countries.get(from).getColor())) {
					player = players;
					break;
				}
			}
			Country fromc = countries.get(from);
			Country toc = countries.get(to);

			playerSet.get(player).fortification(fromc, toc, move, countries);
		}else if(mode.equalsIgnoreCase("aggressive")){
			if(playerSet.get(from).getCountryList().size()<2){
				System.out.println("The "+ from+ " countries are small than 2." +
						" No need to fortificaiton");
			}else {

				System.out.println("InitializePhase no human Fortificaiton");
				String s = playerSet.get(from).fortification(countries.get(0), countries.get(1), move, countries);
				System.out.println("////////////// get fortification of result in InitializePhase ////////////");
				System.out.println("get result of fortification no human, result is : " + s);
				String[] all = s.split(" ");
				System.out.println(" The result length is " + all.length);
				if (all.length == 2) {
					Country one = countries.get(all[0]);       //最符合要兵条件的国家
					Country two = countries.get(all[1]);        //兵多的国家
					if (two.getArmy() > 1) {
						updateFortification(one, two, from);
					} else {
						System.out.println("The second country army no more than 1");
					}
				} else {
					System.out.println(" Cannot find satisfied two country in InitializePhase no human Fortification");
				}
			}

		}else if(mode.equalsIgnoreCase("benevolent")){
			if(playerSet.get(from).getCountryList().size()<2){
				System.out.println("The "+ from+ " countries are small than 2." +
						" No need to fortificaiton");
			}else {
				System.out.println("InitializePhase Benevolent Fortificaiton");
				String s = playerSet.get(from).fortification(countries.get(0), countries.get(1), move, countries);
				System.out.println("////////////// get fortification of result in InitializePhase ////////////");
				System.out.println("get result of fortification no human, result is : " + s);
			}
		}else if(mode.equalsIgnoreCase("random")){
			if(playerSet.get(from).getCountryList().size()<2){
				System.out.println("The "+ from+ " countries are small than 2." +
						" No need to fortificaiton");
			}else {
				System.out.println("InitializePhase Random Fortificaiton");
				String s = playerSet.get(from).fortification(countries.get(0), countries.get(1), move, countries);
				System.out.println("////////////// get fortification of result in InitializePhase ////////////");
				System.out.println("get result of fortification no human, result is : " + s);
			}
		}else if(mode.equalsIgnoreCase("cheater")){
			if(playerSet.get(from).getCountryList().size()<2){
				System.out.println("The "+ from+ " countries are small than 2." +
						" No need to fortificaiton");
			}else {
				System.out.println("InitializePhase cheater Fortificaiton");
				String s = playerSet.get(from).fortification(countries.get(0), countries.get(1), move, countries);
				System.out.println("////////////// get fortification of result in InitializePhase ////////////");
				System.out.println("get result of fortification no human, result is : " + s);
			}
		}

		setChanged();
		notifyObservers(this);
	}

	/**
	 * This method is to fortificate armies from country one to country two.
	 *
	 * @param one The country which armies moving out.
	 * @param two The country which armies moving in.
	 * @param player The name of the player.
	 */

	public void updateFortification(Country one, Country two,String player){

        System.out.println("************* Begin of updateFortification in InitializePhase" +
                "*********************");
        int first=one.getArmy()+two.getArmy()-1;
        System.out.println("There are :"+first +" can move ");
        int army=1;

		countries.get(String.valueOf(one.getName())).setArmy(first);
		countries.get(String.valueOf(two.getName())).setArmy(army);
		int len=playerSet.get(player).getCountryList().size();
		for(int i=0;i<len;i++){
			if(playerSet.get(player).getCountryList().get(i).getName()==one.getName()){
				playerSet.get(player).getCountryList().get(i).setArmy(first);
                System.out.println("Update in playSet for"+one.getName());
            }

			if(playerSet.get(player).getCountryList().get(i).getName()==two.getName()){
				playerSet.get(player).getCountryList().get(i).setArmy(army);
                System.out.println("Update in playSet for "+two.getName());
			}
		}

        System.out.println("done update fortificatio in player from:");
			System.out.println("country one : "+ one.getName()+
					" get amry from country : " +two.getName()+" For " + first);

			System.out.println("country one: "+ one.getName()
					+" army: "+countries.get(String.valueOf(one.getName())).getArmy());

			System.out.println("country two: "+
					two.getName()+" army: "+countries.get(String.valueOf(two.getName())).getArmy());
			System.out.println();
			System.out.println();
		}




	/**
     * This method is to earnCard.
     *
     * @param player Current player.
	 * @param mode The mode to be chosen.
	 * @return The card type earned.
     */
	public String earnCard(String player,String mode) {
		int card = (int) (1 + Math.random() * 3);
		Card c = new Card();
		LinkedList<Card> list = new LinkedList<>();
		String s="";
		switch (card) {
		case 1:

			// infantry
			c.setName("i");
			s="infantry";
			list = playerSet.get(player).getCardList();
			list.add(c);
			playerSet.get(player).setCardList(list);
			System.out.println("you got infantry card");
			if(mode.equalsIgnoreCase("human")) {
				JOptionPane.showMessageDialog(null,
						"you got infantry card");
			}
			break;
		case 2:

			// cavalry
			c.setName("c");
			s="cavalry";
			list = playerSet.get(player).getCardList();
			list.add(c);

			playerSet.get(player).setCardList(list);
			System.out.println("You got cavalry card");
			if(mode.equalsIgnoreCase("human")) {
				JOptionPane.showMessageDialog(null,
						"you got cavalry card");
			}
			break;
		case 3:

			// artillery
			c.setName("a");
			list = playerSet.get(player).getCardList();
			list.add(c);
			s="artillery";

			playerSet.get(player).setCardList(list);
			System.out.println("you got artillery card");
			if(mode.equalsIgnoreCase("human")) {
				JOptionPane.showMessageDialog(null,
						"you got artillery card");
			}
			break;

		}


		setChanged();
		notifyObservers(this);
		return s;
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
		Iterator<Entry<String, Country>> iterator = countries.entrySet().iterator();

		// build graph
		while (iterator.hasNext()) {
			Entry<String, Country> entry = iterator.next();
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
	 * The method is to change card automatically.
	 *
	 * @param player The name of the player.
	 * @return The result of cards gotten.
	 */
	public LinkedList<String> autoChangeCard(String player){

		LinkedList<String> ans=new LinkedList <>();
		if(playerSet.get(player).getCardList().size()<3){
			System.out.println("Card smaller than 3 ");
			return ans;
		}
		System.out.println("Card list larger than 3");
		int time=playerSet.get(player).getChangeCardTime();
		HashMap<String,Integer> store=new HashMap <>();
		for(int i=0;i<playerSet.get(player).getCardList().size();i++){
			store.put(playerSet.get(player).getCardList().get(i).getName(),
					store.getOrDefault(playerSet.get(player).getCardList().get(i).getName(), 0) + 1);
		}

		int curArmy=0;
		String curdelete="";
		while(playerSet.get(player).getCardList().size()>4) {
			curdelete="";
			if(store.size()>2) {
				deleteCard(playerSet.get(player),"i","c","a");
				curdelete="i c a";
				System.out.println("current delete is "+curdelete);
			}else{
				for(String key : store.keySet()){
					if(store.get(key)>=3){
						deleteCard(playerSet.get(player),key,key,key);
						curdelete=key+" "+key+" "+key;
						System.out.println("current delete is "+curdelete);
					}
				}
			}
			curArmy += time *5;
			time++;
			ans.add(curdelete);
		}
		int total=curArmy+playerSet.get(player).getArmy();
		playerSet.get(player).setArmy(total);
		playerSet.get(player).setChangeCardTime(time);

		setChanged();
		notifyObservers(this);
		return ans;


	}

	/**
	 * The method is to delete card when the player has one of three types of cards.
	 *
	 * @param player The name of player.
	 * @param one The card type one.
	 * @param two The card type two.
	 * @param three The card type three.
	 */
	public void deleteCard(Player player,String one,
						   String two, String three ){
		for (int i = 0; i < player.getCardList().size(); i++) {
			if (player.getCardList().get(i).getName().equals(one)) {
				player.getCardList().remove(i);
				break;
			}
		}
		for(int i=0;i<player.getCardList().size();i++) {
			if (player.getCardList().get(i).getName().equals(two)) {
				player.getCardList().remove(i);
				break;
			}
		}
		for(int i=0;i<player.getCardList().size();i++){
			if (player.getCardList().get(i).getName().equals(three)) {
				player.getCardList().remove(i);
				break;
			}

		}

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

	/**
	 * This method is to transfer armies after attacking.
	 *
	 * @param record A record to store different result of attacking.
	 * @param att The attacking country.
	 * @param def The defending country.
	 */

	public void Transfer(String record, Country att, Country def) {
		boolean flag=false;
		String[] readrecord = record.split(" ");
		if (readrecord[1].equals("0")) {//update countries information

			if (readrecord[0].equals(att.getName())) {
				System.out.println( "attacker Player"+ att.getName()+" win");
				if (!readrecord[2].equals("0")) {
					flag = true;

				}

			}
			else if (readrecord[0].equals("-1")) {

				System.out.println("This is a draw.");
			}
			else {

				System.out.println( "defender Player"+ def.getName()+" win");
			}

		}

		int min=Integer.valueOf(readrecord[1]);

		Fortification(String.valueOf(att.getName()),String.valueOf(def.getName()),min,"human");

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
class ColorList implements Serializable{

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