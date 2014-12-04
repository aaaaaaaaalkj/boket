package managementrewards;

import org.eclipse.jdt.annotation.Nullable;

import strategy.ISituation;
import strategy.TypeOfDecision;

public final class RewardKey {
	private final ISituation s;
	private final TypeOfDecision d;

  private RewardKey(final ISituation situation, final TypeOfDecision d) {
		this.s = situation;
		this.d = d;
	}

  public static RewardKey create(final ISituation situation, final TypeOfDecision d) {
		return new RewardKey(situation, d);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + s.getCathegory().hashCode();
		result = prime * result + s.getConnector().hashCode();
		result = prime * result + s.getContribution().hashCode();
		result = prime * result + s.getDraw().hashCode();
		result = prime * result + s.getFlushDanger().hashCode();
		result = prime * result + s.getNumActive().hashCode();
		result = prime * result + s.getPairBasedDanger().hashCode();
		result = prime * result + s.getPot().hashCode();
		result = prime * result + s.getRound().hashCode();
		result = prime * result + s.getStraightDanger().hashCode();
		result = prime * result + s.getSuit().hashCode();
		result = prime * result + d.hashCode();
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
		RewardKey other = (RewardKey) obj;
    if (s.getCathegory() != other.s.getCathegory()) {
      return false;
    }
    if (s.getConnector() != other.s.getConnector()) {
      return false;
    }
    if (s.getContribution() != other.s.getContribution()) {
      return false;
    }
    if (s.getDraw() != other.s.getDraw()) {
      return false;
    }
    if (s.getFlushDanger() != other.s.getFlushDanger()) {
      return false;
    }
    if (s.getNumActive() != other.s.getNumActive()) {
      return false;
    }
    if (s.getPairBasedDanger() != other.s.getPairBasedDanger()) {
      return false;
    }
    if (s.getPot() != other.s.getPot()) {
      return false;
    }
    if (s.getRound() != other.s.getRound()) {
      return false;
    }
    if (s.getStraightDanger() != other.s.getStraightDanger()) {
      return false;
    }
    if (s.getSuit() != other.s.getSuit()) {
      return false;
    }
    if (d != other.d) {
      return false;
    }
		return true;
	}

}
