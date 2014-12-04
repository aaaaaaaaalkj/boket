package strategy.manualStrategy;

import java.util.HashMap;
import java.util.Map;

import managementpaymentstmp.AmountOfJetons;
import common.IPlayer;

public final class Stat {
	private int numPaid = 0;

	private AmountOfJetons payed = AmountOfJetons.ZERO;
	private AmountOfJetons won = AmountOfJetons.ZERO;

	private Map<IPlayer, AmountOfJetons> payedInThisGame = new HashMap<>();

	public Stat() {
	}

  public String toString() {
		String s = "";

		if (numPaid > 0) {
			s += "((((((((";
		}

    if (payed.positive()) {
      s += " payed: " + payed + ", ";
    }
    if (won.positive()) {
      s += "won: " + won + ", ";
    }
    if (numPaid > 0) {
      s += +numPaid + " ))))))))";
    }

		return s;
	}

  public void payed(final IPlayer player, final AmountOfJetons amount) {
		if (!payedInThisGame.containsKey(player)) {
			payedInThisGame.put(player, AmountOfJetons.ZERO);
		}
		payedInThisGame.put(player, payedInThisGame.get(player).plus(amount));

		numPaid++;
	}

  public void won(final IPlayer player, final AmountOfJetons wonAmount,
      final AmountOfJetons sumPaid) {
		if (payedInThisGame.containsKey(player)) {
			payedInThisGame.put(player, AmountOfJetons.ZERO);
		}
		AmountOfJetons wonThisTime = wonAmount.times(payedInThisGame
				.get(player).divide(sumPaid));
		payed = payed.plus(payedInThisGame.get(player));
		won = won.plus(wonThisTime);
		payedInThisGame.put(player, AmountOfJetons.ZERO);
	}

  public void lost(final IPlayer player) {
		if (payedInThisGame.containsKey(player)) {
			payedInThisGame.put(player, AmountOfJetons.ZERO);
		}
		payed = payed.plus(payedInThisGame.get(player));
		payedInThisGame.put(player, AmountOfJetons.ZERO);
	}

  public AmountOfJetons getPaid() {
		return payed;
	}

  public AmountOfJetons getWon() {
		return won;
	}

  public int getNumPaid() {
		return numPaid;
	}
}
