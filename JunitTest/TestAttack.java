import org.junit.Before;
import static org.junit.Assert.*;
import static org.junit.Assert.*;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import org.junit.Before;
import org.junit.Test;
import Model.Attack;
import Model.Continent;
import Model.Country;
import Model.Player;
import View.BackEnd;
import View.BackEnd;


/**
 * @author jiamin_he
 *
 */
public class TestAttack{
    HashMap<String, Player> playerSet;
    HashMap<String, Country> countries;
    HashMap<String, Continent> continents;
    BackEnd backEnd;
    Attack attack;
    Player player1;
    Player player2;
    Continent continentA;
    Continent continentB;
    Country country1;
    Country country2;
    Country country3;
    Country country4;

    @Before
    /**
	 * This method initializes all objects for test.
	 *  
	 * @throws IllegalArgumentException if diameter not in given range.
	 */
    public void setUp() throws Exception {
        this.playerSet = new HashMap<String, Player>();
        this.countries = new HashMap<String, Country>();
        this.continents = new HashMap<String, Continent>();

        this.player1 = new Player("1");
        player1.setColor(new Color(119, 240, 228));
        this.player2 = new Player("2");
        player2.setColor(new Color(91, 255, 120));

        this.continentA = new Continent();
        continentA.setName("A");
        this.continentB = new Continent();
        continentB.setName("B");

        this.country1 = new Country();
        this.country2 = new Country();
        this.country3 = new Country();
        this.country4 = new Country();
        country1.setName(1);// set name
        country2.setName(2);
        country3.setName(3);
        country4.setName(4);
        country1.setColor(new Color(119, 240, 228));// set color
        country2.setColor(new Color(119, 240, 228));
        country3.setColor(new Color(91, 255, 120));
        country4.setColor(new Color(91, 255, 120));
        country1.setContinent("A");// set continent
        country2.setContinent("B");
        country3.setContinent("A");
        country4.setContinent("B");
        String str1 = "2 3";// set countryList
        country1.setCountryList(str1);
        String str2 = "1 4";
        country2.setCountryList(str2);
        String str3 = "1 4";
        country3.setCountryList(str3);
        String str4 = "2 3";
        country4.setCountryList(str4);

        ArrayList<String> l1 = new ArrayList<String>();// set countryList in continent
        l1.add("1");
        l1.add("3");
        continentA.setCountryList(l1);
        ArrayList<String> l2 = new ArrayList<String>();
        l2.add("2");
        l2.add("4");
        continentB.setCountryList(l2);

        LinkedList<Country> l3 = new LinkedList<Country>();
        l3.add(country1);
        l3.add(country2);
        player1.setCountryList(l3);
        LinkedList<Country> l4 = new LinkedList<Country>();
        l4.add(country3);
        l4.add(country4);
        player2.setCountryList(l4);

        playerSet.put(player1.getPlayerName(), player1);
        playerSet.put(player2.getPlayerName(), player2);

        continents.put(continentA.getName(), continentA);
        continents.put(continentB.getName(), continentB);

        countries.put(String.valueOf(country1.getName()), country1);
        countries.put(String.valueOf(country2.getName()), country2);
        countries.put(String.valueOf(country3.getName()), country3);
        countries.put(String.valueOf(country4.getName()), country4);

        this.backEnd = new BackEnd();
        backEnd.countries = this.countries;
        backEnd.continents = this.continents;
        backEnd.playerSet = this.playerSet;

        this.attack = new Attack();
        attack.setPlayerSet(playerSet);
        attack.setCountries(countries);
        attack.setContinents(continents);

        //game map
        //plater1 1(A)-2(B)
        //        |    |
        //player2 3(A)-4(B)

    }

    /**
     * This method tests attacker country's armies whether is greater than 1 or not.
     */
    @Test
    public void testArmy() {

        boolean result;
        //attack country with one army
        country1.setArmy(1);
        result = backEnd.isOne(String.valueOf(country1.getName()));
        assertFalse(result);

        //attack country with more than one amry
        country1.setArmy(2);
        result = backEnd.isOne(String.valueOf(country1.getName()));
        assertTrue(result);
    }

    /**
     * This method tests attacker country and defender country whether are neighbor country or not.
     */
    @Test
    public void testNeighbour() {
        boolean result;
        // attacker and defender are not neighbor
        result = backEnd.Isneighbour(String.valueOf(country1.getName()), String.valueOf(country4.getName()));
        assertFalse(result);

        // attacker and defender are neighbor
        result = backEnd.Isneighbour(String.valueOf(country1.getName()), String.valueOf(country3.getName()));
        assertTrue(result);
    }

    /**
     * This method tests the number of dices which are chosen by attacker and defender whether is legal or not.
     */
    @Test
    public void testDices() {
//		the number of armies of attacker is 2;
        int attResult = 1;
        country1.setArmy(2);
        assertEquals(attResult, backEnd.atdics(country1.getArmy()));

//		the number of armies of attacker is 2; the number of armies of defender is 2;
        int defResult = 1;
        country3.setArmy(2);
        assertEquals(defResult, backEnd.dedics(backEnd.atdics(country1.getArmy()), country3.getArmy()));

//		the number of armies of attacker is 3;
        attResult = 2;
        country1.setArmy(3);
        assertEquals(attResult, backEnd.atdics(country1.getArmy()));

//		the number of armies of attacker is 3; the number of armies of defender is 2;
        defResult = 2;
        assertEquals(defResult, backEnd.dedics(backEnd.atdics(country1.getArmy()), country3.getArmy()));

//		the number of armies of attacker is more than 3;
        attResult = 3;
        country1.setArmy(4);
        assertEquals(attResult, backEnd.atdics(country1.getArmy()));

//		the number of armies of attacker is more than 3; the number of armies of defender is 1;
        defResult = 1;
        country3.setArmy(1);
        assertEquals(defResult, backEnd.dedics(backEnd.atdics(country1.getArmy()), country3.getArmy()));

//		the number of armies of attacker is more than 3; the number of armies of defender is more than 2;
        defResult = 2;
        country3.setArmy(3);
        assertEquals(defResult, backEnd.dedics(backEnd.atdics(country1.getArmy()), country3.getArmy()));
    }

    /**
     * This method tests armies transferred from attack country to captured country whether is correct or not.
     */
    @Test
    public void TestTransferArmy() {

        attack.setAttackCountry(String.valueOf(country1.getName()));
        attack.setDefendCountry(String.valueOf(country3.getName()));

        int transferNum = 3;
        int attA = 3;
        int defA = 3;

        country1.setArmy(6);
        attack.transferArmy(transferNum);
        assertEquals(attA, country1.getArmy());
        assertEquals(defA, country3.getArmy());

    }

    /**
     * This method tests the end of the game.
     */
    @Test
    public void TestWin() {
//		player1 captures country4 which belongs player2
        attack.setAttackCountry(String.valueOf(country2.getName()));
        attack.setDefendCountry(String.valueOf(country4.getName()));
        country2.setArmy(4);
        int[] range = {0,2};
        attack.updating(range);
        assertEquals(attack.findPlayer(String.valueOf(country2.getName())).getPlayerName(), attack.findPlayer(String.valueOf(country4.getName())).getPlayerName());

//		now country 1 2 4 belongs to player1, if player1 captures country3, he will be a game winner.
        attack.setAttackCountry(String.valueOf(country4.getName()));
        attack.setDefendCountry(String.valueOf(country3.getName()));

        range[1] = 1;
        attack.updating(range);
        assertTrue(Attack.isWIN());

    }


}
