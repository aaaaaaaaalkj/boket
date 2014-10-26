package managementCards.cat_rec_new;

import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;

public interface ICatRec {

	FlushDanger checkFlushDanger();

	StraightDanger checkStraightDanger();

	PairBasedDanger checkPairBasedDanger();

	DrawType checkDraw();

	IResult check();
}
