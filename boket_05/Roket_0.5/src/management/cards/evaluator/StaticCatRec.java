package management.cards.evaluator;

import java.io.IOException;
import java.util.List;

import management.cards.cards.Card;
import management.cards.catrecnew.ICatRecBase;
import management.cards.catrecnew.IResult;

import org.eclipse.jdt.annotation.Nullable;

public class StaticCatRec implements ICatRecBase {
  private final HandEvaluator eval;
  private @Nullable List<Card> cards;

  private static @Nullable StaticCatRec instance;

  public static StaticCatRec getInstance() {
    if (instance != null) {
      return instance;
    } else {
      StaticCatRec rec = new StaticCatRec();
      instance = rec;
      return rec;
    }
  }

  private StaticCatRec() {
    try {
      this.eval = HandEvaluator.getInstance();
    } catch (ClassNotFoundException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void setCards(List<Card> cards) {
    this.cards = cards;
  }

  @Override
  public final IResult check() {
    if (cards != null) {
      return eval.getResult(cards);
    } else {
      throw new IllegalArgumentException(
          "setCards() must be called before chekc() is called.");
    }
  }

}
