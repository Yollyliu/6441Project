package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class randomPlayer implements Strategy{

    String behavior="";
    Player player;

    public randomPlayer(Player player){
        this.behavior="Random";
        this.player=player;
    }


    @Override
    public String Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries,
                                HashMap<String, Continent> continents)
            {

        return "";

    }

    @Override
    public LinkedList<String> Attack(String attacker, String defender, String mode, int attDices,
                                     int defDices, HashMap<String, Player> playerSet,
                                     HashMap<String, Country> countries,
                                     HashMap<String, Continent> continents){

        Country strongest=
                player.getCountryList().stream().max(
                        Comparator.comparing(Country::getArmy)).get();

        String neib=strongest.getCountryList();



        LinkedList<String> ans=new LinkedList <>();


        return ans;

    }

    @Override
    public String Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries) {

        String s="";
        return s;
    }
}
