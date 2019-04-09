package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.util.HashMap;

public class randomPlayer implements Strategy{

    String behavior="";
    Player player;

    public randomPlayer(Player player){
        this.behavior="Random";
        this.player=player;
    }


    @Override
    public void Reinforcement() {

    }

    @Override
    public void Attack(String attacker, String defender, String mode, int attDices,
                       int defDices, HashMap<String, Player> playerSet,
                       HashMap<String, Country> countries,
                       HashMap<String, Continent> continents){

    }

    @Override
    public void Fortification(Country from, Country to, int move) {

    }
}
