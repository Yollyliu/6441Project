import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Model.Checkmap;
import Model.Continent;
import Model.Country;
import Model.Message;

/**
 *<h1>TestCheckmap6</h1>
 * This class implements map validation test.
 *
 * @author chenwei_song
 * @version 1.6
 * @since 2019-03-04
 */
public class TestCheckmap6 {
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

		Country c1 = new Country(1, po1, 2, Color.blue, "A", "");
		countries.put("1", c1);
		
		ArrayList<String> arrayA = new ArrayList<>();
		arrayA.add("1");
	
		Continent continent1 = new Continent("A", 10, arrayA);
		continents.put("A", continent1);
		
		checkmap = new Checkmap(countries, continents);
	}

	/**
	 * This method tests judge function.
	 * Test case:
	 * A
	 * 1
	 * 1-2-3
	 */
	@Test
	public void test() {
		 checkmap.judge();
	        boolean result = Message.isSuccess();
		assertFalse(result);
	}

}
