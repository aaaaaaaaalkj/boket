package strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.eclipse.jdt.annotation.NonNull;

import common.Round;

public final class NewStrategy implements IStrategy {
  private List<TypeOfDecision> preflop = new ArrayList<>();
  private List<TypeOfDecision> postflop = new ArrayList<>();

  private NewStrategy(final Random rnd) {
    for (int i = 0; i < PreflopSelector.SIZE; i++) {
      preflop.add(TypeOfDecision.random(rnd));
    }
    for (int i = 0; i < PostflopSelector.SIZE; i++) {
      preflop.add(TypeOfDecision.random(rnd));
    }

  }

  public static NewStrategy random(final Random rnd) {
    NewStrategy s = new NewStrategy(rnd);
    return s;
  }

  public TypeOfDecision getPreflop(final ISelector sel) {
    @NonNull
    TypeOfDecision x = preflop.get(sel.getPosition());
    return x;
  }

  public TypeOfDecision getPostflop(final ISelector sel) {
    @NonNull
    TypeOfDecision x = preflop.get(sel.getPosition());
    return x;
  }

  public void setPreflop(final ISelector sel, final TypeOfDecision dec) {
    preflop.set(sel.getPosition(), dec);
  }

  public void setPostflop(final ISelector sel, final TypeOfDecision dec) {
    postflop.set(sel.getPosition(), dec);
  }

  private TypeOfDecision get(final PreflopSelector sel) {
    TypeOfDecision x = preflop.get(sel.getPosition());
    return x;
  }

  private TypeOfDecision get(final PostflopSelector sel) {
    TypeOfDecision x = postflop.get(sel.getPosition());
    return x;
  }

  @Override
  public TypeOfDecision decide(final ISituation situation) {
    if (Round.PREFLOP == situation.getRound()) {
      return get(new PreflopSelector(
          situation.getContribution(),
          situation.getNumActive(),
          situation.getPot(),
          situation.getConnector(),
          situation.getSuit()));
    } else {
      return get(new PostflopSelector(
          situation.getContribution(),
          situation.getNumActive(),
          situation.getPot(),
          situation.getCathegory(),
          situation.getPairBasedDanger(),
          situation.getFlushDanger(),
          situation.getStraightDanger(),
          situation.getDraw()));
    }
  }
}
