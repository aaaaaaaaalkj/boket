package strategy.conditions;

import strategy.conditions.common.ContributionType;
import strategy.conditions.common.NumActiveType;
import strategy.conditions.common.PotType;
import strategy.conditions.postflop.ComboType;
import strategy.conditions.postflop.DangerType;
import strategy.conditions.postflop.DrawType;

public class PostflopSelector implements ISelector {
	ContributionType contribution;
	NumActiveType numActive;
	PotType pot;
	ComboType combo;
	DangerType danger;
	DrawType draw;

	private static final int n0 = 1;
	private static final int n1 = DrawType.VALUES.size();
	private static final int n2 = n1 * DangerType.VALUES.size();
	private static final int n3 = n2 * ComboType.VALUES.size();
	private static final int n4 = n3 * PotType.VALUES.size();
	private static final int n5 = n4 * NumActiveType.VALUES.size();
	private static final int n6 = n5 * ContributionType.VALUES.size();

	public static final int size = n6;

	public int getPosition() {
		return n5 * contribution.ordinal()
				+ n4 * numActive.ordinal()
				+ n3 * pot.ordinal()
				+ n2 * combo.ordinal()
				+ n1 * danger.ordinal()
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

	public void setDanger(DangerType danger) {
		this.danger = danger;
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
		result = prime * result + ((danger == null) ? 0 : danger.hashCode());
		result = prime * result + ((draw == null) ? 0 : draw.hashCode());
		result = prime * result
				+ ((numActive == null) ? 0 : numActive.hashCode());
		result = prime * result + ((pot == null) ? 0 : pot.hashCode());
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
		if (danger != other.danger)
			return false;
		if (draw != other.draw)
			return false;
		if (numActive != other.numActive)
			return false;
		if (pot != other.pot)
			return false;
		return true;
	}

}
