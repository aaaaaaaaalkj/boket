package strategy.conditions;

public class Utility {
	private final double contribution;
	private final double potOdds;

  public Utility(final double contribution, final double potOdds) {
		this.contribution = contribution;
		this.potOdds = potOdds;
	}

  public final double getDiff() {
		return potOdds - contribution;
	}
}
