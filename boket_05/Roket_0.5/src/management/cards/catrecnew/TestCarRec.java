package management.cards.catrecnew;

import static management.cards.cards.Card.C2;
import static management.cards.cards.Card.C3;
import static management.cards.cards.Card.C9;
import static management.cards.cards.Card.CA;
import static management.cards.cards.Card.CJ;
import static management.cards.cards.Card.CK;
import static management.cards.cards.Card.CQ;
import static management.cards.cards.Card.CT;
import static management.cards.cards.Card.D2;
import static management.cards.cards.Card.D3;
import static management.cards.cards.Card.D4;
import static management.cards.cards.Card.D7;
import static management.cards.cards.Card.D8;
import static management.cards.cards.Card.D9;
import static management.cards.cards.Card.DK;
import static management.cards.cards.Card.DQ;
import static management.cards.cards.Card.DT;
import static management.cards.cards.Card.H2;
import static management.cards.cards.Card.H3;
import static management.cards.cards.Card.H5;
import static management.cards.cards.Card.H6;
import static management.cards.cards.Card.H7;
import static management.cards.cards.Card.H8;
import static management.cards.cards.Card.H9;
import static management.cards.cards.Card.HA;
import static management.cards.cards.Card.HJ;
import static management.cards.cards.Card.HK;
import static management.cards.cards.Card.HQ;
import static management.cards.cards.Card.HT;
import static management.cards.cards.Card.S2;
import static management.cards.cards.Card.S3;
import static management.cards.cards.Card.S4;
import static management.cards.cards.Card.S5;
import static management.cards.cards.Card.S6;
import static management.cards.cards.Card.S7;
import static management.cards.cards.Card.S8;
import static management.cards.cards.Card.S9;
import static management.cards.cards.Card.SA;
import static management.cards.cards.Card.SJ;
import static management.cards.cards.Card.SK;
import static management.cards.cards.Card.SQ;
import static management.cards.cards.Card.ST;
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

import java.util.Arrays;

import management.cards.cards.Card;
import management.cards.cards.Rank;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

import strategy.conditions.postflop.DrawType;

public class TestCarRec {

  @SuppressWarnings("null")
  private ICatRec create() {
    return new CatRec(Arrays.asList(), Arrays.asList());
  }

  // private static StaticCatRec staticCatRec = new StaticCatRec();

  @SuppressWarnings("null")
  private ICatRecBase create(final Card hand1, final Card hand2d,
      final Card... community) {
    // List<Card> cards = new ArrayList<>();
    // cards.add(hand1);
    // cards.add(hand2d);
    // for (Card c : community) {
    // cards.add(c);
    // }
    // staticCatRec.setCards(cards);
    // return staticCatRec;
    return new CatRec(Arrays.asList(hand1, hand2d),
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
    ICatRecBase rec;
    IResult res;

    rec = create(HA, HK, HQ, HJ, HT);
    res = straightFlush(Ace, King, Queen, Jack, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(SQ, ST, SK, SA, SJ, S9);
    res = straightFlush(Ace, King, Queen, Jack, Ten);
    System.out.println(rec.check());
    assertTrue(rec.toString(), rec.check().equals(res));

    rec = create(S2, S5, S3, SA, S4, CT, C3);
    res = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(S2, S5, S3, SA, S4, CT, C3, ST, HT, DT);
    res = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(S2, S5, S3, SA, S4);
    res = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(S2, S5);
    res = highCard(Five, Two);
    assertTrue(rec.check().equals(res));

    rec = create();
    res = highCard();
    assertTrue(rec.check().equals(res));

    rec = create(SQ, ST, SK, HA, SJ, HT, S9);
    res = straightFlush(King, Queen, Jack, Ten, Nine);
    assertTrue(rec.check().equals(res));

    rec = create(SQ, DT, SK, HA, SJ, HT, S9);
    res = straight(Ace, King, Queen, Jack, Ten);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testFourOfAKind() {
    ICatRecBase rec;
    IResult res;
    rec = create(HA, HK, SQ, HJ, HT, DK, HK, SK);
    res = fourOfAKind(King, King, King, King, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(SQ, HQ, DQ, CQ); // simple
    res = fourOfAKind(Queen, Queen, Queen, Queen);
    assertTrue(rec.check().equals(res));

    rec = create(SQ, HQ); // too few cards
    res = pair(Queen, Queen);
    assertTrue(rec.check().equals(res));

    rec = create(SK, SQ, S2, HQ, DQ, H3, CQ); // cards + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, King);
    assertTrue(rec.check().equals(res));

    rec = create(SK, SQ, SJ, HQ, DQ, SA, CQ, HT); // straight + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(SK, SQ, SJ, HQ, DQ, SA, CQ, S9); // flush + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(SK, SQ, SK, HQ, DQ, SK, CQ); // full-house + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, King);
    assertTrue(rec.check().equals(res));

    rec = create(S2, C2, H2, D2, D4, C3, H3);
    res = fourOfAKind(Two, Two, Two, Two, Four);
    IResult x = rec.check();
    assertTrue(x + "", x.equals(res));
  }

  @Test
  public final void testFullHouse() {
    ICatRecBase rec;
    IResult res;
    rec = create(HA, HK, SQ, HJ, HT, DK, SK, ST);
    res = fullHouse(King, King, King, Ten, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(SQ, HQ, DQ, SK, HK); // simple
    res = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(rec.check().equals(res));

    rec = create(HJ, CT, D9, SQ, HQ, DQ, SK, HK); // straight
    res = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(rec.check().equals(res));

    rec = create(HJ, HT, H8, SQ, HQ, DQ, SK, HK); // flush
    res = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testFlush() {
    ICatRecBase rec;
    IResult res;
    rec = create(HA, HK, SQ, HJ, HT, SK, H5); // straight
    res = flush(Ace, King, Jack, Ten, Five);
    assertTrue(rec.check().equals(res));

    rec = create(HA, HK, SQ, HJ, HT, SK, CK, H8); // three-of-a-kind
    res = flush(Ace, King, Jack, Ten, Eight);
    assertTrue(rec.check().equals(res));

    rec = create(H2, HK, HQ, HJ, HT); // 5 cards
    res = flush(King, Queen, Jack, Ten, Two);
    assertTrue(rec.check().equals(res));

    rec = create(H2, HK); // too few cards
    res = highCard(King, Two);
    assertTrue(rec.check().equals(res));

    rec = create(S6, S8, C2, S7, CA, HJ, CK);
    res = highCard(Ace, King, Jack, Eight, Seven);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testStraight() {
    ICatRecBase rec;
    IResult res;
    rec = create(HA, SK, SQ, HJ, HT, SK, H5);
    res = straight(Ace, King, Queen, Jack, Ten);
    IResult got = rec.check();
    assertTrue("expected " + res + " but got " + got, got.equals(res));

    rec = create(HA, H5, S4, S3, H2); // round-a-corner
    res = straight(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(SA, H5, S4, S3, H2, H3, D3); // three-of-a-kind
    res = straight(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(H9, H6, H5, H8, S7); // 5 cards
    res = straight(Nine, Eight, Seven, Six, Five);
    assertTrue(rec.check().equals(res));

    rec = create(H2, HK); // too few cards
    res = highCard(King, Two);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testThreeOfAKind() {
    ICatRecBase rec;
    IResult res;
    rec = create(HA, SK, SJ, HJ, HT, SJ, H5);
    res = threeOfAKind(Jack, Jack, Jack, Ace, King);
    assertTrue(rec.check().equals(res));

    rec = create(H9, D9, SA, H8, S9); // 5 cards
    res = threeOfAKind(Nine, Nine, Nine, Ace, Eight);
    assertTrue(rec.check().equals(res));

  }

  @Test
  public final void testTwoPair() {
    ICatRecBase rec;
    IResult res;
    rec = create(HA, SK, SJ, HJ, HT, DK, H5);
    res = twoPair(King, King, Jack, Jack, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(H9, D9, SA, H8, S8); // 5 cards
    res = twoPair(Nine, Nine, Eight, Eight, Ace);
    IResult got = rec.check();
    assertTrue("expected " + res + " but got " + got, got.equals(res));
  }

  @Test
  public final void testPair() {
    ICatRecBase rec;
    IResult res;
    rec = create(HA, SK, S4, HJ, HT, DK, H5);
    res = pair(King, King, Ace, Jack, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(H9, D9, SA, H2, S8); // 5 cards
    res = pair(Nine, Nine, Ace, Eight, Two);
    assertTrue(rec.check().equals(res));

    rec = create(H2, D2); // poket-pair
    res = pair(Two, Two);
    assertTrue(rec.check().equals(res));

    rec = create(); // too few
    res = highCard();
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testHighCard() {
    ICatRecBase rec;
    IResult res;
    rec = create(HA, SK, S4, HJ, HT, D8, H5);
    res = highCard(Ace, King, Jack, Ten, Eight);
    assertTrue(rec.check().equals(res));

    rec = create(H9, D7, SJ, H2, S8); // 5 cards
    res = highCard(Jack, Nine, Eight, Seven, Two);
    assertTrue(rec.check().equals(res));

    rec = create(H2, D7); // hand
    res = highCard(Seven, Two);
    assertTrue(rec.check().equals(res));
  }

  @SuppressWarnings("null")
  private ICatRec create2(final Card hand1, final Card hand2d,
      final Card... community) {
    return new CatRec(Arrays.asList(hand1, hand2d),
        Arrays.asList(community));
  }

  @Test
  public final void testDraw1() {
    DrawType res;

    res = create2(HA, SK, SQ, ST, H8, D7, H6).checkDraw();
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = create2(SK, SQ, ST, C9, D7, H6).checkDraw();
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = create2(SQ, ST, C9, D8, H6).checkDraw();
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = create2(S3, SQ, ST, C9, D8, H6).checkDraw();
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = create2(SA, C3, SQ, ST, C9, D8, H6).checkDraw();
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = create2(HA, SK, SQ, ST, H8, D7, H5).checkDraw();
    assertTrue(res.toString(), res == DrawType.GUTSHOT);

    res = create2(HA, SK, SQ, S3, H8, D7, H6).checkDraw();
    assertTrue(res.toString(), res == DrawType.NONE);

    res = create2(HA, SK, SQ, S3, H8, H7, H6).checkDraw();
    assertTrue(res.toString(), res == DrawType.FLUSH_DRAW);

    res = create2(SK, SQ, CJ, ST, H8).checkDraw();
    assertTrue(res.toString(), res == DrawType.OESD);

    res = create2(HA, SK, SQ, ST, H8, H7, H5).checkDraw();
    assertTrue(res.toString(), res == DrawType.FLUSH_DRAW);

    res = create2(HA, HK, SQ, ST, H8, D7, H6).checkDraw();
    assertTrue(res.toString(), res == DrawType.MONSTER_DRAW);

    res = create2(SK, SQ, SJ, ST, H8).checkDraw();
    assertTrue(res.toString(), res == DrawType.MONSTER_DRAW);

  }

}
