import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Model.Checkmap;
import Model.Continent;
import Model.Country;
import Model.IO;
import Model.Message;

/**
 *<h1>TestCheckmap2</h1>
 * This class tests reading an invalid map file.
 *
 * @author chenwei_song
 * @version 1.6
 * @since 2019-03-04
 */
public class TestIO {

	/**
	 * This method tests readFile function and judge function.
	 */
	@Test
	public void test() {

		String path = "mapfile/false.map";
		IO io = new IO();
		io.readFile(path);
		HashMap<String, Country> countries = new HashMap<>();
		HashMap<String,Continent> continents = new HashMap<>();
		
		countries = io.getCountries();
		continents = io.getContinents();
		Checkmap checkmap = new Checkmap(countries, continents);
		 checkmap.judge();
	        boolean result = Message.isSuccess();

		assertFalse(result);
	}

}
