package testparkour;

import static managementcards.cards.Card.AD;
import static managementcards.cards.Card.AH;
import static org.junit.Assert.assertTrue;
import inputoutput.Raw_Situation;

import org.junit.Test;
import org.slf4j.LoggerFactory;

import potoddsstrategy.PotOddsDecision;
import potoddsstrategy.PotOddsStrategy;

public class ParkourTest {
	@SuppressWarnings("null")
  static final org.slf4j.Logger LOG = LoggerFactory
			.getLogger(ParkourTest.class);

	// @Test
	// public void testPreFlop1() {
	// Raw_Situation sit;
	// // 177
	// sit = new Raw_Situation.Builder()
	// .hand(_9s, _4h)
	// .communityCards()
	// .activeStatus(0, 1, 2, 3, 4, 5, 8)
	// .posts(4, .01)
	// .posts(5, .02)
	// .posts(8, .05)
	// .stacksAll(2.2)
	// .button(3)
	// .pot(0.08)
	// .itsMyTurn()
	// .brownButtonsAllShown()
	// .build();
	// PotOddsDecision res = test(sit);
	// assertTrue(res.equalType(PotOddsDecision.fold()));
	// }
	//
	// @Test
	// public void testPreFlop2() {
	// Raw_Situation sit;
	// sit = new Raw_Situation.Builder()
	// .hand(Jh, _3c)
	// .communityCards(As, Jc, Qs)
	// .activeStatus(true, false, false, false, false, true, false,
	// false, false)
	// .posts(0.0, 0.0, 0.0, 0.0, 0.0, 0.06, 0.0, 0.0, 0.0)
	// .stacks(2.28, 0.29, 2.15, 3.51, 2.02, 0.0, 2.22, 0.81, 2.87)
	// .button(4)
	// .pot(0.12)
	// .itsMyTurn(true)
	// .brownButtons(true, true, true, false, false, false, false,
	// false, false).build();
	// PotOddsDecision res = test(sit);
	// assertTrue(res.equalType(PotOddsDecision.fold()));
	// }
	//
	// @Test
	// public void testPreFlop3() {
	//
	// Raw_Situation sit;
	// sit = new Raw_Situation.Builder()
	// .hand(_8s, Ad)
	// .communityCards()
	// .activeStatus(true, true, false, true, false, false, false,
	// false, false)
	// .posts(0.04, 0.06, 0.0, 0.2, 0.0, 0.0, 0.0, 0.0, 0.0)
	// .stacks(2.04, 1.83, 0.0, 0.46, 1.99, 0.0, 2.54, 2.03, 1.98)
	// .button(8)
	// .pot(0.3)
	// .itsMyTurn(true)
	// .brownButtons(true, true, true, false, false, false, false,
	// false, false).build();
	// PotOddsDecision res = test(sit);
	// assertTrue(res.equalType(PotOddsDecision.fold()));
	// }
	//
	// @Test
	// public void testFlop1() {
	// Raw_Situation sit;
	// // 239
	// sit = new Raw_Situation.Builder()
	// .hand(_4d, _8s)
	// .communityCards(_3c, _5s, Jh)
	// .activeStatus(0, 6, 7)
	// .posts(6, .05)
	// .stacksAll(2.2)
	// .button(5)
	// .pot(0.11)
	// .itsMyTurn(false)
	// .brownButtonsFirstOnly()
	// .build();
	// PotOddsDecision res = test(sit);
	// assertTrue(res.equalType(PotOddsDecision.fold()));
	// }
	//
	// @Test
	// public void testTurn() {
	// Raw_Situation sit;
	// sit = new Raw_Situation.Builder()
	// .hand(Ah, Th)
	// .communityCards(_7c, _5c, _2h, Ks)
	// .activeStatus(true, false, false, true, false, false, true,
	// true, false)
	// .posts(0.02, 0.0, 0.0, 0.16, 0.0, 0.0, 0.0, 0.0, 0.0)
	// .stacks(2.35, 3.84, 2.46, 0.93, 0.95, 2.0, 0.5, 2.93, 1.98)
	// .button(6)
	// .pot(0.43)
	// .itsMyTurn(false)
	// .brownButtons(true, false, false, false, false, false, false,
	// false, false).build();
	// PotOddsDecision res = test(sit);
	// assertTrue(res.equalType(PotOddsDecision.fold()));
	// }
	//
	// @Test
	// public void testRiver() {
	// Raw_Situation sit;
	// sit = new Raw_Situation.Builder()
	// .hand(Ts, _6h)
	// .communityCards(_7c, _7d, Ac, _6d, _5h)
	// .activeStatus(true, false, false, false, true, false, false,
	// false, false)
	// .posts(0.0, 0.0, 0.0, 0.0, 0.47, 0.0, 0.0, 0.0, 0.0)
	// .stacks(1.92, 3.29, 0.86, 2.2, 0.0, 0.8, 3.01, 2.0, 2.0)
	// .button(2)
	// .pot(1.21)
	// .itsMyTurn(true)
	// .brownButtons(true, false, true, false, false, false, false,
	// false, false).build();
	// PotOddsDecision res = test(sit);
	// assertTrue(res.toString(), res.equalType(PotOddsDecision.fold()));
	// }
	//
  private PotOddsDecision test(final Raw_Situation raw) {
		PotOddsStrategy strategy = new PotOddsStrategy(raw);

		LOG.info("{}", strategy);
		return strategy.decide();
	}

	@Test
  public final void goodPreflop() {
		Raw_Situation sit;
		sit = new Raw_Situation.Builder()
				.hand(AH, AD)
				.communityCards()
				.activeStatus(true, true, true, true, true, true, true, true,
						true)
				.posts(0.0, 0.0, 0.0, 0.0, 0.01, 0.02, 5.4, 0.0, 0.0)
				.stacks(0.83, 1.96, 2.77, 2.61, 0.97, 1.79, 2.11, 2.19, 1.51)
				.button(3)
				.pot(0.07)
				.itsMyTurn(false)
				.brownButtons(true, false, false, false, false, false, false,
						false, false).build();
		PotOddsDecision res = test(sit);
		assertTrue(res.toString(), res.equalType(PotOddsDecision.raise(1)));
	}

}
