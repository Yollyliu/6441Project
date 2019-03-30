import static org.junit.Assert.assertFalse;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Model.Checkmap;
import Model.Continent;
import Model.Country;
import Model.Message;

/**
 *<h1>TestCheckmap5</h1>
 * This class implements map validation test.
 *
 * @author chenwei_song
 * @version 1.6
 * @since 2019-03-04
 */
public class TestCheckmap5 {
	Checkmap checkmap;

	
	@Before
	/**
	 * This method initializes all objects for test.
	 * @throws exceptions exceptions
	 * 
	 */
	public void setUp() throws Exception {


		HashMap<String, Country> countries = new HashMap<>();
		HashMap<String,Continent> continents = new HashMap<>();
		//Checkmap checkmap;
		Point po1 = new Point(100, 100);
		//new Country(name, location, army, color, Continent, countryList)
		Country c1 = new Country(1, po1, 2, Color.blue, "A", "2 ");
		countries.put("1", c1);
		
		Point po2 = new Point(200,200);
		Country c2 = new Country(2, po2,2,Color.blue,"A","1 ");
		countries.put("2", c2);
		
		Point po3 = new Point(300, 300);
		Country c3 = new Country(3, po3, 1,Color.green,"B", "4 ");
		countries.put("3", c3);
		
		Point po4 = new Point(300, 300);
		Country c4 = new Country(4, po4, 1,Color.green,"B", "3 ");
		countries.put("4", c4);

		ArrayList<String> arrayA = new ArrayList<>();
		arrayA.add("1");
		arrayA.add("2");
	
		Continent continent1 = new Continent("A", 10, arrayA);
		continents.put("A", continent1);

		ArrayList<String> arrayB = new ArrayList<>();
		arrayB.add("3");
		arrayB.add("4");
	
		Continent continent2 = new Continent("B", 10, arrayA);
		continents.put("B", continent2);
		checkmap = new Checkmap(countries, continents);
	}

	/**
	 * This method tests judge function.
	 * Test case:
	 * A-A B-B
	 * 1-2 3-4
	 */
	@Test
	public void test() {
	
		 checkmap.judge();
	        boolean result = Message.isSuccess();
		// 1-2 3-4
		//A-A B-B
		assertFalse(result);
	}
}
