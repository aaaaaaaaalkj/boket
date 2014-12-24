package io.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import management.cards.cards.Rank;

import org.eclipse.jdt.annotation.NonNull;

import ranges.atry.CountPlayers;
import ranges.preflop.GroupedRange;
import tools.Tools;

public class PreflopProbabilitiesReader {
  private PreflopProbabilitiesReader() {
    // utility-class. do not instantiate
  }

  public static PreflopScoreDefinition readFromFile(String file_name) {
    File source = new File(file_name);
    PreflopScoreDef2.Builder builder = new PreflopScoreDef2.Builder();

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

        List<Double> probabilities = Arrays.asList(s)
            .subList(1, s.length)
            .stream()
            .map(str -> Double.parseDouble(str))
            .collect(Tools.toList());

        builder.add(GroupedRange.find(first, second, suited), probabilities);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    return normalize(builder.build());
  }

  /**
   * Normalizes Probabilites to a range from 0 to 1 according to the formula: score =
   * (probability-min)/(max-min). There min is the minmum probability for some range-elem and max is
   * the maximum probability for some range-elem.
   * 
   * @param x
   *          - non-normalized hand-supplier
   * @return
   */
  @SuppressWarnings("null")
  private static PreflopScoreDefinition normalize(PreflopScoreDefinition x) {
    List<Double> min = new ArrayList<>();
    List<Double> max = new ArrayList<>();

    // initialize lists
    for (@SuppressWarnings("unused")
    CountPlayers seat : CountPlayers.VALUES) {
      min.add(1.);
      max.add(0.);
    }

    for (CountPlayers seat : CountPlayers.VALUES) {
      for (GroupedRange range : GroupedRange.VALUES) {
        Double d = x.getScore(range, seat);
        if (d != null) {
          int index = seat.getIndex();
          min.set(index, Math.min(min.get(index), d));
          max.set(index, Math.max(max.get(index), d));
        }
      }
    }
    PreflopScoreDef2.Builder builder = new PreflopScoreDef2.Builder();

    for (GroupedRange range : GroupedRange.VALUES) {
      List<Double> scores = new ArrayList<>();
      for (CountPlayers seat : CountPlayers.VALUES) {
        Double d = x.getScore(range, seat);
        if (d != null) {
          int index = seat.getIndex();
          double d2 = (d - min.get(index)) / (max.get(index) - min.get(index));
          scores.add(d2);
        }
      }
      // maybe undefined for some ranges
      if (scores.size() > 0) {
        builder.add(range, scores);
      }
    }
    return builder.build();
  }
}
