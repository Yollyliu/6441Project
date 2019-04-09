package Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * <h1>Continent</h1> 
 * This class for defining a continent. It contains all
 * information of continent.
 *
 * @author jiamin_he
 * @version 3.0
 * @since 2019-02-28
 */
public class Continent implements Serializable {
	private String name;
	private int convalue = 0;
	private ArrayList<String> countryList;

	/**
	 * This is a constructor initializing all information of the continent.
	 *
	 * @param name        Continent name.
	 * @param cvalue      The control value of continent.
	 * @param countryList Countries included in the continent.
	 */
	public Continent(String name, int cvalue, ArrayList<String> countryList) {
		this.name = name;
		this.convalue = cvalue;
		this.countryList = countryList;
	}

	/**
	 * This is a no-argument constructor of Continent.
	 */
	public Continent() {

	}

	/**
	 * The method obtains continent name.
	 *
	 * @return Continent name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * The method modifies continent name.
	 *
	 * @param name Continent name.
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This method obtains the control value of continent.
	 *
	 * @return The control value of continent.
	 */
	public int getConvalue() {
		return convalue;
	}

	/**
	 * This method modifies the control value of continent.
	 *
	 * @param convalue Control value of continent.
	 * 
	 */
	public void setConvalue(int convalue) {
		this.convalue = convalue;
	}

	/**
	 * This method obtains all countries which are located in the continent.
	 *
	 * @return A list stores all countries located in the continent.
	 */
	public ArrayList<String> getCountryList() {
		return countryList;
	}

	/**
	 * The method modifies the country list.
	 *
	 * @param countryList A list stores all countries locates in the continent.
	 * 
	 */
	public void setCountryList(ArrayList<String> countryList) {
		this.countryList = countryList;
	}

}
