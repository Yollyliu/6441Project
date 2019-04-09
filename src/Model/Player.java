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


	private int attDices;
	private int defDices;
	private Country attackCountry;
	private Country defendCountry;


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

	public int getAttDices() {
		return attDices;
	}

	public void setAttDices(int attDices) {
		this.attDices = attDices;
	}

	public int getDefDices() {
		return defDices;
	}

	public void setDefDices(int defDices) {
		this.defDices = defDices;
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


	public String attack(String attacker, String defender, String mode,
					   int attDices, int defDices,
					   HashMap<String, Player> playerSet,
					   HashMap<String, Country> countries,
					   HashMap<String, Continent> continents){
		System.out.println("we are at attack in player");

		String result=this.strategy.Attack(attacker, defender, mode,
				attDices, defDices,playerSet,countries,continents);
		return result;
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



}
