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

    public humanPlayer(Player player){
        System.out.println(" hello, we are in human player");
        this.player=player;
        this.behavior="Human";
    }

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
        String result=player.attackPhase(attacker, defender, mode,
                attDices, defDices,playerSet,countries,continents);
        ans.add(result);
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
