package PlayerStrategy;

import Model.Continent;
import Model.Country;
import Model.Player;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Thread.sleep;

public class aggressivePlayer  implements Strategy{

    String behavior="";
    Player player;

    public aggressivePlayer(Player player){
        this.player=player;
        this.behavior="Agressive";
    }
    @Override
    public String Reinforcement(HashMap<String, Player> playerSet,
                              HashMap<String, Country> countries)
             {
        System.out.println(" hello, we are in aggressive player reinforcement");
        player.getAllArmies();
        System.out.println("new army is "+ player.getArmy());

//        Collections.sort(player.getCountryList(), new Comparator <Country>() {
//            @Override
//            public int compare(Country o1, Country o2) {
//                return Collator.getInstance().compare(o1.getArmy(), o2.getArmy());
//            }
//        } );
        Country strongest=
                player.getCountryList().stream().max(
                        Comparator.comparing(Country::getArmy)).get();

        //Country strongest=player.getCountryList().getFirst();
        System.out.println("Strongest country: "+strongest.getName()+
                " Army: "+strongest.getArmy());
        int totalArmy=player.getArmy()+strongest.getArmy();

        System.out.println("total army is "+totalArmy);
        strongest.setArmy(totalArmy);
        player.setArmy(0);

        player.updateReinforcement(strongest.getName(),
                totalArmy,playerSet,countries);

        System.out.println("the strongest country is "+ String.valueOf(strongest.getName()));
                 try{
                     sleep(Sleep.getSleepTime());
                 } catch (InterruptedException e) {
                     System.out.println(e);
                 }
        return String.valueOf(strongest.getName());

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
        Country strongest=
                player.getCountryList().stream().max(
                        Comparator.comparing(Country::getArmy)).get();

        LinkedList<Country> allDefenders=new LinkedList <>();

        String[] neib=strongest.getCountryList().split(" ");
        for(int i=0;i<neib.length;i++){
            if(!countries.get(neib[i]).getColor().equals(player.getColor())){
                allDefenders.add(countries.get(neib[i]));
            }
        }
        System.out.println("Before: Attack country is "+String.valueOf(strongest.getName())+
        " Army : "+strongest.getArmy());

        ans.add(String.valueOf(strongest.getName()));
        if(allDefenders.size()>0) {
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
                        player.transfer(cur, strongest, allDefenders.get(i), countries);
                        System.out.println();
                        System.out.println();


                        System.out.println("After:  aggressiveAttacker country is " + aggressiveDefend +
                                " Army : " + strongest.getArmy());
                        System.out.println("After:  aggressiveDefender country is " + aggressiveDefend +
                                " Army : " + allDefenders.get(i).getArmy());
                        System.out.println();
                        System.out.println();


                        if (!cur.equals("")) {
                            StringBuffer sb = new StringBuffer();
                            sb.append(aggressiveDefend);
                            ans.add(sb.toString());
                        }
                        i++;
                    }
        }
        System.out.println("the defenders in aggressive attack are :");
        //System.out.println(ans);

        return ans;

    }

    public LinkedList<Country> getStrongest(HashMap<String, Country> countries){
        Country strongest=
                player.getCountryList().stream().max(
                        Comparator.comparing(Country::getArmy)).get();

        LinkedList<Country> allDefenders=new LinkedList <>();

        String[] neib=strongest.getCountryList().split(" ");
        for(int i=0;i<neib.length;i++){
            if(!countries.get(neib[i]).getColor().equals(player.getColor())){
                allDefenders.add(countries.get(neib[i]));
            }
        }
        allDefenders.add(strongest);
        return allDefenders;

    }


    ////////////////////////////////Fortification///////////////////////////
    @Override
    public String Fortification(Country from, Country to, int move,
                              HashMap<String, Country> countries) {

        System.out.println(" hello, we are in aggressive player fortification");

//        Collections.sort(player.getCountryList(), new Comparator <Country>() {
//            @Override
//            public int compare(Country o1, Country o2) {
//                return Collator.getInstance().compare(o1.getArmy(), o2.getArmy());
//            }
//        } );
//        Country strongest=
//                player.getCountryList().stream().max(
//                        Comparator.comparing(Country::getArmy)).get();


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

        for(int i=0;i<length;i++) {

            Country one = newCountryList.get(i);

            if (frontNumb.containsKey(one.getName())) {

                for (int j = 0; j < length; j++) {

                    if (i != j) {

                        Country two = newCountryList.get(j);
                        System.out.println("This is in agreesive Player fortification:");
                        System.out.println("Country one is "+ one.getName()+" Army is "+one.getArmy());
                        System.out.println("Country two is "+two.getName()+ " Army is "+two.getArmy());

                        System.out.println();
                        System.out.println();
                        if (player.canTransfer(one.getName(), two.getName(), countries)) {

                           // player.updateFortification(one, two, countries);
                            s=one.getName()+" "+two.getName();
                            return s;
                        }
                    }
                }
            }
        }
        return s;
    }



}
