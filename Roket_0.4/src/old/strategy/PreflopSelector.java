package old.strategy;

public class PreflopSelector {
	PreflopBuket buket;
	private Position position;
	private PreflopSituation situation;

	@Override
	public int hashCode() {
		return buket.hashCode() + position.hashCode() * 71
				+ situation.hashCode() * 1009;
	}

	public PreflopBuket getBuket() {
		return buket;
	}

	public void setBuket(PreflopBuket buket) {
		this.buket = buket;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public PreflopSituation getSituation() {
		return situation;
	}

	public void setSituation(PreflopSituation situation) {
		this.situation = situation;
	}

	@Override
	public boolean equals(Object o) {
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
