package old.strategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Position {

	SB, BB, UTG1, UTG2, UTG3, MP1, MP2, MP3, CO, BU;

	// TODO: implement recognition of sitting-out-Player
	@SuppressWarnings("null")
	public static final List<Position> VALUES = Collections
			.unmodifiableList(Arrays.asList(values()));

	public Position prev() {
		int num = this.ordinal() - 1;
    if (num < 0) {
      num = VALUES.size() - 1;
    }
		return VALUES.get(num);
	}

	public Position next() {
		int num = this.ordinal() + 1;
    if (num >= VALUES.size()) {
      num = 0;
    }
		return VALUES.get(num);
	}

	// public SimplePosition toSimple() {
	// switch (this) {
	// case SB:
	// return SimplePosition.SB;
	// case BB:
	// return SimplePosition.BB;
	// case UTG1:
	// case UTG2:
	// case UTG3:
	// return SimplePosition.UTG;
	// case MP1:
	// case MP2:
	// case MP3:
	// return SimplePosition.MP;
	// case CO:
	// return SimplePosition.CO;
	// case BU:
	// return SimplePosition.BU;
	// default:
	// throw new RuntimeException(this + " is not supported");
	// }
	// }

}
