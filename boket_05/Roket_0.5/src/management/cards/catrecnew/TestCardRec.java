package management.cards.catrecnew;

import static management.cards.cards.Card.TWO_CLUBS;
import static management.cards.cards.Card.THREE_CLUBS;
import static management.cards.cards.Card.NINE_CLUBS;
import static management.cards.cards.Card.ACE_CLUBS;
import static management.cards.cards.Card.JACK_CLUBS;
import static management.cards.cards.Card.KING_CLUBS;
import static management.cards.cards.Card.QUEEN_CLUBS;
import static management.cards.cards.Card.TEN_CLUBS;
import static management.cards.cards.Card.THREE_DIAMONDS;
import static management.cards.cards.Card.FOUR_DIAMONDS;
import static management.cards.cards.Card.SEVEN_DIAMONDS;
import static management.cards.cards.Card.EIGHT_DIAMONDS;
import static management.cards.cards.Card.NINE_DIAMONDS;
import static management.cards.cards.Card.KING_DIAMONDS;
import static management.cards.cards.Card.QUEEN_DIAMONDS;
import static management.cards.cards.Card.TEN_DIAMONDS;
import static management.cards.cards.Card.THREE_HEARTS;
import static management.cards.cards.Card.FIVE_HEARTS;
import static management.cards.cards.Card.SIX_HEARTS;
import static management.cards.cards.Card.SEVEN_HEARTS;
import static management.cards.cards.Card.EIGHT_HEARTS;
import static management.cards.cards.Card.NINE_HEARTS;
import static management.cards.cards.Card.ACE_HEARTS;
import static management.cards.cards.Card.JACK_HEARTS;
import static management.cards.cards.Card.KING_HEARTS;
import static management.cards.cards.Card.QUEEN_HEARTS;
import static management.cards.cards.Card.TEN_HEARTS;
import static management.cards.cards.Card.THREE_SPADES;
import static management.cards.cards.Card.FOUR_SPADES;
import static management.cards.cards.Card.FIVE_SPADES;
import static management.cards.cards.Card.SIX_SPADES;
import static management.cards.cards.Card.SEVEN_SPADES;
import static management.cards.cards.Card.EIGHT_SPADES;
import static management.cards.cards.Card.NINE_SPADES;
import static management.cards.cards.Card.ACE_SPADES;
import static management.cards.cards.Card.JACK_SPADES;
import static management.cards.cards.Card.KING_SPADES;
import static management.cards.cards.Card.QUEEN_SPADES;
import static management.cards.cards.Card.TEN_SPADES;
import static management.cards.cards.Card.TWO_DIAMONDS;
import static management.cards.cards.Card.TWO_HEARTS;
import static management.cards.cards.Card.TWO_SPADES;
import static management.cards.cards.Rank.Ace;
import static management.cards.cards.Rank.Eight;
import static management.cards.cards.Rank.Five;
import static management.cards.cards.Rank.Four;
import static management.cards.cards.Rank.Jack;
import static management.cards.cards.Rank.King;
import static management.cards.cards.Rank.Nine;
import static management.cards.cards.Rank.Queen;
import static management.cards.cards.Rank.Seven;
import static management.cards.cards.Rank.Six;
import static management.cards.cards.Rank.Ten;
import static management.cards.cards.Rank.Three;
import static management.cards.cards.Rank.Two;
import static management.cards.catrecnew.Cathegory.FLUSH;
import static management.cards.catrecnew.Cathegory.FOUR_OF_A_KIND;
import static management.cards.catrecnew.Cathegory.FULL_HOUSE;
import static management.cards.catrecnew.Cathegory.HIGH_CARD;
import static management.cards.catrecnew.Cathegory.PAIR;
import static management.cards.catrecnew.Cathegory.STRAIGHT;
import static management.cards.catrecnew.Cathegory.STRAIGHT_FLUSH;
import static management.cards.catrecnew.Cathegory.THREE_OF_A_KIND;
import static management.cards.catrecnew.Cathegory.TWO_PAIR;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

import management.cards.cards.Card;
import management.cards.cards.Rank;
import strategy.conditions.postflop.DrawType;
import tools.Tools;

public final class TestCardRec {

	private TestCardRec() {
		// do not instantiate
	}

	private static ICatRec rec = new CatRec();

	private static IResult create() {
		return rec.check(new ArrayList<>());
	}

	// private static StaticCatRec staticCatRec = new StaticCatRec();

	@SuppressWarnings("null")
	private static IResult create(final Card... cards) {
		return rec.check(Tools.asList(cards));

	}

	private static IResult highCard(@NonNull final Rank... ranks) {
		return new ResultImpl(HIGH_CARD, ranks);
	}

	private static IResult pair(@NonNull final Rank... ranks) {
		return new ResultImpl(PAIR, ranks);
	}

	private static IResult twoPair(@NonNull final Rank... ranks) {
		return new ResultImpl(TWO_PAIR, ranks);
	}

	private static IResult threeOfAKind(@NonNull final Rank... ranks) {
		return new ResultImpl(THREE_OF_A_KIND, ranks);
	}

	private static IResult straight(@NonNull final Rank... ranks) {
		return new ResultImpl(STRAIGHT, ranks);
	}

	private static IResult flush(@NonNull final Rank... ranks) {
		return new ResultImpl(FLUSH, ranks);
	}

	private static IResult fullHouse(@NonNull final Rank... ranks) {
		return new ResultImpl(FULL_HOUSE, ranks);
	}

	private static IResult fourOfAKind(@NonNull final Rank... ranks) {
		return new ResultImpl(FOUR_OF_A_KIND, ranks);
	}

	private static IResult straightFlush(@NonNull final Rank... ranks) {
		return new ResultImpl(STRAIGHT_FLUSH, ranks);
	}

	@Test
	public static void testStraightFlush() {
		IResult got;
		IResult expected;

		got = create(ACE_HEARTS, KING_HEARTS, QUEEN_HEARTS, JACK_HEARTS, TEN_HEARTS);
		expected = straightFlush(Ace, King, Queen, Jack, Ten);
		assertTrue(got.equals(expected));

		got = create(QUEEN_SPADES, TEN_SPADES, KING_SPADES, ACE_SPADES, JACK_SPADES, NINE_SPADES);
		expected = straightFlush(Ace, King, Queen, Jack, Ten);
		System.out.println(got);
		assertTrue(rec.toString(), got.equals(expected));

		got = create(TWO_SPADES, FIVE_SPADES, THREE_SPADES, ACE_SPADES, FOUR_SPADES, TEN_CLUBS, THREE_CLUBS);
		expected = straightFlush(Five, Four, Three, Two, Ace);
		assertTrue(got.equals(expected));

		got = create(TWO_SPADES, FIVE_SPADES, THREE_SPADES, ACE_SPADES, FOUR_SPADES, TEN_CLUBS, THREE_CLUBS, TEN_SPADES, TEN_HEARTS, TEN_DIAMONDS);
		expected = straightFlush(Five, Four, Three, Two, Ace);
		assertTrue(got.equals(expected));

		got = create(TWO_SPADES, FIVE_SPADES, THREE_SPADES, ACE_SPADES, FOUR_SPADES);
		expected = straightFlush(Five, Four, Three, Two, Ace);
		assertTrue(got.equals(expected));

		got = create(TWO_SPADES, FIVE_SPADES);
		expected = highCard(Five, Two);
		assertTrue(got.equals(expected));

		got = create();
		expected = highCard();
		assertTrue(got.equals(expected));

		got = create(QUEEN_SPADES, TEN_SPADES, KING_SPADES, ACE_HEARTS, JACK_SPADES, TEN_HEARTS, NINE_SPADES);
		expected = straightFlush(King, Queen, Jack, Ten, Nine);
		assertTrue(got.equals(expected));

		got = create(QUEEN_SPADES, TEN_DIAMONDS, KING_SPADES, ACE_HEARTS, JACK_SPADES, TEN_HEARTS, NINE_SPADES);
		expected = straight(Ace, King, Queen, Jack, Ten);
		assertTrue(got.equals(expected));
	}

	@Test
	public static void testFourOfAKind() {
		IResult got;
		IResult expected;

		got = create(ACE_HEARTS, KING_HEARTS, QUEEN_SPADES, JACK_HEARTS, TEN_HEARTS, KING_DIAMONDS, KING_HEARTS, KING_SPADES);
		expected = fourOfAKind(King, King, King, King, Ace);
		assertTrue(got.equals(expected));

		got = create(QUEEN_SPADES, QUEEN_HEARTS, QUEEN_DIAMONDS, QUEEN_CLUBS); // simple
		expected = fourOfAKind(Queen, Queen, Queen, Queen);
		assertTrue(got.equals(expected));

		got = create(QUEEN_SPADES, QUEEN_HEARTS); // too few cards
		expected = pair(Queen, Queen);
		assertTrue(got.equals(expected));

		got = create(KING_SPADES, QUEEN_SPADES, TWO_SPADES, QUEEN_HEARTS, QUEEN_DIAMONDS, THREE_HEARTS, QUEEN_CLUBS); // cards + 4of_a_kind
		expected = fourOfAKind(Queen, Queen, Queen, Queen, King);
		assertTrue(got.equals(expected));

		got = create(KING_SPADES, QUEEN_SPADES, JACK_SPADES, QUEEN_HEARTS, QUEEN_DIAMONDS, ACE_SPADES, QUEEN_CLUBS, TEN_HEARTS); // straight + 4of_a_kind
		expected = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
		assertTrue(got.equals(expected));

		got = create(KING_SPADES, QUEEN_SPADES, JACK_SPADES, QUEEN_HEARTS, QUEEN_DIAMONDS, ACE_SPADES, QUEEN_CLUBS, NINE_SPADES); // flush + 4of_a_kind
		expected = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
		assertTrue(got.equals(expected));

		got = create(KING_SPADES, QUEEN_SPADES, KING_SPADES, QUEEN_HEARTS, QUEEN_DIAMONDS, KING_SPADES, QUEEN_CLUBS); // full-house + 4of_a_kind
		expected = fourOfAKind(Queen, Queen, Queen, Queen, King);
		assertTrue(got.equals(expected));

		got = create(TWO_SPADES, TWO_CLUBS, TWO_HEARTS, TWO_DIAMONDS, FOUR_DIAMONDS, THREE_CLUBS, THREE_HEARTS);
		expected = fourOfAKind(Two, Two, Two, Two, Four);
		IResult x = got;
		assertTrue(x + "", x.equals(expected));
	}

	@Test
	public static void testFullHouse() {
		IResult got;
		IResult expected;
		got = create(ACE_HEARTS, KING_HEARTS, QUEEN_SPADES, JACK_HEARTS, TEN_HEARTS, KING_DIAMONDS, KING_SPADES, TEN_SPADES);
		expected = fullHouse(King, King, King, Ten, Ten);
		assertTrue(got.equals(expected));

		got = create(QUEEN_SPADES, QUEEN_HEARTS, QUEEN_DIAMONDS, KING_SPADES, KING_HEARTS); // simple
		expected = fullHouse(Queen, Queen, Queen, King, King);
		assertTrue(got.equals(expected));

		got = create(JACK_HEARTS, TEN_CLUBS, NINE_DIAMONDS, QUEEN_SPADES, QUEEN_HEARTS, QUEEN_DIAMONDS, KING_SPADES, KING_HEARTS); // straight
		expected = fullHouse(Queen, Queen, Queen, King, King);
		assertTrue(got.equals(expected));

		got = create(JACK_HEARTS, TEN_HEARTS, EIGHT_HEARTS, QUEEN_SPADES, QUEEN_HEARTS, QUEEN_DIAMONDS, KING_SPADES, KING_HEARTS); // flush
		expected = fullHouse(Queen, Queen, Queen, King, King);
		assertTrue(got.equals(expected));
	}

	@Test
	public static void testFlush() {
		IResult got;
		IResult expected;
		got = create(ACE_HEARTS, KING_HEARTS, QUEEN_SPADES, JACK_HEARTS, TEN_HEARTS, KING_SPADES, FIVE_HEARTS); // straight
		expected = flush(Ace, King, Jack, Ten, Five);
		assertTrue(got.equals(expected));

		got = create(ACE_HEARTS, KING_HEARTS, QUEEN_SPADES, JACK_HEARTS, TEN_HEARTS, KING_SPADES, KING_CLUBS, EIGHT_HEARTS); // three-of-a-kind
		expected = flush(Ace, King, Jack, Ten, Eight);
		assertTrue(got.equals(expected));

		got = create(TWO_HEARTS, KING_HEARTS, QUEEN_HEARTS, JACK_HEARTS, TEN_HEARTS); // 5 cards
		expected = flush(King, Queen, Jack, Ten, Two);
		assertTrue(got.equals(expected));

		got = create(TWO_HEARTS, KING_HEARTS); // too few cards
		expected = highCard(King, Two);
		assertTrue(got.equals(expected));

		got = create(SIX_SPADES, EIGHT_SPADES, TWO_CLUBS, SEVEN_SPADES, ACE_CLUBS, JACK_HEARTS, KING_CLUBS);
		expected = highCard(Ace, King, Jack, Eight, Seven);
		assertTrue(got.equals(expected));
	}

	@Test
	public static void testStraight() {
		IResult got;
		IResult expected;
		got = create(ACE_HEARTS, KING_SPADES, QUEEN_SPADES, JACK_HEARTS, TEN_HEARTS, KING_SPADES, FIVE_HEARTS);
		expected = straight(Ace, King, Queen, Jack, Ten);
		assertTrue("expected " + expected + " but got " + got,
				got.equals(expected));

		got = create(ACE_HEARTS, FIVE_HEARTS, FOUR_SPADES, THREE_SPADES, TWO_HEARTS); // round-a-corner
		expected = straight(Five, Four, Three, Two, Ace);
		assertTrue(got.equals(expected));

		got = create(ACE_SPADES, FIVE_HEARTS, FOUR_SPADES, THREE_SPADES, TWO_HEARTS, THREE_HEARTS, THREE_DIAMONDS); // three-of-a-kind
		expected = straight(Five, Four, Three, Two, Ace);
		assertTrue(got.equals(expected));

		got = create(NINE_HEARTS, SIX_HEARTS, FIVE_HEARTS, EIGHT_HEARTS, SEVEN_SPADES); // 5 cards
		expected = straight(Nine, Eight, Seven, Six, Five);
		assertTrue(got.equals(expected));

		got = create(TWO_HEARTS, KING_HEARTS); // too few cards
		expected = highCard(King, Two);
		assertTrue(got.equals(expected));
	}

	@Test
	public static void testThreeOfAKind() {
		IResult got;
		IResult expected;
		got = create(ACE_HEARTS, KING_SPADES, JACK_SPADES, JACK_HEARTS, TEN_HEARTS, JACK_SPADES, FIVE_HEARTS);
		expected = threeOfAKind(Jack, Jack, Jack, Ace, King);
		assertTrue(got.equals(expected));

		got = create(NINE_HEARTS, NINE_DIAMONDS, ACE_SPADES, EIGHT_HEARTS, NINE_SPADES); // 5 cards
		expected = threeOfAKind(Nine, Nine, Nine, Ace, Eight);
		assertTrue(got.equals(expected));

	}

	@Test
	public static void testTwoPair() {
		IResult got;
		IResult expected;
		got = create(ACE_HEARTS, KING_SPADES, JACK_SPADES, JACK_HEARTS, TEN_HEARTS, KING_DIAMONDS, FIVE_HEARTS);
		expected = twoPair(King, King, Jack, Jack, Ace);
		assertTrue(got.equals(expected));

		got = create(NINE_HEARTS, NINE_DIAMONDS, ACE_SPADES, EIGHT_HEARTS, EIGHT_SPADES); // 5 cards
		expected = twoPair(Nine, Nine, Eight, Eight, Ace);
		assertTrue("expected " + expected + " but got " + got,
				got.equals(expected));
	}

	@Test
	public static void testPair() {
		IResult got;
		IResult res;
		got = create(ACE_HEARTS, KING_SPADES, FOUR_SPADES, JACK_HEARTS, TEN_HEARTS, KING_DIAMONDS, FIVE_HEARTS);
		res = pair(King, King, Ace, Jack, Ten);
		assertTrue(got.equals(res));

		got = create(NINE_HEARTS, NINE_DIAMONDS, ACE_SPADES, TWO_HEARTS, EIGHT_SPADES); // 5 cards
		res = pair(Nine, Nine, Ace, Eight, Two);
		assertTrue(got.equals(res));

		got = create(TWO_HEARTS, TWO_DIAMONDS); // poket-pair
		res = pair(Two, Two);
		assertTrue(got.equals(res));

		got = create(); // too few
		res = highCard();
		assertTrue(got.equals(res));
	}

	@Test
	public static void testHighCard() {
		IResult got;
		IResult res;
		got = create(ACE_HEARTS, KING_SPADES, FOUR_SPADES, JACK_HEARTS, TEN_HEARTS, EIGHT_DIAMONDS, FIVE_HEARTS);
		res = highCard(Ace, King, Jack, Ten, Eight);
		assertTrue(got.equals(res));

		got = create(NINE_HEARTS, SEVEN_DIAMONDS, JACK_SPADES, TWO_HEARTS, EIGHT_SPADES); // 5 cards
		res = highCard(Jack, Nine, Eight, Seven, Two);
		assertTrue(got.equals(res));

		got = create(TWO_HEARTS, SEVEN_DIAMONDS); // hand
		res = highCard(Seven, Two);
		assertTrue(got.equals(res));
	}

	@Test
	public static void testDraw1() {
		DrawType res;

		res = checkDraw(ACE_HEARTS, KING_SPADES, QUEEN_SPADES, TEN_SPADES, EIGHT_HEARTS, SEVEN_DIAMONDS, SIX_HEARTS);
		assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

		res = checkDraw(KING_SPADES, QUEEN_SPADES, TEN_SPADES, NINE_CLUBS, SEVEN_DIAMONDS, SIX_HEARTS);
		assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

		res = checkDraw(QUEEN_SPADES, TEN_SPADES, NINE_CLUBS, EIGHT_DIAMONDS, SIX_HEARTS);
		assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

		res = checkDraw(THREE_SPADES, QUEEN_SPADES, TEN_SPADES, NINE_CLUBS, EIGHT_DIAMONDS, SIX_HEARTS);
		assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

		res = checkDraw(ACE_SPADES, THREE_CLUBS, QUEEN_SPADES, TEN_SPADES, NINE_CLUBS, EIGHT_DIAMONDS, SIX_HEARTS);
		assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

		res = checkDraw(ACE_HEARTS, KING_SPADES, QUEEN_SPADES, TEN_SPADES, EIGHT_HEARTS, SEVEN_DIAMONDS, FIVE_HEARTS);
		assertTrue(res.toString(), res == DrawType.GUTSHOT);

		res = checkDraw(ACE_HEARTS, KING_SPADES, QUEEN_SPADES, THREE_SPADES, EIGHT_HEARTS, SEVEN_DIAMONDS, SIX_HEARTS);
		assertTrue(res.toString(), res == DrawType.NONE);

		res = checkDraw(ACE_HEARTS, KING_SPADES, QUEEN_SPADES, THREE_SPADES, EIGHT_HEARTS, SEVEN_HEARTS, SIX_HEARTS);
		assertTrue(res.toString(), res == DrawType.FLUSH_DRAW);

		res = checkDraw(KING_SPADES, QUEEN_SPADES, JACK_CLUBS, TEN_SPADES, EIGHT_HEARTS);
		assertTrue(res.toString(), res == DrawType.OESD);

		res = checkDraw(ACE_HEARTS, KING_SPADES, QUEEN_SPADES, TEN_SPADES, EIGHT_HEARTS, SEVEN_HEARTS, FIVE_HEARTS);
		assertTrue(res.toString(), res == DrawType.FLUSH_DRAW);

		res = checkDraw(ACE_HEARTS, KING_HEARTS, QUEEN_SPADES, TEN_SPADES, EIGHT_HEARTS, SEVEN_DIAMONDS, SIX_HEARTS);
		assertTrue(res.toString(), res == DrawType.MONSTER_DRAW);

		res = checkDraw(KING_SPADES, QUEEN_SPADES, JACK_SPADES, TEN_SPADES, EIGHT_HEARTS);
		assertTrue(res.toString(), res == DrawType.MONSTER_DRAW);

	}

	@SuppressWarnings("null")
	private static DrawType checkDraw(final Card... cards) {
		return rec.checkDraw(Tools.asList(cards));
	}

}
