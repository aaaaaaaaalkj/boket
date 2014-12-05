package tools;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import managementcards.all_cathegories.AllResults;
import managementcards.cards.Card;
import managementcards.catrecnew.IResult;

public final class HandEvaluator {
  private static final String EVALUATED_HANDS_FILE_NAME = "hand_evaluator.ser";
  private static final String RESULTS_TYPES_FILE_NAME = "cathegories.txt";
  private static final int NUM_CARDS = 52;
  private static final int NUM_CARDS_NEEDED = 7;

  private AllResults allRes;
  private final short[] map;
  private final BinomCoeff bino;


  @SuppressWarnings("null")
  public HandEvaluator() throws ClassNotFoundException, IOException {
    Object o = Tools.deserialize(EVALUATED_HANDS_FILE_NAME);
    map = (short[]) o;

    this.bino = new BinomCoeff(NUM_CARDS, NUM_CARDS_NEEDED);

    this.allRes = AllResults.getInstance(RESULTS_TYPES_FILE_NAME);
  }

  public short getScore(final List<Card> cards) {
    Collections.sort(cards);
    int hash = bino.hash(cards);
    short score = map[hash - 1];
    return score;
  }

  public IResult getResult(final List<Card> cards) {
    short score = getScore(cards);
    return allRes.getResult(score);

  }
}
