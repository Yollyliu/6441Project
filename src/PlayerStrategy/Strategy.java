package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.util.HashMap;

public interface Strategy {

    public void Reinforcement();
    public String Attack(String attacker, String defender, String mode, int attDices,
                       int defDices, HashMap<String, Player> playerSet,
                       HashMap<String, Country> countries,
                       HashMap<String, Continent> continents);
    public void Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries);
}
