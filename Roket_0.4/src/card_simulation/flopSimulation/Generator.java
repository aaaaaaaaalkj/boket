package card_simulation.flopSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import managementCards.cards.Card;
import card_simulation.HandGenerator;

public class Generator implements HandGenerator {
	private static final Random r = new Random();
	private final Map<Integer, List<LateRoundHand>> map;

	public double getRand(double contribution, double variance) {
		double randNumber = (r.nextGaussian() * variance) + contribution;

		if (randNumber > 1) {
			randNumber = 1 - Math.abs(randNumber) % 1;
		} else {
			randNumber = Math.abs(randNumber) % 1;
		}
		return randNumber;
	}

	public Generator(List<LateRoundHand> list) {
		map = new HashMap<>();
		for (int i = 0; i <= 100; i++) {
			map.put(i, new ArrayList<>());
		}

		for (LateRoundHand hand : list) {
			double score = hand.getScore();

			int procent = (int) Math.round(score * 100);

			map.get(procent).add(hand);
		}
	}

	public List<Card> getHand(double contribution, double stdDev) {
		if (contribution > 1) {
			// why is this possible?
			contribution = 1;
		}

		double variance;

		// variance= 1 - contribution;

		variance = stdDev;

		double randNumber;
		randNumber = getRand(contribution, variance);

		int procent = (int) Math.round(randNumber * 100);

		// System.out.println(procent);

		if (!map.containsKey(procent)) {
			throw new IllegalStateException("list is null: " + procent + " "
					+ contribution);
		} else {

			List<LateRoundHand> list = map.get(procent);

			if (procent < 50) {
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
	public List<Card> getHand(int numPlayers, double contribution, double stdDev) {
		return getHand(contribution, stdDev);
	}
}
