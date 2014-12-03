package managementcards.all_cathegories;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import managementcards.cards.Rank;
import managementcards.catrecnew.Cathegory;
import managementcards.catrecnew.ResultImpl;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

public final class AllResults { // Singleton
	private final Map<ResultImpl, @NonNull Short> scores;
	private final Map<Short, @NonNull ResultImpl> scoresInverseMap;
	private final String file_path;

  private AllResults(final String file) throws FileNotFoundException {
		scores = new HashMap<>();
		scoresInverseMap = new HashMap<>();
		file_path = file;
		load();
	}

	private static @Nullable AllResults my_instance;

  public static AllResults getInstance(final String file)
			throws FileNotFoundException {
		AllResults res = my_instance;
		if (null == res) {
			my_instance = new AllResults(file);
			res = my_instance;
		}
		return res;
	}

  public short getScore(final ResultImpl res) {
		if (scores.containsKey(res)) {
			return scores.get(res);
		} else {
			throw new IllegalArgumentException("No Score is defined for res: "
					+ res);
		}
	}

	private void load() throws FileNotFoundException {
		Scanner scanner = new Scanner(new BufferedReader(new FileReader(
				file_path)));

		String s;

		List<ResultImpl> results = new ArrayList<>();

		while (scanner.hasNext()) {
			s = scanner.next();

			String[] numbers = s.split(",");

			Cathegory cat = Cathegory.getCathegory(Integer.valueOf(numbers[0]));
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

		for (int index = 0; index < results.size(); index++) {
			ResultImpl res = results.get(index);
			short score = (short) (index + 1); // score is 1-based
			scores.put(res, score);
			scoresInverseMap.put(score, res);
		}

		scanner.close();
	}

  public ResultImpl getResult(final short score) {
		return scoresInverseMap.get(score);
	}
}
