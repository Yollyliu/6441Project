package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class benevolentPlayer implements Strategy{

    String behavior = "";
    Player player;

    public benevolentPlayer(Player player){
        this.player = player;
        this.behavior = "Benevolent";
    }

    @Override
    public void Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries) {
        System.out.println(" hello, we are in benevolent player reinforcement");
        player.getAllArmies();

        Collections.sort(player.getCountryList(), new Comparator<Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return Collator.getInstance().compare(o1.getArmy(), o2.getArmy());
            }
        } );

        Country strongest=player.getCountryList().getFirst();
        System.out.println("Strongest country: "+strongest.getName()+
                " Army: "+player.getArmy());
        int totalArmy=player.getArmy();
        player.updateReinforcement(strongest.getName(),
                totalArmy,playerSet,countries);


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
