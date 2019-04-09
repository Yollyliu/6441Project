package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.text.Collator;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;

public class aggressivePlayer  implements Strategy{

    String behavior="";
    Player player;

    public aggressivePlayer(Player player){
        this.player=player;
        this.behavior="Agressive";
    }
    @Override
    public void Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries) {
        System.out.println(" hello, we are in human player reinforcement");
        player.getAllArmies();

        Collections.sort(player.getCountryList(), new Comparator <Country>() {
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
        System.out.println(" hello, we are in human player attack");

        LinkedList<String> ans=new LinkedList <>();
        Country strongest=
                player.getCountryList().stream().max(
                        Comparator.comparing(Country::getArmy)).get();

        LinkedList<Country> allDefenders=new LinkedList <>();

        String[] neib=strongest.getCountryList().split(" ");
        for(int i=0;i<neib.length;i++){
            if(!countries.get(neib[i]).getColor().equals(player.getColor())){
                allDefenders.add(countries.get(neib[i]));
            }
        }

        if(allDefenders.size()>0) {
            for (int i = 0; i < allDefenders.size(); i++) {
                if (strongest.getArmy() >= 2) {
                    String aggressiveAttack = String.valueOf(strongest.getName());
                    String aggressiveDefend = String.valueOf(allDefenders.get(i).getName());
                    String cur = player.attackPhase(aggressiveAttack, aggressiveDefend, "All_Out",
                            0, 0, playerSet, countries, continents);
                    ans.add(i, cur);
                }
            }

            return ans.get(0);
        }else{
            return "false";
        }

    }

    @Override
    public void Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries) {

        System.out.println(" hello, we are in human player fortification");

        Collections.sort(player.getCountryList(), new Comparator <Country>() {
            @Override
            public int compare(Country o1, Country o2) {
                return Collator.getInstance().compare(o1.getArmy(), o2.getArmy());
            }
        } );

        HashMap<Integer,Integer> frontNumb=new HashMap <>();
        frontNumb=player.Front(countries);
        int length=player.getCountryList().size();

        for(int i=0;i<length;i++) {
            Country one = player.getCountryList().get(i);

            if (frontNumb.containsKey(one.getName())) {

                for (int j = 0; j < length; j++) {

                    if (i != j) {

                        Country two = player.getCountryList().get(j);

                        if (player.canTransfer(one.getName(), two.getName(), countries)) {
                            player.updateFortification(one, two, countries);
                            return;
                        }
                    }
                }
            }
        }
    }



}
