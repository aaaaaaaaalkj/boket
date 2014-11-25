package test_parkour;

import static managementCards.cards.Card.Ac;
import static managementCards.cards.Card.Ad;
import static managementCards.cards.Card.Ah;
import static managementCards.cards.Card.As;
import static managementCards.cards.Card.Jc;
import static managementCards.cards.Card.Jh;
import static managementCards.cards.Card.Ks;
import static managementCards.cards.Card.Qs;
import static managementCards.cards.Card.Th;
import static managementCards.cards.Card.Ts;
import static managementCards.cards.Card._2h;
import static managementCards.cards.Card._3c;
import static managementCards.cards.Card._4d;
import static managementCards.cards.Card._4h;
import static managementCards.cards.Card._5c;
import static managementCards.cards.Card._5h;
import static managementCards.cards.Card._5s;
import static managementCards.cards.Card._6d;
import static managementCards.cards.Card._6h;
import static managementCards.cards.Card._7c;
import static managementCards.cards.Card._7d;
import static managementCards.cards.Card._8s;
import static managementCards.cards.Card._9s;
import static org.junit.Assert.assertTrue;
import input_output.Raw_Situation;

import org.junit.Test;

import pot_odds_strategy.PotOddsDecision;
import pot_odds_strategy.PotOddsStrategy;

public class ParkourTest {

	@Test
	public void testPreFlop1() {
		Raw_Situation sit;
		// 177
		sit = new Raw_Situation.Builder()
				.hand(_9s, _4h)
				.communityCards()
				.activeStatus(0, 1, 2, 3, 4, 5, 8)
				.posts(4, .01)
				.posts(5, .02)
				.posts(8, .05)
				.stacksAll(2.2)
				.button(3)
				.pot(0.08)
				.itsMyTurn()
				.brownButtonsAllShown()
				.build();
		PotOddsDecision res = test(sit);
		assertTrue(res.equalType(PotOddsDecision.fold()));
	}

	@Test
	public void testPreFlop2() {
		Raw_Situation sit;
		sit = new Raw_Situation.Builder()
				.hand(Jh, _3c)
				.communityCards(As, Jc, Qs)
				.activeStatus(true, false, false, false, false, true, false,
						false, false)
				.posts(0.0, 0.0, 0.0, 0.0, 0.0, 0.06, 0.0, 0.0, 0.0)
				.stacks(2.28, 0.29, 2.15, 3.51, 2.02, 0.0, 2.22, 0.81, 2.87)
				.button(4)
				.pot(0.12)
				.itsMyTurn(true)
				.brownButtons(true, true, true, false, false, false, false,
						false, false).build();
		PotOddsDecision res = test(sit);
		assertTrue(res.equalType(PotOddsDecision.fold()));
	}

	@Test
	public void testPreFlop3() {

		Raw_Situation sit;
		sit = new Raw_Situation.Builder()
				.hand(_8s, Ad)
				.communityCards()
				.activeStatus(true, true, false, true, false, false, false,
						false, false)
				.posts(0.04, 0.06, 0.0, 0.2, 0.0, 0.0, 0.0, 0.0, 0.0)
				.stacks(2.04, 1.83, 0.0, 0.46, 1.99, 0.0, 2.54, 2.03, 1.98)
				.button(8)
				.pot(0.3)
				.itsMyTurn(true)
				.brownButtons(true, true, true, false, false, false, false,
						false, false).build();
		PotOddsDecision res = test(sit);
		assertTrue(res.equalType(PotOddsDecision.fold()));
	}

	@Test
	public void testFlop1() {
		Raw_Situation sit;
		// 239
		sit = new Raw_Situation.Builder()
				.hand(_4d, _8s)
				.communityCards(_3c, _5s, Jh)
				.activeStatus(0, 6, 7)
				.posts(6, .05)
				.stacksAll(2.2)
				.button(5)
				.pot(0.11)
				.itsMyTurn(false)
				.brownButtonsFirstOnly()
				.build();
		PotOddsDecision res = test(sit);
		assertTrue(res.equalType(PotOddsDecision.fold()));
	}

	@Test
	public void testTurn() {
		Raw_Situation sit;
		sit = new Raw_Situation.Builder()
				.hand(Ah, Th)
				.communityCards(_7c, _5c, _2h, Ks)
				.activeStatus(true, false, false, true, false, false, true,
						true, false)
				.posts(0.02, 0.0, 0.0, 0.16, 0.0, 0.0, 0.0, 0.0, 0.0)
				.stacks(2.35, 3.84, 2.46, 0.93, 0.95, 2.0, 0.5, 2.93, 1.98)
				.button(6)
				.pot(0.43)
				.itsMyTurn(false)
				.brownButtons(true, false, false, false, false, false, false,
						false, false).build();
		PotOddsDecision res = test(sit);
		assertTrue(res.equalType(PotOddsDecision.fold()));
	}

	@Test
	public void testRiver() {
		Raw_Situation sit;
		sit = new Raw_Situation.Builder()
				.hand(Ts, _6h)
				.communityCards(_7c, _7d, Ac, _6d, _5h)
				.activeStatus(true, false, false, false, true, false, false,
						false, false)
				.posts(0.0, 0.0, 0.0, 0.0, 0.47, 0.0, 0.0, 0.0, 0.0)
				.stacks(1.92, 3.29, 0.86, 2.2, 0.0, 0.8, 3.01, 2.0, 2.0)
				.button(2)
				.pot(1.21)
				.itsMyTurn(true)
				.brownButtons(true, false, true, false, false, false, false,
						false, false).build();
		PotOddsDecision res = test(sit);
		assertTrue(res.toString(), res.equalType(PotOddsDecision.fold()));
	}

	private PotOddsDecision test(Raw_Situation raw) {
		PotOddsStrategy strategy = new PotOddsStrategy(raw);
		return strategy.decide();
	}
}
