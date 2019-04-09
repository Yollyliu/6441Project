package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.util.HashMap;
import java.util.LinkedList;

public class benevolentPlayer implements Strategy{

    String behavior="";
    Player player;

    public benevolentPlayer(Player player){
        this.player=player;
        this.behavior="Human";
    }

    @Override
    public String Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries,
                                HashMap<String, Continent> continents) {

        return "";
    }

    @Override
    public LinkedList<String> Attack(String attacker, String defender, String mode, int attDices,
                       int defDices, HashMap<String, Player> playerSet,
                       HashMap<String, Country> countries,
                       HashMap<String, Continent> continents) {

        LinkedList<String> ans=new LinkedList <>();
        return ans;
    }

    @Override
    public String Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries) {
        return "";

    }
}
