package managementCards.cat_rec_new.results;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toMap;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import managementCards.cards.Card;
import managementCards.cards.Deck;
import managementCards.cards.Rank;
import managementCards.cat_rec_new.Cat_Rec;
import managementCards.cat_rec_new.Cathegory;
import managementCards.cat_rec_new.ResultImpl;
import tools.Tools;

public class Results {

	@SuppressWarnings("null")
	public static Rank toRank(int z) {
		return Rank.values()[z - 2];
	}

	@SuppressWarnings("null")
	public static Cathegory toCat(int z) {
		return Cathegory.values()[z];
	}

	private static List<ResultImpl> readfromFile(String file)
			throws FileNotFoundException {
		FileInputStream inStr = new FileInputStream(file);
		BufferedInputStream buf = new BufferedInputStream(inStr);
		String line;
		Scanner in = new Scanner(buf);

		List<ResultImpl> list = new ArrayList<>();

		try {
			while (!(line = in.nextLine()).equals("")) {
				String[] numbers = line.split(",");

				ResultImpl r = new ResultImpl(
						toCat(Integer.valueOf(numbers[0])),
						toRank(Integer.valueOf(numbers[1])),
						toRank(Integer.valueOf(numbers[2])),
						toRank(Integer.valueOf(numbers[3])),
						toRank(Integer.valueOf(numbers[4])),
						toRank(Integer.valueOf(numbers[5])));
				list.add(r);
			}
		} catch (NoSuchElementException e) {
			// file end
		}
		in.close();
		return list;
	}

	public static void main(String[] args) throws FileNotFoundException {
		@SuppressWarnings("null")
		Map<ResultImpl, Integer> map = readfromFile(
				"D:\\boket\\Kathegorien\\cathegories2.txt")
				.stream()
				.collect(toMap(x -> x, x -> 0));

		for (int i = 0; i < 10000000; i++) {
			Deck deck = Deck.freshDeck();

			List<Card> hand = Tools.asList(deck.pop(), deck.pop());

			List<Card> communityCards = Tools.asList(
					deck.pop(), deck.pop(),
					deck.pop(), deck.pop(), deck.pop());

			ResultImpl res = new Cat_Rec(
					hand,
					communityCards)
					.check();

			if (map.keySet().contains(res)) {
				map.put(res, map.get(res) + 1);
			}
		}

		Comparator<ResultImpl> comp;
		comp = (res1, res2) -> map.get(res1) - map.get(res2);

		List<ResultImpl> list = new ArrayList<>(map.keySet());

		Collections.sort(list, comp);

		String s = list.stream()
				.map(res -> res.toString() + " : " + map.get(res))
				.collect(joining("\n"));
		System.out.println(s);

	}
}
