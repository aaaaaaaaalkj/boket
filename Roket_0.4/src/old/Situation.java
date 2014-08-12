package old;

import java.util.HashSet;
import java.util.Set;

import managementCards.cards.Card;
import old.strategy.Position;
import old.strategy.PreflopSituation;

public class Situation {
	public static final int numSeats = 9;
	private static final double SMALL_BET = .02;
	private boolean itsMyTurn = false;
	private Hand hand;
	private Flop flop;
	private Card turn;
	private Card river;
	private int button;
	private boolean[] activePlayer = new boolean[numSeats];
	private boolean[] emptySeat = new boolean[numSeats];
	private double pot;
	private double[] posts = new double[numSeats];
	private int checkSum;
	private Set<Player> playerSet;
	private Player buttonPlayer = null;
	public Player itsMe;
	private boolean isFirstRaiseRound;
	public PreflopSituation pSit;

	public void computePreflopSituation() {
		// allFold, oneCalls, moreThanOneCall, oneRaisesAndNobodyCalls,
		// oneRaisesAndAtLeastOneCalls, moreThanOneRaiseBeforeYou,
		// oneRaiseAfterYou, moreThanOneRaiseAfterYou;
		Player utg = buttonPlayer.getLeftNeighbour().getLeftNeighbour()
				.getLeftNeighbour();
		Player p = utg;
		int called = 0;
		int raised = 0;
		double currentBet = SMALL_BET;
		if (isFirstRaiseRound) {
			while (!p.isItsMe()) {
				if (!p.isAtiveInGame()) {
				} else if (p.getPost() == currentBet) {
					called++;
				} else if (p.getPost() > currentBet) {
					raised++;
					called = 0;
					currentBet = p.getPost();
				}
				p = p.getLeftNeighbour();
			}
			if (raised > 1) {
				setpSit(PreflopSituation.moreThanOneRaiseBeforeYou);
			} else if (raised == 1 && called > 0) {
				setpSit(PreflopSituation.oneRaisesAndAtLeastOneCalls);
			} else if (raised == 1 && called == 0) {
				setpSit(PreflopSituation.oneRaisesAndNobodyCalls);
			} else if (called > 1 && raised == 0) {
				setpSit(PreflopSituation.moreThanOneCall);
			} else if (called == 1 && raised == 0) {
				setpSit(PreflopSituation.oneCalls);
			} else if (called == 0 && raised == 0) {
				setpSit(PreflopSituation.allFold);
			}
		} else {
			double max = 0;
			for (int i = 0; i < numSeats; i++) {
				if (max < posts[i]) {
					max = posts[i];
				}
			}
			max = max / SMALL_BET;
			if (max > 2) {
				setpSit(PreflopSituation.moreThanOneRaiseAfterYou);
			} else
				setpSit(PreflopSituation.oneRaiseAfterYou);
		}
	}

	public void init() {
		playerSet = new HashSet<Player>();
		itsMe = new Player();
		itsMe.setAtiveInGame(null != hand);
		itsMe.setTablePos(0);
		itsMe.setPost(posts[0]);
		itsMe.setItsMe(true);

		Player last = itsMe;
		Player last2 = itsMe;

		buttonPlayer = itsMe;

		for (int i = 1; i < numSeats; i++) {
			if (emptySeat[i])
				continue;
			Player p = new Player();
			playerSet.add(p);
			p.setTablePos(i);
			p.setAtiveInGame(activePlayer[i]);
			p.setPost(posts[i]);
			p.setItsMe(false);
			p.setRightNeighbour(last2);
			last2.setLeftNeighbour(p);
			last2 = p;

			if (button == i) {
				buttonPlayer = p;
			}
			if (p.isAtiveInGame()) {
				p.setPrev(last);
				last.setNext(p);
				last = p;
			}
		}
		last2.setLeftNeighbour(itsMe);
		itsMe.setRightNeighbour(last2);
		last.setNext(itsMe);
		itsMe.setPrev(last);

		Player player = null;
		// Player player = buttonPlayer.getNext();
		// if (player == null)
		// Logger.exit("player is null");
		// int k = 1;
		// while (player != buttonPlayer) {
		// player.setGamePos(k++);
		// player = player.getNext();
		// }
		// buttonPlayer.setGamePos(k);

		// TODO: Nullpointer
		if (buttonPlayer == null) {
			System.out.println("buttonPlayer is null");
			return;
		} else {
			player = buttonPlayer.getLeftNeighbour().getLeftNeighbour();
		}

		player.setPosition(Position.BB);

		player = player.getRightNeighbour();
		Position pos = Position.SB;
		while (player.getPosition() != Position.BB) {
			player.setPosition(pos);
			pos = pos.prev();
			player = player.getRightNeighbour();
		}

		// firstRaiseRound?
		if (posts[0] == 0) {
			isFirstRaiseRound = true;
		} else if (posts[0] > 0 && posts[0] < SMALL_BET) {
			// small blind position
			isFirstRaiseRound = true;
		} else if (posts[0] == SMALL_BET) {
			if (allPostsAreLessEqualToSmallBet()) { // we are bigBlind
				isFirstRaiseRound = true;
			} else {
				isFirstRaiseRound = false;
			}
		} else {
			isFirstRaiseRound = false;
		}

		computePreflopSituation();
	}

	// public boolean isFirstRaiseRound(){
	//
	// return posts[0] > 0;
	// }

	private boolean allPostsAreLessEqualToSmallBet() {
		double max = 0;
		for (double d : posts) {
			if (d > max)
				max = d;
		}
		return max == SMALL_BET;
	}

	public String toString() {
		StringBuilder res = new StringBuilder();
		res.append("Table ");
		res.append(checkSum);
		res.append(System.getProperty("line.separator"));
		res.append("Hand: " + hand);
		res.append(System.getProperty("line.separator"));
		res.append("Flop: " + flop);
		res.append(System.getProperty("line.separator"));
		res.append("Turn: " + turn);
		res.append(System.getProperty("line.separator"));
		res.append("River: " + river);
		res.append(System.getProperty("line.separator"));
		res.append("button: ");
		res.append(button);
		res.append(System.getProperty("line.separator"));
		res.append("pot: ");
		res.append(pot);
		res.append(System.getProperty("line.separator"));
		if (isItsMyTurn())
			res.append("my turn");
		else
			res.append("not my turn");
		res.append(System.getProperty("line.separator"));
		for (int i = 0; i < numSeats; i++) {
			if (emptySeat[i]) {
				System.out.println("empty seat: " + i);
			}
		}
		for (int i = 0; i < numSeats; i++) {
			if (activePlayer[i]) {
				res.append("player" + i + "\t");
				res.append(posts[i]);
				res.append(System.getProperty("line.separator"));
			} else {
				res.append("player" + i + " is not active.\t");
				res.append(posts[i]);
				res.append(System.getProperty("line.separator"));
			}
		}
		return res.toString();
	}

	public boolean isItsMyTurn() {
		return itsMyTurn;
	}

	public void setItsMyTurn(boolean itsMyTurn) {
		this.itsMyTurn = itsMyTurn;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public Flop getFlop() {
		return flop;
	}

	public void setFlop(Flop flop) {
		this.flop = flop;
	}

	public boolean[] getActivePlayer() {
		return activePlayer;
	}

	public void setActivePlayer(boolean[] activePlayer) {
		this.activePlayer = activePlayer;
	}

	public int getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}

	public static int getNumseats() {
		return numSeats;
	}

	public Card getTurn() {
		return turn;
	}

	public Card getRiver() {
		return river;
	}

	public int getButton() {
		return button;
	}

	public double getPot() {
		return pot;
	}

	public double[] getPosts() {
		return posts;
	}

	public void setHnad(Hand h) {
		hand = h;
	}

	public void setHnad(Card c1, Card c2) {
		if (c1 == null || null == c2)
			hand = null;
		else
			hand = Hand.newInstance(c1, c2);
	}

	public void setFlop(Card c3, Card c4, Card c5) {
		if (null == c3 || null == c4 || null == c5)
			flop = null;
		else
			flop = Flop.newInstance(c3, c4, c5);
	}

	public void setMyTurn() {
		itsMyTurn = true;
	}

	public void setButton(int button2) {
		button = button2;
	}

	public void setActivePlayers(boolean[] activePlayer2) {
		activePlayer = activePlayer2;
	}

	public void setPosts(double[] posts2) {
		posts = posts2;

	}

	public void setPot(double pot2) {
		pot = pot2;
	}

	public void setChecksum(int pCheckSum) {
		checkSum = pCheckSum;
		// lobby = 1439337809
	}

	public void setRiver(Card river2) {
		river = river2;
	}

	public void setTurn(Card turn2) {
		turn = turn2;
	}

	public void print() {
		System.out.println(this);
	}

	public boolean[] getEmptySeat() {
		return emptySeat;
	}

	public void setEmptySeat(boolean[] emptySeat) {
		this.emptySeat = emptySeat;
	}

	public void setEmptySeat(int i, boolean b) {
		this.emptySeat[i] = b;
	}

	public boolean isFirstRaiseRound() {
		return isFirstRaiseRound;
	}

	public void setFirstRaiseRound(boolean isFirstRaiseRound) {
		this.isFirstRaiseRound = isFirstRaiseRound;
	}

	public PreflopSituation getpSit() {
		return pSit;
	}

	public void setpSit(PreflopSituation pSit) {
		this.pSit = pSit;
	}

}
