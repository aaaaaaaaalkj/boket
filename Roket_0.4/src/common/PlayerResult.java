package common;

import managementcards.catrecnew.IResult;
import managementpaymentstmp.AmountOfJetons;
import strategy.IStrategy;

public final class PlayerResult {
	private final IStrategy strategy;
	private final IResult result;
	private final AmountOfJetons payed;
	private final AmountOfJetons received;

  private PlayerResult(final IStrategy player, final IResult result,
      final AmountOfJetons payed,
      final AmountOfJetons received) {
		this.strategy = player;
		this.result = result;
		this.payed = payed;
		this.received = received;
	}

  public static PlayerResult create(final IStrategy player, final IResult result,
      final AmountOfJetons payed, final AmountOfJetons received) {
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
