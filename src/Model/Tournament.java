package Model;

import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Tournament implements Serializable {


    private LinkedList<String> resultInformation=new LinkedList <>();
    private LinkedList<String> maps;      //map road
    int gameTime;
    int gameTurn;
    InitializePhase mode=new InitializePhase();
    ArrayList<String> behavoirs = new ArrayList<>();

    public Tournament(ArrayList<String> behavoirs,
            LinkedList <String> maps, int gameTime, int gameTurn) {

        this.maps = maps;
        this.gameTime = gameTime;
        this.gameTurn = gameTurn;
        mode.setBehavoirs(behavoirs);

    }

    public void runTourn() {

        //loop for map
        for (int i = 0; i < maps.size(); i++) {
            File file = new File(maps.get(i));
            IO io = new IO();
            io.readFile(maps.get(i));


            StringBuffer eachGameForMap = new StringBuffer();
            //loop for each game
            for (int j = 0; j < gameTime; j++) {
                mode.clearAll();
                mode.addData(behavoirs, io.getCountries(), io.getContinents());
                mode.initPhase();

                //loop for every player start up
                for (int k = 0; k < mode.getPlayerNum(); k++) {
                    mode.getPlayerSet().get(String.valueOf(k)).getAllArmies(mode.getContinents());
                    mode.Startup(String.valueOf(k), mode.randomSelect(String.valueOf(k)));
                }

                //loop for maximum gameTurn
                int alreadyTime = 0;
                //loop for every player three phase
                for (int k = 0; k < mode.getPlayerNum(); k++) {

                    if (alreadyTime < gameTurn) {
                        mode.Reinforcement(String.valueOf(k));
                        mode.attackPhase(String.valueOf(k), "0",
                                "All_out", 0, 0, behavoirs.get(k));

                        if (Attack.isWIN()) {
                            eachGameForMap.append(behavoirs.get(k));
                            eachGameForMap.append(" ");
                            break;
                        } else {
                            mode.Fortification(String.valueOf(k), "0", 0,
                                    mode.getPlayerSet().get(String.valueOf(k)).getMode());
                        }

                    } else {
                        eachGameForMap.append("Draw");
                        eachGameForMap.append(" ");
                        break;
                    }
                    alreadyTime++;
                }
                resultInformation.add(i, eachGameForMap.toString());
            }


        }

    }


    public LinkedList <String> getMaps() {
        return maps;
    }

    public void setMaps(LinkedList <String> maps) {
        this.maps = maps;
    }

    public int getGameTime() {
        return gameTime;
    }

    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    public int getGameTurn() {
        return gameTurn;
    }

    public void setGameTurn(int gameTurn) {
        this.gameTurn = gameTurn;
    }
}
