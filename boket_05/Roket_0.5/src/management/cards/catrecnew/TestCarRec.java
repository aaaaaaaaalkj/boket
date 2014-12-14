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

import java.util.ArrayList;

import management.cards.cards.Card;
import management.cards.cards.Rank;

import org.eclipse.jdt.annotation.NonNull;
import org.junit.Test;

import strategy.conditions.postflop.DrawType;
import tools.Tools;

public class TestCarRec {

  private static ICatRec rec = new CatRec();

  private IResult create() {
    return rec.check(new ArrayList<>());
  }

  // private static StaticCatRec staticCatRec = new StaticCatRec();

  @SuppressWarnings("null")
  private IResult create(final Card... cards) {
    return rec.check(Tools.asList(cards));

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
    IResult got;
    IResult expected;

    got = create(HA, HK, HQ, HJ, HT);
    expected = straightFlush(Ace, King, Queen, Jack, Ten);
    assertTrue(got.equals(expected));

    got = create(SQ, ST, SK, SA, SJ, S9);
    expected = straightFlush(Ace, King, Queen, Jack, Ten);
    System.out.println(got);
    assertTrue(rec.toString(), got.equals(expected));

    got = create(S2, S5, S3, SA, S4, CT, C3);
    expected = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(got.equals(expected));

    got = create(S2, S5, S3, SA, S4, CT, C3, ST, HT, DT);
    expected = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(got.equals(expected));

    got = create(S2, S5, S3, SA, S4);
    expected = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(got.equals(expected));

    got = create(S2, S5);
    expected = highCard(Five, Two);
    assertTrue(got.equals(expected));

    got = create();
    expected = highCard();
    assertTrue(got.equals(expected));

    got = create(SQ, ST, SK, HA, SJ, HT, S9);
    expected = straightFlush(King, Queen, Jack, Ten, Nine);
    assertTrue(got.equals(expected));

    got = create(SQ, DT, SK, HA, SJ, HT, S9);
    expected = straight(Ace, King, Queen, Jack, Ten);
    assertTrue(got.equals(expected));
  }

  @Test
  public final void testFourOfAKind() {
    IResult got;
    IResult expected;

    got = create(HA, HK, SQ, HJ, HT, DK, HK, SK);
    expected = fourOfAKind(King, King, King, King, Ace);
    assertTrue(got.equals(expected));

    got = create(SQ, HQ, DQ, CQ); // simple
    expected = fourOfAKind(Queen, Queen, Queen, Queen);
    assertTrue(got.equals(expected));

    got = create(SQ, HQ); // too few cards
    expected = pair(Queen, Queen);
    assertTrue(got.equals(expected));

    got = create(SK, SQ, S2, HQ, DQ, H3, CQ); // cards + 4of_a_kind
    expected = fourOfAKind(Queen, Queen, Queen, Queen, King);
    assertTrue(got.equals(expected));

    got = create(SK, SQ, SJ, HQ, DQ, SA, CQ, HT); // straight + 4of_a_kind
    expected = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
    assertTrue(got.equals(expected));

    got = create(SK, SQ, SJ, HQ, DQ, SA, CQ, S9); // flush + 4of_a_kind
    expected = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
    assertTrue(got.equals(expected));

    got = create(SK, SQ, SK, HQ, DQ, SK, CQ); // full-house + 4of_a_kind
    expected = fourOfAKind(Queen, Queen, Queen, Queen, King);
    assertTrue(got.equals(expected));

    got = create(S2, C2, H2, D2, D4, C3, H3);
    expected = fourOfAKind(Two, Two, Two, Two, Four);
    IResult x = got;
    assertTrue(x + "", x.equals(expected));
  }

  @Test
  public final void testFullHouse() {
    IResult got;
    IResult expected;
    got = create(HA, HK, SQ, HJ, HT, DK, SK, ST);
    expected = fullHouse(King, King, King, Ten, Ten);
    assertTrue(got.equals(expected));

    got = create(SQ, HQ, DQ, SK, HK); // simple
    expected = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(got.equals(expected));

    got = create(HJ, CT, D9, SQ, HQ, DQ, SK, HK); // straight
    expected = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(got.equals(expected));

    got = create(HJ, HT, H8, SQ, HQ, DQ, SK, HK); // flush
    expected = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(got.equals(expected));
  }

  @Test
  public final void testFlush() {
    IResult got;
    IResult expected;
    got = create(HA, HK, SQ, HJ, HT, SK, H5); // straight
    expected = flush(Ace, King, Jack, Ten, Five);
    assertTrue(got.equals(expected));

    got = create(HA, HK, SQ, HJ, HT, SK, CK, H8); // three-of-a-kind
    expected = flush(Ace, King, Jack, Ten, Eight);
    assertTrue(got.equals(expected));

    got = create(H2, HK, HQ, HJ, HT); // 5 cards
    expected = flush(King, Queen, Jack, Ten, Two);
    assertTrue(got.equals(expected));

    got = create(H2, HK); // too few cards
    expected = highCard(King, Two);
    assertTrue(got.equals(expected));

    got = create(S6, S8, C2, S7, CA, HJ, CK);
    expected = highCard(Ace, King, Jack, Eight, Seven);
    assertTrue(got.equals(expected));
  }

  @Test
  public final void testStraight() {
    IResult got;
    IResult expected;
    got = create(HA, SK, SQ, HJ, HT, SK, H5);
    expected = straight(Ace, King, Queen, Jack, Ten);
    assertTrue("expected " + expected + " but got " + got, got.equals(expected));

    got = create(HA, H5, S4, S3, H2); // round-a-corner
    expected = straight(Five, Four, Three, Two, Ace);
    assertTrue(got.equals(expected));

    got = create(SA, H5, S4, S3, H2, H3, D3); // three-of-a-kind
    expected = straight(Five, Four, Three, Two, Ace);
    assertTrue(got.equals(expected));

    got = create(H9, H6, H5, H8, S7); // 5 cards
    expected = straight(Nine, Eight, Seven, Six, Five);
    assertTrue(got.equals(expected));

    got = create(H2, HK); // too few cards
    expected = highCard(King, Two);
    assertTrue(got.equals(expected));
  }

  @Test
  public final void testThreeOfAKind() {
    IResult got;
    IResult expected;
    got = create(HA, SK, SJ, HJ, HT, SJ, H5);
    expected = threeOfAKind(Jack, Jack, Jack, Ace, King);
    assertTrue(got.equals(expected));

    got = create(H9, D9, SA, H8, S9); // 5 cards
    expected = threeOfAKind(Nine, Nine, Nine, Ace, Eight);
    assertTrue(got.equals(expected));

  }

  @Test
  public final void testTwoPair() {
    IResult got;
    IResult expected;
    got = create(HA, SK, SJ, HJ, HT, DK, H5);
    expected = twoPair(King, King, Jack, Jack, Ace);
    assertTrue(got.equals(expected));

    got = create(H9, D9, SA, H8, S8); // 5 cards
    expected = twoPair(Nine, Nine, Eight, Eight, Ace);
    assertTrue("expected " + expected + " but got " + got, got.equals(expected));
  }

  @Test
  public final void testPair() {
    IResult got;
    IResult res;
    got = create(HA, SK, S4, HJ, HT, DK, H5);
    res = pair(King, King, Ace, Jack, Ten);
    assertTrue(got.equals(res));

    got = create(H9, D9, SA, H2, S8); // 5 cards
    res = pair(Nine, Nine, Ace, Eight, Two);
    assertTrue(got.equals(res));

    got = create(H2, D2); // poket-pair
    res = pair(Two, Two);
    assertTrue(got.equals(res));

    got = create(); // too few
    res = highCard();
    assertTrue(got.equals(res));
  }

  @Test
  public final void testHighCard() {
    IResult got;
    IResult res;
    got = create(HA, SK, S4, HJ, HT, D8, H5);
    res = highCard(Ace, King, Jack, Ten, Eight);
    assertTrue(got.equals(res));

    got = create(H9, D7, SJ, H2, S8); // 5 cards
    res = highCard(Jack, Nine, Eight, Seven, Two);
    assertTrue(got.equals(res));

    got = create(H2, D7); // hand
    res = highCard(Seven, Two);
    assertTrue(got.equals(res));
  }

  @Test
  public final void testDraw1() {
    DrawType res;

    res = checkDraw(HA, SK, SQ, ST, H8, D7, H6);
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = checkDraw(SK, SQ, ST, C9, D7, H6);
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = checkDraw(SQ, ST, C9, D8, H6);
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = checkDraw(S3, SQ, ST, C9, D8, H6);
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = checkDraw(SA, C3, SQ, ST, C9, D8, H6);
    assertTrue(res.toString(), res == DrawType.DOUBLE_GUTSHOT);

    res = checkDraw(HA, SK, SQ, ST, H8, D7, H5);
    assertTrue(res.toString(), res == DrawType.GUTSHOT);

    res = checkDraw(HA, SK, SQ, S3, H8, D7, H6);
    assertTrue(res.toString(), res == DrawType.NONE);

    res = checkDraw(HA, SK, SQ, S3, H8, H7, H6);
    assertTrue(res.toString(), res == DrawType.FLUSH_DRAW);

    res = checkDraw(SK, SQ, CJ, ST, H8);
    assertTrue(res.toString(), res == DrawType.OESD);

    res = checkDraw(HA, SK, SQ, ST, H8, H7, H5);
    assertTrue(res.toString(), res == DrawType.FLUSH_DRAW);

    res = checkDraw(HA, HK, SQ, ST, H8, D7, H6);
    assertTrue(res.toString(), res == DrawType.MONSTER_DRAW);

    res = checkDraw(SK, SQ, SJ, ST, H8);
    assertTrue(res.toString(), res == DrawType.MONSTER_DRAW);

  }

  @SuppressWarnings("null")
  private DrawType checkDraw(Card... cards) {
    return rec.checkDraw(Tools.asList(cards));
  }

}
