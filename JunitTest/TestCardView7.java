import static org.junit.Assert.*;
import Model.Card;
import Model.InitializePhase;
import Model.Player;
import View.CardView;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;

/**
 *<h1>TestCardView7</h1>
 * This class implements card view test.
 *
 * @author youlin_liu
 * @version 3.0
 * @since 2019-03-21
 */

import java.util.HashMap;
import java.util.LinkedList;

public class TestCardView7 {

	CardView cardView;
	InitializePhase model;
	LinkedList<String> diffCards;
	LinkedList<String> sameCards;
	LinkedList<Card> currCard;

	@Before
	/**
	 * This method initializes all objects for test.
	 * 
	 * @throws IllegalArgumentException if diameter not in given range.
	 */
	public void setUp() throws Exception {

		model = new InitializePhase();
		cardView = new CardView(model);
		cardView.playerSet = new HashMap<>();
		cardView.boxes = new HashMap<>();
		cardView.boxes.put("0", "i");
		cardView.boxes.put("1", "c");
		cardView.boxes.put("2", "a");
		cardView.cards = new LinkedList<>();

		Player player1 = new Player("1");
		player1.setArmy(28);
		;
		player1.setColor(new Color(119, 240, 228));

		cardView.playerSet.put("1", player1);

		Card card1 = new Card();
		card1.setName("i");
		Card card2 = new Card();
		card2.setName("c");
		Card card3 = new Card();
		card3.setName("a");
		Card card4 = new Card();
		card4.setName("i");
		Card card5 = new Card();
		card5.setName("c");
		Card card6 = new Card();
		card6.setName("a");
		Card card7 = new Card();
		card7.setName("c");
		Card card8 = new Card();
		card8.setName("a");
		currCard = new LinkedList<>();
		currCard.add(card1);
		currCard.add(card2);
		currCard.add(card3);
		currCard.add(card4);
		currCard.add(card5);
		currCard.add(card6);
		currCard.add(card7);
		currCard.add(card8);

		sameCards = new LinkedList<>();
		sameCards.add("i");
		sameCards.add("i");
		sameCards.add("i");

		diffCards = new LinkedList<>();
		diffCards.add("i");
		diffCards.add("c");
		diffCards.add("a");
	}

	/**
	 * This method tests receive function.
	 * 
	 * Test case: "1": i-c-a-i-c-a-c-a
	 */
	@Test
	public void testreceive() {
		cardView.receive("1", currCard);
		int num = cardView.cards.size();
		assertEquals(num, 8);
	}

	/**
	 * This method tests is same function. 
	 * 
	 * Test case: i-i-i
	 */
	@Test
	public void testIsSame() {
		boolean flag = cardView.isSame(sameCards);
		assertEquals(flag, true);
	}

	/**
	 * This method tests is different function. 
	 * 
	 * Test case: i-c-a
	 */
	@Test
	public void testIsDifferent() {
		boolean flag = cardView.isDifferent(diffCards);
		assertEquals(flag, true);
	}

	/**
	 * This method tests is delete function. 
	 * 
	 * Test case: boxes: i-c-a card:
	 * i-c-a-i-c-a-c-a
	 */
	@Test
	public void testdelete() {
		cardView.receive("1", currCard);
		int numDelete = cardView.boxes.size();
		LinkedList<Card> reminder = cardView.delete();
		int reminderNum = reminder.size();

		assertEquals(numDelete, 3);
		assertEquals(reminderNum, 5);

		cardView.receive("1", reminder);
		int numberSecond = cardView.delete().size();
		System.out.println(numberSecond);
		assertEquals(numberSecond, 2);

	}
}
