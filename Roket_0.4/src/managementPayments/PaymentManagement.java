package managementPayments;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import managementPaymentsNew.IPayManagement;
import strategy.PlayerDecision;
import strategy.TypeOfDecision;

import common.IOutcome;
import common.IPlayer;

public class PaymentManagement implements IPayManagement {
	private AmountOfJetons highestBid;
	private AmountOfJetons lastRaise;
	private List<SidePot> pots;
	private final List<IPlayer> players;

	public PaymentManagement() {
		this.highestBid = AmountOfJetons.ZERO;
		this.pots = new ArrayList<>();
		this.lastRaise = AmountOfJetons.BB;
		this.players = new ArrayList<>();
	}

	public AmountOfJetons getStack(IPlayer player) {
		return player.getStack().get();
	}

	public void postSB(IPlayer player) {
		post_intern(player, AmountOfJetons.SB);
		lastRaise = AmountOfJetons.BB;
		highestBid = AmountOfJetons.BB;
	}

	public void postBB(IPlayer player) {
		post_intern(player, AmountOfJetons.BB);
		lastRaise = AmountOfJetons.BB;
		highestBid = AmountOfJetons.BB;
	}

	public void post(IPlayer player, AmountOfJetons amount) {
		amount = modifyAmount(player, amount);
		post_intern(player, amount);
	}

	private boolean post_intern(IPlayer player, AmountOfJetons amount) {
		AmountOfJetons wholeAmount = amount.plus(player.getPost());
		boolean raised = wholeAmount.greaterAs(highestBid);
		if (raised) {
			lastRaise = wholeAmount.minus(highestBid);
			highestBid = wholeAmount;
		}
		player.getStack().removeFromStack(amount);
		player.setPost(player.getPost().plus(amount));
		return raised;
	}

	public PlayerDecision modifyTypeOfDecision(IPlayer player, TypeOfDecision d) {
		if (d == TypeOfDecision.CALL) {
			if (highestBid.minus(player.getPost()).isZero())
				return PlayerDecision.CHECK;
		}
		if (d == TypeOfDecision.FOLD) {
			return PlayerDecision.FOLD;
		}
		if (d == TypeOfDecision.CALL) {
			return PlayerDecision.CALL;
		}
		assert d != TypeOfDecision.FOLD && d != TypeOfDecision.CALL;
		// Only Raise-Types possible here

		if (highestBid.isZero())
			return PlayerDecision.BET;

		if (highestBid.minus(player.getPost()).greaterOrEqual(getStack(player))) {
			return PlayerDecision.CALL;
		}
		// if (lastRaise.greaterAs(AmountOfJetons.BB))
		// return PlayerDecision.RERAISE;
		return PlayerDecision.RAISE;
	}

	public AmountOfJetons computePost(IPlayer player, TypeOfDecision d) {
		AmountOfJetons amount = AmountOfJetons.ZERO;
		AmountOfJetons wholeAmount;

		// at least we need to call the highest bid
		amount = highestBid.minus(player.getPost());

		AmountOfJetons stack = player.getStack().get();

		switch (d) {
		case ALL_IN:
			amount = stack;
			break;
		case CALL:
			// amount already computed
			break;
		case FOLD:
			amount = AmountOfJetons.ZERO;
			break;
		case RAISE_DOUBLE_POT:
			amount = amount.plus(computeTotalPot(player).times(2));
			break;
		case RAISE_HALF_POT:
			amount = amount.plus(computeTotalPot(player).divideToEven(2));
			break;
		case RAISE_POT_SIZE:
			amount = amount.plus(computeTotalPot(player));
			break;
		case RAISE_QUARTER_POT:
			amount = amount.plus(computeTotalPot(player).divideToEven(4));
			break;
		case RAISE_FIFTH_STACK:
			amount = amount.plus(stack.divideToEven(5));
			break;
		case RAISE_HALF_STACK:
			amount = amount.plus(stack.divideToEven(2));
			break;
		case RAISE_TENTH_STACK:
			amount = amount.plus(stack.divideToEven(10));
			break;
		default:
			throw new UnsupportedOperationException(d + " is not supported");
		}

		// cant put more jetons on table than available
		amount = AmountOfJetons.min(amount, stack);

		wholeAmount = amount.plus(player.getPost());

		Post post = new Post(stack, player.getPost(), highestBid, lastRaise,
				amount);

		if (post.moreThanCall()) {
			if (post.lessThanRaise() && post.notAllIn()) {
				amount = highestBid.plus(lastRaise);
				// d = TypeOfDecision.RAISE_QUARTER_POT;
			}
			if (!wholeAmount.minus(highestBid).isEven()) {
				amount = wholeAmount.plus(AmountOfJetons.SB);
			}
		}
		amount = AmountOfJetons.min(amount, stack);
		return amount;
	}

	private AmountOfJetons modifyAmount(IPlayer player, AmountOfJetons amount) {
		AmountOfJetons wholeAmount = amount.plus(player.getPost());
		AmountOfJetons stack = player.getStack().get();

		Post post = new Post(stack, player.getPost(), highestBid, lastRaise,
				amount);

		if (post.lessThanHighestBid() && post.notAllIn()) {
			amount = highestBid;
		}
		if (post.moreThanCall()) {
			if (post.lessThanRaise() && post.notAllIn()) {
				amount = highestBid.plus(lastRaise);
			}
			if (!wholeAmount.minus(highestBid).isEven()) {
				amount = wholeAmount.plus(AmountOfJetons.SB);
			}
		}
		amount = AmountOfJetons.min(amount, stack);
		return amount;
	}

	/**
	 * a round is over. everybody should have contributed the same amount to the
	 * pot (except all-in's) Otherwise exit() should be called.
	 * 
	 * @param inGame
	 */
	public void roundEnd() {
		AmountOfJetons minPost;
		AmountOfJetons removed;

		while ((minPost = minPost()).positive()) {
			SidePot pot = new SidePot();
			for (IPlayer p : players) {
				removed = p.getPost().min(minPost);
				p.setPost(p.getPost().minus(removed));

				if (p.getState().isInGame()) {
					pot.add(p, removed); // all-in or active
				} else {
					pot.add(removed); // folded
				}
			}
			pots.add(pot);
		}
		highestBid = AmountOfJetons.ZERO;
		lastRaise = AmountOfJetons.BB;
	}

	private AmountOfJetons minPost() {
		AmountOfJetons res = AmountOfJetons.INFINITY;
		for (IPlayer p : players) {
			if (p.getStack().isEmpty() && p.getPost().positive()) // all-in
				res = AmountOfJetons.min(res, p.getPost());
		}
		if (res == AmountOfJetons.INFINITY) {
			res = AmountOfJetons.ZERO;
			for (IPlayer p : players) {
				res = AmountOfJetons.max(res, p.getPost());
			}
		}
		return res;
	}

	public void register(IPlayer player) {
		player.setPost(AmountOfJetons.ZERO);
		players.add(player);
	}

	public AmountOfJetons getHighestBid(IPlayer player) {
		AmountOfJetons playerAmount = player.getStack().get()
				.plus(player.getPost());
		return AmountOfJetons.min(highestBid, playerAmount);
	}

	public AmountOfJetons toPay(IPlayer player) {
		AmountOfJetons playerStack = player.getStack().get();
		AmountOfJetons playerPost = player.getPost();
		return AmountOfJetons.min(highestBid.minus(playerPost), playerStack);
	}

	public AmountOfJetons computeTotalPot(IPlayer player) {
		AmountOfJetons sum = AmountOfJetons.ZERO;
		AmountOfJetons playerAmount = player.getStack().get()
				.plus(player.getPost());

		for (SidePot pot : pots) {
			if (pot.participants.contains(player))
				sum = sum.plus(pot.value);
		}
		for (IPlayer other : players) {
			sum = sum.plus(AmountOfJetons.min(other.getPost(), playerAmount));
		}
		return sum;
	}

	public String toString() {
		String res = "";
		for (IPlayer player : players) {
			res += player + ": " + player.getPost() + "\n";
		}
		return res;
	}

	public PayOuts payOut(IOutcome outcome) {
		PayOuts payOuts = new PayOuts();
		for (SidePot pot : pots) {
			Iterator<IPlayer> i = pot.participants.iterator();

			while (i.hasNext()) {
				IPlayer p = i.next();
				boolean underDog = false;
				for (IPlayer p2 : pot.participants) {
					if (outcome.betterAs(p2, p))
						underDog = true;
				}
				if (underDog)
					i.remove();
			}

			// split the pot to winners
			AmountOfJetons part = pot.value.divide(pot.participants.size());
			for (IPlayer p : pot.participants) {
				p.getStack().addToStack(part);
				payOuts.add(p, part);
			}
			// some jetons may remain due to division with remainder
			pot.value = pot.value.minus(part.times(pot.participants.size()));

			// divide the remains by random
			// Collections.shuffle(pot.participants, rand);
			for (IPlayer p : pot.participants) {
				if (pot.value.equals(AmountOfJetons.ZERO))
					break;
				pot.value = pot.value.minus(AmountOfJetons.SB);
				p.getStack().addToStack(AmountOfJetons.SB);
				payOuts.add(p, AmountOfJetons.SB);
			}
		}
		return payOuts;
	}

	@Override
	public ActionInfo action(int player, TypeOfDecision dec) {
		// TODO Auto-generated method stub
		return null;
	}
}
