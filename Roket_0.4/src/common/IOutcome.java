package common;

import managementCards.cat_rec_new.IResult;

public interface IOutcome {
	public boolean betterAs(IPlayer p1, IPlayer p2);

	public IResult getResult(IPlayer player);
}
