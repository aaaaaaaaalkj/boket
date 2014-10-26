package common;

import managementCards.cat_rec_new.IResult;

public interface IOutcome {
	default boolean betterAs(IPlayer p1, IPlayer p2) {
		return getResult(p1).compareTo(getResult(p2)) > 0;
	}

	IResult getResult(IPlayer player);
}
