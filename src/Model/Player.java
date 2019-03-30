package Model;

import java.awt.*;
import java.util.LinkedList;

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
	public Player(String playerName) {
		this.playerName = playerName;
		this.color = null;
		this.army = 0;

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
}
