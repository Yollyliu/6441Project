package PlayerStrategy;

import Model.Player;

public class strategyFactory {

    private strategyFactory(){

    }

    static public Strategy getBehavior(String name, Player player){
        if(name.equalsIgnoreCase("human")){
            return new humanPlayer(player);
        }else if(name.equalsIgnoreCase("random")){
            return new randomPlayer(player);

        }else if(name.equalsIgnoreCase("aggressive")){
            return new aggressivePlayer(player);

        }else if(name.equalsIgnoreCase("benevolent")){
            return new benevolentPlayer(player);
        }else if(name.equalsIgnoreCase("cheater")) {
            return new cheaterPlayer(player);
        }
        return null;

    }



}
