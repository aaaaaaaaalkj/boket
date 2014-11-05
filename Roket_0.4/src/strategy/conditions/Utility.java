package strategy.conditions;

public class Utility {
	private final double contribution;
	private final double potOdds;

	public Utility(double contribution, double potOdds) {
		this.contribution = contribution;
		this.potOdds = potOdds;
	}

	public double getDiff() {
		return potOdds - contribution;
	}
}
