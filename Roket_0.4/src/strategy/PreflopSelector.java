package strategy;

import org.eclipse.jdt.annotation.Nullable;

import strategy.conditions.common.ContributionType;
import strategy.conditions.common.NumActiveType;
import strategy.conditions.common.PotType;
import strategy.conditions.preflop.ConnectorType;
import strategy.conditions.preflop.SuitedType;

public final class PreflopSelector implements ISelector {
	private final ContributionType contribution;
	private final NumActiveType numActive;
	private final PotType pot;
	private final ConnectorType connector;
	private final SuitedType suited;

	private static final int n0 = 1;
	private static final int n1 = SuitedType.VALUES.size();
	private static final int n2 = n1 * ConnectorType.VALUES.size();
	private static final int n3 = n2 * PotType.getCount();
	private static final int n4 = n3 * NumActiveType.getCount();
	private static final int n5 = n4 * ContributionType.getCount();

	public static final int size = n5;

	public PreflopSelector(ContributionType contr, NumActiveType numAct,
			PotType pot, ConnectorType conn, SuitedType suit) {
		this.contribution = contr;
		this.numActive = numAct;
		this.pot = pot;
		this.connector = conn;
		this.suited = suit;
	}

	public int getPosition() {
		return n4 * contribution.ordinal()
				+ n3 * numActive.ordinal()
				+ n2 * pot.ordinal()
				+ n1 * connector.ordinal()
				+ n0 * suited.ordinal();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((connector == null) ? 0 : connector.hashCode());
		result = prime * result
				+ ((contribution == null) ? 0 : contribution.hashCode());
		result = prime * result
				+ ((numActive == null) ? 0 : numActive.hashCode());
		result = prime * result + ((pot == null) ? 0 : pot.hashCode());
		result = prime * result + ((suited == null) ? 0 : suited.hashCode());
		return result;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PreflopSelector other = (PreflopSelector) obj;
		if (connector != other.connector)
			return false;
		if (contribution != other.contribution)
			return false;
		if (numActive != other.numActive)
			return false;
		if (pot != other.pot)
			return false;
		if (suited != other.suited)
			return false;
		return true;
	}

}
