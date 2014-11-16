package managementCards.all_cathegories;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import managementCards.cards.Rank;
import managementCards.cat_rec_new.Cathegory;
import managementCards.cat_rec_new.ResultImpl;

public class AllResults { // Singleton
	private final Map<ResultImpl, Integer> scores;
	private final String file_path;

	private AllResults(String file) throws FileNotFoundException {
		scores = new HashMap<>();
		file_path = file;
		load();
	}

	private static AllResults my_instance;

	public static AllResults getInstance(String file)
			throws FileNotFoundException {
		if (null == my_instance) {
			my_instance = new AllResults(file);
		}
		return my_instance;
	}

	public int getScore(ResultImpl res) {
		Integer i = scores.get(res);
		if (i == null) {
			throw new IllegalArgumentException("No Score is defined for res: "
					+ res);
		}
		return i;
	}

	private void load() throws FileNotFoundException {
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(
				file_path)));

		String s;

		List<ResultImpl> results = new ArrayList<>();

		while (scanner.hasNext()) {
			s = scanner.next();

			String[] numbers = s.split(",");

			Cathegory cat = Cathegory.VALUES[Integer.valueOf(numbers[0])];
			List<Rank> ranks = new ArrayList<>();

			for (int i = 1; i <= 5; i++) {
				ranks.add(
						Rank.VALUES.get(
								Integer.valueOf(numbers[i]) - 2
								)
						);
			}

			ResultImpl res = new ResultImpl(cat, ranks);

			results.add(res);
		}

		Collections.sort(results);

		for (int score = 0; score < results.size(); score++) {
			scores.put(results.get(score), score + 1);
		}

		scanner.close();
	}

}
