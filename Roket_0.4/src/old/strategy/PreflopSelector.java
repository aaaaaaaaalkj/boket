package old.strategy;

import org.eclipse.jdt.annotation.Nullable;

public class PreflopSelector {
  private PreflopBuket buket;
	private Position position;
	private PreflopSituation situation;

  public PreflopSelector(final PreflopBuket buket, final Position position,
      final PreflopSituation sit) {
		this.buket = buket;
		this.position = position;
		this.situation = sit;
	}

	@Override
  public final int hashCode() {
		return buket.hashCode() + position.hashCode() * 71
				+ situation.hashCode() * 1009;
	}

  public final PreflopBuket getBuket() {
		return buket;
	}

  public final Position getPosition() {
		return position;
	}

  public final PreflopSituation getSituation() {
		return situation;
	}

	@Override
  public final boolean equals(@Nullable final Object o) {
    if (o == this) {
      return true;
    }
		if (o instanceof PreflopSelector) {
			PreflopSelector p = (PreflopSelector) o;
			return buket == p.buket && position == p.position
					&& situation == p.situation;
		}
		return false;
	}

  public final void print() {
		System.out.println(this);

	}

  public final String toString() {
		return buket.toString() + "\n" + position.toString() + "\n"
				+ situation.toString();
	}

}
