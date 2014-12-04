package managementpaymentsnewtmp;

import static java.lang.Math.max;
import static java.util.stream.Collectors.summingInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import managementcards.catrecnew.IResult;
import managementpaymentsnewtmp.decisions.AllowedDecision;
import managementpaymentsnewtmp.decisions.Decision;

import org.apache.commons.lang3.NotImplementedException;
import org.eclipse.jdt.annotation.NonNull;

import strategy.conditions.common.PotType;
import tools.Tools;

public final class PayManagementNew implements IPayManagement2 {
  private final List<@NonNull Wallet> posts;
  private final List<@NonNull Wallet> stacks;
  private int highestBid; // in small blinds
  private int lastRaise; // in small blinds
  private List<SidePot> pots;

  private PayManagementNew(
      final List<Wallet> posts,
      final List<Wallet> stacks,
      final int highestBid,
      final int lastRaise,
      final List<SidePot> pots) {
    this.posts = posts;
    this.stacks = stacks;
    this.highestBid = highestBid;
    this.lastRaise = lastRaise;
    this.pots = new ArrayList<>(pots);
  }

  public static PayManagementNew newInstance(final int numSeats, final int stackSizes) {
    List<Wallet> posts = new ArrayList<>();
    List<Wallet> stacks = new ArrayList<>();
    for (int player = 0; player < numSeats; player++) {
      posts.add(Wallet.newInstance(player, 0));
      stacks.add(Wallet.newInstance(player, stackSizes));
    }

    List<SidePot> sidePots = Tools.emptyList();

    PayManagementNew m = new PayManagementNew(
        posts,
        stacks,
        2, // highest bid
        2, // last raise
        sidePots
        );
    m.pay(1, 1); // Small Blind
    m.pay(2, 2); // Big Blind

    return m;
  }

  private Optional<Integer> positiveNonZeroPosts() {
    @NonNull
    Optional<Integer> res = Tools.empty();
    for (Wallet w : posts) {
      Integer amount = w.getAmount();
      if (amount <= 0) {
        continue;
      } else {
        if (res.isPresent()) {
          @SuppressWarnings("null")
          @NonNull
          Integer min = Math.min(res.get(), amount);
          res = Tools.of(min);
        } else {
          res = Tools.of(amount);
        }
      }
    }
    return res;
  }

  public void collectPostsToSidePots() {
    Optional<Integer> min;

    for (min = positiveNonZeroPosts(); min.isPresent(); min = positiveNonZeroPosts()) {
      List<Integer> players = posts.stream()
          .filter(x -> x.getAmount() > 0)
          .map(Wallet::getPlayer)
          .collect(Tools.toList());

      int value = min.get();

      SidePot pot = new SidePot();

      players.forEach(player -> posts.get(player).transferTo(pot, value));

      pots.add(pot);
    }
  }

  private void pay(final Integer player, final int amount) {
    if (amount > getStack(player)) {
      throw new IllegalArgumentException("player " + player
          + " doesnt have " + amount + " smallblinds");
    }

    stacks.get(player).transferTo(
        posts.get(player),
        amount);
  }

  @Override
  public AllowedDecision getAllowed(final int player) {
    if (getToCall(player) == 0 && getStack(player) > 2 /* bb=2 */) {
      return AllowedDecision.checkBet(getStack(player) - 2);
    }
    if (getStack(player) > getToCall(player) + lastRaise + 2) {
      return AllowedDecision
          .callRaise(
          getStack(player) - getToCall(player) - lastRaise - 2 /* bb=2 */
          );
    }
    if (getStack(player) > getToCall(player)) {
      return AllowedDecision.call();
    }
    return AllowedDecision.foldAllin();
  }

  @Override
  public void action(final int player, final Decision decision) {
    if (getAllowed(player).isAllowed(decision)) {
      Integer value = decision.getValue();
      switch (decision.getDecisionType()) {
      case ALL_IN:
        pay(player, getStack(player));
        int diff = max(0, getStack(player) - getToCall(player));
        highestBid += diff;
        lastRaise = max(lastRaise, diff);
        break;
      case BET:
        pay(player, value);
        highestBid = value;
        lastRaise = value;
        break;
      case RAISE:
        pay(player, getToCall(player) + lastRaise + value);
        highestBid = highestBid + lastRaise + value;
        lastRaise = lastRaise + value;
        break;
      case CALL:
        pay(player, getToCall(player));
        break;
      case CHECK:
        // do nothing
        break;
      case FOLD:
        // do nothing
        break;
      default:
        throw new AssertionError(decision.getDecisionType()
            + " is not supported");
      }
    } else {
      throw new IllegalStateException(decision + " is not allowed");
    }
  }

  @Override
  public PotType getPotType(final int currentPlayer) {
    return PotType.of(
        ((double) getPotSize())
            /
            getStack(currentPlayer)
        );
  }

  @Override
  public int getPotSize() {
    return posts.stream().map(Wallet::getAmount)
        .collect(Tools.summingInt())
        + pots.stream().map(SidePot::getValue)
            .collect(summingInt(x -> x));
  }

  @Override
  public int getLastRaise() {
    return lastRaise;
  }

  @Override
  public int getToCall(final int player) {
    return highestBid - getPost(player);
  }

  @Override
  public int getStack(final int player) {
    return stacks.get(player).getAmount();
  }

  private int getPost(final int player) {
    return posts.get(player).getAmount();
  }

  @Override
  public List<Integer> payOut(final Set<Integer> notFolded, final List<IResult> results) {
    collectPostsToSidePots();

    throw new NotImplementedException("todo");
  }

}
