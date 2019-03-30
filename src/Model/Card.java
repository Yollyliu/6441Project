package Model;

/**
 * <h1>Card</h1> 
 * This class for defining a card object.
 * 
 * @author jiamin_he
 * @version 3.0
 * @since 2019-03-01
 */
public class Card {

	String name;

	/**
	 * This method obtains card name.
	 * 
	 * @return Card name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * This method modifies card name.
	 * 
	 * @param name Card name.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
