package old.strategy;

public enum Position {

	SB, BB, UTG1, UTG2, UTG3, MP1, MP2, MP3, CO, BU;

	// TODO: implement recognition of sitting-out-Player

	public Position prev() {
		int num = this.ordinal() - 1;
		if (num < 0)
			num = Position.values().length - 1;
		return Position.values()[num];
	}

	public Position next() {
		int num = this.ordinal() + 1;
		if (num >= Position.values().length)
			num = 0;
		return Position.values()[num];
	}

//	public SimplePosition toSimple() {
//		switch (this) {
//		case SB:
//			return SimplePosition.SB;
//		case BB:
//			return SimplePosition.BB;
//		case UTG1:
//		case UTG2:
//		case UTG3:
//			return SimplePosition.UTG;
//		case MP1:
//		case MP2:
//		case MP3:
//			return SimplePosition.MP;
//		case CO:
//			return SimplePosition.CO;
//		case BU:
//			return SimplePosition.BU;
//		default:
//			throw new RuntimeException(this + " is not supported");
//		}
//	}

}
