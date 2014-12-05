package managementcards.catrecnew;

import java.util.List;

import strategy.ISituation;
import strategy.conditions.ICondition;
import tools.Tools;

public enum Cathegory implements ICondition {
  HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH;

  @SuppressWarnings("null")
  public static final List<Cathegory> VALUES = Tools.asList(values());

  public static Cathegory getCathegory(final int index) {
    return VALUES.get(index);
  }

  public static int getCount() {
    return VALUES.size();
  }

  @Override
  public boolean eval(final ISituation sit) {
    return this == sit.getCathegory();
  }

  public ICondition orBetter() {
    return (sit -> sit.getCathegory().ordinal() >= ordinal());
  }

  public void print() {
    System.out.println(this);
  }
}
