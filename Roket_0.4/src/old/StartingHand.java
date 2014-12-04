package old;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public enum StartingHand {
  o22, s32, s42, s52, s62, s72, s82, s92, sT2, sJ2,
  sQ2, sK2, sA2, o32, o33, s43, s53, s63, s73, s83,
  s93, sT3, sJ3, sQ3, sK3, sA3, o42, o43, o44, s54,
  s64, s74, s84, s94, sT4, sJ4, sQ4, sK4, sA4, o52,
  o53, o54, o55, s65, s75, s85, s95, sT5, sJ5, sQ5,
  sK5, sA5, o62, o63, o64, o65, o66, s76, s86, s96,
  sT6, sJ6, sQ6, sK6, sA6, o72, o73, o74, o75, o76,
  o77, s87, s97, sT7, sJ7, sQ7, sK7, sA7, o82, o83,
  o84, o85, o86, o87, o88, s98, sT8, sJ8, sQ8, sK8,
  sA8, o92, o93, o94, o95, o96, o97, o98, o99, sT9,
  sJ9, sQ9, sK9, sA9, oT2, oT3, oT4, oT5, oT6, oT7,
  oT8, oT9, oTT, sJT, sQT, sKT, sAT, oJ2, oJ3, oJ4,
  oJ5, oJ6, oJ7, oJ8, oJ9, oJT, oJJ, sQJ, sKJ, sAJ,
  oQ2, oQ3, oQ4, oQ5, oQ6, oQ7, oQ8, oQ9, oQT, oQJ,
  oQQ, sKQ, sAQ, oK2, oK3, oK4, oK5, oK6, oK7, oK8,
  oK9, oKT, oKJ, oKQ, oKK, sAK, oA2, oA3, oA4, oA5,
  oA6, oA7, oA8, oA9, oAT, oAJ, oAQ, oAK, oAA;

  static final Set<StartingHand> PAIR_OF_JACKS_OR_BETTER = create(oJJ, oQQ, oKK,
      oAA);
  static final Set<StartingHand> PAIR_OF_QUENS_OR_BETTER = create(oQQ, oKK, oAA);
  static final Set<StartingHand> PAIR_OF_NINES_OR_BETTER = create(o99, oTT, oJJ,
      oQQ, oKK, oAA);
  static final Set<StartingHand> PAIR_OF_TENS_OR_BETTER = create(oTT, oJJ, oQQ,
      oKK, oAA);
  static final Set<StartingHand> PAIR_OF_SEVENS_OR_BETTER = create(o77, o88, o99,
      oTT, oJJ, oQQ, oKK, oAA);

  static final Set<StartingHand> MIDDLE_ACES = create(oAQ, oAJ, oAT, sAQ, sAJ,
      sAT);
  static final Set<StartingHand> OFF_SUITE_MIDDLE_ACES = create(oAQ, oAJ, oAT);
  static final Set<StartingHand> SUITED_MIDDLE_ACES = create(sAQ, sAJ, sAT);
  static final Set<StartingHand> LOW_SUITED_ACES = create(sA9, sA8, sA7, sA6,
      sA5, sA4, sA3, sA2);
  static final Set<StartingHand> LOW_OFFSUITE_ACES = create(oA9, oA8, oA7, oA6,
      oA5, oA4, oA3, oA2);
  static final Set<StartingHand> HIGH_PAIRS = create(oAA, oKK, oQQ);
  static final Set<StartingHand> MIDDLE_PAIRS = create(oJJ, oTT);
  static final Set<StartingHand> LOW_PAIRS = create(o99, o88, o77, o66, o55,
      o44, o33, o22);
  static final Set<StartingHand> SUITED_FACES = create(sKQ, sKJ, sKT, sQJ,
      sQT, sJT);
  static final Set<StartingHand> OFFSUITE_FACES = create(oKQ, oKJ, oKT, oQJ,
      oQT, oJT);
  static final Set<StartingHand> SUITED_CONNECTORS = create(sT9, s98, s87,
      s76, s65, s54);

  @SafeVarargs
  private static <X> Set<X> create(final X... x) {
    Set<X> set = new LinkedHashSet<>();
    set.addAll(Arrays.asList(x));
    return set;
  }

  public static StartingHand fromString(final String s) {
    return valueOf(s);
  }

  public static void main(final String[] args) {
    String[] ar = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q",
        "K", "A" };
    String str = "";
    for (int i = 0; i < ar.length; i++) {
      for (int j = 0; j < ar.length; j++) {
        String s = (i > j ? ar[i] + ar[j] : ar[j] + ar[i]);
        str += (i < j ? "s" : "o") + s + ", ";
      }
      str += "\n";
    }
    System.out.println(str);
  }
}
