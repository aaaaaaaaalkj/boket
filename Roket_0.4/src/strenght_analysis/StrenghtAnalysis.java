package strenght_analysis;

import input_output.Raw_Situation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;

public class StrenghtAnalysis {
	Raw_Situation sit;

	/*
	 * schalter==true => Andreas-Formel scahlter==false => Alex-Formel
	 */
	boolean schalter = !true;

	int countPlayers;

	private List<Double> strength;

	@SuppressWarnings("null")
	public static <T> Collector<@NonNull T, @NonNull ?, @NonNull Set<@NonNull T>> toSet() {
		@NonNull
		Collector<@NonNull T, ?, @NonNull Set<@NonNull T>> res = Collectors
				.toSet();

		return res;
	}

	public StrenghtAnalysis(Raw_Situation sit) {

		this.sit = sit;
		this.countPlayers = sit.getNumActive();
		this.strength = new ArrayList<>();
		// for (int i = 0; i < countPlayers; i++) {
		// strength.add(1. / countPlayers);
		// }

		int raiser = sit.button;
		double post1 = 0;
		boolean first = true;
		for (int i = next(sit.button); i != next(sit.button) || first; i = next(i)) {
			first = false;
			if (sit.posts[i] > post1) {
				post1 = sit.posts[i];
				raiser = i;
			}
		}

		System.out.println("raiser: " + raiser);

		double naked_pod = sit.getPot();
		for (double d : sit.posts) {
			naked_pod -= d;
		}

		double pot_so_far = sit.communityCards.size() == 0 ? 0.03 : naked_pod;
		first = true;
		for (int i = next(raiser); i != next(raiser) || first; i = next(i)) {
			first = false;
			double blind_preflop = sit.communityCards.size() == 0 ?
					(i == next(sit.button) ? 0.01
							: i == next(next(sit.button)) ? 0.02 : 0.) : 0.;

			double post = sit.posts[i] - blind_preflop;

			if (!schalter) {
				pot_so_far += post;
			}

			if (sit.activeStatus[i]) {
				double contribution = pot_so_far == 0 ? 0 : post / pot_so_far;

				if (contribution == 0) {
					contribution = 1. / countPlayers;
				}

				contribution = round(contribution);

				if (i != 0) { // ignore our own contribution
					strength.add(contribution);
					System.out.println(contribution);
				}

			}
			if (schalter) {
				pot_so_far += post;
			}

		}

		for (int i = 1; i < sit.posts.length; i++) {
			if (sit.activeStatus[i] && i <= sit.button && sit.posts[i] == 0) {

			}
		}

		// normalize();
	}

	private int next(int i) {
		int res = i + 1;
		if (res >= sit.posts.length) {
			res = 0;
		}
		return res;
	}

	private double round(double d) {
		return ((double) Math.round(d * 100)) / 100;
	}

	// private void normalize() {
	// double sum = 0;
	// for (int i = 0; i < strength.size(); i++) {
	// sum += strength.get(i);
	// }
	//
	// for (int i = 0; i < strength.size(); i++) {
	// strength.set(i, strength.get(i) / sum);
	// strength.set(i, round(strength.get(i)));
	//
	// }
	//
	// System.out.println(strength);
	// }

	public List<Double> getStrength() {
		return strength;
	}

}
