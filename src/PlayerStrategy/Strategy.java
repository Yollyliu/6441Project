package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.util.HashMap;
import java.util.LinkedList;

public interface Strategy {

    public String Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries) ;
    public LinkedList<String> Attack(String attacker, String defender, String mode, int attDices,
                                     int defDices, HashMap<String, Player> playerSet,
                                     HashMap<String, Country> countries,
                                     HashMap<String, Continent> continents);
    public String Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries);
}
