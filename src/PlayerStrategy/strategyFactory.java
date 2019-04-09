package PlayerStrategy;

import Model.Player;

/**
 * <h1>strategyFactory</h1>
 * The strategy Factory is to new different player types.
 *
 * @author tianshu_ji
 * @version 3.0
 * @since 2019-04-07
 */
public class strategyFactory {

    private strategyFactory(){

    }

    /**
     * Thi method is to get the behavior from the type of player name.
     *
     * @param name the type of player name.
     * @param player the player object.
     * @return the object the particular type of player.
     */
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
