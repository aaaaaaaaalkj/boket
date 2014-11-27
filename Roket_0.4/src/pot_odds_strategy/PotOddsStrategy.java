package pot_odds_strategy;

import static java.util.Comparator.naturalOrder;
import input_output.Raw_Situation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import old.Hand;

import org.slf4j.LoggerFactory;

import strenght_analysis.StrenghtAnalysis;
import tools.Tools;
import card_simulation.CardSimulation;

public class PotOddsStrategy {
	@SuppressWarnings("null")
	final static org.slf4j.Logger logger = LoggerFactory
			.getLogger(PotOddsStrategy.class);

	private double valueOfSituation;
	private double toPay;
	private double[] posts;
	private double odds;
	private double pot;
	private int round;
	double prod_der_gegenwahrscheinlichkeiten_meiner_aktiven_gegner = 1;
	double gegenwahrscheinlichkeit_von_zufaelligen_gegnern_mit_zufaelligen_haenden;
	private double naked_pod;

	// private static Memory memory = new Memory();

	@Override
	public String toString() {
		return "[value Of Situation = " + valueOfSituation
				+ ", toPay=" + toPay
				+ ", pot=" + pot
				+ ", odds=" + odds
				// "\nprod der gegen-wahrscheinlichkeit: "
				// + prod_der_gegenwahrscheinlichkeiten_meiner_aktiven_gegner
				// +
				// "\ngegenwahrscheinlichkeit_von_zufälligen_gegnern: "
				// +
				// gegenwahrscheinlichkeit_von_zufaelligen_gegnern_mit_zufaelligen_haenden
				+ "]";
	}

	public PotOddsStrategy(Raw_Situation s) {
		List<Double> activeContributors;
		this.posts = s.getPosts().clone();

		double maxPost = Arrays.stream(s.getPosts()).max().getAsDouble();
		toPay = Tools.round(maxPost - s.getPosts()[0]);

		round = s.getCommunityCards().size();
		if (round > 2) {
			round -= 2;
		}

		naked_pod = s.getPot();
		for (double d : posts) {
			naked_pod -= d;
		}
		naked_pod = Tools.round(naked_pod);
		double pot2 = naked_pod;
		double[] new_posts = new double[posts.length];
		for (int i = 0; i < posts.length; i++) {
			if (s.getActiveStatus()[i]) {
				new_posts[i] = toPay + posts[0];
				pot2 += new_posts[i];
			}
		}
		pot = Tools.round(pot2);

		int count = 0;
		for (int i = 0; i < posts.length; i++) {
			if (s.getActiveStatus()[i]) {
				count++;
			}
		}

		StrenghtAnalysis anal = new StrenghtAnalysis(s);
		activeContributors = anal.getStrength();
		List<Double> stdDev = anal.getStdDev();

		Hand hand = s.getHand();
		if (hand == null) {
			return;
		}

		logger.debug("contributions: {}", activeContributors);
		logger.debug("stdDev: {}", stdDev);

		odds = new CardSimulation(count, activeContributors, stdDev,
				hand,
				s.getCommunityCards())
				.run();

		// Andreas möchte hier eine 2 statt 1 haben
		double value = Math.min(Integer.MAX_VALUE, pot * odds / (1 - odds));

		valueOfSituation = Tools.round(value);
	}

	private double computeMinRaise() {
		List<Double> list = new ArrayList<>();
		for (double d : posts) {
			list.add(d);
		}

		double max = list.stream().max(naturalOrder()).orElse(0.);

		while (list.contains(max)) {
			list.remove(max);
		}
		double max2 = list.stream().max(naturalOrder()).orElse(0.);

		double myResult = max - max2;

		if (myResult < .02) {
			return 0.02; // at least a bigblind
		}

		return max - max2;
	}

	public PotOddsDecision decide() {
		if (valueOfSituation < toPay) {
			return PotOddsDecision.fold();
		} else if (valueOfSituation < toPay + computeMinRaise()) {
			return PotOddsDecision.call(toPay);
		} else {

			double res = Tools.round(valueOfSituation - toPay);

			res = Math.min(100000, res);

			return PotOddsDecision.raise(res);
		}
	}
}
