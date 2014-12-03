package card_simulation;

public class TriangleProbabilityDestribution {
	// the olny parameter of triangle distribution
	private final double a;

  public TriangleProbabilityDestribution(final double a) {
		this.a = a;
	}

	/*
	 * random number between 0 and 1 according to the triangle distribution
	 */
  public final double rand(final double y) {
		if (y < a) {
			return Math.sqrt(a * y);
		} else {
			return 1. - Math.sqrt((y - 1) * (a - 1));
		}
	}
}
