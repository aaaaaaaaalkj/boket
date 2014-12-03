package input_output;

import java.awt.AWTException;
import java.awt.Color;

import managementcards.cards.Card;

import org.eclipse.jdt.annotation.Nullable;

import tools.Pos;

public final class Input_Test {

  private Input_Test() {
  }

  public static void test3() throws AWTException {
    Color ref = new Color(16762401);
    MyRobot robot = new MyRobot();
    Pos logo = recognizeLogo(robot);

    if (logo != null) {

      Pos p = new Pos(20 - 14, 20 - 14).plus(logo);
      Pos p2 = new Pos(550 - 14, 470 - 14).plus(logo);

      Pos res = robot.pixelSearch(p, p2, ref);
      System.out.println(res);
    }
  }

  public static void main(final String[] args) throws AWTException {

    // Color ref = new Color((248 << 16) + (121 << 8) + 121);
    MyRobot robot = new MyRobot();
    Pos logo = recognizeLogo(robot);

    if (null == logo) {
      System.out.println("no logo found");
      return;
    }

    // MyRobot.mouseMove(logo);

    // Hand c = Card_Recognition.start(logo);
    //
    // System.out.println(c);

    // Pos logo = recognizeLogo();
    // int button = ScreenScraper.searchButton(logo);
    // System.out.println("button: " + button);

    // Color ref = new Color(12010269);
    // Pos p = new Pos(0, 0);
    // Pos p2 = new Pos(1000, 1000);
    // Pos pp = MyRobot.pixelSearch(p, p2, ref);
    // System.out.println(pp.minus(recognizeLogo()));
  }

  public static void test1(final MyRobot robot) {
    Pos pos;
    Card c;

    Pos logo = recognizeLogo(robot);

    if (logo != null) {

      pos = new Pos(24, 217).plus(logo); // pocket1

      c = Card_Recognition.recognizeCard(pos, robot);
      System.out.println(c);

      pos = new Pos(39, 221).plus(logo); // pocket2
      c = Card_Recognition.recognizeCard(pos, robot);
      System.out.println(c);

      pos = new Pos(262, 168).plus(logo); // flop 1
      c = Card_Recognition.recognizeCard(pos, robot);
      System.out.println(c);
      pos = new Pos(316, 168).plus(logo); // flop 2
      c = Card_Recognition.recognizeCard(pos, robot);
      System.out.println(c);
      pos = new Pos(370, 168).plus(logo); // flop 3
      c = Card_Recognition.recognizeCard(pos, robot);
      System.out.println(c);

      pos = new Pos(424, 168).plus(logo); // turn
      c = Card_Recognition.recognizeCard(pos, robot);
      System.out.println(c);
      pos = new Pos(478, 168).plus(logo); // river
      c = Card_Recognition.recognizeCard(pos, robot);
      System.out.println(c);

    }
    // 1. Flopkarte: 262, 168
    // 2. flopkarte: 316,168
    // 3. flopkarte: 370, 168
    // 4. flopkarte: 424, 168
    // 5. flopkarte: 478, 168

  }

  private static @Nullable Pos recognizeLogo(final MyRobot robot) {
    // Farbe im Logo von Pokerstars
    Color c = new Color(0x00FFCAC5);
    Pos logo = robot.pixelSearch(0, 0, 100, 250, c);
    return logo;
  }

}
