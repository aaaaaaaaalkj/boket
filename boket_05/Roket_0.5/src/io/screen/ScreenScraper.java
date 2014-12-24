package io.screen;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import management.cards.cards.Card;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;

import tools.Pos;
import tools.Tools;

public final class ScreenScraper {
  private static final int NUM_SEATS = 9;
  private static final boolean REAL_MONEY = true;
  private RawSituation situation2;
  private final RawSituation.Builder situation;
  private final MyRobot robot;
  @Nullable
  private Pos logo;

  public ScreenScraper() throws AWTException {
    robot = new MyRobot();
    situation = new RawSituation.Builder();

    Pos logoTmp = recognizeLogo();
    if (logoTmp != null) {
      this.logo = logoTmp;

      recognizeBrownButtons(logoTmp);
      searchButton(logoTmp);
      tableCheckSum(logoTmp);
      activePlayers(logoTmp);
      postRecognition(logoTmp);
      stackRecognition(logoTmp);

      List<Card> hand = CardRecognition.recognizeHand(logoTmp, robot);
      situation.hand(hand);
      situation.communityCards(CardRecognition
          .recognizeCommunityCards(logoTmp, robot));

      if (!hand.isEmpty()) {
        situation.activeStatus(0, true);
      }
    }
    situation2 = situation.build();
  }

  public BufferedImage getScreenshot() {
    return robot.getScreenshot();
  }

  private void stackRecognition(final Pos logo1) {
    Pos[] positions = { pos(20, 306), pos(10, 122), pos(155, 64),
        pos(565, 65), pos(700, 121), pos(700, 306), pos(480, 370),
        pos(350, 446), pos(225, 370) };
    situation.stacks(NUM_SEATS);
    for (int i = 0; i < NUM_SEATS; i++) {

      List<Pos> indicators = new ArrayList<>();
      indicators.add(pos(0, 2));
      indicators.add(pos(1, 3));
      indicators.add(pos(0, 5));
      indicators.add(pos(0, 8));
      indicators.add(pos(1, 9));
      indicators.add(pos(2, 5));
      indicators.add(pos(0, 6));
      indicators.add(pos(0, 9));

      Color ref = new Color(192, 192, 192); // grey

      @SuppressWarnings("null")
      @NonNull
      Pos p = positions[i];
      Optional<Double> opt = ocrRealMoney(logo1.plus(p), ref,
          4, 7, 4, indicators);

      if (opt.isPresent()) {
        situation.stacks(i, opt.get());
      } else {
        // Pos p = new Pos(40, 314).plus(logo);
        ref = new Color(32, 32, 32); // black
        opt = ocrRealMoney(logo1.plus(p), ref,
            4, 7, 4, indicators);
        situation.stacks(i, opt.orElse(0.));
      }
    }
  }

  private static Pos pos(final int x, int y) {
    return new Pos(x, y);
  }

  @SuppressWarnings("null")
  private void activePlayers(final Pos logo1) {

    // 10-player-table
    Pos[] positions = { pos(105, 207), pos(176, 132), pos(238, 108),
        pos(540, 108), pos(603, 133), pos(675, 208), pos(589, 289),
        pos(427, 351), pos(355, 351), pos(187, 284) };

    // 9-player-table
    positions = new Pos[] { pos(95, 215), pos(169, 124), pos(231, 98),
        pos(528, 98), pos(590, 126), pos(664, 216), pos(580, 289),
        pos(412, 324), pos(165, 288) };

    situation.activeStatus(new boolean[NUM_SEATS]);

    for (int i = 0; i < NUM_SEATS; i++) {
      int dif = robot.maxColor(positions[i].plus(logo1), pos(25, 30));
      if (dif == 146)
        situation.activeStatus(i, true);
    }
  }

  private void tableCheckSum(Pos logo1) {
    situation.checkSum(robot.pixelCheckSum(logo1.plus(new Pos(15, -5)),
        logo1.plus(new Pos(50, 5))));
    // lobby = 1439337809
  }

  @Nullable
  private Pos recognizeLogo() {
    // Farbe im Logo von Pokerstars
    Color c = new Color(0x00FFCAC5);
    Pos logo1 = robot.pixelSearch(0, 0, 100, 250, c);

    return logo1;
    // TODO: checken sind die zwei Pixel für win7 ?
    // logo = logo.plus(0, 2);
  }

  void searchButton(final Pos logo1) {
    List<Pos> centersOfPlayerCirles = Constants.CENTERS_OF_PLAYER_CIRLES;
    Color c = new Color(12010269);
    Pos button = robot.pixelSearch(logo1.getX(), logo1.getY(), 850, 550, c);
    if (null == button) {
      System.out.println("cant find button");
      return;
    }
    int minIndex = -1;
    double minDist = Double.MAX_VALUE;

    for (int i = 0; i < centersOfPlayerCirles.size(); i++) {
      Pos p = centersOfPlayerCirles.get(i);
      double d = p.dist(button);
      if (d < minDist) {
        minDist = d;
        minIndex = i;
      }
    }
    situation.button(minIndex);
  }

  @SuppressWarnings("null")
  private void postRecognition(final Pos logo1) {
    // für OCR werden Rechtecke gebraucht, wo nach Ziffern gesucht wird.
    // for 10-seats-table:
    // Pos[] positions = { pos(116, 197), pos(141, 156), pos(218, 114),
    // pos(390, 117), pos(486, 157), pos(491, 194), pos(480, 260),
    // pos(413, 307), pos(247, 309), pos(159, 261) };
    Pos[] positions = { pos(0, 0), pos(169, 176), pos(218, 114),
        pos(390, 117), pos(486, 157), pos(491, 194), pos(480, 260),
        pos(413, 307), pos(247, 309), pos(159, 261) };

    positions = new Pos[] { pos(139 - 14, 248 - 14),
        pos(169 - 14, 176 - 14), pos(259 - 14, 147 - 14),
        pos(400, 124), pos(500, 175), pos(538 - 14, 241 - 14),
        pos(455, 290), pos(320, 308),
        pos(205, 291) };
    double pot = 0;
    situation.posts(new double[NUM_SEATS]);
    for (int i = 0; i < NUM_SEATS; i++) {
      @NonNull
      Pos p = positions[i];
      double value = ocr(logo1, p).orElse(0.);
      situation.posts(i, value);
      pot += value;
    }
    pot += ocr(logo1, (pos(308 - 14, 260 - 14))).orElse(0.);
    situation.pot(Tools.round(pot));
  }

  public static boolean isNumber(final String s) {
    try {
      Double.parseDouble(s);
    } catch (NumberFormatException nfe) {
      return false;
    }
    return true;
  }

  private Optional<Double> ocr(final Pos logo1, final Pos pos) {

    if (REAL_MONEY) {
      List<Pos> indicators = new ArrayList<>();
      indicators.add(pos(0, 2)); // dollar-offset
      indicators.add(pos(0, 3));
      indicators.add(pos(0, 4));
      indicators.add(pos(0, 5));
      indicators.add(pos(0, 7));
      indicators.add(pos(2, 4));
      Color ref = new Color(16774863);
      return ocrRealMoney(logo1.plus(pos), ref, 4, 6, 3, indicators);
    }
    return Tools.of(ocr_play_money(logo1, pos));

  }

  public Optional<Double> ocrRealMoney(final Pos pos, final Color ref,
      final int startOffset,
      final int digitOffset, final int dotOffset,
      final List<Pos> indicators) {

    Pos dollar = robot.pixelSearch(pos, pos.plus(160, 60),
        ref);

    String letter;
    // robot.mouseMove(pos);
    if (dollar != null) {
      Color colorLeft = robot
          .getPixelColor(pos.plus(dollar).plus(-1, 0));
      Color colorRight = robot
          .getPixelColor(pos.plus(dollar).plus(1, 0));
      Color colorLeft2 = robot.getPixelColor(pos.plus(dollar)
          .plus(-1, 1));
      Color colorRight2 = robot.getPixelColor(pos.plus(dollar)
          .plus(1, 1));

      if (!ref.equals(colorLeft)
          && !ref.equals(colorRight)
          && ref.equals(colorLeft2)
          && ref.equals(colorRight2)) {

        String text = "";
        int position = startOffset; // 4

        do {
          // System.out.println("-");
          // robot.mouseMove(pos.plus(dollar.plus(position, 0)));

          letter = recognizeLetter(
              pos.plus(dollar.plus(position, 0))
              , ref,
              indicators);

          // System.out.println(letter);

          if (letter.equals(".")) {
            position += dotOffset; // 3
          } else {
            position += digitOffset; // 6
          }
          text += letter;
        } while (isNumber(letter) || ".".equals(letter));

        if (text.equals("")) {
          return Tools.empty();
        }
        try {
          return Tools.of(Tools.parseDouble2(text));

        } catch (NumberFormatException e) {
          return Tools.empty();
        }
      }
    }
    return Tools.empty();
  }

  @Nullable
  private Integer recognizeFirstLetter(final Pos logo1, final Pos pos,
      final Pos first, final Color ref) {
    Pos x;
    Pos start = logo1.plus(pos).plus(first);

    for (int i = 0; i > -4; i--) {
      start = start.minus(1, 0);
      x = robot.pixelSearch(start, start.plus(1, 10), ref);
      if (x == null) {
        return i;
      }
    }
    return null;
  }

  private Double ocr_play_money(final Pos logo1, final Pos pos) {
    // r=255,g=246,b=207
    Color ref = new Color(16774863);

    Pos first = robot.pixelSearch(logo1.plus(pos),
        logo1.plus(pos).plus(160, 60),
        ref);
    String letter;

    if (first != null) {
      Integer x = recognizeFirstLetter(logo1, pos, first, ref);
      if (x == null) {
        System.out.println("can not recognize first letter");
        return 0.;
      }
      Pos start_delta = pos(x, -1);

      String text = "";

      List<Pos> indicators = new ArrayList<>();
      indicators.add(pos(0, 2)); // dollar-offset
      indicators.add(pos(0, 3));
      indicators.add(pos(0, 4));
      indicators.add(pos(0, 5));
      indicators.add(pos(0, 7));
      indicators.add(pos(2, 4));

      do {
        letter = recognizeLetter(
            logo1.plus(pos).plus(first.plus(start_delta)), ref,
            indicators);

        if (letter.equals(".")) {
          // do not add "." to text
          // its not a ".", its a ","
          // its play money -> big numbers
          start_delta = start_delta.plus(3, 0);
        } else {
          text += letter;
          start_delta = start_delta.plus(6, 0);
        }

      } while (isNumber(letter) || ".".equals(letter));
      try {
        @SuppressWarnings("null")
        @NonNull
        Double d = Double.parseDouble(text);
        return d;
      } catch (NumberFormatException e) {
        return 0.;
      }
    }
    return 0.;
  }

  public String recognizeLetter(final Pos start, final Color refColor,
      final List<Pos> indicators) {
    Color color = robot.getPixelColor(start.plus(indicators.get(0)));

    if (refColor.equals(color)) {
      // 0,2,3,6,8,9
      color = robot.getPixelColor(start.plus(indicators.get(1)));
      if (refColor.equals(color)) {
        // 0,6,8,9
        color = robot.getPixelColor(start.plus(indicators.get(5)));
        if (refColor.equals(color)) {
          // 6,8
          color = robot.getPixelColor(start.plus(indicators.get(2)));
          if (refColor.equals(color)) {
            return "6";
          }
          return "8";
        }
        // 0,9
        color = robot.getPixelColor(start.plus(indicators.get(3)));
        if (refColor.equals(color)) {
          return "0";
        }
        return "9";
      }
      // 2,3
      color = robot.getPixelColor(start.plus(indicators.get(5)));
      if (refColor.equals(color)) {
        return "3";
      }
      return "2";
    }
    // 1,4,5,7,., (6)
    if (indicators.size() > 6) {
      color = robot.getPixelColor(start.plus(indicators.get(6)));
      if (refColor.equals(color)) {
        return "6";
      }
    }
    color = robot.getPixelColor(start.plus(indicators.get(1)));
    if (refColor.equals(color)) {
      // 1,5
      color = robot.getPixelColor(start.plus(indicators.get(3)));
      if (refColor.equals(color)) {
        return "5";
      }
      if (indicators.size() > 7) { // exception
        color = robot.getPixelColor(start.plus(indicators
            .get(7)));
        if (refColor.equals(color)) {
          return "5";
        }
      }
      return "1";
    }
    // 4,7,.
    color = robot.getPixelColor(start.plus(indicators.get(3)));
    if (refColor.equals(color)) {
      return "4";
    }
    color = robot.getPixelColor(start.plus(indicators.get(5)));
    if (refColor.equals(color)) {
      return "7";
    }
    // .," "
    color = robot.getPixelColor(start.plus(indicators
        .get(4)));
    if (refColor.equals(color)) {
      return ".";
    }
    return "";
  }

  private void recognizeBrownButtons(final Pos logo1) {

    boolean first = checkBrownButton(logo1, new Pos(417, 525));
    boolean second = checkBrownButton(logo1, new Pos(550, 525));
    boolean third = checkBrownButton(logo1, new Pos(683, 525));

    // first button
    situation.brownButtons(0, first);
    // middle button
    situation.brownButtons(1, second);
    // last button
    situation.brownButtons(2, third);

    int sum = 0;
    sum += first ? 1 : 0;
    sum += second ? 1 : 0;
    sum += third ? 1 : 0;

    if (sum > 1) { // at least two buttons shown
      situation.itsMyTurn(true);
      situation.activeStatus(0, true);
    }
  }

  private boolean checkBrownButton(final Pos logo1, final Pos pos) {
    Color brown = robot.getPixelColor(pos.plus(logo1));

    int difference = Math.abs(brown.getRed() - 148)
        + Math.abs(brown.getGreen() - 66)
        + Math.abs(brown.getBlue() - 15);

    return difference < 50;
  }

  public RawSituation getSituation() {
    return situation2;
  }

  @Nullable
  public Pos getLogo() {
    return logo;
  }

}
