package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <h1>benevolentPlayer</h1>
 * The benevolentPlayer class is for implement all interface method for the benevolent player.
 *
 * @author tianshu_ji, shou_chi, youlin_liu
 * @version 3.0
 * @since 2019-04-07
 */
public class benevolentPlayer implements Strategy, Serializable {

    String behavior="";
    Player player;

    public benevolentPlayer(Player player){
        this.player=player;
        this.behavior="Benevolent";
    }

    @Override
    public String Reinforcement(HashMap<String, Player> playerSet,
                                      HashMap<String, Country> countries,
                                      HashMap<String, Continent> continents) {
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println("we are in benevolent player reinforcement");
        player.getAllArmies(continents);
        System.out.println(player.getArmy());

        stringBuffer.append(player.getArmy());
        stringBuffer.append(" ");

        System.out.println("weakest country: "+ weekCon(player)+
                " Army: "+ countries.get(String.valueOf(weekCon(player))).getArmy());
        int totalArmy = player.getArmy() + countries.get(String.valueOf(weekCon(player))).getArmy();
        System.out.println("total army is "+totalArmy);
        stringBuffer.append(String.valueOf(weekCon(player)));
        player.setArmy(0);
        player.updateReinforcement(weekCon(player), totalArmy,playerSet,countries);
        System.out.println(stringBuffer);

        return stringBuffer.toString();
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
        System.out.println();
        System.out.println();
        System.out.println(" hello, we are in benevolent player fortification");

        String s = "";
        //国家按兵力从小到大排序
        List<Country> newCountryList = player.getCountryList().stream()
                .sorted((c1, c2) -> {
                    if (c2.getArmy() - c1.getArmy() < 0 ) return 1;
                    else if (c2.getArmy() - c1.getArmy() == 0) return 0;
                    else return -1; })
                .collect(Collectors.toList());
        HashMap<Integer,Integer> front = player.Front(countries);
        int length = newCountryList.size();
        for (int i = 0; i < length; i++) {
            Country one = newCountryList.get(i);
            if(front.containsKey(one.getName())){
                System.out.println("Country "+ one.getName()+ " has neib to another player," +
                        " numb is "+ front.get(one.getName())+" Army is "+one.getArmy());

                for (int j = length - 1; j > i; j--) {
                    if(newCountryList.get(j).getArmy() > 1){
                        Country two = newCountryList.get(j);

                        System.out.println();

                        System.out.println("This is in benevolent Player fortification:");
                        System.out.println("Country one is "+ one.getName()+" Army is "+one.getArmy());
                        System.out.println("Country two is "+two.getName()+ " Army is "+two.getArmy());
                        System.out.println("Path between two countries are :");
                        if (player.canTransfer(one.getName(), two.getName(), countries)) {
                            System.out.println("There is a path between "+ one.getName()+
                                    " and "+two.getName());
                            if(!front.containsKey(two.getName())){
                                move = two.getArmy() - 1;
                                System.out.println("country:" + two.getName() + "is not front,move armies:" + move);
                            }else{
                                move = two.getArmy() - (one.getArmy() + two.getArmy()) / 2;
                                System.out.println("country:" + two.getName() + "is front,move armies:" + move);
                            }
                            int oneNew = countries.get(String.valueOf(one.getName())).getArmy() + move;
                            countries.get(String.valueOf(one.getName())).setArmy(oneNew);
                            int twoNew = countries.get(String.valueOf(one.getName())).getArmy() - move;
                            countries.get(String.valueOf(one.getName())).setArmy(twoNew);
                            for (Country c:player.getCountryList()) {
                                if(c.getName() == one.getName())
                                    c.setArmy(oneNew);
                                if(c.getName() == two.getName())
                                    c.setArmy(twoNew);
                            }
                            // player.updateFortification(one, two, countries);
                            s=one.getName()+" "+two.getName() + " " + move;
                            System.out.println("Choice of two countries are : "+s);
                            return s;
                        }else{
                            System.out.println("No path between them");
                            System.out.println();
                            System.out.println();
                        }

                    }
                }
            }else{
                System.out.println("Country "+ one.getName()+ " is not neib to another player, " +
                        " Army is "+one.getArmy());
            }

        }

        return s;

    }

    /**
     * This method is find the weak country.
     *
     * @param player the object of the player.
     * @return the armies' number of the weakest country.
     */
    public int weekCon(Player player){
        System.out.println("This is weekCon function");
        LinkedList<Country> c = player.getCountryList();
        int minCon = c.get(0).getName();
        int minArmy = c.get(0).getArmy();
        for (Country s:c) {
            if(s.getArmy() < minArmy){
                minCon = s.getName();
                minArmy = s.getArmy();
            }
        }
        System.out.println("The week con is "+minArmy);
        return minCon;
    }
}
