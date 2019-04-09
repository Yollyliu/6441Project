package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.io.Serializable;
import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class aggressivePlayer  implements Strategy, Serializable {

    String behavior="";
    Player player;

    public aggressivePlayer(Player player){
        this.player=player;
        this.behavior="Agressive";
    }
    @Override
    public String Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries,
                                HashMap<String, Continent> continents) {
        System.out.println();
        System.out.println(" hello, we are in aggressive player reinforcement");
        player.getAllArmies(continents);

        StringBuffer sb=new StringBuffer();
        sb.append(player.getArmy());
        sb.append(" ");
        //3
        System.out.println("new army is "+ player.getArmy());

        Country strongest=
                player.getCountryList().stream().max(
                        Comparator.comparing(Country::getArmy)).get();

        //Country strongest=player.getCountryList().getFirst();
        System.out.println("Strongest country: "+strongest.getName()+
                " Army: "+strongest.getArmy());
        int totalArmy=player.getArmy()+strongest.getArmy(); //3 to:6

        System.out.println("total army is "+totalArmy);
        strongest.setArmy(totalArmy);
        player.setArmy(0);

        player.updateReinforcement(strongest.getName(), totalArmy,playerSet,countries);

        System.out.println("the strongest/lucky country is "+ String.valueOf(strongest.getName()));
        System.out.println();
        System.out.println();
        sb.append(strongest.getName());

        return sb.toString();

    }

    ////////////////////////////////////Attack/////////////////////////////////////

    @Override
    public LinkedList<String> Attack(String attacker, String defender, String mode, int attDices,
                       int defDices, HashMap<String, Player> playerSet,
                       HashMap<String, Country> countries,
                       HashMap<String, Continent> continents) {


        //ans 返回的，第一个是attacker country，后面的都是defender
        System.out.println(" **********hello, we are in aggressive player attack *********");

        LinkedList<String> ans=new LinkedList <>();
        Country strongest=new Country();
        boolean flag=true;
        LinkedList<Country> allDefenders=new LinkedList <>();



        //找到最强的国家，更行所有的，
        while(flag) {
            //拿到最强国家的排序
            List <Country> allCountry = getStrongestCountry(playerSet,attacker);
            if (allCountry.get(0).getArmy() < 2) {
                System.out.println("cannot attack as no country army >=2");
                return ans;

            }
            for (int i = 0; i < allCountry.size(); i++) {
                strongest = allCountry.get(i);

                LinkedList<Country> curAllDefenders = isStrongestWithNeib(strongest, countries);
                if (curAllDefenders.size() > 0) {
                    System.out.println("Before: Attack country is "+String.valueOf(strongest.getName())+
                            " Army : "+strongest.getArmy());
                    allDefenders=curAllDefenders;
                    System.out.println("The neib size of strongest counntry : "+allDefenders.size());
                    flag=false;
                    break;
                }

            }

        }

        //ans.add(String.valueOf(strongest.getName()));
        if(allDefenders.size()>0) {
            System.out.println("the size");
            int i=0;
                    while (strongest.getArmy() >= 2 && i<allDefenders.size()) {

                        String aggressiveAttack = String.valueOf(strongest.getName());
                        String aggressiveDefend = String.valueOf(allDefenders.get(i).getName());

                        System.out.println("Before: aggressiveDefender country is " + aggressiveDefend +
                                " Army : " + allDefenders.get(i).getArmy());
                        System.out.println();
                        System.out.println();
                        String cur = player.attackPhase(aggressiveAttack, aggressiveDefend, "All_Out",
                                0, 0, playerSet, countries, continents);
                        System.out.println();
                        System.out.println();
                        System.out.println("the attack information is "+ cur);
                        String[] readrecord = cur.split(" ");
                        if(readrecord[0].equals(attacker)) {
                            System.out.println("attacker Country " + aggressiveAttack + " win");
                            if(Integer.valueOf(readrecord[1])!= 0 ) {
                                player.transfer(cur, strongest, allDefenders.get(i), countries);
                            }
                        }
                        else if (readrecord[0].equals("-1")) {
                            System.out.println("This is a draw. No need to transfer");
                        }
                        else {
                            System.out.println("defender Country " + aggressiveDefend + " win");
                        }

                        System.out.println();
                        System.out.println();

                        System.out.println("After:  aggressiveAttacker country is " + aggressiveAttack +
                                " Army : " + strongest.getArmy());
                        System.out.println("After:  aggressiveDefender country is " + aggressiveDefend +
                                " Army : " + allDefenders.get(i).getArmy());
                        System.out.println();
                        System.out.println();


                        if (!cur.equals("")) {
                            StringBuffer sb = new StringBuffer();
                            sb.append(cur);
                            ans.add(sb.toString());
                        }
                        i++;
                    }
            for(int k=0;k<ans.size();k++){
                System.out.println("******* attack result information is *******");
                System.out.println(ans.get(k));
            }
        }else{
            System.out.println("There is no neibs in attack country");
        }
       // System.out.println("the defenders in aggressive attack are :");
        //System.out.println(ans);


        return ans;

    }


    public List<Country> getStrongestCountry(
            HashMap<String, Player> playerSet,String attacker){

        LinkedList<Country> allCountry=playerSet.get(attacker).getCountryList();


       return ( allCountry.stream()
                .sorted((c1, c2) -> {
                    if (c2.getArmy() - c1.getArmy() > 0 ) return 1;
                    else if (c2.getArmy() - c1.getArmy() == 0) return 0;
                    else return -1; })
                .collect(Collectors.toList()));
    }

    public LinkedList<Country> isStrongestWithNeib(Country strongest,
                                       HashMap<String, Country> countries){
        LinkedList<Country> allDefenders=new LinkedList <>();

        String[] neib=strongest.getCountryList().split(" ");
        for(int i=0;i<neib.length;i++){
            if(!countries.get(neib[i]).getColor().equals(player.getColor())){
                allDefenders.add(countries.get(neib[i]));
            }
        }
        if(allDefenders.size()>0){
            System.out.println("Strongest Country "+strongest.getName()+" has neib");

        }else{
            System.out.println("Strongest Country "+ strongest.getName()+ "has no neib ");

        }
        return allDefenders;
    }



    ////////////////////////////////Fortification///////////////////////////
    @Override
    public String Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries) {

        System.out.println();
        System.out.println();
        System.out.println(" hello, we are in aggressive player fortification");

        String s="";
        List<Country> newCountryList = player.getCountryList().stream()
                .sorted((c1, c2) -> {
                    if (c2.getArmy() - c1.getArmy() > 0 ) return 1;
                    else if (c2.getArmy() - c1.getArmy() == 0) return 0;
                    else return -1; })
                .collect(Collectors.toList());

        HashMap<Integer,Integer> frontNumb=new HashMap <>();
        frontNumb=player.Front(countries);
        int length=newCountryList.size();

        System.out.println();
        for(int i=0;i<length;i++) {

            Country one = newCountryList.get(i);

            if (frontNumb.containsKey(one.getName())) {
                System.out.println("Country "+ one.getName()+ " has neib to another player," +
                        " numb is "+ frontNumb.get(one.getName())+" Army is "+one.getArmy());


                for (int j = 0; j < length; j++) {

                    if ((i != j) && newCountryList.get(j).getArmy()>1) {

                        Country two = newCountryList.get(j);
                        System.out.println();

                        System.out.println("This is in agreesive Player fortification:");
                        System.out.println("Country one is "+ one.getName()+" Army is "+one.getArmy());
                        System.out.println("Country two is "+two.getName()+ " Army is "+two.getArmy());
                        System.out.println("Path between two countries are :");
                        if (player.canTransfer(one.getName(), two.getName(), countries)) {

                            System.out.println("There is a path between "+ one.getName()+
                            " and "+two.getName());
                           // player.updateFortification(one, two, countries);
                            s=one.getName()+" "+two.getName();
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


}
