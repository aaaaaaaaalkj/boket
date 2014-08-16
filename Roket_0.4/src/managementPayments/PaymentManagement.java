package managementPayments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import strategy.PlayerDecision;
import strategy.TypeOfDecision;

import common.IOutcome;
import common.IPlayer;

public class PaymentManagement {
	private Map<IPlayer, PlayerJetons> playerJetons = new HashMap<>();
	private AmountOfJetons start;
	private AmountOfJetons highestBid;
	private AmountOfJetons lastRaise;
	private List<SidePot> pots;
	private final List<IPlayer> players; // used only in toString

	// private final Random rand;

	public PaymentManagement(int startAmountBB) {
		this.start = AmountOfJetons.BB(startAmountBB);
		this.highestBid = AmountOfJetons.ZERO;
		this.pots = new ArrayList<>();
		this.lastRaise = AmountOfJetons.BB;
		this.players = new ArrayList<>();
	}

	public AmountOfJetons getStack(IPlayer player) {
		return playerJetons.get(player).getStack();
	}

	public AmountOfJetons getPost(IPlayer player) {
		return playerJetons.get(player).getPost();
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

	public StateInfo post(IPlayer player, AmountOfJetons amount) {
		PlayerJetons j = playerJetons.get(player);

		amount = modifyAmount(player, amount);

		boolean raised = post_intern(player, amount);
		return new StateInfo(j.getStack().isZero(), raised);
	}

	private boolean post_intern(IPlayer player, AmountOfJetons amount) {
		PlayerJetons j = playerJetons.get(player);
		AmountOfJetons wholeAmount = amount.plus(j.getPost());
		boolean raised = wholeAmount.greaterAs(highestBid);
		if (raised) {
			lastRaise = wholeAmount.minus(highestBid);
			highestBid = wholeAmount;
		}
		j.removeFromStack(amount);
		j.addToPost(amount);
		return raised;
	}

	public PlayerDecision modifyTypeOfDecision(IPlayer player, TypeOfDecision d) {
		if (d == TypeOfDecision.FOLD || d == TypeOfDecision.CALL) {
			if (highestBid.minus(getPost(player)).isZero())
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

		if (highestBid.minus(getPost(player)).greaterOrEqual(getStack(player))) {
			return PlayerDecision.CALL;
		}
		// if (lastRaise.greaterAs(AmountOfJetons.BB))
		// return PlayerDecision.RERAISE;
		return PlayerDecision.RAISE;
	}

	public AmountOfJetons computePost(IPlayer player, TypeOfDecision d) {
		AmountOfJetons amount = AmountOfJetons.ZERO;
		PlayerJetons j = playerJetons.get(player);
		AmountOfJetons wholeAmount;

		// at least we need to call the highest bid
		amount = highestBid.minus(j.getPost());

		switch (d) {
		case ALL_IN:
			amount = j.getStack();
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
			amount = amount.plus(j.getStack().divideToEven(5));
			break;
		case RAISE_HALF_STACK:
			amount = amount.plus(j.getStack().divideToEven(2));
			break;
		case RAISE_TENTH_STACK:
			amount = amount.plus(j.getStack().divideToEven(10));
			break;
		default:
			throw new UnsupportedOperationException(d + " is not supported");
		}

		// cant put more jetons on table than available
		amount = AmountOfJetons.min(amount, j.getStack());

		wholeAmount = amount.plus(j.getPost());

		Post post = new Post(j, highestBid, lastRaise, amount);

		if (post.moreThanCall()) {
			if (post.lessThanRaise() && post.notAllIn()) {
				amount = highestBid.plus(lastRaise);
				// d = TypeOfDecision.RAISE_QUARTER_POT;
			}
			if (!wholeAmount.minus(highestBid).isEven()) {
				amount = wholeAmount.plus(AmountOfJetons.SB);
			}
		}
		amount = AmountOfJetons.min(amount, j.getStack());
		return amount;
	}

	private AmountOfJetons modifyAmount(IPlayer player, AmountOfJetons amount) {
		PlayerJetons j = playerJetons.get(player);
		AmountOfJetons wholeAmount = amount.plus(j.getPost());

		Post post = new Post(j, highestBid, lastRaise, amount);

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
		amount = AmountOfJetons.min(amount, j.getStack());
		return amount;
	}

	/**
	 * a round is over. everybody should have contributed the same amount to the
	 * pot (except all-in's) Otherwise exit() should be called.
	 * 
	 * @param inGame
	 */
	public void roundEnd(List<IPlayer> inGame) {
		AmountOfJetons minPost;
		AmountOfJetons removed;

		while ((minPost = minPost()).positive()) {
			SidePot pot = new SidePot();
			for (IPlayer p : playerJetons.keySet()) {
				removed = playerJetons.get(p).removeFromPostAtMost(minPost);
				if (inGame.contains(p))
					pot.add(p, removed);
				else
					pot.add(removed);
			}
			pots.add(pot);
		}
		highestBid = AmountOfJetons.ZERO;
		lastRaise = AmountOfJetons.BB;
	}

	private AmountOfJetons minPost() {
		AmountOfJetons res = AmountOfJetons.INFINITY;
		for (IPlayer p : playerJetons.keySet()) {
			PlayerJetons j = playerJetons.get(p);
			if (j.getStack().isZero() && j.getPost().positive()) // all-in
				res = AmountOfJetons.min(res, j.getPost());
		}
		if (res == AmountOfJetons.INFINITY) {
			res = AmountOfJetons.ZERO;
			for (IPlayer p : playerJetons.keySet()) {
				PlayerJetons j = playerJetons.get(p);
				res = AmountOfJetons.max(res, j.getPost());
			}
		}
		return res;
	}

	public void register(IPlayer player) {
		playerJetons.put(player, new PlayerJetons(start));
		players.add(player);
	}

	public AmountOfJetons getHighestBid(IPlayer player) {
		AmountOfJetons playerAmount = playerJetons.get(player).getStack()
				.plus(playerJetons.get(player).getPost());
		return AmountOfJetons.min(highestBid, playerAmount);
	}

	public AmountOfJetons toPay(IPlayer player) {
		AmountOfJetons playerStack = playerJetons.get(player).getStack();
		AmountOfJetons playerPost = playerJetons.get(player).getPost();
		return AmountOfJetons.min(highestBid.minus(playerPost), playerStack);
	}

	public AmountOfJetons computeTotalPot(IPlayer player) {
		AmountOfJetons sum = AmountOfJetons.ZERO;
		AmountOfJetons playerAmount = playerJetons.get(player).getStack()
				.plus(playerJetons.get(player).getPost());

		for (SidePot pot : pots) {
			if (pot.participants.contains(player))
				sum = sum.plus(pot.value);
		}
		for (PlayerJetons j : playerJetons.values()) {
			sum = sum.plus(AmountOfJetons.min(j.getPost(), playerAmount));
		}
		return sum;
	}

	public String toString() {
		String res = "";
		for (IPlayer player : players) {
			PlayerJetons j = playerJetons.get(player);
			res += player + ": " + j.getPost() + "\n";
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
				playerJetons.get(p).won(part);
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
				playerJetons.get(p).won(AmountOfJetons.SB);
				payOuts.add(p, AmountOfJetons.SB);
			}
		}
		return payOuts;
	}
}
