package tools;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import managementcards.all_cathegories.AllResults;
import managementcards.cards.Card;
import managementcards.catrecnew.IResult;

import org.eclipse.jdt.annotation.NonNull;

public class HandEvaluator {
  private static final String EVALUATED_HANDS_FILE_NAME = "hand_evaluator.ser";
  private static final String RESULTS_TYPES_FILE_NAME = "cathegories.txt";

  AllResults allRes;
  private final short[] map;
  private final BinomCoeff bino;

  public HandEvaluator() throws ClassNotFoundException, IOException {
    Object o = Tools.deserialize(EVALUATED_HANDS_FILE_NAME);
    map = (short @NonNull []) o;

    int numCards = 52;
    int numCardsNeeded = 7;

    this.bino = new BinomCoeff(numCards, numCardsNeeded);

    this.allRes = AllResults.getInstance(RESULTS_TYPES_FILE_NAME);
  }

  public short getScore(List<Card> cards) {
    Collections.sort(cards);
    int hash = bino.hash(cards);
    short score = map[hash - 1];
    return score;
  }

  public IResult getResult(List<Card> cards) {
    short score = getScore(cards);
    return allRes.getResult(score);

  }
}
