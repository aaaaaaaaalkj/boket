package card_simulation;

import java.util.Arrays;
import java.util.List;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;
import old.Hand;

import org.junit.Test;

public class SimulationTest {
	@Test
	public void test() {
		Card first = new Card(Rank.Ace, Suit.Clubs);
		Card second = new Card(Rank.Ace, Suit.Diamonds);

		List<Card> community = Arrays.asList(
				new Card(Rank.Eight, Suit.Spades),
				new Card(Rank.Three, Suit.Spades),
				new Card(Rank.Ten, Suit.Hearts),
				new Card(Rank.Two, Suit.Diamonds),
				new Card(Rank.Queen, Suit.Spades)
				);

		long l = System.currentTimeMillis();

		double res = new CardSimulation(3, Hand.newInstance(first, second),
				community).run();

		l = System.currentTimeMillis() - l;

		System.out.println(l);

		System.out.println(res);

		// assertTrue(res > .1);
	}
}
