package Model;

import java.awt.Color;
import java.awt.Point;

/**
 *<h1>Country</h1>
 * This class for defining a country. It contains all information of country. 
 *
 * @author jiamin_he
 * @version 3.0
 * @since 2019-02-28
 */
public class Country {
	private int name;
	private Point location;
	private int army;
	private Color color;
	private String continent;
	private String countryList = "";

	/**
	 * This is a no-argument constructor of IO.
	 */
	public Country(){
		
	}
	
	/**
	 * This is a constructor initializing all information of the country.
	 *
	 * @param name Country name.
	 * @param location The location of the country
	 * @param army The number of army.
	 * @param color The color of the country.
	 * @param continent The continent where the country is located.
	 * @param countryList All adjacent countries.
	 * 
	 */
	public Country(Integer name, Point location,
			int army, Color color, String continent,String countryList ) {
		
		this.name = name;
		this.location = location;
		
		this.army = army;
		this.color = color;
		this.continent = continent;
		this.countryList = countryList;
	}

	/**
	 * The method obtains country name.
	 *
	 * @return Country name.
	 */
	public int getName() {
		return name;
	}

	/**
	 * This method modifies country name.
	 *
	 * @param name Country name.
	 * 
	 */
	public void setName(Integer name) {
		this.name = name;
	}

	/**
	 * This method obtains the location of country.
	 *
	 * @return The location of country.
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * This method modifies the location of country.
	 *
	 * @param location The location of country.
	 * 
	 */
	public void setLocation(Point location) {
		this.location = location;
	}

	/**
	 * This method obtains the number of army.
	 *
	 * @return The number of army.
	 */
	public int getArmy() {
		return army;
	}

	/**
	 * This method modifies the number of army.
	 * @param army The number of army.
	 * 
	 */
	public void setArmy(int army) {
		this.army = army;
	}

	/**
	 * This method obtains the color of country
	 *
	 * @return The color of country.
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * This method modifies the color of country.
	 * @param color The color of country.
	 * 
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * This method obtains the continent which the country is located.
	 *
	 * @return Continent name.
	 */
	public String getContinent() {
		return continent;
	}

	/**
	 * This method modifies the continent which the country is located.
	 *
	 * @param continent Continent name.
	 * 
	 */
	public void setContinent(String continent) {
		this.continent = continent;
	}

	/**
	 * This method obtains adjacent country list.
	 *
	 * @return An adjacent country list.
	 */
	public String getCountryList() {
		return countryList;
	}

	/**
	 * The method modifies adjacent country list.
	 *
	 * @param countryList An adjacent country list.
	 *
	 */
	public void setCountryList(String countryList) {
		this.countryList = countryList;
	}
	

}
