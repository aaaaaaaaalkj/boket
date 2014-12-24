package io.screen;

import static tools.InputTool.pat;
import static tools.InputTool.pos;
import static tools.InputTool.toRGB;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import management.cards.cards.Card;
import management.cards.cards.Rank;
import management.cards.cards.Suit;

import org.eclipse.jdt.annotation.Nullable;

import tools.Pos;
import tools.Tools;

public final class CardRecognition {
  private static final Color CARD_EDGE = Constants.CARD_EDGE;
  private static final Pos HAND_POS = Constants.CENTERS_OF_PLAYER_CIRLES
      .get(0);
  private static final int RADIUS = Constants.RADIUS_OF_PLAYER_CIRCLES;

  private CardRecognition() {
  }

  @Nullable
  private static Pos searchCard(final Pos pos, final MyRobot robot) {
    Pos hit = robot.pixelSearch(pos.getX(), pos.getY(), 25, 25, CARD_EDGE);
    if (null == hit) {
      return null;
    }
    hit = hit.plus(pos).minus(4, 0);
    return hit;
  }

  public static List<Card> recognizeCommunityCards(final Pos logo,
      final MyRobot robot) {

    Pos pos = Constants.FLOP.plus(logo);
    List<Card> list = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      Pos hit = searchCard(pos, robot);
      if (null == hit) {
        break;
      }
      Card card = recognizeCard(hit, robot);
      if (card != null) {
        list.add(card);
      }
      pos = hit.plus(Constants.CARD_OFFSET, 0);
    }
    // TODO: maybe its possible that list.get(0) == null
    return list;
  }

  public static List<Card> recognizeHand(final Pos logo, final MyRobot robot) {
    Pos pos = logo.plus(HAND_POS).minus(RADIUS, RADIUS);

    Pos hit = searchCard(pos, robot);
    if (null == hit) {
      // System.out.println("no hit found");
      return Tools.emptyList();
    }
    Card first = recognizeCard(hit, robot);

    pos = hit.plus(4, 1);
    hit = searchCard(pos, robot);
    if (null == hit) {
      return Tools.emptyList();
    }
    Card second = recognizeCard(hit, robot);
    if (Objects.isNull(first) || Objects.isNull(second)) {
      return Tools.emptyList(); // animation may interfere here
    }
    if (first != null && second != null) {
      return Tools.asList(first, second);
    }
    return Tools.emptyList();
  }

  @Nullable
  public static Card recognizeCard(final Pos pos, final MyRobot robot) {
    Suit resColor = recognizeCardColor(pos, robot);
    if (null == resColor) {
      System.out.println("no card color found");
      return null;
    }
    Rank resNum = recognizeCardNum(pos, robot);
    if (resNum == null) {
      System.out.println("no card num found");
      return null;
    }
    return Card.instance(resNum, resColor);
  }

  @Nullable
  private static Rank recognizeCardNum(final Pos pos, final MyRobot robot) {
    final int num = 6;
    Pos[] positions = { pos(7, 11), pos(9, 14), pos(4, 11), pos(7, 5),
        pos(6, 12), pos(11, 8) };
    Pattern myPattern = new Pattern(num);

    for (int i = 0; i < num; i++) {
      Color c = robot.getPixelColor(positions[i].plus(pos));
      Color ref = new Color(16777215);
      if (c.equals(ref)) {
        myPattern.set(i);
      }
    }

    Pattern[] pattern = { pat(1, 1, 1, 0, 1, 0), pat(0, 1, 1, 0, 1, 0),
        pat(1, 0, 0, 1, 0, 1), pat(0, 1, 0, 0, 1, 1),
        pat(0, 1, 0, 0, 0, 1), pat(1, 0, 1, 0, 1, 0),
        pat(0, 1, 1, 0, 0, 0), pat(1, 1, 0, 0, 0, 0),
        pat(0, 1, 0, 1, 1, 0), pat(1, 0, 1, 1, 1, 0),
        pat(1, 0, 0, 0, 0, 0), pat(0, 0, 1, 0, 0, 0),
        pat(0, 1, 1, 0, 0, 1) };
    Rank resNum = null;
    for (int i = 0; i < pattern.length; i++) {
      if (pattern[i].equals(myPattern)) {
        resNum = Rank.values()[i];
      }
    }
    if (resNum == null) {
      System.out.println("cant recognize card" + myPattern);
      return null;
    }
    return resNum;

  }

  @Nullable
  private static Suit recognizeCardColor(final Pos pos, final MyRobot robot) {
    Suit resColor;
    Color cardColor = robot.getPixelColor(pos.plus(9, 25));

    switch (toRGB(cardColor)) {
    case 13109256:
      resColor = Suit.DIAMONDS;
      break;
    case 13174792:
      resColor = Suit.HEARTS;
      break;
    case 0:
      resColor = Suit.SPADES;
      break;
    case 16448250:
      resColor = Suit.CLUBS;
      break;
    default:
      // Logger.warning("unexpected Color in recognizeColor():"
      // + toRGB(cardColor));
      resColor = null;
    }
    return resColor;
  }

}
