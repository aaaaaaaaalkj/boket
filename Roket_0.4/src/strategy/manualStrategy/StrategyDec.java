package strategy.manualStrategy;

import managementpaymentstmp.AmountOfJetons;
import strategy.TypeOfDecision;
import common.IPlayer;

public class StrategyDec {
	private final TypeOfDecision decision;
	private Stat stat;

  public StrategyDec(final TypeOfDecision d) {
		this.decision = d;
		this.stat = new Stat();
	}

  public final TypeOfDecision getDecision() {
		return this.decision;
	}

  public final void payed(final IPlayer player, final AmountOfJetons amount) {
		stat.payed(player, amount);

	}

  public final void lost(final IPlayer player) {
		stat.lost(player);
	}

  public final void won(final IPlayer player, final AmountOfJetons amountOfJetons,
      final AmountOfJetons payed) {
		stat.won(player, amountOfJetons, payed);
	}

  public final String toString() {
		String s = decision.toString();

		s += " " + stat;

		return s;
	}
}
