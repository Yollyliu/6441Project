import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.junit.Before;
import org.junit.Test;

import Model.Checkmap;
import Model.Continent;
import Model.Country;
import Model.Message;
import Model.Player;

/**
 *<h1>TestCheckmap2</h1>
 * This class implements map validation test.
 *
 * @author chenwei_song
 * @version 1.6
 * @since 2019-03-04
 */
public class TestCheckmap2 {
	
	Checkmap checkmap;
	@Before
	/**
	 *This method initializes all objects for test.
	 *@throws IllegalArgumentException if diameter not in given range.
	 */
	public void setUp() throws Exception {
		HashMap<String, Country> countries = new HashMap<>();
		HashMap<String,Continent> continents = new HashMap<>();
		Point po1 = new Point(100, 100);
		//new Country(name, location, army, color, Continent, countryList)
		Country c1 = new Country(1, po1, 2, Color.blue, "C", "2 ");
		countries.put("1", c1);
		
		Point po2 = new Point(200,200);
		Country c2 = new Country(2, po2,2,Color.blue,"B","2 3 ");
		countries.put("2", c2);
		
		Point po3 = new Point(300, 300);
		Country c3 = new Country(3, po3, 1,Color.green,"C", "2 ");
		countries.put("3", c3);
		
		ArrayList<String> arrayB = new ArrayList<>();
		arrayB.add("2");
		Continent continent2 = new Continent("B", 11, arrayB);
		continents.put("B",continent2);
		
		ArrayList<String> arrayC = new ArrayList<>();
		arrayC.add("1");
		arrayC.add("3");
		Continent continent3 = new Continent("C", 11, arrayC);
		continents.put("C",continent3);
		
	//	System.out.print(continents.size());
		checkmap = new Checkmap(countries, continents);
		
	}

	/**
	 * This method tests judge function.
	 * Test case:
	 * C-B-C
	 * 1-2-3
	 */
	@Test
	public void map1() {

		 checkmap.judge();
	        boolean result = Message.isSuccess();
		assertFalse(result);
	}

}
