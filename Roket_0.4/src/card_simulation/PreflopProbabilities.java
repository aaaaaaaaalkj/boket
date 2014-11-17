package card_simulation;

import static java.util.stream.Collectors.toList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import managementCards.cards.Card;
import managementCards.cards.Rank;
import managementCards.cards.Suit;

public class PreflopProbabilities {
	// List<PossiblePreflopHand> hands;
	Map<Integer, Map<Integer, List<PossiblePreflopHand>>> map;

	public PreflopProbabilities() {
		// hands = new ArrayList<>();

		map = new HashMap<>();
		for (int count_players = 2; count_players <= 10; count_players++) {
			map.put(count_players, new HashMap<>());
			for (int procent = 0; procent <= 100; procent++) {
				map.get(count_players).put(procent, new ArrayList<>());
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
	Random r = new Random();

	private double rand(double contribution, double variance) {
		double randNumber = (r.nextGaussian() * variance) + contribution;

		if (randNumber > 1) {
			randNumber = 1 - Math.abs(randNumber) % 1;
		} else {
			randNumber = Math.abs(randNumber) % 1;
		}
		return randNumber;
	}

	public PossiblePreflopHand get(int countPlayers, double contribution) {
		if (contribution > 1) {
			contribution = 1;
		}
		// sort(countPlayers);

		double randNumber;

		double variance = 0.3;// 1 - contribution;

		variance = Math.max(.03, variance);

		randNumber = rand(contribution, variance);

		int procent = (int) Math.round(randNumber * 100);

		List<PossiblePreflopHand> list = map.get(countPlayers).get(procent);

		// for (int i = 0; i < 100; i++) {
		// System.out
		// .println("procent: " + i + " size: "
		// + map.get(countPlayers).get(i).size());
		// }

		if (null == list) {
			throw new IllegalStateException("list is null " + countPlayers
					+ " " + procent);
		}

		int i = 0;
		while (list.isEmpty()) {
			i++;
			List<PossiblePreflopHand> list1 = map.get(countPlayers).get(
					procent + i);
			List<PossiblePreflopHand> list2 = map.get(countPlayers).get(
					procent - i);

			if (list1 == null && list2 == null) {
				throw new IllegalStateException("no elements in preflop");
			}

			if (list1 != null && !list1.isEmpty()) {
				list = list1;
			}
			if (list2 != null && !list2.isEmpty()) {
				list = list2;
			}
		}

		int index = (int) Math.floor(Math.random() * list.size());

		return list.get(index);

		// int index = (int) Math.floor(randNumber * hands.size());
		// System.out.println(index + ": " + hands.get(index));
		// return hands.get(index);

	}

	public void print() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		return "no toString implemented for PrefolopProbabilities";
		// return hands.stream()
		// .map(PossiblePreflopHand::toString)
		// .collect(joining("\n"));
	}

	private void add(PossiblePreflopHand hand) {
		// hands.add(hand);
		for (int count_players = 2; count_players <= 10; count_players++) {
			double score = hand.getScore(count_players);
			int procent = (int) Math.round(score * 100);
			map.get(count_players).get(procent).add(hand);
		}
	}

	private void readFromFile() {
		File source = new File(
				"preflop_probabilities.csv");

		try (Scanner scanner = new Scanner(source)) {
			scanner.useDelimiter("\n");

			while (scanner.hasNext()) {
				String[] s = scanner.next().split(";");

				String[] hand_code = s[0].split("");

				Rank first = Rank.fromShortString(hand_code[0]);
				Rank second = Rank.fromShortString(hand_code[1]);

				boolean suited = false;
				if (hand_code.length > 2 && hand_code[2].equals("s")) {
					suited = true;
				}
				Suit firstSuit = Suit.HEARTS;
				Suit secondSuit = suited ? Suit.HEARTS : Suit.CLUBS;

				List<Double> probabilities = Arrays.asList(s)
						.subList(1, s.length - 1)
						.stream()
						.map(str -> Double.parseDouble(str)).collect(toList());

				if (probabilities.size() != 9) {
					System.out.println("---------");
					System.out.println(Arrays.toString(s));
					System.out.println("---------");
				}

				PossiblePreflopHand hand = new PossiblePreflopHand(new Card(
						first, firstSuit),
						new Card(second, secondSuit), probabilities);
				add(hand);

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
