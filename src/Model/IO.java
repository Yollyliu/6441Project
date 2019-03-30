package Model;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * <h1>IO</h1> 
 * The IO class implements read file and write file from a
 * particular path.
 *
 * @author jiamin_he
 * @version 3.0
 * @since 2019-02-28
 */
public class IO extends Observable {
	private HashMap<String, Country> countries;
	private HashMap<String, Continent> continents;
	private String image;
	private String wrap;
	private String scroll;
	private String author;
	private String warn;
	private String path;

	/**
	 * This is a no-argument constructor of IO.
	 */
	public IO() {
		this.image = "";
		this.wrap = "";
		this.scroll = "";
		this.author = "";
		this.warn = "";
		this.path = "";
	}

	/**
	 * This is a constructor of IO and initializes all parameters.
	 *
	 * @param countries  A hash map stores all countries which are in the map.
	 * @param continents A hash map stores all continents in which are in the map.
	 */
	public IO(HashMap<String, Country> countries, HashMap<String, Continent> continents) {
		this.countries = countries;
		this.continents = continents;
		this.image = "";
		this.wrap = "";
		this.scroll = "";
		this.author = "";
		this.warn = "";
	}

	/**
	 * This method obtains file path.
	 * 
	 * @return File path.
	 */
	public String getPath() {
		return path;
	}

	/**
	 * This method obtains a hash map which stores all countries in the map.
	 *
	 * @return A hash map which stores all countries in the map.
	 */
	public HashMap<String, Country> getCountries() {
		return countries;
	}

	/**
	 * This method obtains a hash map which stores all continents in the map.
	 *
	 * @return A hash map which stores all continents in the map.
	 */
	public HashMap<String, Continent> getContinents() {
		return continents;
	}

	/**
	 * This method reads a particular map file and builds a map.
	 *
	 * @param filePath The specified path of the map file.
	 */
	public void readFile(String filePath) {

		path = filePath;
		String suffix = filePath.substring(filePath.indexOf(".") + 1, filePath.length());

		if (!suffix.equals("map")) {

			Message.setSuccess(false);
			Message.setMessage("This is not a map file.");
			return;

		} else {

			this.continents = new HashMap<>();
			this.countries = new HashMap<>();

			String line = "";
			String data = "";

			try {
				BufferedReader reader = new BufferedReader(new FileReader(filePath));
				line = reader.readLine();
				while (line != null) {
					data = data + line + "\n";
					line = reader.readLine();
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

			String[] dataScetion = data.split("\n\n");
			for (String str : dataScetion) {
				String[] info = str.split("\n");
				if (!info[0].isEmpty()) {
					switch (info[0]) {
					case "[Map]":
						break;
					case "[Continents]":
						continentSection(info, this.continents);
						break;
					case "[Territories]":
						countriesSection(info, this.continents, this.countries);
						break;
					default:
						break;
					}
				}
			}

			if (countries.isEmpty() && continents.isEmpty()) {
				Message.setSuccess(false);
				Message.setMessage("This is an empty map.");
				return;
			}

			Message.setSuccess(true);
			Message.setMessage("ReadFile finished.");
			setChanged();
			notifyObservers(this);
			return;
		}
	}

	/**
	 * This method initializes all continents.
	 *
	 * @param data       A string array stores all continents' information, like
	 *                   control value, continent name.
	 * @param continents A hash map stores all continents.
	 */
	private void continentSection(String[] data, HashMap<String, Continent> continents) {

		for (int i = 1; i < data.length; i++) {
			String[] str = data[i].split("=");
			Continent continent = new Continent();
			continent.setName(str[0]);
			continent.setConvalue(Integer.parseInt(str[1]));

			// modify
			continent.setCountryList(new ArrayList<String>());
			continents.put(continent.getName(), continent);
		}
	}

	/**
	 * This method initializes all countries.
	 *
	 * @param data       A string array stores all countries' information, like
	 *                   location, name, adjacent countries.
	 * @param continents A hash map stores all continents.
	 * @param countries  A hash map stores all countries.
	 */
	private void countriesSection(String[] data, HashMap<String, Continent> continents,
			HashMap<String, Country> countries) {

		for (int i = 1; i < data.length; i++) {
			String[] str = data[i].split(",");
			Country country = new Country();

			country.setName(Integer.parseInt(str[0]));
			country.setLocation(new Point(Integer.parseInt(str[1]), Integer.parseInt(str[2])));

			country.setContinent(str[3]);

			for (Map.Entry<String, Continent> entry : continents.entrySet()) {
				if (entry.getValue().getName().equalsIgnoreCase(str[3])) {
					entry.getValue().getCountryList().add(str[0]);
				}
			}

			country.setCountryList(toString(Arrays.copyOfRange(str, 4, str.length)));
			countries.put(String.valueOf(country.getName()), country);
		}

	}

	/**
	 * This method convert array to string.
	 *
	 * @param strings A string array stores all adjacent countries of current
	 *                country.
	 * @return A string stores all adjacent countries of current country.
	 */
	private String toString(String[] strings) {
		String tmp = "";
		for (int i = 0; i < strings.length; i++) {
			tmp = tmp + strings[i];
			if (i != strings.length - 1) {
				tmp += " ";
			}
		}
		return tmp;
	}

	/**
	 * This method output a map file to a specified folder.
	 *
	 * @param fileName name of map file.
	 * @return true if writes a map file successfully, otherwise return false.
	 */
	public boolean writeFile(String fileName) {
		if (continents.isEmpty() || countries.isEmpty()) {
			return false;
		} else {
			String suffix = ".map";
			String path = "mapfile/" + fileName + suffix;
			this.image = fileName + ".bmp";
			this.wrap = "yes";
			this.scroll = "none";
			this.author = "Soen6441_team_25";
			this.warn = "yes";

			String header_1 = "[Map]\nimage=" + this.image + "\nwrap=" + this.wrap + "\nscroll=" + this.scroll
					+ "\nauthor=" + this.author + "\nwarn=" + this.warn + "\n";
			String header_2 = "[Continents]\n";
			String header_3 = "[Territories]\n";
			String blankLine = "\n";
			String comma = ",";

			File newFile = new File(path);
			if (!newFile.exists()) {
				try {
					newFile.createNewFile();
					FileWriter writer = new FileWriter(path, true);
					writer.write(header_1 + blankLine);

//              This is continent section;
					writer.write(header_2);
					for (Map.Entry<String, Continent> entry : continents.entrySet()) {
						Continent continent = entry.getValue();
						writer.write(continent.getName() + "=" + continent.getConvalue() + "\n");

					}
//
					writer.write(blankLine);

//              This is territories section;
					writer.write(header_3);
					for (Map.Entry<String, Continent> entry : continents.entrySet()) {
						Continent continent = entry.getValue();
						for (String string : continent.getCountryList()) {
							String line = string + comma;
							Country country = countries.get(string);
							line = line + (int) country.getLocation().getX() + comma
									+ (int) country.getLocation().getY() + comma;
							line = line + continent.getName() + comma;
							line = line + country.getCountryList().trim().replaceAll(" ", comma) + "\n";
							writer.write(line);
						}
					}
					writer.close();
					return true;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
		}
		return false;
	}

	/**
	 * The method rewrites a map file existed.
	 *
	 * @param filePath A specified path of the map file.
	 * @return true if rewrites successfully, otherwise return false.
	 */
	public boolean rewriteFile(String filePath) {

		File file = new File(filePath);
		if (file.exists()) {

			file.delete();

			String[] str = filePath.split("/");
			String fileName = str[str.length - 1];
			fileName = fileName.substring(0, fileName.indexOf("."));

			return writeFile(fileName);

		}

		return false;
	}
}
