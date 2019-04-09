package PlayerStrategy;

import Model.Attack;
import Model.Continent;
import Model.Country;
import Model.Player;

import java.util.HashMap;

public class humanPlayer implements Strategy{

    String behavior="";
    Player player;

    public humanPlayer(Player player){
        this.player=player;
        this.behavior="Human";
    }

    @Override
    public void Reinforcement() {
        player.getAllArmies();
    }

    @Override
    public void Attack(String attacker, String defender,
                       String mode, int attDices, int defDices,
                       HashMap<String, Player> playerSet,
                       HashMap<String, Country> countries,
                       HashMap<String, Continent> continents) {
        player.attackPhase(attacker, defender, mode,
                attDices, defDices,playerSet,countries,continents);

    }

    @Override
    public void Fortification(Country from, Country to,
                              int move,HashMap<String, Country> countries) {

        if(player.canTransfer(player.getPlayerName(),String.valueOf(from.getName()),
                String.valueOf(to.getName()),countries)) {
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
                if (key.equals(from.getName())) {
                    countries.get(key).setArmy(start);
                }
                if (key.equals(to.getName())) {
                    countries.get(key).setArmy(end);
                }
            }

        }else{
            System.out.println("Cannot Transfer");
        }

    }


}
