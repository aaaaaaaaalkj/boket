package start;

import inputoutput.MyOutput;
import inputoutput.Raw_Situation;
import inputoutput.ScreenScraper;

import java.awt.AWTException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import managementpaymentsnewtmp.decisions.DecisionType;
import potoddsstrategy.PotOddsDecision;
import potoddsstrategy.PotOddsStrategy;
import tools.Pos;

public class PotOddsStart {
  public final Pos pos(final int x, final int y) {
    return new Pos(x, y);
  }

  private static int counter = 0;

  private static String fillZeros(final int counter) {
    String res = String.valueOf(counter);
    while (res.length() < 3) {
      res = "0" + res;
    }
    return res;
  }

  private static void saveImage(final BufferedImage capture, final String folder) {
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

  public static void main(final String[] ignored) throws InterruptedException,
      AWTException {
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
          Thread.sleep(500);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

  private static void handleSituation(final ScreenScraper scraper,
      final boolean myTurn) {
    Raw_Situation raw = scraper.getSituation();

    saveImage(scraper.getScreenshot(), "");

    PotOddsStrategy strategy = new PotOddsStrategy(raw);
    PotOddsDecision d = strategy.decide();

    if (d.getDec() == DecisionType.FOLD || myTurn) {

      if (d.hasValue()) {
        if (raw.getCommunityCards().size() >= 3) {
          if (d.getValue() > .3) {
            saveImage(scraper.getScreenshot(), "expensive\\");
          }
        } else {
          if (d.getValue() > .1) {
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
        decision2ouput(d, logo, raw);
      }
    } else {
      System.out.println("wait for my turn (" + d + ")");
    }
  }

  public static void decision2ouput(final PotOddsDecision d, final Pos logo,
      final Raw_Situation raw) {
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
