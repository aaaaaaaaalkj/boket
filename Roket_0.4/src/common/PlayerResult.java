package common;

import managementCards.cat_rec_new.IResult;
import managementPayments.AmountOfJetons;

public class PlayerResult {
	private final IPlayer player;
	private final IResult result;
	private final AmountOfJetons payed;
	private final AmountOfJetons received;

	private PlayerResult(IPlayer player, IResult result, AmountOfJetons payed,
			AmountOfJetons received) {
		this.player = player;
		this.result = result;
		this.payed = payed;
		this.received = received;
	}

	public static PlayerResult create(IPlayer player, IResult result,
			AmountOfJetons payed, AmountOfJetons received) {
		return new PlayerResult(player, result, payed, received);
	}

	public IPlayer getPlayer() {
		return player;
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
