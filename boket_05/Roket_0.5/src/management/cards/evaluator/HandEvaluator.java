package management.cards.evaluator;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import management.cards.AllResults;
import management.cards.cards.Card;
import management.cards.catrecnew.IResult;

import org.eclipse.jdt.annotation.Nullable;

import tools.Tools;

public final class HandEvaluator {
  private static final String EVALUATED_HANDS_FILE_NAME = "hand_evaluator.ser";
  private static final String RESULTS_TYPES_FILE_NAME = "cathegories.txt";
  private static final int NUM_CARDS = 52;
  private static final int NUM_CARDS_NEEDED = 7;
  private static final int OFFSET = 1; // zero-based

  private AllResults allRes;
  private final short[] map;
  private final BinomCoeff bino;

  @SuppressWarnings("null")
  private HandEvaluator() throws ClassNotFoundException, IOException {
    map = (short[]) Tools.deserialize(EVALUATED_HANDS_FILE_NAME);

    this.bino = new BinomCoeff(NUM_CARDS, NUM_CARDS_NEEDED);

    this.allRes = AllResults.getInstance(RESULTS_TYPES_FILE_NAME);
  }

  @Nullable
  private static HandEvaluator singeltonInstance;

  public static HandEvaluator getInstance() throws ClassNotFoundException,
      IOException {
    HandEvaluator instance;
    if (null != singeltonInstance) {
      instance = singeltonInstance;
    } else {
      instance = new HandEvaluator();
      singeltonInstance = instance;
    }
    return instance;
  }

  public short getScore(final List<Card> cards) {
    Collections.sort(cards);
    int hash = bino.hash(cards);
    short score = map[hash - OFFSET];
    return score;
  }


  public IResult getResult(final List<Card> cards) {
    short score = getScore(cards);
    IResult res = allRes.getResult(score);
    return res;

  }


}
