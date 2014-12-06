package cardsimulation.flopSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import management.cards.cards.Card;
import cardsimulation.HandSupplier;

public class Generator implements HandSupplier {
	private static final int PROCENT = 100;

	private static final Random RND = new Random();
	private final Map<Integer, List<LateRoundHand>> map;

	public final double getRand(final double contribution, final double variance) {
		double randNumber = (RND.nextGaussian() * variance) + contribution;

		if (randNumber > 1) {
			randNumber = 1 - Math.abs(randNumber) % 1;
		} else {
			randNumber = Math.abs(randNumber) % 1;
		}
		return randNumber;
	}

	public Generator(final List<LateRoundHand> list) {
		map = new HashMap<>();
		for (int i = 0; i <= PROCENT; i++) {
			map.put(i, new ArrayList<>());
		}

		for (LateRoundHand hand : list) {
			double score = hand.getScore();

			int procent = (int) Math.round(score * PROCENT);

			map.get(procent).add(hand);
		}
	}

	public final List<Card> getHand(final double contribution,
			final double stdDev) {
		double contribution1 = contribution;
		if (contribution1 > 1) {
			// why is this possible?
			contribution1 = 1;
		}

		double variance;

		// variance= 1 - contribution;

		variance = stdDev;

		double randNumber;
		randNumber = getRand(contribution1, variance);

		int procent = (int) Math.round(randNumber * PROCENT);

		// System.out.println(procent);

		if (!map.containsKey(procent)) {
			throw new IllegalStateException("list is null: " + procent + " "
					+ contribution1);
		} else {

			List<LateRoundHand> list = map.get(procent);

			if (procent < PROCENT / 2) {
				while (list.isEmpty()) {
					list = map.get(procent++);
				}
			} else {
				while (list.isEmpty()) {
					list = map.get(procent--);
				}
			}

			int index = (int) Math.floor(Math.random() * list.size());

			return list.get(index).getCards();
		}
	}

	@Override
	public final List<Card> getHand(final int numPlayers,
			final double contribution,
			final double stdDev) {
		return getHand(contribution, stdDev);
	}
}
