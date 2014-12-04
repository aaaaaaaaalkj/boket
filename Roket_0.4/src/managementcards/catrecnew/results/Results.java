package managementcards.catrecnew.results;

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

import managementcards.cards.Card;
import managementcards.cards.Deck;
import managementcards.cards.Rank;
import managementcards.catrecnew.CatRec;
import managementcards.catrecnew.Cathegory;
import managementcards.catrecnew.ResultImpl;
import tools.Tools;

public final class Results {

  private Results() {
  }

  @SuppressWarnings("null")
  public static Rank toRank(final int z) {
    return Rank.values()[z - 2];
  }

  @SuppressWarnings("null")
  public static Cathegory toCat(final int z) {
    return Cathegory.values()[z];
  }

  private static List<ResultImpl> readfromFile(final String file)
      throws FileNotFoundException {
    FileInputStream inStr = new FileInputStream(file);
    BufferedInputStream buf = new BufferedInputStream(inStr);
    String line;
    Scanner in = new Scanner(buf);

    List<ResultImpl> list = new ArrayList<>();

    try {
      for (line = in.nextLine(); !line.equals(""); line = in.nextLine()) {
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
      e.printStackTrace();
      // file end
    }
    in.close();
    return list;
  }

  public static void main(final String[] args) throws FileNotFoundException {
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

      ResultImpl res = new CatRec(
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
