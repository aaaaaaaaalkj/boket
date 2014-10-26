package common;

import managementCards.cat_rec_new.IResult;
import managementPayments.AmountOfJetons;
import strategy.IStrategy;

public class PlayerResult {
	private final IStrategy strategy;
	private final IResult result;
	private final AmountOfJetons payed;
	private final AmountOfJetons received;

	private PlayerResult(IStrategy player, IResult result,
			AmountOfJetons payed,
			AmountOfJetons received) {
		this.strategy = player;
		this.result = result;
		this.payed = payed;
		this.received = received;
	}

	public static PlayerResult create(IStrategy player, IResult result,
			AmountOfJetons payed, AmountOfJetons received) {
		return new PlayerResult(player, result, payed, received);
	}

	public IStrategy getPlayer() {
		return strategy;
	}

	public IResult getResult() {
		return result;
	}

	public AmountOfJetons getPayed() {
		return payed;
	}

	public AmountOfJetons getReceived() {
		return received;
	}

}
