package card_simulation.flopSimulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import managementCards.cards.Card;
import card_simulation.HandGenerator;

public class FlopHands implements HandGenerator {
	private static final Random r = new Random();
	private final Map<Integer, List<FlopHand>> map;

	public double getRand(double contribution, double variance) {
		double randNumber = (r.nextGaussian() * variance) + contribution;

		if (randNumber > 1) {
			randNumber = 1 - Math.abs(randNumber) % 1;
		} else {
			randNumber = Math.abs(randNumber) % 1;
		}
		return randNumber;
	}

	public FlopHands(List<FlopHand> list) {
		map = new HashMap<>();
		for (int i = 0; i <= 100; i++) {
			map.put(i, new ArrayList<>());
		}

		for (FlopHand hand : list) {
			double score = hand.getScore();

			int procent = (int) Math.round(score * 100);

			map.get(procent).add(hand);
		}
	}

	public List<Card> getHand(double contribution) {
		if (contribution > 1) {
			// why is this possible?
			contribution = 1;
		}

		double variance = 1 - contribution;

		variance = Math.max(.03, variance);

		double randNumber;
		randNumber = getRand(contribution, variance);

		int procent = (int) Math.round(randNumber * 100);

		if (!map.containsKey(procent)) {
			throw new IllegalStateException("list is null: " + procent + " "
					+ contribution);
		} else {

			List<FlopHand> list = map.get(procent);

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
	public List<Card> getHand(int numPlayers, double contribution) {
		return getHand(contribution);
	}
}
