package old.strategy;

import org.eclipse.jdt.annotation.Nullable;

public class PreflopSelector {
	PreflopBuket buket;
	private Position position;
	private PreflopSituation situation;

	public PreflopSelector(PreflopBuket buket, Position position,
			PreflopSituation sit) {
		this.buket = buket;
		this.position = position;
		this.situation = sit;
	}

	@Override
	public int hashCode() {
		return buket.hashCode() + position.hashCode() * 71
				+ situation.hashCode() * 1009;
	}

	public PreflopBuket getBuket() {
		return buket;
	}

	public Position getPosition() {
		return position;
	}

	public PreflopSituation getSituation() {
		return situation;
	}

	@Override
	public boolean equals(@Nullable Object o) {
		if (o == this)
			return true;
		if (o instanceof PreflopSelector) {
			PreflopSelector p = (PreflopSelector) o;
			return buket == p.buket && position == p.position
					&& situation == p.situation;
		}
		return false;
	}

	public void print() {
		System.out.println(this);

	}

	public String toString() {
		return buket.toString() + "\n" + position.toString() + "\n"
				+ situation.toString();
	}

}
