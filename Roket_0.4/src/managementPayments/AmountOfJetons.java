package managementPayments;

public class AmountOfJetons {
	private final int numSmallBlinds;
	public static final AmountOfJetons INFINITY = new AmountOfJetons(
			Integer.MAX_VALUE);
	public static final AmountOfJetons ZERO = new AmountOfJetons(0);
	public static final AmountOfJetons SB = new AmountOfJetons(1);
	public static final AmountOfJetons BB = new AmountOfJetons(2);

	public static final AmountOfJetons BB(int num) {
		return new AmountOfJetons(2 * num);
	}

	public AmountOfJetons(int numSB) {
		if (numSB < 0)
			throw new IllegalArgumentException("Negative Amount");
		this.numSmallBlinds = numSB;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numSmallBlinds;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmountOfJetons other = (AmountOfJetons) obj;
		if (numSmallBlinds != other.numSmallBlinds)
			return false;
		return true;
	}

	public AmountOfJetons plus(AmountOfJetons amount) {
		return new AmountOfJetons(numSmallBlinds + amount.numSmallBlinds);
	}

	public AmountOfJetons minus(AmountOfJetons amount) {
		return new AmountOfJetons(numSmallBlinds - amount.numSmallBlinds);
	}

	public boolean greaterOrEqual(AmountOfJetons amount) {
		return numSmallBlinds >= amount.numSmallBlinds;
	}

	public boolean greaterAs(AmountOfJetons amount) {
		return numSmallBlinds > amount.numSmallBlinds;
	}

	public static AmountOfJetons max(AmountOfJetons amount1,
			AmountOfJetons amount2) {
		if (amount1.numSmallBlinds > amount2.numSmallBlinds)
			return amount1;
		else
			return amount2;
	}

	public int numSmallBlinds() {
		return numSmallBlinds;
	}

	public String toString() {
		if (numSmallBlinds == 0)
			return "-";
		if (numSmallBlinds % 2 == 0)
			return numSmallBlinds / 2 + " BB";
		return numSmallBlinds / 2 + ".5 BB";
	}

	public boolean unequalTo(AmountOfJetons toPost) {
		return this.numSmallBlinds != toPost.numSmallBlinds;
	}

	public AmountOfJetons max(AmountOfJetons post) {
		return this.greaterAs(post) ? this : post;
	}

	public AmountOfJetons min(AmountOfJetons post) {
		return post.greaterAs(this) ? this : post;
	}

	public boolean isEven() {
		return numSmallBlinds % 2 == 0;
	}

	public static AmountOfJetons min(AmountOfJetons amount1,
			AmountOfJetons amount2) {
		return amount1.greaterAs(amount2) ? amount2 : amount1;
	}

	public boolean positive() {
		return this.greaterAs(AmountOfJetons.ZERO);
	}

	public AmountOfJetons divide(int n) {
		return new AmountOfJetons(numSmallBlinds / n);
	}

	public AmountOfJetons divideToEven(int n) {
		assert n > 0;
		int res = numSmallBlinds / n;
		if (res % 2 == 1)
			res--;
		return new AmountOfJetons(res);
	}

	public AmountOfJetons times(int n) {
		return new AmountOfJetons(numSmallBlinds * n);
	}

	public AmountOfJetons times(double d) {
		return new AmountOfJetons((int) (numSmallBlinds * d));
	}

	public boolean isZero() {
		return this.equals(AmountOfJetons.ZERO);
	}

	public double divide(AmountOfJetons amount) {
		return ((double) numSmallBlinds) / ((double) amount.numSmallBlinds);
	}
}
