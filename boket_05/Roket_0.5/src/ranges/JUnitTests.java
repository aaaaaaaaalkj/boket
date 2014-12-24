package ranges;

import static management.cards.cards.Rank.Ace;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static ranges.ElementRange.Ac_Ad;
import static ranges.ElementRange.Ac_Ah;
import static ranges.ElementRange.Ac_As;
import static ranges.ElementRange.Ac_Kc;
import static ranges.ElementRange.Ac_Kd;
import static ranges.ElementRange.Ac_Qd;
import static ranges.ElementRange.Ad_4h;
import static ranges.ElementRange.Ah_Ad;
import static ranges.ElementRange.As_8d;
import static ranges.ElementRange.As_Ad;
import static ranges.ElementRange.As_Ah;
import static ranges.ElementRange.Kc_2c;
import static ranges.ElementRange.Kc_Kd;
import static ranges.ElementRange.Kc_Kh;
import static ranges.ElementRange.Kc_Ks;
import static ranges.ElementRange.Kc_Qc;
import static ranges.ElementRange.Kh_Kd;
import static ranges.ElementRange.Ks_8d;
import static ranges.ElementRange.Ks_Kd;
import static ranges.ElementRange.Ks_Kh;
import static ranges.ElementRange.Qc_Qd;
import static ranges.ElementRange.Qc_Qh;
import static ranges.ElementRange.Qc_Qs;
import static ranges.ElementRange.Qh_Qd;
import static ranges.ElementRange.Qs_Qd;
import static ranges.ElementRange.Qs_Qh;
import static ranges.ElementRange.Tc_Td;
import static ranges.ElementRange._2c_2d;
import static ranges.ElementRange._2c_2h;
import static ranges.ElementRange._2c_2s;
import static ranges.ElementRange._2h_2d;
import static ranges.ElementRange._2s_2d;
import static ranges.ElementRange._2s_2h;
import static ranges.ElementRange._3c_2c;
import static ranges.ElementRange._3c_2d;
import static ranges.ElementRange._3c_2h;
import static ranges.ElementRange._3c_2s;
import static ranges.ElementRange._3c_3d;
import static ranges.ElementRange._3d_2c;
import static ranges.ElementRange._3d_2d;
import static ranges.ElementRange._3d_2h;
import static ranges.ElementRange._3d_2s;
import static ranges.ElementRange._3h_2c;
import static ranges.ElementRange._3h_2d;
import static ranges.ElementRange._3h_2h;
import static ranges.ElementRange._3h_2s;
import static ranges.ElementRange._3s_2c;
import static ranges.ElementRange._3s_2d;
import static ranges.ElementRange._3s_2h;
import static ranges.ElementRange._3s_2s;
import static ranges.ElementRange._3s_3h;
import static ranges.ElementRange._4c_2c;
import static ranges.ElementRange._4c_3c;
import static ranges.ElementRange._4c_3d;
import static ranges.ElementRange._4d_2d;
import static ranges.ElementRange._4d_3d;
import static ranges.ElementRange._4h_2h;
import static ranges.ElementRange._4h_3h;
import static ranges.ElementRange._4s_2s;
import static ranges.ElementRange._4s_3s;
import static ranges.ElementRange._5c_2d;
import static ranges.ElementRange._5c_2h;
import static ranges.ElementRange._5c_2s;
import static ranges.ElementRange._5c_3d;
import static ranges.ElementRange._5c_3h;
import static ranges.ElementRange._5c_3s;
import static ranges.ElementRange._5c_4d;
import static ranges.ElementRange._5c_4h;
import static ranges.ElementRange._5c_4s;
import static ranges.ElementRange._5d_2c;
import static ranges.ElementRange._5d_2h;
import static ranges.ElementRange._5d_2s;
import static ranges.ElementRange._5d_3c;
import static ranges.ElementRange._5d_3h;
import static ranges.ElementRange._5d_3s;
import static ranges.ElementRange._5d_4c;
import static ranges.ElementRange._5d_4h;
import static ranges.ElementRange._5d_4s;
import static ranges.ElementRange._5h_2c;
import static ranges.ElementRange._5h_2d;
import static ranges.ElementRange._5h_2s;
import static ranges.ElementRange._5h_3c;
import static ranges.ElementRange._5h_3d;
import static ranges.ElementRange._5h_3s;
import static ranges.ElementRange._5h_4c;
import static ranges.ElementRange._5h_4d;
import static ranges.ElementRange._5h_4s;
import static ranges.ElementRange._5s_2c;
import static ranges.ElementRange._5s_2d;
import static ranges.ElementRange._5s_2h;
import static ranges.ElementRange._5s_3c;
import static ranges.ElementRange._5s_3d;
import static ranges.ElementRange._5s_3h;
import static ranges.ElementRange._5s_4c;
import static ranges.ElementRange._5s_4d;
import static ranges.ElementRange._5s_4h;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import management.cards.cards.Card;

import org.junit.Test;

import ranges.preflop.GroupedPlusRange;
import ranges.preflop.GroupedRange;
import tools.Tools;

@SuppressWarnings("static-method")
public final class JUnitTests {
  private static final int NUMBER_OF_POSSIBLE_PAIRS = 78;
  private static final int NUMBER_OF_POSSIBLE_ACES_OFFSUIT = 144;
  private static final int NUMBER_OF_SUITED_GROUPED = 4;
  private static final int NUMBER_OF_CARDS = 52;

  public JUnitTests() {
    // do not instantiate this class
  }

  @Test
  public void testFindGrouped() {
    assertTrue(GroupedRange.AA == GroupedRange.find(Ace, Ace));

    for (GroupedRange range : GroupedRange.VALUES) {
      assertTrue(range == GroupedRange.find(range.getRank1(),
          range.getRank2(), range.isSuited()));
    }

  }

  @Test
  public void testConsistency() {
    List<ElementRange> hands = new ArrayList<>();
    hands.add(_5s_4h);
    hands.add(_5s_2d);

    List<Card> commCards = Tools.asList(Card.C2, Card.C3, Card.C6);

    boolean res = ConsistencyChecker.areConsistent(hands, commCards);

    assertTrue(!res);
  }

  @Test
  public void testConsistency2() {
    List<ElementRange> hands = new ArrayList<>();
    hands.add(_5s_4h);
    hands.add(_5h_2d);

    List<Card> commCards = Tools.asList(Card.C2, Card.C3, Card.C6);

    boolean res = ConsistencyChecker.areConsistent(hands, commCards);

    assertTrue(res);
  }

  @Test
  public void testConsistency3() {
    List<ElementRange> hands = new ArrayList<>();
    hands.add(_5s_4h);
    hands.add(_5h_2d);
    hands.add(_5h_3c);

    List<Card> commCards = Tools.asList(Card.C2, Card.C3, Card.C6);

    boolean res = ConsistencyChecker.areConsistent(hands, commCards);

    assertTrue(!res);
  }

  @Test
  public void testBroadWayCards() {
    SimpleRange range = SimpleRange.broadwayHands();

    assertTrue(range.contains(As_Ah));
    assertTrue(range.contains(Kc_Qc));

    assertFalse(range.contains(Kc_2c));
    assertFalse(range.contains(ElementRange._3d_2h));

  }

  @Test
  public void testAssociated() {
    for (Card c : Card.getAllCards()) {

      Set<ElementRange> set = ElementRange.findAssociated(c);

      int count = NUMBER_OF_CARDS - 1;

      assertTrue(set.size() + " != " + count, set.size() == count);

      for (ElementRange e : set) {
        assertTrue(e.getFirstCard() == c || e.getSecondCard() == c);
      }
    }
  }

  @Test
  public void testFindElementRange() {
    for (ElementRange r1 : ElementRange.VALUES) {
      ElementRange r2 = ElementRange.find(
          r1.getFirstCard(),
          r1.getSecondCard());

      ElementRange r3 = ElementRange.find(
          r1.getSecondCard(),
          r1.getFirstCard());
      assertTrue(r1 == r2);
      assertTrue(r1 == r3);
    }
  }

  @Test
  public void testUngroup() {
    SimpleRange range, target;

    target = new SimpleRange();
    target.add(_2c_2d);
    target.add(_2c_2h);
    target.add(_2c_2s);
    target.add(_2h_2d);
    target.add(_2s_2h);
    target.add(_2s_2d);

    range = GroupedRange._22.ungroup();
    assertTrue(range.equals(target));

    // ++++++++++++++++++++++++

    target = new SimpleRange();
    target.add(_3c_2d);
    target.add(_3c_2h);
    target.add(_3c_2s);
    target.add(_3h_2d);
    target.add(_3s_2h);
    target.add(_3s_2d);

    target.add(_3d_2c);
    target.add(_3h_2c);
    target.add(_3s_2c);
    target.add(_3d_2h);
    target.add(_3h_2s);
    target.add(_3d_2s);

    range = GroupedRange._32o.ungroup();
    assertTrue(range.equals(target));

    // ++++++++++++++++++++++++

    target = new SimpleRange();
    target.add(_3c_2c);
    target.add(_3h_2h);
    target.add(_3s_2s);
    target.add(_3d_2d);

    range = GroupedRange._32.ungroup();
    assertTrue(range.equals(target));
  }

  @Test
  public void testUngroupPlus() {
    SimpleRange range, target;

    target = new SimpleRange();
    target.add(_5s_2h);
    target.add(_5s_3h);
    target.add(_5s_4h);
    target.add(_5c_2h);
    target.add(_5c_3h);
    target.add(_5c_4h);
    target.add(_5d_2h);
    target.add(_5d_3h);
    target.add(_5d_4h);

    target.add(_5s_2d);
    target.add(_5s_3d);
    target.add(_5s_4d);
    target.add(_5c_2d);
    target.add(_5c_3d);
    target.add(_5c_4d);
    target.add(_5h_2d);
    target.add(_5h_3d);
    target.add(_5h_4d);

    target.add(_5h_2s);
    target.add(_5h_3s);
    target.add(_5h_4s);
    target.add(_5c_2s);
    target.add(_5c_3s);
    target.add(_5c_4s);
    target.add(_5d_2s);
    target.add(_5d_3s);
    target.add(_5d_4s);

    target.add(_5h_2c);
    target.add(_5h_3c);
    target.add(_5h_4c);
    target.add(_5s_2c);
    target.add(_5s_3c);
    target.add(_5s_4c);
    target.add(_5d_2c);
    target.add(_5d_3c);
    target.add(_5d_4c);

    range = new GroupedPlusRange(GroupedRange._52o).ungroup();
    assertTrue(target.equals(range));

    // ++++++++++++++++++++++++

    target = new SimpleRange();

    target.add(_4s_2s);
    target.add(_4h_2h);
    target.add(_4d_2d);
    target.add(_4c_2c);

    target.add(_4s_3s);
    target.add(_4h_3h);
    target.add(_4d_3d);
    target.add(_4c_3c);

    range = new GroupedPlusRange(GroupedRange._42).ungroup();
    assertTrue(target.equals(range));

    // ++++++++++++++++++++++++

    target = new SimpleRange();

    target.add(Qc_Qd);
    target.add(Qs_Qd);
    target.add(Qh_Qd);
    target.add(Qc_Qs);
    target.add(Qs_Qh);
    target.add(Qc_Qh);

    target.add(Kc_Kd);
    target.add(Ks_Kd);
    target.add(Kh_Kd);
    target.add(Kc_Ks);
    target.add(Ks_Kh);
    target.add(Kc_Kh);

    target.add(Ac_Ad);
    target.add(As_Ad);
    target.add(Ah_Ad);
    target.add(Ac_As);
    target.add(As_Ah);
    target.add(Ac_Ah);

    range = new GroupedPlusRange(GroupedRange.QQ).ungroup();
    assertTrue(target.equals(range));
  }

  @Test
  public void testGrouped() {
    assertTrue(_3h_2s.grouped() == GroupedRange._32o);
    assertTrue(_3h_2h.grouped() == GroupedRange._32);
    assertTrue(_3s_3h.grouped() == GroupedRange._33);
  }

  @Test
  public void testGrouptedPlus() {
    GroupedPlusRange r;

    r = new GroupedPlusRange(GroupedRange._22);
    assertTrue(r.size() == NUMBER_OF_POSSIBLE_PAIRS);
    assertTrue(r.contains(Ac_Ad));
    assertTrue(r.contains(_2c_2h));
    assertTrue(r.contains(Tc_Td));

    assertFalse(r.contains(Ac_Kd));
    assertFalse(r.contains(Ac_Kc));

    r = new GroupedPlusRange(GroupedRange.A2o);
    assertTrue(r.size() == NUMBER_OF_POSSIBLE_ACES_OFFSUIT);
    assertTrue(r.contains(Ac_Qd));
    assertTrue(r.contains(Ad_4h));
    assertTrue(r.contains(As_8d));

    assertFalse(r.contains(Ac_Ad));
    assertFalse(r.contains(Ks_8d));

    r = new GroupedPlusRange(GroupedRange._32);
    assertTrue(r.size() == NUMBER_OF_SUITED_GROUPED);
    assertTrue(r.contains(_3c_2c));
    assertTrue(r.contains(_3h_2h));
    assertTrue(r.contains(_3s_2s));
    assertTrue(r.contains(_3d_2d));

    assertFalse(r.contains(_3d_2c));
    assertFalse(r.contains(_3c_3d));
    assertFalse(r.contains(_4c_3d));
    assertFalse(r.contains(_2c_2d));

  }
}
