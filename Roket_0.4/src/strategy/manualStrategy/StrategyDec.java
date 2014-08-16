package strategy.manualStrategy;

import strategy.TypeOfDecision;
import common.IPlayer;
import managementPayments.AmountOfJetons;

public class StrategyDec {
	private final TypeOfDecision decision;
	private Stat stat;

	public StrategyDec(TypeOfDecision d) {
		this.decision = d;
		this.stat = new Stat();
	}

	public TypeOfDecision getDecision() {
		return this.decision;
	}

	public void payed(IPlayer player, AmountOfJetons amount) {
		stat.payed(player, amount);

	}

	public void lost(IPlayer player) {
		stat.lost(player);
	}

	public void won(IPlayer player, AmountOfJetons amountOfJetons,
			AmountOfJetons payed) {
		stat.won(player, amountOfJetons, payed);
	}

	public String toString() {
		String s = decision.toString();

		s += " " + stat;

		return s;
	}
}
