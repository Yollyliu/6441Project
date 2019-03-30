import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import Model.Checkmap;
import Model.Continent;
import Model.Country;
import Model.Message;


public class TestCheckmap {

	  Checkmap check;
	    HashMap<String, Country> countries;
	    HashMap<String, Continent> continents;
	    Country country1, country2,country3;
	    Continent continent1,continent2, continent3;
	    
	@Before
	
	public void setUp() throws Exception {
		  this.countries = new HashMap<>();
	        this.continents = new HashMap<>();

	        this.country1 = new Country();
	        this.country2 = new Country();
	        this.country3 = new Country();

	        this.continent1 = new Continent();
	        this.continent2 = new Continent();
	        this.continent3 = new Continent();

	        continent1.setName("A");
	        continent2.setName("B");
	        continent3.setName("C");

	        country1.setName(1);
	        country1.setContinent(continent1.getName());
	        country2.setName(2);
	        country2.setContinent(continent2.getName());
	        country3.setName(3);
	        country3.setContinent(continent3.getName());

	        ArrayList<String> l1 = new ArrayList<>();
	        l1.add(String.valueOf(country1.getName()));
	        continent1.setCountryList(l1);

	        ArrayList<String> l2 = new ArrayList<>();
	        l2.add(String.valueOf(country2.getName()));
	        continent2.setCountryList(l2);

	        ArrayList<String> l3 = new ArrayList<>();
	        l3.add(String.valueOf(country3.getName()));
	        continent3.setCountryList(l3);

	        country1.setCountryList("2 ");
	        country2.setCountryList("1 3 ");
	        country3.setCountryList("2 ");

	        countries.put(String.valueOf(country1.getName()), country1);
	        countries.put(String.valueOf(country2.getName()), country2);
	        countries.put(String.valueOf(country3.getName()), country3);

	        continents.put(continent1.getName(), continent1);
	        continents.put(continent2.getName(), continent2);
	        continents.put(continent3.getName(), continent3);


	        check = new Checkmap(countries, continents);
		
	}

	@Test
	public void findAdjList() {
		  boolean flag = false;
	        ArrayList<String> result = new ArrayList<>();
	        result.add("A");
	        result.add("C");

	        ArrayList<String> find = new ArrayList<>(check.findAdjList(continent2));
	        boolean a  = result.equals(find);
	 //       System.out.println(a);
	        assertTrue(a);
	}
	
	@Test
    public void checkContinent() {

        boolean result = check.checkContinent();
        assertTrue(result);
    }
	
	@Test
    public void checkCountry() {

        boolean result = check.checkCountry(continent2.getCountryList());
        assertTrue(result);
    }
	
	 @Test
	    public void judge() {

	        check.judge();
	        boolean result = Message.isSuccess();
	        assertTrue(result);
	    }

}
