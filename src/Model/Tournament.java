package Model;

import View.BackEnd;

import java.io.File;
import java.io.FileReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
/**
 * <h1>Tournament</h1>
 * This class is for tournament mode.
 *
 * @author youlin_liu,shuo_chi,tianshu_ji.
 * @version 5.0
 * @since 2019-04-01
 */
public class Tournament implements Serializable {


    private LinkedList<String> resultInformation=new LinkedList <>();
    private LinkedList<String> maps;      //map road
    int gameTime;
    int gameTurn;
    InitializePhase mode=new InitializePhase();
    ArrayList<String> behavoirs = new ArrayList<>();

    /**
     * This method is constructor of class tournament.
     */
    public Tournament() {
    }

    /**
     * This method is used to get initializePhase object.
     * @return initializePhase object.
     */
    public InitializePhase getMode() {
        return mode;
    }

    /**
     * This method is setter for resultInformation.
     * @param resultInformation results.
     */
    public void setResultInformation(LinkedList <String> resultInformation) {

        this.resultInformation = resultInformation;
    }

    /**
     * This method is a setter for InitializePhase.
     *
     * @param mode object of InitializePhase.
     */
    public void setMode(InitializePhase mode) {
        this.mode = mode;
    }

    /**
     * This method is a setter for behaviors.
     *
     * @param behavoirs behaviors of players.
     */
    public void setBehavoirs(ArrayList <String> behavoirs) {
        this.behavoirs = behavoirs;
    }

    /**
     * This method is a getter for behaviors.
     *
     * @return behaviors of players.
     */
    public ArrayList <String> getBehavoirs() {
        return behavoirs;
    }

    /**
     * This method is a constructor.
     *
     * @param behavoirs behaviors of players.
     * @param maps maps.
     * @param gameTime time of game.
     * @param gameTurn turn of each game.
     */
    public Tournament(ArrayList<String> behavoirs,
                      LinkedList <String> maps, int gameTime, int gameTurn) {


        this.maps = maps;
        this.gameTime = gameTime;
        this.gameTurn = gameTurn;
        mode.setBehavoirs(behavoirs);
        this.setBehavoirs(behavoirs);
        System.out.println("get maps, maps size: "+ maps.size());
        System.out.println("get game Time : "+gameTime);
        System.out.println("get game turn : "+gameTurn);
        System.out.println("get game behavoir : "+mode.getBehavoirs().size());


    }


    /**
     * This method is used to run this class.
     */
    public void runTourn() {

        //loop for map
        for (int i = 0; i < maps.size(); i++) {
            File file = new File(maps.get(i));
            IO io = new IO();
            io.readFile(maps.get(i));
            StringBuffer eachGameForMap = new StringBuffer();

            //loop for each game
            for (int j = 0; j < gameTime; j++) {
                //mode.clearAll();
                //mode.setPlayerNum(mode.getBehavoirs().size());

                mode.clearAll();
                System.out.println("get game behavoir : " + mode.getBehavoirs().size());
                mode.addData(this.behavoirs, io.getCountries(), io.getContinents());
                System.out.println("get game behavoir : " + mode.getBehavoirs().size());
                System.out.println(" playNum is " + mode.getPlayerNum());
                System.out.println("get game behavoir : " + mode.getBehavoirs().size());
                mode.initPhase();

                //loop for every player start up
                for (int k = 1; k < mode.getPlayerNum() + 1; k++) {
                    mode.getPlayerSet().get(String.valueOf(k)).getAllArmies(mode.getContinents());
                    mode.Startup(String.valueOf(k), mode.randomSelect(String.valueOf(k)));
                }

                //loop for maximum gameTurn
                int alreadyTime = 0;
                String k = "1";
                boolean flagWin=false;
                System.out.println("This is J Game Time "+j);
                while (alreadyTime < gameTurn && !Attack.isWIN()) {
                    //loop for every player three phase
                    // for (int k = 1; k < mode.getPlayerNum() + 1; k++) {


                    // String first=mode.getPlayerSet().get(0).getPlayerName();

                    LinkedList <String> left = new LinkedList <>();
                    for (String key : mode.getPlayerSet().keySet()) {
                        left.add(key);
                    }

                    if (k.equalsIgnoreCase(left.get(0))) {

                        alreadyTime++;
                        System.out.println(" already Time is " + alreadyTime +
                                " gameTurn is " + gameTurn);
                    }

                    mode.Reinforcement(String.valueOf(k));

                    BackEnd b = new BackEnd(mode.getCountries(),
                            mode.getContinents(), mode.getPlayerSet());

                    boolean canAttack = b.canAttack(String.valueOf(k));
                    if (canAttack) {
                        mode.attackPhase(String.valueOf(k), "0",
                                "All_out", 0, 0,
                                mode.getPlayerSet().get(String.valueOf(k)).getMode());
                        if (Attack.isWIN()) {

                            System.out.println("***********" +
                                    "********* One is Win **********");
                            String win=behavoirs.get(Integer.valueOf(k) - 1);
                            System.out.println("Get winner: "+ win +
                            " we will and this wo eachGameForMap "+win);
                            eachGameForMap.append(behavoirs.get(Integer.valueOf(k) - 1));
                            if (j != gameTime - 1) {
                                eachGameForMap.append(" ");
                            }
                            flagWin=true;
                            System.out.println("Information : " + eachGameForMap.toString());
                            Attack.WIN=false;
                            break;
                        }
                    }

                    mode.Fortification(String.valueOf(k), "0", 0,
                            mode.getPlayerSet().get(String.valueOf(k)).getMode());

                    k = b.findnext(k);
                    //alreadyTime++;

                }
                //System.out.println("eachGameMap : " + eachGameForMap.toString());
                if (!flagWin) {
                    eachGameForMap.append("Draw");
                    if (j != gameTime - 1) {
                        eachGameForMap.append(" ");
                    }
                   // break;
                }
                System.out.println("eachGameMap in every turn: " + eachGameForMap.toString()
                        + " This is " + j + " times game");
            }



                System.out.println("eachGameMap : " + eachGameForMap.toString());
                resultInformation.add(i, eachGameForMap.toString());
                System.out.println("The resultINformation size " + resultInformation.size());
            }

        for (int j = 0; j < resultInformation.size(); j++) {
            System.out.println(resultInformation.get(j));
        }
    }


    /**
     * This method is a getter for resultInformation.
     *
     * @return resultInformation.
     */
    public LinkedList <String> getResultInformation() {
        return resultInformation;
    }

    /**
     *This method is a getter for maps.
     *
     * @return maps.
     */
    public LinkedList <String> getMaps() {
        return maps;
    }

    /**
     * This method is a setter for maps.
     *
     * @param maps maps.
     */
    public void setMaps(LinkedList <String> maps) {
        this.maps = maps;
    }

    /**
     * This method is a getter for gameTime.
     *
     * @return gameTime.
     */
    public int getGameTime() {
        return gameTime;
    }

    /**
     * This method is a setter for gameTime.
     *
     * @param gameTime times of game.
     */
    public void setGameTime(int gameTime) {
        this.gameTime = gameTime;
    }

    /**
     * This method is a getter for gameTurn.
     *
     * @return gameTurn.
     */
    public int getGameTurn() {
        return gameTurn;
    }


    /**
     * This method is setter for gameTurn.
     *
     * @param gameTurn turns of game.
     */
    public void setGameTurn(int gameTurn) {
        this.gameTurn = gameTurn;
    }
}
