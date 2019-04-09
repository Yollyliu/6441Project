package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * <h1>Strategy</h1>
 * The Strategy interface gives the three phrases methods(reinforcement, attack, fortification).
 *
 * @author tianshu_ji
 * @version 3.0
 * @since 2019-04-07
 */
public interface Strategy {

    /**
     * This interface method is to reinforcement in general.
     *
     * @param playerSet a hash map stores all players.
     * @param countries a hash map stores all countries.
     * @param continents a hash map stores all continents.
     * @return the information of armies and country.
     */
    public String Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries,
                                HashMap<String, Continent> continents);

    /**
     * This interface method is to attack in general.
     *
     * @param attacker the name of attacker.
     * @param defender the name of defender.
     * @param mode the name of strategy.
     * @param attDices the number of dices attacker chosen.
     * @param defDices the number of dices defender chosen.
     * @param playerSet a hash map stores all players.
     * @param countries a hash map stores all countries.
     * @param continents a hash map stores all continents.
     * @return the result of attacking.
     */
    public LinkedList<String> Attack(String attacker, String defender, String mode, int attDices,
                                     int defDices, HashMap<String, Player> playerSet,
                                     HashMap<String, Country> countries,
                                     HashMap<String, Continent> continents);

    /**
     * This interface method is for fortification in general.
     *
     * @param from the country moving out armies.
     * @param to the country moving in armies.
     * @param move the number of armies to move.
     * @param countries a hash mao stores all contries.
     * @return the result ofn fortification.
     */
    public String Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries);
}
