import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import Model.Continent;
import Model.Country;
import Model.IO;
import Model.InitializePhase;
import Model.Player;

/**
 * 
 */

/**
 * @author songchenwei
 *
 */
public class TestPlayGame {
	 
	 
	 InitializePhase model;

	
	@Before
	/**
	 *This method initializes all objects for test.
	 *@throws IllegalArgumentException if diameter not in given range.
	 */
	public void setUp() throws Exception {
		model = new InitializePhase();
		HashMap<String, Player> playerSet = new HashMap<>();
		HashMap<String, Country> countries = new HashMap<>();
		HashMap<String,Continent> continents = new HashMap<>();
		Point po1 = new Point(100, 100);

		Country c1 = new Country(1, po1, 2, Color.blue, "A", "2 4 ");
		countries.put("1", c1);
		
		Point po2 = new Point(200,200);
		Country c2 = new Country(2, po2,2,Color.blue,"A","1 3 ");
		countries.put("2", c2);
		
		Point po3 = new Point(300, 300);
		Country c3 = new Country(3, po3, 1,Color.green,"B", "2 4 ");
		countries.put("3", c3);
		
		Point po4 = new Point(310, 310);
		Country c4 = new Country(4,po4, 1, Color.green, "B", "1 3 ");
		countries.put("4", c4);
		
		
		ArrayList<String> arrayA = new ArrayList<>();
		arrayA.add("1");
		arrayA.add("2");
		Continent continent1 = new Continent("A", 10, arrayA);
		continents.put("A", continent1);
		
		ArrayList<String> arrayB = new ArrayList<>();
		arrayB.add("3");
		arrayB.add("4");
		Continent continent2 = new Continent("B", 11, arrayB);
		continents.put("B",continent2);
		
		Player p1 = new Player("1");
		p1.setArmy(38);
		p1.setColor(Color.blue);
		LinkedList<Country> lA = new LinkedList<>();
		lA.add(c1);
		lA.add(c2);
		p1.setCountryList(lA);
		playerSet.put("1", p1);
		
		Player p2 = new Player("2");
		p2.setArmy(38);
		p2.setColor(Color.green);
		LinkedList<Country> lB = new LinkedList<>();
		p2.setCountryList(lB);
		lB.add(c3);
		lB.add(c4);
		playerSet.put("2", p2);

		model.setCountries(countries);
		model.setContinents(continents);
		model.setPlayerSet(playerSet);
	}

	/**
	 * Test method for {@link Model.InitializePhase#Startup(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testStartup() {

		model.Startup("1", "1");
		
		int result = model.getCountries().get("1").getArmy();

		assertEquals(result, 3);
		
	}

	/**
	 * Test method for {@link Model.InitializePhase#Reinforcement(java.lang.String)}.
	 */
	@Test
	public void testReinforcement() {

		model.Reinforcement("1");
		int number = model.getPlayerSet().get("1").getArmy();

		assertEquals(number, 13);
	}

	/**
	 * Test method for {@link Model.InitializePhase#SystemArmy(java.lang.String)}.
	 */
	@Test
	public void testSystemArmy() {

		model.Reinforcement("1");
		int number = model.SystemArmy("1");
		assertEquals(number, 3);
		
	}

	/**
	 * Test method for {@link Model.InitializePhase#ContinentArmy(java.lang.String)}.
	 */
	@Test
	public void testContinentArmy() {

		model.Reinforcement("1");
		int number = model.ContinentArmy("1");
		assertEquals(number, 10);
		
	}

	/**
	 * Test method for {@link Model.InitializePhase#Fortification(java.lang.String, java.lang.String, int)}.
	 */
	@Test
	public void testFortification() {

		model.Fortification("1", "2", 1);
		int n = model.getCountries().get("2").getArmy();
		assertEquals(n, 3);

	}

}
