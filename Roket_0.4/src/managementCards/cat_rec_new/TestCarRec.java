package managementCards.cat_rec_new;

import static managementCards.cards.Rank.Ace;
import static managementCards.cards.Rank.Eight;
import static managementCards.cards.Rank.Five;
import static managementCards.cards.Rank.Four;
import static managementCards.cards.Rank.Jack;
import static managementCards.cards.Rank.King;
import static managementCards.cards.Rank.Nine;
import static managementCards.cards.Rank.Queen;
import static managementCards.cards.Rank.Seven;
import static managementCards.cards.Rank.Six;
import static managementCards.cards.Rank.Ten;
import static managementCards.cards.Rank.Three;
import static managementCards.cards.Rank.Two;
import static managementCards.cards.Suit.Clubs;
import static managementCards.cards.Suit.Diamonds;
import static managementCards.cards.Suit.Hearts;
import static managementCards.cards.Suit.Spades;
import static managementCards.cat_rec_new.Cathegory.FLUSH;
import static managementCards.cat_rec_new.Cathegory.FOUR_OF_A_KIND;
import static managementCards.cat_rec_new.Cathegory.FULL_HOUSE;
import static managementCards.cat_rec_new.Cathegory.HIGH_CARD;
import static managementCards.cat_rec_new.Cathegory.PAIR;
import static managementCards.cat_rec_new.Cathegory.STRAIGHT;
import static managementCards.cat_rec_new.Cathegory.STRAIGHT_FLUSH;
import static managementCards.cat_rec_new.Cathegory.THREE_OF_A_KIND;
import static managementCards.cat_rec_new.Cathegory.TWO_PAIR;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import managementCards.cards.Card;
import managementCards.cards.Rank;

import org.junit.Test;

public class TestCarRec {

	Card Ac = Card.newInstance(Ace, Clubs);
	Card Ad = Card.newInstance(Ace, Diamonds);
	Card Ah = Card.newInstance(Ace, Hearts);
	Card As = Card.newInstance(Ace, Spades);

	Card Kc = Card.newInstance(King, Clubs);
	Card Kd = Card.newInstance(King, Diamonds);
	Card Kh = Card.newInstance(King, Hearts);
	Card Ks = Card.newInstance(King, Spades);

	Card Qc = Card.newInstance(Queen, Clubs);
	Card Qd = Card.newInstance(Queen, Diamonds);
	Card Qh = Card.newInstance(Queen, Hearts);
	Card Qs = Card.newInstance(Queen, Spades);

	Card Jc = Card.newInstance(Jack, Clubs);
	Card Jd = Card.newInstance(Jack, Diamonds);
	Card Jh = Card.newInstance(Jack, Hearts);
	Card Js = Card.newInstance(Jack, Spades);

	Card Tc = Card.newInstance(Ten, Clubs);
	Card Td = Card.newInstance(Ten, Diamonds);
	Card Th = Card.newInstance(Ten, Hearts);
	Card Ts = Card.newInstance(Ten, Spades);

	Card c9 = Card.newInstance(Nine, Clubs);
	Card d9 = Card.newInstance(Nine, Diamonds);
	Card h9 = Card.newInstance(Nine, Hearts);
	Card s9 = Card.newInstance(Nine, Spades);

	Card c8 = Card.newInstance(Eight, Clubs);
	Card d8 = Card.newInstance(Eight, Diamonds);
	Card h8 = Card.newInstance(Eight, Hearts);
	Card s8 = Card.newInstance(Eight, Spades);

	Card c7 = Card.newInstance(Seven, Clubs);
	Card d7 = Card.newInstance(Seven, Diamonds);
	Card h7 = Card.newInstance(Seven, Hearts);
	Card s7 = Card.newInstance(Seven, Spades);

	Card c6 = Card.newInstance(Six, Clubs);
	Card d6 = Card.newInstance(Six, Diamonds);
	Card h6 = Card.newInstance(Six, Hearts);
	Card s6 = Card.newInstance(Six, Spades);

	Card c5 = Card.newInstance(Five, Clubs);
	Card d5 = Card.newInstance(Five, Diamonds);
	Card h5 = Card.newInstance(Five, Hearts);
	Card s5 = Card.newInstance(Five, Spades);

	Card c4 = Card.newInstance(Four, Clubs);
	Card d4 = Card.newInstance(Four, Diamonds);
	Card h4 = Card.newInstance(Four, Hearts);
	Card s4 = Card.newInstance(Four, Spades);

	Card c3 = Card.newInstance(Three, Clubs);
	Card d3 = Card.newInstance(Three, Diamonds);
	Card h3 = Card.newInstance(Three, Hearts);
	Card s3 = Card.newInstance(Three, Spades);

	Card c2 = Card.newInstance(Two, Clubs);
	Card d2 = Card.newInstance(Two, Diamonds);
	Card h2 = Card.newInstance(Two, Hearts);
	Card s2 = Card.newInstance(Two, Spades);

	Card _2c = Card.newInstance(Ace, Diamonds);

	private ICatRec rec;
	IResult res;

	private ICatRec create() {
		return new Cat_Rec(Arrays.asList(), Arrays.asList());
	}

	private ICatRec create(Card hand1, Card hand2, Card... community) {
		return new Cat_Rec(Arrays.asList(hand1, hand2),
				Arrays.asList(community));
	}

	private IResult highCard(Rank... ranks) {
		return new ResultImpl(HIGH_CARD, ranks);
	}

	private IResult pair(Rank... ranks) {
		return new ResultImpl(PAIR, ranks);
	}

	private IResult twoPair(Rank... ranks) {
		return new ResultImpl(TWO_PAIR, ranks);
	}

	private IResult threeOfAKind(Rank... ranks) {
		return new ResultImpl(THREE_OF_A_KIND, ranks);
	}

	private IResult straight(Rank... ranks) {
		return new ResultImpl(STRAIGHT, ranks);
	}

	private IResult flush(Rank... ranks) {
		return new ResultImpl(FLUSH, ranks);
	}

	private IResult fullHouse(Rank... ranks) {
		return new ResultImpl(FULL_HOUSE, ranks);
	}

	private IResult fourOfAKind(Rank... ranks) {
		return new ResultImpl(FOUR_OF_A_KIND, ranks);
	}

	private IResult straightFlush(Rank... ranks) {
		return new ResultImpl(STRAIGHT_FLUSH, ranks);
	}

	@Test
	public void testStraightFlush() {
		rec = create(Ah, Kh, Qh, Jh, Th);
		res = straightFlush(Ace, King, Queen, Jack, Ten);
		assertTrue(rec.check().equals(res));

		rec = create(Qs, Ts, Ks, As, Js, s9);
		res = straightFlush(Ace, King, Queen, Jack, Ten);
		assertTrue(rec.check().equals(res));

		rec = create(s2, s5, s3, As, s4, Tc, c3);
		res = straightFlush(Five, Four, Three, Two, Ace);
		assertTrue(rec.check().equals(res));

		rec = create(s2, s5, s3, As, s4, Tc, c3, Ts, Th, Td);
		res = straightFlush(Five, Four, Three, Two, Ace);
		assertTrue(rec.check().equals(res));

		rec = create(s2, s5, s3, As, s4);
		res = straightFlush(Five, Four, Three, Two, Ace);
		assertTrue(rec.check().equals(res));

		rec = create(s2, s5);
		res = highCard(Five, Two);
		assertTrue(rec.check().equals(res));

		rec = create();
		res = highCard();
		assertTrue(rec.check().equals(res));

		rec = create(Qs, Ts, Ks, Ah, Js, Th, s9);
		res = straightFlush(King, Queen, Jack, Ten, Nine);
		assertTrue(rec.check().equals(res));

		rec = create(Qs, Td, Ks, Ah, Js, Th, s9);
		res = straight(Ace, King, Queen, Jack, Ten);
		assertTrue(rec.check().equals(res));
	}

	@Test
	public void testFourOfAKind() {
		rec = create(Ah, Kh, Qs, Jh, Th, Kd, Kh, Ks);
		res = fourOfAKind(King, King, King, King, Ace);
		assertTrue(rec.check().equals(res));

		rec = create(Qs, Qh, Qd, Qc); // simple
		res = fourOfAKind(Queen, Queen, Queen, Queen);
		assertTrue(rec.check().equals(res));

		rec = create(Qs, Qh);// too few cards
		res = pair(Queen, Queen);
		assertTrue(rec.check().equals(res));

		rec = create(Ks, Qs, s2, Qh, Qd, h3, Qc); // cards + 4of_a_kind
		res = fourOfAKind(Queen, Queen, Queen, Queen, King);
		assertTrue(rec.check().equals(res));

		rec = create(Ks, Qs, Js, Qh, Qd, As, Qc, Th);// straight + 4of_a_kind
		res = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
		assertTrue(rec.check().equals(res));

		rec = create(Ks, Qs, Js, Qh, Qd, As, Qc, s9); // flush + 4of_a_kind
		res = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
		assertTrue(rec.check().equals(res));

		rec = create(Ks, Qs, Ks, Qh, Qd, Ks, Qc); // full-house + 4of_a_kind
		res = fourOfAKind(Queen, Queen, Queen, Queen, King);
		assertTrue(rec.check().equals(res));

		rec = create(s2, c2, h2, d2, d4, c3, h3);
		res = fourOfAKind(Two, Two, Two, Two, Four);
		IResult x = rec.check();
		assertTrue(x + "", x.equals(res));
	}

	@Test
	public void testFullHouse() {
		rec = create(Ah, Kh, Qs, Jh, Th, Kd, Ks, Ts);
		res = fullHouse(King, King, King, Ten, Ten);
		assertTrue(rec.check().equals(res));

		rec = create(Qs, Qh, Qd, Ks, Kh); // simple
		res = fullHouse(Queen, Queen, Queen, King, King);
		assertTrue(rec.check().equals(res));

		rec = create(Jh, Tc, d9, Qs, Qh, Qd, Ks, Kh); // straight
		res = fullHouse(Queen, Queen, Queen, King, King);
		assertTrue(rec.check().equals(res));

		rec = create(Jh, Th, h8, Qs, Qh, Qd, Ks, Kh); // flush
		res = fullHouse(Queen, Queen, Queen, King, King);
		assertTrue(rec.check().equals(res));
	}

	@Test
	public void testFlush() {
		rec = create(Ah, Kh, Qs, Jh, Th, Ks, h5);// straight
		res = flush(Ace, King, Jack, Ten, Five);
		assertTrue(rec.check().equals(res));

		rec = create(Ah, Kh, Qs, Jh, Th, Ks, Kc, h8); // three-of-a-kind
		res = flush(Ace, King, Jack, Ten, Eight);
		assertTrue(rec.check().equals(res));

		rec = create(h2, Kh, Qh, Jh, Th); // 5 cards
		res = flush(King, Queen, Jack, Ten, Two);
		assertTrue(rec.check().equals(res));

		rec = create(h2, Kh); // too few cards
		res = highCard(King, Two);
		assertTrue(rec.check().equals(res));

		rec = create(s6, s8, c2, s7, Ac, Jh, Kc);
		res = highCard(Ace, King, Jack, Eight, Seven);
		assertTrue(rec.check().equals(res));
	}

	@Test
	public void testStraight() {
		rec = create(Ah, Ks, Qs, Jh, Th, Ks, h5);
		res = straight(Ace, King, Queen, Jack, Ten);
		assertTrue(rec.check().equals(res));

		rec = create(Ah, h5, s4, s3, h2); // round-a-corner
		res = straight(Five, Four, Three, Two, Ace);
		assertTrue(rec.check().equals(res));

		rec = create(As, h5, s4, s3, h2, h3, d3); // three-of-a-kind
		res = straight(Five, Four, Three, Two, Ace);
		assertTrue(rec.check().equals(res));

		rec = create(h9, h6, h5, h8, s7); // 5 cards
		res = straight(Nine, Eight, Seven, Six, Five);
		assertTrue(rec.check().equals(res));

		rec = create(h2, Kh); // too few cards
		res = highCard(King, Two);
		assertTrue(rec.check().equals(res));
	}

	@Test
	public void testThreeOfAKind() {
		rec = create(Ah, Ks, Js, Jh, Th, Js, h5);
		res = threeOfAKind(Jack, Jack, Jack, Ace, King);
		assertTrue(rec.check().equals(res));

		rec = create(h9, d9, As, h8, s9); // 5 cards
		res = threeOfAKind(Nine, Nine, Nine, Ace, Eight);
		assertTrue(rec.check().equals(res));

	}

	@Test
	public void testTwoPair() {
		rec = create(Ah, Ks, Js, Jh, Th, Kd, h5);
		res = twoPair(King, King, Jack, Jack, Ace);
		assertTrue(rec.check().equals(res));

		rec = create(h9, d9, As, h8, s8); // 5 cards
		res = twoPair(Nine, Nine, Eight, Eight, Ace);
		assertTrue(rec.check().equals(res));
	}

	@Test
	public void testPair() {
		rec = create(Ah, Ks, s4, Jh, Th, Kd, h5);
		res = pair(King, King, Ace, Jack, Ten);
		assertTrue(rec.check().equals(res));

		rec = create(h9, d9, As, h2, s8); // 5 cards
		res = pair(Nine, Nine, Ace, Eight, Two);
		assertTrue(rec.check().equals(res));

		rec = create(h2, d2); // poket-pair
		res = pair(Two, Two);
		assertTrue(rec.check().equals(res));

		rec = create(); // too few
		res = highCard();
		assertTrue(rec.check().equals(res));
	}

	@Test
	public void testHighCard() {
		rec = create(Ah, Ks, s4, Jh, Th, d8, h5);
		res = highCard(Ace, King, Jack, Ten, Eight);
		assertTrue(rec.check().equals(res));

		rec = create(h9, d7, Js, h2, s8); // 5 cards
		res = highCard(Jack, Nine, Eight, Seven, Two);
		assertTrue(rec.check().equals(res));

		rec = create(h2, d7); // hand
		res = highCard(Seven, Two);
		assertTrue(rec.check().equals(res));
	}

}
