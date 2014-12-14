package cardsimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import management.cards.cards.Card;
import management.cards.cards.Rank;
import management.cards.cards.Suit;

import org.eclipse.jdt.annotation.NonNull;

import tools.Tools;

public class PreflopProbabilities implements HandSupplier {
	private static final int MIN_PLAYERS = 2;
	private static final int MAX_PLAYERS = 10;
	private static final int PROCENT = 100;
	private static final double MIN_DIVIATION = .01;

	// List<PossiblePreflopHand> hands;
	private Map<Integer, Map<Integer, List<PossiblePreflopHand>>> map;

	public PreflopProbabilities() {
		// hands = new ArrayList<>();

		map = new HashMap<>();
		for (Integer countPlayers = MIN_PLAYERS; countPlayers <= MAX_PLAYERS; countPlayers++) {
			map.put(countPlayers, new HashMap<>());
			for (int procent = 0; procent <= PROCENT; procent++) {
				map.get(countPlayers).put(procent, new ArrayList<>());
			}

		}

		readFromFile();
	}

	// public static void main(String[] args) throws FileNotFoundException {
	// PreflopProbabilities p = new PreflopProbabilities();
	// p.sort(9);
	//
	// p.print();
	// }
	private Random r = new Random();

	private double rand(final double contribution, final double variance) {
		double randNumber = (r.nextGaussian() * variance) + contribution;

		if (randNumber > 1) {
			randNumber = 1 - Math.abs(randNumber) % 1;
		} else {
			randNumber = Math.abs(randNumber) % 1;
		}
		return randNumber;
	}

	public final PossiblePreflopHand get(final int countPlayers,
			final double contribution,
			final double stdDev) {
		double contribution1 = contribution;
		if (contribution1 > 1) {
			contribution1 = 1;
		}
		// sort(countPlayers);

		double randNumber;

		double variance = Math.max(MIN_DIVIATION, stdDev);

		randNumber = rand(contribution1, variance);

		int procent = (int) Math.round(randNumber * PROCENT);

		if (map.get(countPlayers).containsKey(procent)) {
			List<PossiblePreflopHand> list = map.get(countPlayers).get(procent);

			int i = 0;
			while (list.isEmpty()) {
				i++;

				Map<Integer, List<PossiblePreflopHand>> map2 = map
						.get(countPlayers);
				if (map2.containsKey(procent + i)) {
					if (!map2.get(procent + i).isEmpty()) {
						list = map2.get(procent + i);
					}
				}
				if (map2.containsKey(procent - i)) {
					if (!map2.get(procent - i).isEmpty()) {
						list = map2.get(procent - i);
					}
				}

				if (!map2.containsKey(procent - i)
						&& !map2.containsKey(procent + i)) {
					//
					// for (Integer x : map.keySet()) {
					// for (Integer y : map.get(x).keySet()) {
					// System.out.println(x + " 	" + y + " "
					// + map.get(x).get(y));
					// }
					// }
					throw new IllegalStateException("no elements in preflop"
							+ procent);
				}
			}

			int index = (int) Math.floor(Math.random() * list.size());

			return list.get(index);

			// int index = (int) Math.floor(randNumber * hands.size());
			// System.out.println(index + ": " + hands.get(index));
			// return hands.get(index);

		}
    throw new IllegalStateException("list is null " + countPlayers
        + " " + procent);

	}

	public final void print() {
		System.out.println(this);
	}

	@Override
	public final String toString() {
		return "no toString implemented for PrefolopProbabilities";
		// return hands.stream()
		// .map(PossiblePreflopHand::toString)
		// .collect(joining("\n"));
	}

	private void add(final PossiblePreflopHand hand) {
		// hands.add(hand);
		for (int countPlayers = MIN_PLAYERS; countPlayers <= MAX_PLAYERS; countPlayers++) {
			double score = hand.getScore(countPlayers);
			int procent = (int) Math.round(score * PROCENT);
			map.get(countPlayers).get(procent).add(hand);
		}
	}

	private void readFromFile() {
		File source = new File(
				"preflop_probabilities.csv");

		try (Scanner scanner = new Scanner(source)) {
			scanner.useDelimiter("\r\n");

			while (scanner.hasNext()) {
				@SuppressWarnings("null")
				@NonNull
				String next = scanner.next();
				@SuppressWarnings("null")
				@NonNull
				String[] s = Tools.split(next, ";");
				@SuppressWarnings("null")
				@NonNull
				String[] handCode = Tools.split(s[0], "");

				Rank first = Rank.fromShortString(handCode[0]);
				Rank second = Rank.fromShortString(handCode[1]);

				boolean suited = false;
				if (handCode.length > 2 && handCode[2].equals("s")) {
					suited = true;
				}
				Suit firstSuit = Suit.HEARTS;
				Suit secondSuit = suited ? Suit.HEARTS : Suit.CLUBS;

				List<Double> probabilities = Arrays.asList(s)
						.subList(1, s.length)
						.stream()
						.map(str -> Double.parseDouble(str))
						.collect(Tools.toList());

				PossiblePreflopHand hand = new PossiblePreflopHand(
						Card.instance(
								first, firstSuit),
						Card.instance(second, secondSuit), probabilities);
				add(hand);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public final List<Card> getHand(final int numPlayers,
			final double contribution,
			final double stdDev) {
		return get(numPlayers, contribution, stdDev).getHand();
	}

}
