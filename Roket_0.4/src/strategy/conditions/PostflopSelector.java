package strategy.conditions;

import strategy.conditions.common.ContributionType;
import strategy.conditions.common.NumActiveType;
import strategy.conditions.common.PotType;
import strategy.conditions.postflop.ComboType;
import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;

public class PostflopSelector implements ISelector {
	ContributionType contribution;
	NumActiveType numActive;
	PotType pot;
	ComboType combo;
	PairBasedDanger pairBasedDanger;
	FlushDanger flushDanger;
	StraightDanger straightDanger;
	DrawType draw;

	private static final int n0 = 1;
	private static final int n1 = DrawType.VALUES.size();
	private static final int n2 = n1 * PairBasedDanger.VALUES.size();
	private static final int n3 = n2 * FlushDanger.VALUES.size();
	private static final int n4 = n3 * StraightDanger.VALUES.size();
	private static final int n5 = n4 * ComboType.VALUES.size();
	private static final int n6 = n5 * PotType.VALUES.size();
	private static final int n7 = n6 * NumActiveType.VALUES.size();
	private static final int n8 = n7 * ContributionType.VALUES.size();

	public static final int size = n6;

	public int getPosition() {
		return n8 * contribution.ordinal()
				+ n7 * numActive.ordinal()
				+ n6 * pot.ordinal()
				+ n5 * combo.ordinal()
				+ n4 * straightDanger.ordinal()
				+ n3 * flushDanger.ordinal()
				+ n2 * pairBasedDanger.ordinal()
				+ n0 * draw.ordinal();
	}

	public void setContribution(ContributionType contribution) {
		this.contribution = contribution;
	}

	public void setNumActive(NumActiveType numActive) {
		this.numActive = numActive;
	}

	public void setPot(PotType pot) {
		this.pot = pot;
	}

	public void setCombo(ComboType combo) {
		this.combo = combo;
	}

	public void setPairBasedDanger(PairBasedDanger danger) {
		this.pairBasedDanger = danger;
	}

	public FlushDanger getFlushDanger() {
		return flushDanger;
	}

	public void setFlushDanger(FlushDanger flushDanger) {
		this.flushDanger = flushDanger;
	}

	public StraightDanger getStraightDanger() {
		return straightDanger;
	}

	public void setStraightDanger(StraightDanger straightDanger) {
		this.straightDanger = straightDanger;
	}

	public void setDraw(DrawType draw) {
		this.draw = draw;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((combo == null) ? 0 : combo.hashCode());
		result = prime * result
				+ ((contribution == null) ? 0 : contribution.hashCode());
		result = prime * result + ((draw == null) ? 0 : draw.hashCode());
		result = prime * result
				+ ((flushDanger == null) ? 0 : flushDanger.hashCode());
		result = prime * result
				+ ((numActive == null) ? 0 : numActive.hashCode());
		result = prime * result
				+ ((pairBasedDanger == null) ? 0 : pairBasedDanger.hashCode());
		result = prime * result + ((pot == null) ? 0 : pot.hashCode());
		result = prime * result
				+ ((straightDanger == null) ? 0 : straightDanger.hashCode());
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
		PostflopSelector other = (PostflopSelector) obj;
		if (combo != other.combo)
			return false;
		if (contribution != other.contribution)
			return false;
		if (draw != other.draw)
			return false;
		if (flushDanger != other.flushDanger)
			return false;
		if (numActive != other.numActive)
			return false;
		if (pairBasedDanger != other.pairBasedDanger)
			return false;
		if (pot != other.pot)
			return false;
		if (straightDanger != other.straightDanger)
			return false;
		return true;
	}
}
