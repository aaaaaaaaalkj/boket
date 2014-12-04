package managementpaymentstmp;

import org.eclipse.jdt.annotation.Nullable;

public class AmountOfJetons implements Comparable<AmountOfJetons> {
	private final int numSmallBlinds;
	public static final AmountOfJetons INFINITY = new AmountOfJetons(
			Integer.MAX_VALUE);
	public static final AmountOfJetons ZERO = new AmountOfJetons(0);
	public static final AmountOfJetons SB = new AmountOfJetons(1);
	public static final AmountOfJetons BB = new AmountOfJetons(2);

  public static final AmountOfJetons bb(final int num) {
		return new AmountOfJetons(2 * num);
	}

  public AmountOfJetons(final int numSB) {
    if (numSB < 0) {
      throw new IllegalArgumentException("Negative Amount");
    }
		this.numSmallBlinds = numSB;
	}

	@Override
  public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + numSmallBlinds;
		return result;
	}

	@Override
  public final boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
		AmountOfJetons other = (AmountOfJetons) obj;
    if (numSmallBlinds != other.numSmallBlinds) {
      return false;
    }
		return true;
	}

  public final AmountOfJetons plus(final AmountOfJetons amount) {
		return new AmountOfJetons(numSmallBlinds + amount.numSmallBlinds);
	}

  public final AmountOfJetons minus(final AmountOfJetons amount) {
		return new AmountOfJetons(numSmallBlinds - amount.numSmallBlinds);
	}

  public final boolean greaterOrEqual(final AmountOfJetons amount) {
		return numSmallBlinds >= amount.numSmallBlinds;
	}

  public final boolean greaterAs(final AmountOfJetons amount) {
		return numSmallBlinds > amount.numSmallBlinds;
	}

  public static AmountOfJetons max(final AmountOfJetons amount1,
      final AmountOfJetons amount2) {
    if (amount1.numSmallBlinds > amount2.numSmallBlinds) {
      return amount1;
    } else {
      return amount2;
    }
	}

  public final int numSmallBlinds() {
		return numSmallBlinds;
	}

  public final String toString() {
    if (numSmallBlinds == 0) {
      return "-";
    }
    if (numSmallBlinds % 2 == 0) {
      return numSmallBlinds / 2 + " BB";
    }
		return numSmallBlinds / 2 + ".5 BB";
	}

  public final boolean unequalTo(final AmountOfJetons toPost) {
		return this.numSmallBlinds != toPost.numSmallBlinds;
	}

  public final AmountOfJetons max(final AmountOfJetons post) {
		return this.greaterAs(post) ? this : post;
	}

  public final AmountOfJetons min(final AmountOfJetons post) {
		return post.greaterAs(this) ? this : post;
	}

  public final boolean isEven() {
		return numSmallBlinds % 2 == 0;
	}

  public static AmountOfJetons min(final AmountOfJetons amount1,
      final AmountOfJetons amount2) {
		return amount1.greaterAs(amount2) ? amount2 : amount1;
	}

  public final boolean positive() {
		return this.greaterAs(AmountOfJetons.ZERO);
	}

  public final AmountOfJetons divide(final int n) {
		return new AmountOfJetons(numSmallBlinds / n);
	}

  public final AmountOfJetons divideToEven(final int n) {
		assert n > 0;
		int res = numSmallBlinds / n;
    if (res % 2 == 1) {
      res--;
    }
		return new AmountOfJetons(res);
	}

  public final AmountOfJetons times(final int n) {
		return new AmountOfJetons(numSmallBlinds * n);
	}

  public final AmountOfJetons times(final double d) {
		return new AmountOfJetons((int) (numSmallBlinds * d));
	}

  public final boolean isZero() {
		return this.equals(AmountOfJetons.ZERO);
	}

  public final double divide(final AmountOfJetons amount) {
		return ((double) numSmallBlinds) / ((double) amount.numSmallBlinds);
	}

	@Override
  public final int compareTo(final AmountOfJetons o) {
		return numSmallBlinds - o.numSmallBlinds;
	}
}
