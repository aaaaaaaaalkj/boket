package strenght_analysis;

import input_output.Raw_Situation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.LoggerFactory;

public class StrenghtAnalysis {
	@SuppressWarnings("null")
	final static org.slf4j.Logger logger = LoggerFactory
			.getLogger(StrenghtAnalysis.class);

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

		int raiser = sit.getButton();
		double post1 = 0;
		boolean first = true;
		for (int i = next(sit.getButton()); i != next(sit.getButton()) || first; i = next(i)) {
			first = false;
			if (sit.getPosts()[i] > post1) {
				post1 = sit.getPosts()[i];
				raiser = i;
			}
		}

		logger.trace("raiser: {}", raiser);

		double naked_pod = sit.getPot();
		for (double d : sit.getPosts()) {
			naked_pod -= d;
		}

		double pot_so_far = sit.getCommunityCards().size() == 0 ? 0.03
				: naked_pod;
		first = true;
		String contributions = "";
		for (int i = next(raiser); i != next(raiser) || first; i = next(i)) {
			first = false;
			double blind_preflop = sit.getCommunityCards().size() == 0 ?
					(i == next(sit.getButton()) ? 0.01
							: i == next(next(sit.getButton())) ? 0.02 : 0.)
					: 0.;

			double post = sit.getPosts()[i] - blind_preflop;

			if (!schalter) {
				pot_so_far += post;
			}

			if (sit.getActiveStatus()[i]) {
				double contribution = pot_so_far == 0 ? 0 : post / pot_so_far;

				if (contribution == 0) {
					contribution = 1. / countPlayers;
				}

				contribution = round(contribution);

				if (i != 0) { // ignore our own contribution
					strength.add(contribution);
					contributions += ", " + contribution;
				}

			}
			if (schalter) {
				pot_so_far += post;
			}

		}
		logger.debug("contributions: {}", contributions);

		for (int i = 1; i < sit.getPosts().length; i++) {
			if (sit.getActiveStatus()[i] && i <= sit.getButton()
					&& sit.getPosts()[i] == 0) {

			}
		}

		// normalize();
	}

	private int next(int i) {
		int res = i + 1;
		if (res >= sit.getPosts().length) {
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
	// logger.info(strength);
	// }

	public List<Double> getStrength() {
		return strength;
	}

}
