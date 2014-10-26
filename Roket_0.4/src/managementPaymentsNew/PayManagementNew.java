package managementPaymentsNew;

import static java.lang.Math.max;
import static java.util.Comparator.naturalOrder;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import managementCards.cat_rec_new.IResult;
import managementPaymentsNew.decisions.AllowedDecision;
import managementPaymentsNew.decisions.Decision;
import strategy.conditions.common.PotType;

public final class PayManagementNew implements IPayManagement2 {
	private final List<Wallet> posts;
	private final List<Wallet> stacks;
	private int highestBid; // in small blinds
	private int lastRaise; // in small blinds
	private List<SidePot> pots;

	private PayManagementNew(
			List<Wallet> posts,
			List<Wallet> stacks,
			int highestBid,
			int lastRaise,
			List<SidePot> pots) {
		this.posts = posts;
		this.stacks = stacks;
		this.highestBid = highestBid;
		this.lastRaise = lastRaise;
		this.pots = new ArrayList<>(pots);
	}

	public static PayManagementNew newInstance(int numSeats, int stackSizes) {
		List<Wallet> posts = new ArrayList<>();
		List<Wallet> stacks = new ArrayList<>();
		for (int player = 0; player < numSeats; player++) {
			posts.add(Wallet.newInstance(player, 0));
			stacks.add(Wallet.newInstance(player, stackSizes));
		}

		PayManagementNew m = new PayManagementNew(
				posts,
				stacks,
				2, // highest bid
				2, // last raise
				Collections.emptyList() // pots
		);
		m.pay(1, 1); // Small Blind
		m.pay(2, 2); // Big Blind

		return m;
	}

	public void collectPostsToSidePots() {
		Optional<Integer> min;

		while ((min = posts.stream()
				.map(Wallet::getAmount)
				.filter(post -> post > 0)
				.min(naturalOrder())).isPresent()) {

			List<Integer> players = posts.stream()
					.filter(x -> x.getAmount() > 0)
					.map(Wallet::getPlayer)
					.collect(toList());

			int value = min.get();

			SidePot pot = new SidePot();

			players.forEach(player -> posts.get(player).transferTo(pot, value));

			pots.add(pot);
		}
	}

	private void pay(int player, int amount) {
		if (amount > getStack(player)) {
			throw new IllegalArgumentException("player " + player
					+ " doesnt have " + amount + " smallblinds");
		}

		stacks.get(player).transferTo(
				posts.get(player),
				amount);
	}

	@Override
	public AllowedDecision getAllowed(int player) {
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
	public void action(int player, Decision decision) {
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
	public PotType getPotType() {
		return PotType.of(
				((double) getPotSize())
						/
						getStackSizes()
				);
	}

	private int getStackSizes() {
		return stacks.stream()
				.map(Wallet::getAmount)
				.collect(summingInt(x -> x));
	}

	@Override
	public int getPotSize() {
		return posts.stream().map(Wallet::getAmount)
				.collect(summingInt(x -> x))
				+ pots.stream().map(SidePot::getValue)
						.collect(summingInt(x -> x));
	}

	@Override
	public int getLastRaise() {
		return lastRaise;
	}

	@Override
	public int getToCall(int player) {
		return highestBid - getPost(player);
	}

	@Override
	public int getStack(int player) {
		return stacks.get(player).getAmount();
	}

	private int getPost(int player) {
		return posts.get(player).getAmount();
	}

	@Override
	public List<Integer> payOut(Set<Integer> notFolded, List<IResult> results) {
		collectPostsToSidePots();

		return null;
	}

}
