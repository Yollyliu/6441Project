package PlayerStrategy;

import Model.Attack;
import Model.Continent;
import Model.Country;
import Model.Player;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * <h1>humanPlayer</h1>
 * The humanPlayer class is for implement all interface method for the human player.
 *
 * @author tianshu_ji, shuo_chi, youlin_liu
 * @version 3.0
 * @since 2019-04-07
 */
public class humanPlayer implements Strategy, Serializable {

    String behavior="";
    Player player;

    /**
     * This method is to get the behavior.
     *
     * @return behavior.
     */
    public String getBehavior() {
        return behavior;
    }

    /**
     * This method is to set the behavior.
     *
     * @param behavior a behavior to set.
     */
    public void setBehavior(String behavior) {
        this.behavior = behavior;
    }

    /**
     * This method is to get the player.
     *
     * @return the player object.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * This method is to set the player.
     *
     * @param player a player to set.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * This method is a constructor.
     *
     * @param player object of Player.
     */
    public humanPlayer(Player player){
        System.out.println(" hello, we are in human player");
        this.player=player;
        this.behavior="Human";
    }

    /**
     * This method is the reinforcement phase in player.
     *
     * @param playerSet object of Players.
     * @param countries  A hash map storing all countries which are in the map.
     * @param continents A hash map storing all continents which are in the map.
     * @return String of reinforcement result.
     */
    @Override
    public String Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries,
                                HashMap<String, Continent> continents) {
        System.out.println(" hello, we are in human player reinforcement");
        player.getAllArmies(continents);
        StringBuffer sb=new StringBuffer();
        sb.append(player.getArmy());
        sb.append(" ");
        sb.append("clicked");
        return sb.toString();
    }

    @Override
    public LinkedList<String> Attack(String attacker, String defender,
                                     String mode, int attDices, int defDices,
                                     HashMap<String, Player> playerSet,
                                     HashMap<String, Country> countries,
                                     HashMap<String, Continent> continents) {
        LinkedList<String> ans=new LinkedList <>();
        System.out.println(" hello, we are in human player attack");
        String cur=player.attackPhase(attacker, defender, mode,
                attDices, defDices,playerSet,countries,continents);
        System.out.println("The Human Attack information is: ");
        System.out.println(cur);
        String[] readrecord = cur.split(" ");
        if(readrecord[0].equals(attacker)) {
            System.out.println("attacker Country " + attacker + " win");
            Country att=countries.get(attacker);
            Country def=countries.get(defender);
            if(Integer.valueOf(readrecord[1])!= 0 ) {
                player.transfer(cur, att, def, countries);
            }
        }
        else if (readrecord[0].equals("-1")) {
            System.out.println("This is a draw. No need to transfer");
        }
        else {
            System.out.println("defender Country " + defender + " win");
        }

        ans.add(cur);
        return ans;
    }

    @Override
    public String Fortification(Country from, Country to,
                              int move,HashMap<String, Country> countries) {

        System.out.println(" hello, we are in human player fortification");
     //   if(player.canTransfer(player.getPlayerName(),String.valueOf(from.getName()),
      //          String.valueOf(to.getName()),countries)) {
            int start = from.getArmy() - move;
            int end = to.getArmy() + move;
            for(int i=0;i<player.getCountryList().size();i++) {
                if (player.getCountryList().get(i).getName() == from.getName()) {

                    from.setArmy(start);
                }
                if (player.getCountryList().get(i).getName() == to.getName()) {

                    to.setArmy(end);
                }
            }
            for(String key:countries.keySet()) {
                if (key.equals(String.valueOf(from.getName()))) {
                    countries.get(key).setArmy(start);
                }
                if (key.equals(String.valueOf(to.getName()))) {
                    countries.get(key).setArmy(end);
                }
            }
//
//        }else{
//            System.out.println("Cannot Transfer");
//        }
        return "";

    }


}
