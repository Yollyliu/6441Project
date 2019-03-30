package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * <h1>CheckMap</h1> 
 * The CheckMap class implements judge function for checking
 * the map whether is a valid map.
 *
 * @author jiamin_he, youlin_liu, chenwei_song, shuo_chi, tianshu_ji
 * @version 3.0
 * @since 2019-02-28
 */
public class Checkmap {

	private HashMap<String, Country> countries;
	private HashMap<String, Continent> continents;
	private String error = "";

	/**
	 * This is model.Checkmap constructor.
	 *
	 * @param countries  A hash map stores all countries which are in the map.
	 * @param continents A hash map stores all continents in which are in the map.
	 *
	 */
	public Checkmap(HashMap<String, Country> countries, HashMap<String, Continent> continents) {

		this.countries = countries;
		this.continents = continents;
	}

	/**
	 * This method checks a map whether is a valid map.
	 */
	public void judge() {

		boolean checkContinents = false;
		boolean checkCountries = false;

		if (countries.isEmpty() || continents.isEmpty() || countries.size() == 1) {
			if (countries.isEmpty() && continents.isEmpty()) {

				Message.setSuccess(false);
				Message.setMessage("Invalid Map - This is an empty Map.");
				return;

			} else {
				Message.setSuccess(false);
				Message.setMessage("Invalid Map - The map contains only one country.");
				return;
			}

		} else if (continents.size() == 1) {

			checkContinents = true;

		} else {

			checkContinents = checkContinent();
			if (checkContinents == false) {
				Message.setSuccess(false);
				Message.setMessage("Invalid Map - Error in Continent " + this.error);
				return;
			}
		}

		for (Map.Entry<String, Continent> entry : continents.entrySet()) {
			checkCountries = checkCountry(entry.getValue().getCountryList());
			if (checkCountries == false) {
				Message.setSuccess(false);
				Message.setMessage(
						"Invalid Map - Error in Country " + this.error + " which is in Continent " + entry.getKey());
				return;
			}
		}

		if (checkCountries && checkContinents) {
			Message.setSuccess(true);
			Message.setMessage("Valid Map.");
			return;
		} else {
			Message.setSuccess(false);
			Message.setMessage("Invalid Map.");
			return;
		}
	}

	/**
	 * This method checks all continents whether connect with each other.
	 *
	 * @return true if all continents connects with each other; otherwise return
	 *         false.
	 */
	public boolean checkContinent() {

		LinkedList<String> checkList = new LinkedList<>();
		LinkedList<String> copy = new LinkedList<>();
		LinkedList<String> queue = new LinkedList<>();

		for (Map.Entry<String, Continent> entry : continents.entrySet()) {
			checkList.addLast(entry.getKey());
		}

		String tmp1 = checkList.peekFirst();
		queue.addLast(tmp1);
		copy.addLast(tmp1);

		while (!queue.isEmpty()) {
			String tmp = queue.pollFirst();
			LinkedList<String> adjList = findAdjList(continents.get(tmp));

			for (String str : adjList) {
				if (!queue.contains(str) && !copy.contains(str)) {
					queue.addLast(str);
					copy.addLast(str);
				}

			}
		}

		for (String c : checkList) {
			if (!copy.contains(c)) {
				this.error = c;
				return false;
			}
		}

		return true;
	}

	/**
	 * This method checks all countries in same continent whether connect with each
	 * other.
	 *
	 * @param countryList The list stores all countries which are in the same
	 *                    continent.
	 * @return true if all countries are connected, otherwise return false.
	 */
	public boolean checkCountry(ArrayList<String> countryList) {

		if (countryList.size() == 1) {
			return true;
		}

		LinkedList<String> checkList = new LinkedList<>(countryList);
		LinkedList<String> copy = new LinkedList<>();
		LinkedList<String> queue = new LinkedList<>();

		String tmp1 = checkList.peekFirst();
		queue.addLast(tmp1);
		copy.addLast(tmp1);

		while (!queue.isEmpty()) {
			String tmp = queue.pollFirst();
			if (countries.get(tmp).getCountryList() == null) {
				continue;
			}
			String[] adjList = countries.get(tmp).getCountryList().split(" ");

			if (adjList[0].equals("") || countries.get(tmp).getCountryList() == null) {
				continue;
			} else {
				for (String str : adjList) {
					if (countries.get(tmp1).getContinent().equalsIgnoreCase(countries.get(str).getContinent())) {
						if (!queue.contains(str) && !copy.contains(str)) {
							queue.addLast(str);
							copy.addLast(str);
						}
					}
				}
			}
		}

		for (String str : checkList) {
			if (!copy.contains(str)) {
				this.error = str;
				return false;
			}
		}
		return true;
	}

	/**
	 * This method aims at finding current continent's neighbor continent.
	 *
	 * @param continent it is a continent
	 * @return A list stores all continents which are neighbor of the current
	 *         continent.
	 */
	public LinkedList<String> findAdjList(Continent continent) {

		LinkedList<String> adjList = new LinkedList<>();
		for (String str : continent.getCountryList()) {
			Country country = countries.get(str);

			if (country.getCountryList() == null || country.getCountryList().isEmpty()) {
				continue;
			} else {

				String[] countList = country.getCountryList().split(" ");

				for (String string : countList) {

					String adjContinent = countries.get(string).getContinent();
					if (adjContinent.equalsIgnoreCase(country.getContinent())) {
						continue;
					} else {
						adjList.addLast(adjContinent);
					}
				}
			}
		}
		return adjList;
	}
}