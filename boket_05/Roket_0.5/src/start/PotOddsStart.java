package start;

import inputoutput.MyOutput;
import inputoutput.Raw_Situation;
import inputoutput.ScreenScraper;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import management.payments.decisions.DecisionType;
import potoddsstrategy.PotOddsDecision;
import potoddsstrategy.PotOddsStrategy;
import tools.Pos;

public final class PotOddsStart {
  public static Pos pos(final int x, final int y) {
    return new Pos(x, y);
  }

  private PotOddsStart() {
  }

  private static int counter = 0;

  private static final int NUMBER_OF_DIGITS_FOR_SCREENSHOT_SAVE = 3;

  private static String fillZeros(final int counter1) {
    String res = String.valueOf(counter1);
    while (res.length() < NUMBER_OF_DIGITS_FOR_SCREENSHOT_SAVE) {
      res = "0" + res;
    }
    return res;
  }

  private static void saveImage(final BufferedImage capture,
      final String folder) {
    try {
      String type = "png";
      File outputfile = new File("screenshots\\" + folder
          + fillZeros(counter++)
          + "."
          + type);
      ImageIO.write(capture, type, outputfile);
    } catch (IOException e) {
      e.printStackTrace();
      // ignore
    }
  }

  private static final long SLEEP_BETWEEN_ITERATIONS = 500L;

  public static void main(final String[] ignored) throws AWTException {
    System.out.println("main start");
    while (true) {
      ScreenScraper scraper = new ScreenScraper();

      Raw_Situation raw = scraper.getSituation();

      if (raw.getBrownButtons()[0] && !raw.getBrownButtons()[1]
          && !raw.getBrownButtons()[2]) {
        // fast fold possible
        handleSituation(scraper, false);
      }

      if (raw.isItsMyTurn() && raw.getHand() != null) {
        handleSituation(scraper, true);
      } else {
        System.out.println(raw);
        try {
          Thread.sleep(SLEEP_BETWEEN_ITERATIONS);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private static final int NUMBER_OF_FLOP_CARDS = 3;
  private static final double BIG_POST_PREFLOP = .1;
  private static final double BIG_POST_POSTFLOP = .3;

  private static void handleSituation(final ScreenScraper scraper,
      final boolean myTurn) {
    Raw_Situation raw = scraper.getSituation();

    saveImage(scraper.getScreenshot(), "");

    PotOddsStrategy strategy = new PotOddsStrategy(raw);
    PotOddsDecision d = strategy.decide();

    if (d.getDec() == DecisionType.FOLD || myTurn) {

      if (d.hasValue()) {
        if (raw.getCommunityCards().size() >= NUMBER_OF_FLOP_CARDS) {
          if (d.getValue() > BIG_POST_POSTFLOP) {
            saveImage(scraper.getScreenshot(), "expensive\\");
          }
        } else {
          if (d.getValue() > BIG_POST_PREFLOP) {
            saveImage(scraper.getScreenshot(), "expensive\\");
          }
        }
      }

      System.out.println("---------------------");
      System.out.println(strategy);
      System.out.println(raw);
      System.out.println(d);

      Pos logo = scraper.getLogo();
      if (null != logo) {
        decision2ouput(d, logo);
      }
    } else {
      System.out.println("wait for my turn (" + d + ")");
    }
  }

  public static void decision2ouput(final PotOddsDecision d, final Pos logo) {
    MyOutput out = new MyOutput();
    System.out.println("try click");
    switch (d.getDec()) {
    case ALL_IN: // should not happen
    case CALL:
      out.clickCallButton(logo);
      break;
    case FOLD:
      out.clickFoldButton(logo);
      break;
    case RAISE:
      out.type2(d.getValue(), logo);
      out.clickRaiseButton(logo);
      break;
    default:
      throw new IllegalStateException("unexpected decision: " + d);
    }

  }

}
