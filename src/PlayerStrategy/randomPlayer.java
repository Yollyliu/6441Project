package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * <h1>randomPlayer</h1>
 * The randomPlayer class is for implement all interface method for the random player.
 *
 * @author tianshu_ji, shuo_chi, youlin_liu
 * @version 3.0
 * @since 2019-04-07
 */
//A random computer player strategy that reinforces random a random country,
//attacks a random number of times a random country, and fortifies a random
//country, all following the standard rules for each phase.
public class randomPlayer implements Strategy,Serializable{

    String behavior="";
    Player player;

    public randomPlayer(Player player){
        this.behavior="Random";
        this.player=player;
    }

    //随机选择一个国家，将所有armyInHand给它
    @Override
    public String Reinforcement(HashMap<String, Player> playerSet,
                                HashMap<String, Country> countries,
                                HashMap<String, Continent> continents){
        StringBuffer stringBuffer = new StringBuffer();
        System.out.println("**********hello, we are in random player reinforcement *********");
        player.getAllArmies(continents);
        int armyInhand = player.getArmy();
        stringBuffer.append(armyInhand);
        stringBuffer.append(" ");
        System.out.println("Army in hand:" + armyInhand);
        String ranCon = randomSelect(player.getCountryList());
        stringBuffer.append(ranCon);
        System.out.println("random select country:" + ranCon);
        int armyInCountry = countries.get(ranCon).getArmy();
        System.out.println("before reinforcement, this country has armies:" + armyInCountry);
        armyInCountry += armyInhand;
        countries.get(ranCon).setArmy(armyInCountry);
        System.out.println("after reinforcement,this country has armies:" + countries.get(ranCon).getArmy());
        for (Country c:player.getCountryList()) {
            if(c.getName() == Integer.parseInt(ranCon)){
                c.setArmy(armyInCountry);
                break;
            }
        }
        player.setArmy(0);
        return stringBuffer.toString();

    }

    //返回每次one Time的结果

    @Override
    public LinkedList<String> Attack(String attacker, String defender, String mode, int attDices,
                                     int defDices, HashMap<String, Player> playerSet,
                                     HashMap<String, Country> countries,
                                     HashMap<String, Continent> continents){

        System.out.println(" **********hello, we are in random player attack *********");
        LinkedList<String> ans=new LinkedList <>();
        Boolean flag = true;
        String randomAttacker = "";
        String randomDefender = "";
        Random random = new Random();
        while(flag) {
            randomAttacker = randomAttacker();
            if(!randomAttacker.equalsIgnoreCase("")) {
                String[] neighbours = countries.get(randomAttacker).getCountryList().split(" ");
                LinkedList <String> enemies = new LinkedList <>();
                for (String n : neighbours) {
                    if (!countries.get(n).getColor().equals(player.getColor()) && countries.get(n).getArmy() >= 1)
                        enemies.add(n);
                }
                if (enemies.size() != 0) {
                    int index = random.nextInt(enemies.size());
                    randomDefender = enemies.get(index);
                    flag = false;
                }
            }
        }
        System.out.println("random attacker is:" + randomAttacker);
        System.out.println("random defender is:" + randomDefender);
        int attackerArmy = countries.get(randomAttacker).getArmy();
        System.out.println("attacker has army:" + attackerArmy);
        int defenderArmy = countries.get(randomDefender).getArmy();
        System.out.println("defender has army:" + defenderArmy);
        int attackTime = random.nextInt(Math.max(attackerArmy,defenderArmy) - 1) + 1;
        System.out.println("random attack time is:" + attackTime);
        for (int i = 0; i < attackTime; i++) {
            if (countries.get(randomAttacker).getArmy() >= 2 && countries.get(randomDefender).getArmy() >= 1
                    && !countries.get(randomAttacker).getColor().equals(countries.get(randomDefender).getColor())) {
                attDices = Math.min(3, countries.get(randomAttacker).getArmy() - 1);
                if(countries.get(randomAttacker).getArmy() >= countries.get(randomDefender).getArmy() ){
                    if(countries.get(randomDefender).getArmy() != 1){
                        defDices = Math.min(2,countries.get(randomDefender).getArmy() - 1);
                    }else{
                        defDices = 1;
                    }
                }else{
                    defDices = Math.min(2,attDices);
                }
                String cur = player.attackPhase(randomAttacker,randomDefender,"One_Time",attDices,defDices,playerSet,countries,continents);
                System.out.println("Attack time:" + i + 1 + ",result " + cur);
                ans.add(cur);
                String[] readrecord = cur.split(" ");
                if(readrecord[0].equals(player.getPlayerName())) {
                    System.out.println("attacker Player " + randomAttacker + " win");
                    if(Integer.valueOf(readrecord[1])!= 0 ) {
                        updateAttack(cur, randomAttacker,randomDefender,countries);
                    }
                }
                else if (readrecord[0].equals("-1")) {
                    System.out.println("This is a draw. No need to transfer");
                }
                else {
                    System.out.println("defender Country " + randomDefender + " win");
                }

            } else {
                System.out.println("can't attack anymore");
                break;
            }
        }
        System.out.println("attack finish");

        return ans;

    }

    @Override
    public String Fortification(Country from, Country to, int move,
                                HashMap<String, Country> countries) {
        System.out.println("**********hello, we are in random player fortification *********");
        String s="";
        Random random = new Random();
        String two = randomAttacker();
        System.out.println("the two country in fortificaiton is "+two);
        if(!two.equalsIgnoreCase("")) {
            int trasfer = random.nextInt(countries.get(two).getArmy() - 1) + 1;
            int index = random.nextInt(player.getCountryList().size());
            String one = String.valueOf(player.getCountryList().get(index).getName());
            if (!one.equals(two) && player.canTransfer(Integer.parseInt(one), Integer.parseInt(two), countries)) {
                s = s + one + " " + two;
                System.out.println("transfer army:" + trasfer + "from country " + two + " to country " + one);
                int oneArmy = countries.get(one).getArmy();
                countries.get(one).setArmy(oneArmy + trasfer);
                System.out.println("After transfer country " + one + " has armies:" + countries.get(one).getArmy());
                int twoArmy = countries.get(two).getArmy();
                countries.get(two).setArmy(twoArmy - trasfer);
                System.out.println("After transfer country " + two + " has armies:" + countries.get(two).getArmy());
                for (Country c : player.getCountryList()) {
                    if (c.getName() == Integer.parseInt(one)) {
                        c.setArmy(oneArmy + trasfer);
                    }
                    if (c.getName() == Integer.parseInt(two)) {
                        c.setArmy(twoArmy - trasfer);
                    }
                }

            } else {
                System.out.println("random selected countries can not fortification");
            }
        }else{
            System.out.println("random selected countries can not fortification");
        }


//        for (Country c:player.getCountryList()) {
//
//            int armyNow = c.getArmy();
//            c.setArmy(armyNow+1);
//            countries.get(String.valueOf(c.getName())).setArmy(armyNow+1);
//        }


        return s;
    }

    /**
     * This is the method to random select a country.
     *
     * @param countryList a list stores the countries of the player.
     * @return the name of the country to be random selected.
     */
    public String randomSelect(LinkedList<Country> countryList){

        System.out.println(" In random select: the player is "+player.getPlayerName());
        System.out.println("The countrylist size is "+countryList.size());
        Random randomC = new Random();

        if(countryList.size()>0) {
            int index = randomC.nextInt(countryList.size());
            int find = countryList.get(index).getName();
            String findc = String.valueOf(find);

            System.out.println(" the random selected country is " + findc);
            return findc;
        }else{
            return "";
        }

    }

    /**
     * This is the method to random select the attacker.
     *
     * @return the name of the attacker to be random selected.
     */
    public String randomAttacker(){
        System.out.println("In random attacker selected : player is " + player.getPlayerName());
        LinkedList<Country> attackers = new LinkedList<>();
        for (Country c:player.getCountryList()) {
            if(c.getArmy() >= 2)
                attackers.add(c);
        }
        if(attackers.size()>0) {
            String randomAttacker = randomSelect(attackers);
            return randomAttacker;
        }else{
            return "";
        }
    }

    /**
     * This is the method to update the information of all changed in attack phase.
     *
     * @param cur the current records.
     * @param attacker the name of attacker.
     * @param defender the name of defender.
     * @param countries a hash map stores all countries.
     */
    public void updateAttack(String cur,String attacker,String defender,HashMap<String,Country> countries){
        String[] records = cur.split(" ");
        Random random = new Random();
        int attArmy = countries.get(attacker).getArmy();
        System.out.println("before transfer attacker has army:" + attArmy);
        int defArmy = countries.get(defender).getArmy();
        System.out.println("before transfer defender has army:" + defArmy);
        int transfer = random.nextInt(Integer.parseInt(records[2]) - Integer.parseInt(records[1]) + 1) + Integer.parseInt(records[1]);
        System.out.println("random armies transfer:" + transfer);
        for (Country c:player.getCountryList()) {
            if(c.getName() == Integer.parseInt(attacker)){
                c.setArmy(attArmy - transfer);
                System.out.println("after transfer attacker has army:" + c.getArmy());
            }
            if(c.getName() == Integer.parseInt(defender)){
                c.setArmy(defArmy + transfer);
                System.out.println("after transfer defender has army:" + c.getArmy());
            }
        }
        countries.get(attacker).setArmy(attArmy - transfer);
        System.out.println("after transfer attacker has army:" + countries.get(attacker).getArmy());
        countries.get(defender).setArmy(defArmy + transfer);
        System.out.println("after transfer defender has army:" + countries.get(defender).getArmy());
    }
}
