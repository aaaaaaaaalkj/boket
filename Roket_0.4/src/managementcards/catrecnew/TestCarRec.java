package managementcards.catrecnew;

import static managementcards.cards.Card.AC;
import static managementcards.cards.Card.AH;
import static managementcards.cards.Card.AS;
import static managementcards.cards.Card.JH;
import static managementcards.cards.Card.JS;
import static managementcards.cards.Card.KC;
import static managementcards.cards.Card.KD;
import static managementcards.cards.Card.KH;
import static managementcards.cards.Card.KS;
import static managementcards.cards.Card.QC;
import static managementcards.cards.Card.QD;
import static managementcards.cards.Card.QH;
import static managementcards.cards.Card.QS;
import static managementcards.cards.Card.TC;
import static managementcards.cards.Card.TD;
import static managementcards.cards.Card.TH;
import static managementcards.cards.Card.TS;
import static managementcards.cards.Card.C2;
import static managementcards.cards.Card.D2;
import static managementcards.cards.Card.H2;
import static managementcards.cards.Card.S2;
import static managementcards.cards.Card.C3;
import static managementcards.cards.Card.D3;
import static managementcards.cards.Card.H3;
import static managementcards.cards.Card.S3;
import static managementcards.cards.Card.D4;
import static managementcards.cards.Card.S4;
import static managementcards.cards.Card.H5;
import static managementcards.cards.Card.S5;
import static managementcards.cards.Card.H6;
import static managementcards.cards.Card.S6;
import static managementcards.cards.Card.D7;
import static managementcards.cards.Card.S7;
import static managementcards.cards.Card.D8;
import static managementcards.cards.Card.H8;
import static managementcards.cards.Card.S8;
import static managementcards.cards.Card.D9;
import static managementcards.cards.Card.H9;
import static managementcards.cards.Card.S9;
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
  private ICatRec create(final Card hand1, final Card hand2d, final Card... community) {
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
    ICatRec rec;
    IResult res;

    rec = create(AH, KH, QH, JH, TH);
    res = straightFlush(Ace, King, Queen, Jack, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(QS, TS, KS, AS, JS, S9);
    res = straightFlush(Ace, King, Queen, Jack, Ten);
    System.out.println(rec.check());
    assertTrue(rec.toString(), rec.check().equals(res));

    rec = create(S2, S5, S3, AS, S4, TC, C3);
    res = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(S2, S5, S3, AS, S4, TC, C3, TS, TH, TD);
    res = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(S2, S5, S3, AS, S4);
    res = straightFlush(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(S2, S5);
    res = highCard(Five, Two);
    assertTrue(rec.check().equals(res));

    rec = create();
    res = highCard();
    assertTrue(rec.check().equals(res));

    rec = create(QS, TS, KS, AH, JS, TH, S9);
    res = straightFlush(King, Queen, Jack, Ten, Nine);
    assertTrue(rec.check().equals(res));

    rec = create(QS, TD, KS, AH, JS, TH, S9);
    res = straight(Ace, King, Queen, Jack, Ten);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testFourOfAKind() {
    ICatRec rec;
    IResult res;
    rec = create(AH, KH, QS, JH, TH, KD, KH, KS);
    res = fourOfAKind(King, King, King, King, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(QS, QH, QD, QC); // simple
    res = fourOfAKind(Queen, Queen, Queen, Queen);
    assertTrue(rec.check().equals(res));

    rec = create(QS, QH); // too few cards
    res = pair(Queen, Queen);
    assertTrue(rec.check().equals(res));

    rec = create(KS, QS, S2, QH, QD, H3, QC); // cards + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, King);
    assertTrue(rec.check().equals(res));

    rec = create(KS, QS, JS, QH, QD, AS, QC, TH); // straight + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(KS, QS, JS, QH, QD, AS, QC, S9); // flush + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(KS, QS, KS, QH, QD, KS, QC); // full-house + 4of_a_kind
    res = fourOfAKind(Queen, Queen, Queen, Queen, King);
    assertTrue(rec.check().equals(res));

    rec = create(S2, C2, H2, D2, D4, C3, H3);
    res = fourOfAKind(Two, Two, Two, Two, Four);
    IResult x = rec.check();
    assertTrue(x + "", x.equals(res));
  }

  @Test
  public final void testFullHouse() {
    ICatRec rec;
    IResult res;
    rec = create(AH, KH, QS, JH, TH, KD, KS, TS);
    res = fullHouse(King, King, King, Ten, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(QS, QH, QD, KS, KH); // simple
    res = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(rec.check().equals(res));

    rec = create(JH, TC, D9, QS, QH, QD, KS, KH); // straight
    res = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(rec.check().equals(res));

    rec = create(JH, TH, H8, QS, QH, QD, KS, KH); // flush
    res = fullHouse(Queen, Queen, Queen, King, King);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testFlush() {
    ICatRec rec;
    IResult res;
    rec = create(AH, KH, QS, JH, TH, KS, H5); // straight
    res = flush(Ace, King, Jack, Ten, Five);
    assertTrue(rec.check().equals(res));

    rec = create(AH, KH, QS, JH, TH, KS, KC, H8); // three-of-a-kind
    res = flush(Ace, King, Jack, Ten, Eight);
    assertTrue(rec.check().equals(res));

    rec = create(H2, KH, QH, JH, TH); // 5 cards
    res = flush(King, Queen, Jack, Ten, Two);
    assertTrue(rec.check().equals(res));

    rec = create(H2, KH); // too few cards
    res = highCard(King, Two);
    assertTrue(rec.check().equals(res));

    rec = create(S6, S8, C2, S7, AC, JH, KC);
    res = highCard(Ace, King, Jack, Eight, Seven);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testStraight() {
    ICatRec rec;
    IResult res;
    rec = create(AH, KS, QS, JH, TH, KS, H5);
    res = straight(Ace, King, Queen, Jack, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(AH, H5, S4, S3, H2); // round-a-corner
    res = straight(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(AS, H5, S4, S3, H2, H3, D3); // three-of-a-kind
    res = straight(Five, Four, Three, Two, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(H9, H6, H5, H8, S7); // 5 cards
    res = straight(Nine, Eight, Seven, Six, Five);
    assertTrue(rec.check().equals(res));

    rec = create(H2, KH); // too few cards
    res = highCard(King, Two);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testThreeOfAKind() {
    ICatRec rec;
    IResult res;
    rec = create(AH, KS, JS, JH, TH, JS, H5);
    res = threeOfAKind(Jack, Jack, Jack, Ace, King);
    assertTrue(rec.check().equals(res));

    rec = create(H9, D9, AS, H8, S9); // 5 cards
    res = threeOfAKind(Nine, Nine, Nine, Ace, Eight);
    assertTrue(rec.check().equals(res));

  }

  @Test
  public final void testTwoPair() {
    ICatRec rec;
    IResult res;
    rec = create(AH, KS, JS, JH, TH, KD, H5);
    res = twoPair(King, King, Jack, Jack, Ace);
    assertTrue(rec.check().equals(res));

    rec = create(H9, D9, AS, H8, S8); // 5 cards
    res = twoPair(Nine, Nine, Eight, Eight, Ace);
    assertTrue(rec.check().equals(res));
  }

  @Test
  public final void testPair() {
    ICatRec rec;
    IResult res;
    rec = create(AH, KS, S4, JH, TH, KD, H5);
    res = pair(King, King, Ace, Jack, Ten);
    assertTrue(rec.check().equals(res));

    rec = create(H9, D9, AS, H2, S8); // 5 cards
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
    ICatRec rec;
    IResult res;
    rec = create(AH, KS, S4, JH, TH, D8, H5);
    res = highCard(Ace, King, Jack, Ten, Eight);
    assertTrue(rec.check().equals(res));

    rec = create(H9, D7, JS, H2, S8); // 5 cards
    res = highCard(Jack, Nine, Eight, Seven, Two);
    assertTrue(rec.check().equals(res));

    rec = create(H2, D7); // hand
    res = highCard(Seven, Two);
    assertTrue(rec.check().equals(res));
  }

}
