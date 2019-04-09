package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.util.HashMap;

public class cheaterPlayer implements Strategy{
    String behavior="";
    Player player;

    public cheaterPlayer(Player player){
        this.player=player;
        this.behavior="Human";
    }

    @Override
    public void Reinforcement() {

    }

    @Override
    public String Attack(String attacker, String defender, String mode, int attDices,
                       int defDices, HashMap<String, Player> playerSet,
                       HashMap<String, Country> countries,
                       HashMap<String, Continent> continents) {
        return "";

    }

    @Override
    public void Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries) {

    }
}
