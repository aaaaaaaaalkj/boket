package managementcards.catrecnew;

import java.util.List;

import managementcards.cards.Rank;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import tools.Tools;

public final class ResultImpl implements IResult {
	private final Cathegory cathegory;
	private final List<Rank> tieBreaker;

  public ResultImpl(final Cathegory cat, final List<Rank> tieBreaker) {
		this.cathegory = cat;
		this.tieBreaker = tieBreaker;
	}

  public ResultImpl(final Cathegory cat, @NonNull final Rank... cardNums) {
		this.cathegory = cat;
		this.tieBreaker = Tools.asList(cardNums);
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
				+ (cathegory.hashCode());
		result = prime * result
				+ (tieBreaker.hashCode());
		return result;
	}

	@Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
		ResultImpl other = (ResultImpl) obj;
    if (cathegory != other.cathegory) {
      return false;
    }
    if (!tieBreaker.equals(other.tieBreaker)) {
      return false;
    }
		return true;
	}

	@Override
  public int compareTo(@Nullable final IResult res2) {
		if (res2 instanceof ResultImpl) {
			ResultImpl res = (ResultImpl) res2;
      if (cathegory.ordinal() > res.cathegory.ordinal()) {
        return 1;
      }
      if (cathegory.ordinal() < res.cathegory.ordinal()) {
        return -1;
      }
			for (int i = 0; i < tieBreaker.size() && i < res.tieBreaker.size(); i++) {
				int tieThis = tieBreaker.get(i).ordinal();
				int tieOther = res.tieBreaker.get(i).ordinal();

        if (tieThis > tieOther) {
          return 1;
        }
        if (tieThis < tieOther) {
          return -1;
        }
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

	  /**
   * returns an impossible bad hand.
   * 
   * @return
   */
	public static ResultImpl bottom() {
		return new ResultImpl(Cathegory.HIGH_CARD,
				Tools.asList(Rank.Two, Rank.Two, Rank.Two, Rank.Two, Rank.Two));
	}
}
