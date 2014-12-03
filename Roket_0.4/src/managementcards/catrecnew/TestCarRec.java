package managementcards.catrecnew;

import static managementcards.cards.Card.Ac;
import static managementcards.cards.Card.Ah;
import static managementcards.cards.Card.As;
import static managementcards.cards.Card.Jh;
import static managementcards.cards.Card.Js;
import static managementcards.cards.Card.Kc;
import static managementcards.cards.Card.Kd;
import static managementcards.cards.Card.Kh;
import static managementcards.cards.Card.Ks;
import static managementcards.cards.Card.Qc;
import static managementcards.cards.Card.Qd;
import static managementcards.cards.Card.Qh;
import static managementcards.cards.Card.Qs;
import static managementcards.cards.Card.Tc;
import static managementcards.cards.Card.Td;
import static managementcards.cards.Card.Th;
import static managementcards.cards.Card.Ts;
import static managementcards.cards.Card._2c;
import static managementcards.cards.Card._2d;
import static managementcards.cards.Card._2h;
import static managementcards.cards.Card._2s;
import static managementcards.cards.Card._3c;
import static managementcards.cards.Card._3d;
import static managementcards.cards.Card._3h;
import static managementcards.cards.Card._3s;
import static managementcards.cards.Card._4d;
import static managementcards.cards.Card._4s;
import static managementcards.cards.Card._5h;
import static managementcards.cards.Card._5s;
import static managementcards.cards.Card._6h;
import static managementcards.cards.Card._6s;
import static managementcards.cards.Card._7d;
import static managementcards.cards.Card._7s;
import static managementcards.cards.Card._8d;
import static managementcards.cards.Card._8h;
import static managementcards.cards.Card._8s;
import static managementcards.cards.Card._9d;
import static managementcards.cards.Card._9h;
import static managementcards.cards.Card._9s;
import static managementcards.cards.Rank.Ace;
import static managementcards.cards.Rank.Eight;
import static managementcards.cards.Rank.Five;
import static managementcards.cards.Rank.Four;
import static managementcards.cards.Rank.Jack;
import static managementcards.cards.Rank.King;
import static managementcards.cards.Rank.Nine;
import static managementcards.cards.Rank.Queen;
import static managementcards.cards.Rank.Seven;
import static managementcards.cards.Rank.Six;
import static managementcards.cards.Rank.Ten;
import static managementcards.cards.Rank.Three;
import static managementcards.cards.Rank.Two;
import static managementcards.catrecnew.Cathegory.FLUSH;
import static managementcards.catrecnew.Cathegory.FOUR_OF_A_KIND;
import static managementcards.catrecnew.Cathegory.FULL_HOUSE;
import static managementcards.catrecnew.Cathegory.HIGH_CARD;
import static managementcards.catrecnew.Cathegory.PAIR;
import static managementcards.catrecnew.Cathegory.STRAIGHT;
import static managementcards.catrecnew.Cathegory.STRAIGHT_FLUSH;
import static managementcards.catrecnew.Cathegory.THREE_OF_A_KIND;
import static managementcards.catrecnew.Cathegory.TWO_PAIR;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import managementcards.cards.Card;
import managementcards.cards.Rank;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

public class TestCarRec {

  @SuppressWarnings("null")
  private ICatRec create() {
    return new CatRec(Arrays.asList(), Arrays.asList());
  }

  @SuppressWarnings("null")
  private ICatRec create(final Card hand1, final Card han_2d, final Card... community) {
    return new CatRec(Arrays.asList(hand1, han_2d),
        Arrays.asList(community));
  }

  private IResult highCard(@NonNull final Rank... ranks) {
    return new ResultImpl(HIGH_CARD, ranks);
  }

  private IResult pair(@NonNull final Rank... ranks) {
    return new ResultImpl(PAIR, ranks);
  }

  private IResult twoPair(@NonNull final Rank... ranks) {
    return new ResultImpl(TWO_PAIR, ranks);
  }

  private IResult threeOfAKind(@NonNull final Rank... ranks) {
    return new ResultImpl(THREE_OF_A_KIND, ranks);
  }

  private IResult straight(@NonNull final Rank... ranks) {
    return new ResultImpl(STRAIGHT, ranks);
  }

  private IResult flush(@NonNull final Rank... ranks) {
    return new ResultImpl(FLUSH, ranks);
  }

  private IResult fullHouse(@NonNull final Rank... ranks) {
    return new ResultImpl(FULL_HOUSE, ranks);
  }

  private IResult fourOfAKind(@NonNull final Rank... ranks) {
    return new ResultImpl(FOUR_OF_A_KIND, ranks);
  }

  private IResult straightFlush(@NonNull final Rank... ranks) {
    return new ResultImpl(STRAIGHT_FLUSH, ranks);
  }

  @Test
  public final void testStraightFlush() {
    ICatRec rec;
    IResult res;

    rec = create(Ah, Kh, Qh, Jh, Th);
    res = straightFlush(Ace, King, Queen, Jack, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(Qs, Ts, Ks, As, Js, _9s);
    res = straightFlush(Ace, King, Queen, Jack, Ten);
    System.out.println(rec.check());
    assertTrue(rec.toString(), rec.check().equals(res));

    rec = create(_2s, _5s, _3s, As, _4s, Tc, _3c);
    res = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(_2s, _5s, _3s, As, _4s, Tc, _3c, Ts, Th, Td);
    res = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(_2s, _5s, _3s, As, _4s);
    res = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(_2s, _5s);
    res = highCard(Five, Two);
    assertTrue(rec.check().equals(res));

    rec = create();
    res = highCard();
    assertTrue(rec.check().equals(res));

    rec = create(Qs, Ts, Ks, Ah, Js, Th, _9s);
    res = straightFlush(King, Queen, Jack, Ten, Nine);
    assertTrue(rec.check().equals(res));

    rec = create(Qs, Td, Ks, Ah, Js, Th, _9s);
    res = straight(Ace, King, Queen, Jack, Ten);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testFourOfAKind() {
    ICatRec rec;
    IResult res;
    rec = create(Ah, Kh, Qs, Jh, Th, Kd, Kh, Ks);
    res = fourOfAKind(King, King, King, King, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(Qs, Qh, Qd, Qc); // simple
    res = fourOfAKind(Queen, Queen, Queen, Queen);
    assertTrue(rec.check().equals(res));

    rec = create(Qs, Qh); // too few cards
    res = pair(Queen, Queen);
    assertTrue(rec.check().equals(res));

    rec = create(Ks, Qs, _2s, Qh, Qd, _3h, Qc); // cards + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, King);
    assertTrue(rec.check().equals(res));

    rec = create(Ks, Qs, Js, Qh, Qd, As, Qc, Th); // straight + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(Ks, Qs, Js, Qh, Qd, As, Qc, _9s); // flush + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(Ks, Qs, Ks, Qh, Qd, Ks, Qc); // full-house + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, King);
    assertTrue(rec.check().equals(res));

    rec = create(_2s, _2c, _2h, _2d, _4d, _3c, _3h);
    res = fourOfAKind(Two, Two, Two, Two, Four);
    IResult x = rec.check();
    assertTrue(x + "", x.equals(res));
  }

  @Test
  public final void testFullHouse() {
    ICatRec rec;
    IResult res;
    rec = create(Ah, Kh, Qs, Jh, Th, Kd, Ks, Ts);
    res = fullHouse(King, King, King, Ten, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(Qs, Qh, Qd, Ks, Kh); // simple
    res = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(rec.check().equals(res));

    rec = create(Jh, Tc, _9d, Qs, Qh, Qd, Ks, Kh); // straight
    res = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(rec.check().equals(res));

    rec = create(Jh, Th, _8h, Qs, Qh, Qd, Ks, Kh); // flush
    res = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testFlush() {
    ICatRec rec;
    IResult res;
    rec = create(Ah, Kh, Qs, Jh, Th, Ks, _5h); // straight
    res = flush(Ace, King, Jack, Ten, Five);
    assertTrue(rec.check().equals(res));

    rec = create(Ah, Kh, Qs, Jh, Th, Ks, Kc, _8h); // three-of-a-kind
    res = flush(Ace, King, Jack, Ten, Eight);
    assertTrue(rec.check().equals(res));

    rec = create(_2h, Kh, Qh, Jh, Th); // 5 cards
    res = flush(King, Queen, Jack, Ten, Two);
    assertTrue(rec.check().equals(res));

    rec = create(_2h, Kh); // too few cards
    res = highCard(King, Two);
    assertTrue(rec.check().equals(res));

    rec = create(_6s, _8s, _2c, _7s, Ac, Jh, Kc);
    res = highCard(Ace, King, Jack, Eight, Seven);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testStraight() {
    ICatRec rec;
    IResult res;
    rec = create(Ah, Ks, Qs, Jh, Th, Ks, _5h);
    res = straight(Ace, King, Queen, Jack, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(Ah, _5h, _4s, _3s, _2h); // round-a-corner
    res = straight(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(As, _5h, _4s, _3s, _2h, _3h, _3d); // three-of-a-kind
    res = straight(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(_9h, _6h, _5h, _8h, _7s); // 5 cards
    res = straight(Nine, Eight, Seven, Six, Five);
    assertTrue(rec.check().equals(res));

    rec = create(_2h, Kh); // too few cards
    res = highCard(King, Two);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testThreeOfAKind() {
    ICatRec rec;
    IResult res;
    rec = create(Ah, Ks, Js, Jh, Th, Js, _5h);
    res = threeOfAKind(Jack, Jack, Jack, Ace, King);
    assertTrue(rec.check().equals(res));

    rec = create(_9h, _9d, As, _8h, _9s); // 5 cards
    res = threeOfAKind(Nine, Nine, Nine, Ace, Eight);
    assertTrue(rec.check().equals(res));

  }

  @Test
  public final void testTwoPair() {
    ICatRec rec;
    IResult res;
    rec = create(Ah, Ks, Js, Jh, Th, Kd, _5h);
    res = twoPair(King, King, Jack, Jack, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(_9h, _9d, As, _8h, _8s); // 5 cards
    res = twoPair(Nine, Nine, Eight, Eight, Ace);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testPair() {
    ICatRec rec;
    IResult res;
    rec = create(Ah, Ks, _4s, Jh, Th, Kd, _5h);
    res = pair(King, King, Ace, Jack, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(_9h, _9d, As, _2h, _8s); // 5 cards
    res = pair(Nine, Nine, Ace, Eight, Two);
    assertTrue(rec.check().equals(res));

    rec = create(_2h, _2d); // poket-pair
    res = pair(Two, Two);
    assertTrue(rec.check().equals(res));

    rec = create(); // too few
    res = highCard();
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testHighCard() {
    ICatRec rec;
    IResult res;
    rec = create(Ah, Ks, _4s, Jh, Th, _8d, _5h);
    res = highCard(Ace, King, Jack, Ten, Eight);
    assertTrue(rec.check().equals(res));

    rec = create(_9h, _7d, Js, _2h, _8s); // 5 cards
    res = highCard(Jack, Nine, Eight, Seven, Two);
    assertTrue(rec.check().equals(res));

    rec = create(_2h, _7d); // hand
    res = highCard(Seven, Two);
    assertTrue(rec.check().equals(res));
  }

}
