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

	private static final int N0 = 1;
	private static final int N1 = SuitedType.VALUES.size();
	private static final int N2 = N1 * ConnectorType.VALUES.size();
	private static final int N3 = N2 * PotType.getCount();
	private static final int N4 = N3 * NumActiveType.getCount();
	private static final int N5 = N4 * ContributionType.getCount();

	public static final int SIZE = N5;

  public PreflopSelector(final ContributionType contr, final NumActiveType numAct,
      final PotType pot, final ConnectorType conn, final SuitedType suit) {
		this.contribution = contr;
		this.numActive = numAct;
		this.pot = pot;
		this.connector = conn;
		this.suited = suit;
	}

	public int getPosition() {
		return N4 * contribution.ordinal()
				+ N3 * numActive.ordinal()
				+ N2 * pot.ordinal()
				+ N1 * connector.ordinal()
				+ N0 * suited.ordinal();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (connector.hashCode());
		result = prime * result
				+ (contribution.hashCode());
		result = prime * result
				+ (numActive.hashCode());
		result = prime * result + (pot.hashCode());
		result = prime * result + (suited.hashCode());
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
		PreflopSelector other = (PreflopSelector) obj;
    if (connector != other.connector) {
      return false;
    }
    if (contribution != other.contribution) {
      return false;
    }
    if (numActive != other.numActive) {
      return false;
    }
    if (pot != other.pot) {
      return false;
    }
    if (suited != other.suited) {
      return false;
    }
		return true;
	}

}
