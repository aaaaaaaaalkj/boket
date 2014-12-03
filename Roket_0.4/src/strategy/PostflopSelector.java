package strategy;

import managementcards.catrecnew.Cathegory;

import org.eclipse.jdt.annotation.Nullable;

import strategy.conditions.common.ContributionType;
import strategy.conditions.common.NumActiveType;
import strategy.conditions.common.PotType;
import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;

public class PostflopSelector implements ISelector {
	private final ContributionType contribution;
	private final NumActiveType numActive;
	private final PotType pot;
	private final Cathegory combo;
	private final PairBasedDanger pairBasedDanger;
	private final FlushDanger flushDanger;
	private final StraightDanger straightDanger;
	private final DrawType draw;

	private static final int n0 = 1;
	private static final int n1 = DrawType.VALUES.size();
	private static final int n2 = n1 * PairBasedDanger.getCount();
	private static final int n3 = n2 * FlushDanger.getCount();
	private static final int n4 = n3 * StraightDanger.getCount();
	private static final int n5 = n4 * Cathegory.getCount();
	private static final int n6 = n5 * PotType.getCount();
	private static final int n7 = n6 * NumActiveType.getCount();
	private static final int n8 = n7 * ContributionType.getCount();

	public static final int size = n6;

  public PostflopSelector(final ContributionType contr, final NumActiveType numAct,
      final PotType pot, final Cathegory combo, final PairBasedDanger pairb,
      final FlushDanger flush, final StraightDanger straight, final DrawType draw) {
		this.contribution = contr;
		this.numActive = numAct;
		this.pot = pot;
		this.combo = combo;
		this.pairBasedDanger = pairb;
		this.flushDanger = flush;
		this.straightDanger = straight;
		this.draw = draw;
	}

  public final int getPosition() {
		return n8 * contribution.ordinal()
				+ n7 * numActive.ordinal()
				+ n6 * pot.ordinal()
				+ n5 * combo.ordinal()
				+ n4 * straightDanger.ordinal()
				+ n3 * flushDanger.ordinal()
				+ n2 * pairBasedDanger.ordinal()
				+ n0 * draw.ordinal();
	}

	@Override
  public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (combo.hashCode());
		result = prime * result
				+ (contribution.hashCode());
		result = prime * result + (draw.hashCode());
		result = prime * result
				+ (flushDanger.hashCode());
		result = prime * result
				+ (numActive.hashCode());
		result = prime * result
				+ (pairBasedDanger.hashCode());
		result = prime * result + (pot.hashCode());
		result = prime * result
				+ (straightDanger.hashCode());
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
		PostflopSelector other = (PostflopSelector) obj;
    if (combo != other.combo) {
      return false;
    }
    if (contribution != other.contribution) {
      return false;
    }
    if (draw != other.draw) {
      return false;
    }
    if (flushDanger != other.flushDanger) {
      return false;
    }
    if (numActive != other.numActive) {
      return false;
    }
    if (pairBasedDanger != other.pairBasedDanger) {
      return false;
    }
    if (pot != other.pot) {
      return false;
    }
    if (straightDanger != other.straightDanger) {
      return false;
    }
		return true;
	}
}
