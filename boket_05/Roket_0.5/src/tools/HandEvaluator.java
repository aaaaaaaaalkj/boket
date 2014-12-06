package tools;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import management.cards.AllResults;
import management.cards.cards.Card;
import management.cards.catrecnew.IResult;

import org.eclipse.jdt.annotation.Nullable;

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
	public HandEvaluator() throws ClassNotFoundException, IOException {
		Object o = Tools.deserialize(EVALUATED_HANDS_FILE_NAME);
		map = (short[]) o;

		this.bino = new BinomCoeff(NUM_CARDS, NUM_CARDS_NEEDED);

		this.allRes = AllResults.getInstance(RESULTS_TYPES_FILE_NAME);
	}

	public short getScore(final List<Card> cards) {
		Collections.sort(cards);
		int hash = bino.hash(cards);
		short score = map[hash - OFFSET];
		return score;
	}

	@Nullable
	public IResult getResult(final List<Card> cards) {
		short score = getScore(cards);
		IResult res = allRes.getResult(score);
		return res;

	}
}
