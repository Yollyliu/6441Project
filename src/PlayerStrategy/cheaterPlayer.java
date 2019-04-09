package PlayerStrategy;

import Model.Attack;
import Model.Continent;
import Model.Country;
import Model.Player;

import java.util.*;

//computer player strategy whose reinforce() method doubles the number of armies on all its countries,
// whose attack() method automatically conquers all the neighbors of all its countries,
//and whose fortify() method doubles the number of armies on its countries that have neighbors that belong to other players.
public class cheaterPlayer implements Strategy{
    String behavior="";
    Player player;

    public cheaterPlayer(Player player){
        this.player=player;
        this.behavior="Cheater";
    }
    //return countries that double armies,like 2 3 5
    @Override
    public String Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries,
                                HashMap<String, Continent> continents) {
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println("**********hello, we are in cheater player reinforcement *********");
        for (Country c:player.getCountryList()) {
            int armyNow = c.getArmy();
            System.out.println("Before reinforcement, country " + c.getName() + " has armies " + armyNow );
            c.setArmy(2 * armyNow);
            System.out.println("After reinforcement, country " + c.getName() + " has armies " + c.getArmy());
            countries.get(String.valueOf(c.getName())).setArmy(2 * armyNow);
            stringBuffer.append(String.valueOf(c.getName()));
            stringBuffer.append(" ");
        }

        return stringBuffer.toString();
    }
    //返回所有attacker
    @Override
    public LinkedList<String> Attack(String attacker, String defender, String mode, int attDices,
                                     int defDices, HashMap<String, Player> playerSet,
                                     HashMap<String, Country> countries,
                                     HashMap<String, Continent> continents) {
        System.out.println("**********hello, we are in cheater player attack *********");
        LinkedList<String> ans=new LinkedList <>();
        //找到邻国是敌国，改变颜色，army设置为1
        LinkedList<Integer> enemy = new LinkedList<>();
        for (Country c:player.getCountryList()) {
            if(c.getArmy() > 1) {
                int num = 0;
                String[] neighbours = c.getCountryList().split(" ");
                for (String s : neighbours) {
                    if (!countries.get(s).getColor().equals(c.getColor()) && num <= c.getArmy() - 1) {

                        num++;
                        enemy.add(Integer.parseInt(s));
                        System.out.println("country " + c.getName() + " conquers country " + s + ",transfer army is 1");
                        int armyNow = c.getArmy();
                        System.out.println("Before attack,attacker " + c.getName() + " has armies " + armyNow);
                        c.setArmy(armyNow - 1);
                        System.out.println("After attack,attacker " + c.getName() + " has armies " + c.getArmy());
                        countries.get(String.valueOf(c.getName())).setArmy(armyNow - 1);
                        countries.get(s).setArmy(1);
                        System.out.println("After attack,defender " + s + " has armies " + countries.get(s).getArmy());
                        countries.get(s).setColor(c.getColor());
                    }
                }
                if(num != 0)
                    ans.add(String.valueOf(c.getName()));
            }

        }
        if(enemy.size() != 0){
            System.out.println("has enemy:" + enemy.size());
            Iterator iter = playerSet.entrySet().iterator();
            LinkedList<String> playerModify = new LinkedList<>();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                String name =(String)entry.getKey();
                Player p = (Player) entry.getValue();
                for (Integer i:enemy) {
                    for (Country country:p.getCountryList()) {
                        if(country.getName() == i){
                            playerModify.add(name);
                            System.out.println("player " + player.getPlayerName() +
                                    " conquers player " + name + "'s country " + i);
                            country.setArmy(1);
                            country.setColor(player.getColor());
                            player.getCountryList().add(country);
                            p.getCountryList().remove(country);
                            break;
                        }
                    }
                }
                if(p.getCountryList().size() != 0){
                    playerSet.put(name,p);

                }
            }

            for (String s:playerModify) {
                if(playerSet.containsKey(s) && playerSet.get(s).getCountryList().size() == 0)
                    playerSet.remove(s);
            }

            if(playerSet.size() == 1){

                player.attackPhase("0","0","WIN",0,0,playerSet,countries,continents);


            }
        }


        return ans;

    }
    //返回所有被加兵的国家
    @Override
    public String Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries) {
        String s="";
        System.out.println("**********hello, we are in cheater player fortification *********");
        HashMap<Integer,Integer> front = player.Front(countries);
        for (Country c:player.getCountryList()) {
            if(front.containsKey(c.getName())){
                int armyNow = c.getArmy();
                System.out.println("Before fortification, country " + c.getName() + "has armies " + armyNow );
                c.setArmy(2 * armyNow);
                System.out.println("After fortification, country " + c.getName() + "has armies " + c.getArmy() );
                countries.get(String.valueOf(c.getName())).setArmy(2 * armyNow);
                s = s + String.valueOf(c.getName()) + " ";
            }
        }
        return s;

    }


}
