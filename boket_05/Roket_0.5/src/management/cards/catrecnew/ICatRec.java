package management.cards.catrecnew;

import java.util.List;

import management.cards.cards.Card;
import strategy.conditions.postflop.DrawType;
import strategy.conditions.postflop.FlushDanger;
import strategy.conditions.postflop.PairBasedDanger;
import strategy.conditions.postflop.StraightDanger;

public interface ICatRec extends ICatRecBase {

  FlushDanger checkFlushDanger(List<Card> community);

  StraightDanger checkStraightDanger(List<Card> community);

  PairBasedDanger checkPairBasedDanger(List<Card> community);

  DrawType checkDraw(List<Card> all);

  @Override
  IResult check(List<Card> all);
}
