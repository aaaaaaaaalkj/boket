package strenghtanalysis;

import inputoutput.Raw_Situation;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.jdt.annotation.NonNull;
import org.slf4j.LoggerFactory;

import tools.Tools;

public class StrenghtAnalysis {
  @SuppressWarnings("null")
  static final org.slf4j.Logger LOG = LoggerFactory
      .getLogger(StrenghtAnalysis.class);

  private Raw_Situation sit;

  /*
   * schalter==true => Andreas-Formel scahlter==false => Alex-Formel
   */
  private boolean schalter = false;

  private List<Double> strength;
  private List<Double> stdDev;

  @SuppressWarnings("null")
  public static <T> Collector<@NonNull T, @NonNull ?, @NonNull Set<@NonNull T>> toSet() {
    @NonNull
    Collector<@NonNull T, ?, @NonNull Set<@NonNull T>> res = Collectors
        .toSet();

    return res;
  }

  public StrenghtAnalysis(final Raw_Situation sit) {

    this.sit = sit;
    this.strength = new ArrayList<>();
    this.stdDev = new ArrayList<>();
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

    LOG.trace("raiser: {}", raiser);

    double nakedPod = sit.getPot();
    for (double d : sit.getPosts()) {
      nakedPod -= d;
    }

    double potSoFar = sit.getCommunityCards().size() == 0 ? 0.03
        : nakedPod;
    first = true;
    for (int i = next(raiser); i != next(raiser) || first; i = next(i)) {
      first = false;
      double blindPreflop = sit.getCommunityCards().size() == 0
          ? (i == next(sit.getButton()) ? 0.01
              : i == next(next(sit.getButton())) ? 0.02 : 0.)
          : 0.;

      double post = sit.getPosts()[i] - blindPreflop;

      if (!schalter) {
        potSoFar += post;
      }

      if (sit.getActiveStatus()[i]) {
        double contribution = potSoFar == 0 ? 0 : post / potSoFar;

        // if (contribution == 0) {
        // contribution = 1. / countPlayers;
        // }

        contribution = Tools.round(contribution);

        if (i != 0) { // ignore our own contribution
          double x = 1;

          strength.add(Tools.round(contribution * x));

          stdDev.add(Tools.round(x / (1. + 100. * post)));
        }

      }
      if (schalter) {
        potSoFar += post;
      }

    }

    for (int i = 1; i < sit.getPosts().length; i++) {
      if (sit.getActiveStatus()[i] && i <= sit.getButton()
          && sit.getPosts()[i] == 0) {
        System.out.println("todo");
      }
    }

    // normalize();
  }

  private int next(final int i) {
    int res = i + 1;
    if (res >= sit.getPosts().length) {
      res = 0;
    }
    return res;
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

  public final List<Double> getStrength() {
    return strength;
  }

  public final List<Double> getStdDev() {
    return stdDev;
  }

}
