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
	private String mode="";




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
		this.mode=behavoir;

		this.strategy= strategyFactory.getBehavior(behavoir,this);
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
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

	public String reinforcement(HashMap<String, Player> playerSet,
			HashMap<String, Country> countries) {

		String c=this.strategy.Reinforcement(playerSet,countries);
		return c;
	}


	public void getAllArmies() {

		// update armies number for each player
		int system = SystemArmy();
		int continent = ContinentArmy();
		int card = 0;
		setArmy(system + continent + card);
	}
	/**
	 * This method calculating the number of reinforcement armies.
	 *
	 * @return The number of reinforcement armies.
	 */
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

	public void updateReinforcement(int country, int army,
						   HashMap<String, Player> playerSet,
						   HashMap<String, Country> countries) {


		for(int i=0;i<countryList.size();i++){
			if(countryList.get(i).getName()==country){
				countryList.get(i).setArmy(army);
				System.out.println("countrylist army is "+countryList.get(i).getArmy());
				break;
			}

		}
		this.setArmy(0);

		playerSet.get(this.playerName).setArmy(0);
		countries.get(String.valueOf(country)).setArmy(army);
		System.out.println("countries army is "+countries.get(String.valueOf(country)).getArmy());
		System.out.println("updateReinforcement done well");
		System.out.println();
		System.out.println();

	}


	/**
	 * This method judges a play whether occupies a continent or not. If yes, then
	 * player get control value, otherwise.
	 *
	 * @return Control value.
	 */
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


	public LinkedList<String> attack(String attacker, String defender, String mode,
					   int attDices, int defDices,
					   HashMap<String, Player> playerSet,
					   HashMap<String, Country> countries,
					   HashMap<String, Continent> continents){
		System.out.println("we are at attack in player");

		LinkedList<String> ans=new LinkedList <>();
		ans=this.strategy.Attack(attacker, defender, mode,
				attDices, defDices,playerSet,countries,continents);
		return ans;
	}

	public String attackPhase(String attacker, String defender, String mode,
							  int attDices, int defDices,
							  HashMap<String, Player> playerSet,
							  HashMap<String, Country> countries,
							  HashMap<String, Continent> continents) {

		Attack attack = new Attack(countries, continents, playerSet, attacker,
				defender, mode, attDices, defDices);

		String result = attack.attacking(this.mode);// invoking attacking function

		if (result != "") {
			return result;

		} else {
			System.out.println("Attack Failure!!!");
		}

		return "Failure";
	}

	////////////////////////  Fortification /////////////////////////
	public String fortification(Country from, Country to, int move,
							  HashMap<String, Country> countries){
		String s=this.strategy.Fortification(from,  to, move,countries);
		return s;

	}

	public boolean canTransfer(int start, int end,
							   HashMap<String, Country> countries) {
		int maxCountry = returnMax(countries);
		FindPath fp = new FindPath(maxCountry);
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
		//System.out.println(allpath);
		boolean result = checkPath(allpath);
		return result;

	}

	/**
	 * The method is to check whether exists a path that can execute transfer method.
	 *
	 * @param Path All the path from start country to destination.
	 * @return true If there is a path that belongs to this player.
	 */
	private boolean checkPath(ArrayList<ArrayList<Integer>> Path) {
		boolean isOwn = false;


		for (int i = 0; i < Path.size(); i++) {
			boolean temp = true;
			for (int j = 0; j < Path.get(i).size(); j++) {
				boolean isMatch = rightcountry(String.valueOf(Path.get(i).get(j)));

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
	 * @param ccountry Current country.
	 * @return true if the player owns the country.
	 */
	public boolean rightcountry(String ccountry) {
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

	//from
	public void updateFortification(Country one, Country two,
									HashMap<String, Country> countries){

		int first=one.getArmy()+two.getArmy()-1;

		int army=two.getArmy()-1;
		for(int k=0;k<getCountryList().size();k++) {

			if (getCountryList().get(k).getName() == one.getName()) {

				one.setArmy(first);
			}
			if (getCountryList().get(k).getName() == two.getName()) {
				two.setArmy(1);
			}
		}
		for(String key:countries.keySet()) {
			if (Integer.valueOf(key)==one.getName()) {
				countries.get(key).setArmy(first);
			}
			if (Integer.valueOf(key)==two.getName()) {
				countries.get(key).setArmy(1);
			}
		}

		System.out.println("done update fortificatio in player from:");
		System.out.println("country one : "+ one.getName()+
				" get amry from country : " +two.getName()+" For " +army);
		System.out.println("country one: "+ one.getName()+" army: "+one.getArmy());
		System.out.println("country two: "+two.getName()+" army: "+two.getArmy());
		System.out.println();
		System.out.println();

	}


	public void agrressiveStartUp(HashMap<String, Country> countries){
		System.out.println("we are in aggressive");
		HashMap<Integer,Integer> frontNum=new HashMap <>();
		frontNum=Front(countries);
		int max=-999;
		String flag="";
		for(String key:countries.keySet()){
			int cur=countries.get(key).getArmy();
			if(max<cur) {
				max = cur;
				flag=key;
			}
		}
		int previous=countries.get(flag).getArmy();
		countries.get(flag).setArmy(this.getArmy()+previous);
		this.setArmy(0);

	}

	public HashMap<Integer,Integer> Front(
			HashMap<String, Country> countries){

		HashMap<Integer,Integer> frontNum=new HashMap <>();
		for(int i=0;i<this.getCountryList().size();i++){  //一个国家一个国家的来
			String[] neib= this.getCountryList().get(i).getCountryList().split(" ");
			int numb=0;
			for(int j=0;j<neib.length;j++){
				if(! countries.get(neib[j]).getColor().equals(this.getColor())){
					numb++;
				}
			}
			if(numb!=0){
				frontNum.put(this.getCountryList().get(i).getName(),numb);
			}
		}
		return frontNum;

	}

	public void transfer(String record, Country att, Country def,
						 HashMap<String, Country> countries) {
		boolean flag=false;
		System.out.println("*************** In transfer of Player ************");
		String[] readrecord = record.split(" ");
		if (readrecord[1].equals("0")) {//update countries information

			if (readrecord[0].equals(att.getName())) {
				System.out.println( "attacker Player "+ att.getName()+" win");
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
//		int min=Integer.valueOf(readrecord[1]);
//		System.out.println(" Army need to transfer in Player is "+ min);
//
//		int afterdef=min;
//		int aftatt=att.getArmy()-min;
		int afterdef=Integer.valueOf(readrecord[2]);
		int aftatt=Integer.valueOf(readrecord[0]);
		for(int k=0;k<getCountryList().size();k++) {

			if (getCountryList().get(k).getName() == def.getName()) {
				def.setArmy(afterdef);
				System.out.println("After transfer: def: "+ def.getName()+"  Army: "+def.getArmy());
			}
			if (getCountryList().get(k).getName() == att.getName()) {
				att.setArmy(aftatt);
				System.out.println("After transfer: att: "+ att.getName()+"  Army: "+att.getArmy());

			}
		}
		for(String key:countries.keySet()) {
			if (Integer.valueOf(key)==att.getName()) {
				countries.get(key).setArmy(aftatt);
			}
			if (Integer.valueOf(key)==def.getName()) {
				countries.get(key).setArmy(afterdef);
			}
		}

	}

}
