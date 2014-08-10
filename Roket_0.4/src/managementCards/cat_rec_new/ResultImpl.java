package managementCards.cat_rec_new;

import java.util.Arrays;
import java.util.List;

import managementCards.cards.Rank;

public final class ResultImpl implements Result {
	private final Cathegory cathegory;
	private final List<Rank> tieBreaker;

	public ResultImpl(Cathegory cat, List<Rank> tieBreaker) {
		this.cathegory = cat;
		this.tieBreaker = tieBreaker;
	}

	public ResultImpl(Cathegory cat, Rank... cardNums) {
		this.cathegory = cat;
		this.tieBreaker = Arrays.asList(cardNums);
	}

	@Override
	public String toString() {
		return cathegory + " ( " + tieBreaker + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cathegory == null) ? 0 : cathegory.hashCode());
		result = prime * result
				+ ((tieBreaker == null) ? 0 : tieBreaker.hashCode());
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
		ResultImpl other = (ResultImpl) obj;
		if (cathegory != other.cathegory)
			return false;
		if (tieBreaker == null) {
			if (other.tieBreaker != null)
				return false;
		} else if (!tieBreaker.equals(other.tieBreaker))
			return false;
		return true;
	}

	@Override
	public int compareTo(Result res2) {
		if (res2 instanceof ResultImpl) {
			ResultImpl res = (ResultImpl) res2;
			if (cathegory.ordinal() > res.cathegory.ordinal())
				return 1;
			if (cathegory.ordinal() < res.cathegory.ordinal())
				return -1;
			for (int i = 0; i < tieBreaker.size() && i < res.tieBreaker.size(); i++) {
				int tieThis = tieBreaker.get(i).ordinal();
				int tieOther = res.tieBreaker.get(i).ordinal();

				if (tieThis > tieOther)
					return 1;
				if (tieThis < tieOther)
					return -1;
			}

			return 0;
		} else {
			throw new IllegalArgumentException(res2
					+ " can not be compared to " + this);
		}
	}

	@Override
	public Cathegory getCathegory() {
		return cathegory;
	}
}