package Model;

import java.awt.*;
import java.util.*;

/**
 * <h1>Attack</h1> 
 * The Attack class implements attack phase operation.
 *
 * @author jiamin_he
 * @version 3.0
 * @since 2019-03-08
 */
public class Attack {

	private HashMap<String, Country> countries;
	private HashMap<String, Continent> continents;
	private HashMap<String, Player> playerSet;
	private String attackCountry;
	private String defendCountry;
	private String mode;
	private int attDices;
	private int defDices;
	private static boolean HAS_CARD = false;
	public static boolean WIN = false;

	/**
	 * This is a no-argument constructor.
	 */
	public Attack() {
	}

	/**
	 * This is Attack constructor initializing variables.
	 *
	 * @param countries     A hash map stores all countries.
	 * @param continents    A hash map stores all continents.
	 * @param playerSet     A hash map stores all players.
	 * @param attackCountry The attack country's name.
	 * @param defendCountry The defend country's name.
	 * @param mode          The attack mode which player chooses.
	 * @param attDices      The number of dices attacker chooses.
	 * @param defDices      The number of dices defender chooses.
	 */
	public Attack(HashMap<String, Country> countries, HashMap<String, Continent> continents,
			HashMap<String, Player> playerSet, String attackCountry,
				  String defendCountry, String mode, int attDices,
			int defDices) {
		this.countries = countries;
		this.continents = continents;
		this.playerSet = playerSet;
		this.attackCountry = attackCountry;
		this.defendCountry = defendCountry;
		this.mode = mode;
		this.attDices = attDices;
		this.defDices = defDices;
	}

	/**
	 * This is Attack constructor initializing variables.
	 * 
	 * @param countries     A hash map stores all countries.
	 * @param continents    A hash map stores all continents.
	 * @param playerSet     A hash map stores all players.
	 * @param attackCountry The attack country's name.
	 * @param defendCountry The defend country's name.
	 */
	public Attack(HashMap<String, Country> countries, HashMap<String, Continent> continents,
			HashMap<String, Player> playerSet, String attackCountry, String defendCountry) {
		this.countries = countries;
		this.continents = continents;
		this.playerSet = playerSet;
		this.attackCountry = attackCountry;
		this.defendCountry = defendCountry;
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
	 * This method is to get playerSet in hash map.
	 * 
	 * @return playerSet  It is a hash map.
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
	 * This method obtains attack country's name.
	 *
	 * @return Attack country's name.
	 */
	public String getAttackCountry() {
		return attackCountry;
	}

	/**
	 * This method modifies attack country's name.
	 *
	 * @param attackCountry A new attack country's name.
	 */
	public void setAttackCountry(String attackCountry) {
		this.attackCountry = attackCountry;
	}

	/**
	 * This method obtains defended country's name.
	 *
	 * @return Defended country's name.
	 */
	public String getDefendCountry() {
		return defendCountry;
	}

	/**
	 * This method modifies defended country's name.
	 *
	 * @param defendCountry A new defender country's name.
	 */
	public void setDefendCountry(String defendCountry) {
		this.defendCountry = defendCountry;
	}

	/**
	 * This method obtains current attack mode.
	 *
	 * @return Current attack mode.
	 */
	public String getMode() {
		return mode;
	}

	/**
	 * This method modifies attack mode.
	 *
	 * @param mode A new attack mode.
	 */
	public void setMode(String mode) {
		this.mode = mode;
	}

	/**
	 * This method obtains the number of dices attacker chooses.
	 *
	 * @return The number of dices attacker chooses.
	 */
	public int getAttDices() {
		return attDices;
	}

	/**
	 * The method modifies the number of dices attacker chooses.
	 *
	 * @param attDices The number of dices attacker chooses.
	 */
	public void setAttDices(int attDices) {
		this.attDices = attDices;
	}

	/**
	 * This method obtains the number of dices defender chooses.
	 *
	 * @return The number of dices defender chooses.
	 */
	public int getDefDices() {
		return defDices;
	}

	/**
	 * The method modifies the number of dices defender chooses.
	 *
	 * @param defDices The number of dices defender chooses.
	 */
	public void setDefDices(int defDices) {
		this.defDices = defDices;
	}

	/**
	 * This method obtains a signal whether attacker get a risk card or not.
	 *
	 * @return A signal whether attacker get a risk card or not.
	 */
	public static boolean isHasCard() {
		return HAS_CARD;
	}

	/**
	 * This method modifies a signal whether attacker get a risk card or not.
	 *
	 * @param hasCard A signal whether attacker get a risk card or not.
	 */
	public static void setHasCard(boolean hasCard) {
		HAS_CARD = hasCard;
	}

	/**
	 * This method obtains a signal whether attacker wins the game.
	 *
	 * @return A signal whether attacker wins the game.
	 */
	public static boolean isWIN() {
		return WIN;
	}

	/**
	 * This method implements according different mode invoking different attack
	 * function.
	 *
	 * @return A result of attack.
	 */
	public String attacking(String behavioir) {

		switch (mode) {
		case "All_Out":
			System.out.println("Get into All_Out mode.");
			return allOut(behavioir);
		case "One_Time":
			System.out.println("Get into One_Time mode.");
			return oneTime();
		default:
			System.out.println("Mode function invoking Failure");
			break;
		}
		System.out.println("This is in Attacking function");
		return "Failure";
	}

	/**
	 * This method implements finding a player who owns current country.
	 *
	 * @param countryName Current country name.
	 * @return The player who owns current country.
	 */
	public Player findPlayer(String countryName) {

		Country country = countries.get(countryName);
		Color color = country.getColor();
		for (Map.Entry<String, Player> entry : playerSet.entrySet()) {
			if (entry.getValue().getColor().equals(color)) {
				return entry.getValue();
			}
		}
		System.out.println("Cannot find the player!!!");
		return null;
	}

	/**
	 * This method implements one time attack.
	 *
	 * @return A result of one time attack.
	 */
	public String oneTime() {

		System.out.println("This is an " + this.mode + "mode: ");
		String result = "";

//      getting two rolling dice result
		LinkedList<Integer> attDicesList = rollDices(attDices);
		LinkedList<Integer> defDicesList = rollDices(defDices);
		
		System.out.println("Result rolling dices:\n" + "attDices: " + attDicesList.toString() + "\n" + "defDices: "
				+ defDicesList.toString());

		int attArmies = countries.get(attackCountry).getArmy();
		int defArmies = countries.get(defendCountry).getArmy();

		fightingPhase(attDicesList, defDicesList);// two countries are fighting

		result = findWinner(attArmies, defArmies);// find one time winner

		int[] range = intervalNum();

		// verification
		if (countries.get(defendCountry).getArmy() == 0) {
			updating(range);
		}

		result = result + " " + String.valueOf(range[0]) + " " + String.valueOf(range[1]);

		System.out.println(this.mode + " attack finished!");
		System.out.println(result);

		return result;
	}

	/**
	 * This method implements all out attack.
	 *
	 * @return A result of all out attack.
	 */
	public String allOut(String behavoir) {

		System.out.println("This is an " + this.mode + "mode: ");
		String result = "";

		while ((countries.get(attackCountry).getArmy() != 1) && (countries.get(defendCountry).getArmy() != 0)) {
			this.attDices = findDicesNum()[0];
			this.defDices = findDicesNum()[1];

			LinkedList<Integer> attDicesList = rollDices(attDices);
			LinkedList<Integer> defDicesList = rollDices(defDices);

			System.out.println("Result rolling dices:\n" + "attDices: " + attDicesList.toString() + "\n" + "defDices: "
					+ defDicesList.toString());

			fightingPhase(attDicesList, defDicesList);
		}

		result = findWinner(0, 0);

		int [] range=intervalNum();
//		if(!behavoir.equals("Human")) {
//		  range[0]=intervalNum()[0];
//		  range[1]=1;
//
//		}else {
//			 range[0] = intervalNum()[0];
//			 range[1]=intervalNum()[1];
//		}


		if (countries.get(defendCountry).getArmy() == 0) {
			updating(range);
		}

		result = result + " " + String.valueOf(range[0]) + " " + String.valueOf(range[1]);

		System.out.println(this.mode + " attack finished!");
		System.out.println(result);
		return result;

	}

	/**
	 * This method implements that after defended country was captured, transfers
	 * armies from attack country to defended country after defended country.
	 *
	 * @param armies The number of armies to be transferred.
	 */
	public void transferArmy(int armies) { // transfer armies from attacker to defender

		System.out.println(
				"Before transfer: " + "\n" + "Current attacker armies: " + countries.get(attackCountry).getArmy() + "\n"
						+ "Current capture country's armies: " + countries.get(defendCountry).getArmy());

		int currentAtt = countries.get(attackCountry).getArmy();
		int currentDef = countries.get(defendCountry).getArmy();

		countries.get(defendCountry).setArmy(currentDef + armies);
		countries.get(attackCountry).setArmy(currentAtt - armies);

		System.out.println(
				"Finished transfer: " + "\n" + "Current attacker armies: " + countries.get(attackCountry).getArmy()
						+ "\n" + "Current capture country's armies: " + countries.get(defendCountry).getArmy());
	}

	/**
	 * The method implements rolling dice operation.
	 *
	 * @param dices The number of dices.
	 * @return The result of rolling dices.
	 */
	private LinkedList<Integer> rollDices(int dices) {
		LinkedList<Integer> diceList = new LinkedList<>();
		int tmp = dices;
		while (tmp != 0) {
			int dic = (int) (Math.random() * 6 + 1);
			diceList.add(dic);
			--tmp;
		}
		return diceList;
	}

	/**
	 * This method implements fighting phase operation.
	 *
	 * @param attDicesList The result of rolling dices of attacker.
	 * @param defDicesList The result of rolling dices of defender.
	 */
	private void fightingPhase(LinkedList<Integer> attDicesList, LinkedList<Integer> defDicesList) {
		int time = defDices;

		while (time != 0) {

			int att = Collections.max(attDicesList);
			int def = Collections.max(defDicesList);

			if (att > def) {// attacker win, then defender -1 army, update data

				countries.get(defendCountry).setArmy(countries.get(defendCountry).getArmy() - 1);
				System.out.println("Defender -1 army! " + "Defender armies: " + countries.get(defendCountry).getArmy());

			} else {// defender win, then attacker -1 army, update data

				countries.get(attackCountry).setArmy(countries.get(attackCountry).getArmy() - 1);
				System.out.println("Attacker -1 army! " + "Attacker armies: " + countries.get(attackCountry).getArmy());
			}

			attDicesList.remove((Integer) att);
			defDicesList.remove((Integer) def);
			--time;
		}

	}

	/**
	 * This method implements finding the number of dice in the all out mode.
	 *
	 * @return The number of dices which is chosen by attacker and defender.
	 */
	private int[] findDicesNum() {
		int[] dicsNum = { 0, 0 };

		Country att = countries.get(attackCountry);
		Country def = countries.get(defendCountry);

//      finding the number of dices of attacker
		if (att.getArmy() > 3) {
			dicsNum[0] = 3;
		} else if (att.getArmy() == 3) {
			dicsNum[0] = 2;
		} else if (att.getArmy() == 2) {
			dicsNum[0] = 1;
		}

//      finding the number of dices of defender
		if (def.getArmy() >= 2) {
			dicsNum[1] = 2;
			if (dicsNum[1] > dicsNum[0]) {
				dicsNum[1] = dicsNum[0];
			}
		} else if (def.getArmy() == 1) {
			dicsNum[1] = 1;
		}

		for (int i : dicsNum) {
			if (i == 0) {
				System.out.println(" Exist a zero dices");
			}
		}

		return dicsNum;
	}

	/**
	 * This method implements finding who is the winner in current attack phase.
	 *
	 * @param att The number of armies of attacker before attacking.
	 * @param def The number of armies of defender before attacking.
	 * @return Winner name.
	 */
	private String findWinner(int att, int def) {

		switch (this.mode) {
		case "One_Time":
			System.out.println("Armies compare" + att + " " + def + " " + countries.get(attackCountry).getArmy() + " "
					+ countries.get(defendCountry).getArmy());
			if (countries.get(defendCountry).getArmy() == 0) {
				System.out.println("Winner is attacker.");
				return findPlayer(attackCountry).getPlayerName();
			} else {// modify
				int a = att - countries.get(attackCountry).getArmy();
				int d = def - countries.get(defendCountry).getArmy();

				if (a == d) {
					System.out.println("Draw");
					return "-1";
				} else if (a > d) {
					System.out.println("Winner is defender.");
					return findPlayer(defendCountry).getPlayerName();
				} else {
					System.out.println("Winner is attacker.");
					return findPlayer(attackCountry).getPlayerName();
				}
			}
		case "All_Out":
			if (countries.get(defendCountry).getArmy() == 0) {
				return findPlayer(attackCountry).getPlayerName();
			} else if (countries.get(attackCountry).getArmy() == 1) {
				return findPlayer(defendCountry).getPlayerName();
			}
		default:
			System.out.println("Find winner failure!");
			break;
		}

		return null;
	}

	/**
	 * This method implements the interval number of armies that attack country can
	 * transfer.
	 *
	 * @return The interval number of armies that attack country can transfer.
	 */
	private int[] intervalNum() {

		int[] range = { 0, 0 };

		if (countries.get(defendCountry).getArmy() != 0) {
			System.out.println("Range is: " + range[0] + " " + range[1]);
			return range;
		} else {

			if ((countries.get(attackCountry).getArmy() - 1) == this.attDices) {
				range[1] = this.attDices;
			} else if ((countries.get(attackCountry).getArmy() - 1) > this.attDices) {
				range[0] = this.attDices;
				range[1] = countries.get(attackCountry).getArmy() - 1;
				System.out.println("Range is: " + range[0] + " " + range[1]);
				return range;
			}
			System.out.println("Range is: " + range[0] + " " + range[1]);
			return range;
		}
	}

	/**
	 * This method implements that after defended country was captured updating all
	 * data. If rang is [0,X], then the armies transfers automatically from attack
	 * country to defended country.
	 *
	 * @param range The interval number of armies that attack country can transfer.
	 */
	public void updating(int[] range) {

//		attacker can get a card;
		this.HAS_CARD = true;
		Player attPlayer = findPlayer(attackCountry);
		Player defPlayer = findPlayer(defendCountry);
		
//		updating attacker and defender card List
		System.out.println("Player" + attPlayer.getPlayerName()+ " cards: "  + attPlayer.getCardList().size());
		for (Card card : attPlayer.getCardList()) {
			System.out.println(card.getName());
		}
		System.out.println("Player" + defPlayer.getPlayerName()+ " cards: "  + defPlayer.getCardList().size());
		for (Card card : defPlayer.getCardList()) {
			System.out.println(card.getName());
		}
		while (defPlayer.getCountryList().size() == 1) {
			System.out.println("--------------- Update winner card List ---------------");
			LinkedList<Card> cards = attPlayer.getCardList();
			for (Card card : defPlayer.getCardList()) {
				cards.add(card);
			}
			attPlayer.setCardList(cards);
			LinkedList<Card> nullList = defPlayer.getCardList();
			nullList.clear();
			defPlayer.setCardList(nullList);
			break;
		}	
		System.out.println("Player" + attPlayer.getPlayerName()+ " cards: " + attPlayer.getCardList().size());
		for (Card card : attPlayer.getCardList()) {
			System.out.println(card.getName());
		}
		System.out.println( "Player" + defPlayer.getPlayerName()+ " cards: " + defPlayer.getCardList().size());
		for (Card card : defPlayer.getCardList()) {
			System.out.println(card.getName());
		}
	
//      update information, like Player, country
		Country country = countries.get(defendCountry);
		country.setColor(countries.get(attackCountry).getColor());

		LinkedList<Country> list = playerSet.get(attPlayer.getPlayerName()).getCountryList();
		list.add(country);
		playerSet.get(attPlayer.getPlayerName()).setCountryList(list);

		for (Country c : defPlayer.getCountryList()) {// update defender
			if (c.getName() == country.getName()) {
				LinkedList<Country> deff = playerSet.get(defPlayer.getPlayerName()).getCountryList();
				deff.remove(c);
				playerSet.get(defPlayer.getPlayerName()).setCountryList(deff);
				break;
			}
		}

		if (range[0] == 0 && range[1] > 0) {
			transferArmy(range[1]);
		}

		if (defPlayer.getCountryList().size() == 0) {
			playerSet.remove(defPlayer.getPlayerName());
		}

		if (playerSet.size() == 1) {
			this.WIN = true;
		}
	}
}