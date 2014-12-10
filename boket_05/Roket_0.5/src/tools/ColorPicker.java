package tools;

import inputoutput.MyRobot;
import inputoutput.Raw_Situation;
import inputoutput.ScreenScraper;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.List;

import management.cards.AllResults;
import management.cards.cards.Card;
import management.cards.cards.Rank;
import management.cards.cards.Suit;
import management.cards.catrecnew.CatRec;
import management.cards.catrecnew.ResultImpl;
import management.cards.evaluator.HandEvaluator;

import org.eclipse.jdt.annotation.Nullable;
import org.slf4j.LoggerFactory;

import potoddsstrategy.PotOddsDecision;
import potoddsstrategy.PotOddsStrategy;
import ranges.ElementRange;

public class ColorPicker {
  @SuppressWarnings("null")
  static final org.slf4j.Logger LOG = LoggerFactory
      .getLogger(ColorPicker.class);

  public ColorPicker() {
    // TODO Auto-generated constructor stub
  }

  public final void test2() throws AWTException {
    ScreenScraper scraper = new ScreenScraper();
    Raw_Situation raw = scraper.getSituation();
    raw.print();
    PotOddsStrategy strategy = new PotOddsStrategy(raw);
    LOG.info("{}", strategy);
    PotOddsDecision d = strategy.decide();
    // ISituation sit = new BoketSituation(raw);
    // logger.info(sit);

    LOG.info("{}", d);

  }

  // static List<Card> all = Tools.asList(Card._3s, Card._7c, Card.Ac,
  // Card.Ad,
  // Card.Ah, Card.Kc, Card.Qc);
  // static List<Rank> window = Tools.asList(Five, Four, Three, Two, Ace);
  @SuppressWarnings("unused")
  private static List<Rank> ranks = Tools.asList(Rank.Five, Rank.King,
      Rank.Three,
      Rank.Seven, Rank.Four,
      Rank.Two, Rank.Two);

  static class Test1 {
    Rank rank;

    Test1(Rank rank) {
      this.rank = rank;
    }

    public void test() {

    }
  }

  static class Test2 {
    Rank rank;
    int cached_hashCode;

    Test2(Rank rank) {
      this.rank = rank;
      this.cached_hashCode = rank.hashCode();
    }

    @Override
    public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + cached_hashCode;
      return result;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
      if (this == obj)
        return true;
      if (obj == null)
        return false;
      if (getClass() != obj.getClass())
        return false;
      Test2 other = (Test2) obj;
      if (rank != other.rank)
        return false;
      return true;
    }

  }

  static Test1 test1 = new Test1(Rank.Ace);
  static Test2 test2 = new Test2(Rank.Ace);

  public static void test1() {
    ElementRange.find(Card.C2, Card.C2);
  }

  private static void test1b() {
    ElementRange.findAssociated(Card.C2);
  }

  public static void test1c() {
  }

  public static void comparePerformance() {
    long lAll = System.nanoTime();
    long sum1 = 0;
    long sum2 = 0;
    long sum3 = 0;
    long l;

    int num = 500000000;
    l = System.nanoTime();
    for (int i = 0; i < num; i++) {
      test1();
    }
    l = System.nanoTime() - l;
    sum1 += l;

    l = System.nanoTime();
    for (int i = 0; i < num; i++) {
      test1b();
    }
    l = System.nanoTime() - l;
    sum2 += l;

    l = System.nanoTime();
    for (int i = 0; i < num; i++) {
      test1c();
    }
    l = System.nanoTime() - l;
    sum3 += l;

    sum1 /= 1000000;
    sum2 /= 1000000;
    sum3 /= 1000000;

    lAll = System.nanoTime() - lAll;
    lAll /= 1000000;
    LOG.info("all: {}", lAll);

    LOG.info("test1: {}, test2: {}, test3: {}", sum1, sum2, sum3);

  }

  @SuppressWarnings("unused")
  private void generateCodeForCards() {
    for (Suit s : Suit.VALUES) {
      for (Rank r : Rank.VALUES) {
        String underScore = r.ordinal() < 8 ? "_" : "";
        String str = "public static final Card " + underScore
            + r.shortString()
            + s.shortString()
            + " = new Card(Rank." + r + ", Suit." + s + ");";

        LOG.info(str);
      }
    }
  }

  public static void main(final String[] args) throws Exception {
    LOG.info("Entering application.");
    // new ColorPicker().test2();
    comparePerformance();

    System.exit(1);

    // for (int i = 0; i < 1000000; i++) {
    // ResultImpl res = new Cat_Rec(
    // Card._3s, Card.Ad, Tools.asList(Card.Kc, Card.Qc, Card.Qd,
    // Card.Kd, Card.Ah))
    // .check();

    // }
    AllResults allRes = AllResults.getInstance("cathegories.txt");

    // short[] map = createAll7Cards(allRes);

    List<Card> cards = Tools.asList(
        Card.D2,
        Card.H4,
        Card.DQ,
        Card.D9,
        Card.H2,
        Card.H9,
        Card.SJ
        );

    HandEvaluator ev = HandEvaluator.getInstance();

    short score = ev.getScore(cards);
    // System.out.println(cards.stream().map(Card::ordinal)
    // .collect(Tools.toList()));

    ResultImpl res = new CatRec(
        cards.get(0), cards.get(1), Tools.asList(
            cards.get(2),
            cards.get(3),
            cards.get(4),
            cards.get(5),
            cards.get(6)
            ))
        .check();
    short score2 = allRes.getScore(res);

    System.out.println("score1: " + score + " , score2: " + score2);

    // Tools.serialize(map, fileName);

  }

  public final void findColorAtMousePosition() {
    try {
      Robot r = new Robot();
      Point p = MouseInfo.getPointerInfo().getLocation();
      // logo = recognizeLogo();
      // Pos p2 = new Pos(p.x, p.y);
      //
      // logger.info(p2.minus(logo));

      Color c = r.getPixelColor(p.x, p.y);

      LOG.debug("{}", c);
    } catch (AWTException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  @Nullable
  private Pos recognizeLogo(final MyRobot robot) {
    // Farbe im Logo von Pokerstars
    Color c = new Color(0x00FFCAC5);
    Pos logo = robot.pixelSearch(0, 0, 100, 250, c);
    return logo;
  }

  public void test() {
    // Color cardColor = getPixelColor(new Pos().plus(9, 25));
  }

  public final void start(final MyRobot robot) {
    Pos logo = recognizeLogo(robot);
    // Color refColor = new Color(12010269);
    // logger.info(refColor);

    if (logo == null) {
      LOG.warn("logo is null");
    } else {

      Pos p = new Pos(40, 314).plus(logo);
      Color c = robot.getPixelColor(p);
      LOG.debug("[}", c);

      robot.mouseMove(p);
    }
    // 38,231
  }

  public static Pos pos(final int x, final int y) {
    return new Pos(x, y);
  }
}
