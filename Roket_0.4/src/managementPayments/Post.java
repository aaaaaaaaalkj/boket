package managementPayments;

public class Post {
	private final PlayerJetons playerJetons;
	private final AmountOfJetons highestBid;
	private final AmountOfJetons lastRaise;
	private final AmountOfJetons amount;
	private final AmountOfJetons wholeAmount;

	public Post(PlayerJetons playerJetons, AmountOfJetons highestBid,
			AmountOfJetons lastRaise, AmountOfJetons amount) {
		this.playerJetons = playerJetons;
		this.highestBid = highestBid;
		this.lastRaise = lastRaise;
		this.amount = amount;
		this.wholeAmount = amount.plus(playerJetons.getPost());
	}

	public boolean moreThanCall() {
		return wholeAmount.greaterAs(highestBid);
	}

	public boolean lessThanHighestBid() {
		return highestBid.greaterAs(wholeAmount);
	}

	public boolean lessThanRaise() {
		return highestBid.plus(lastRaise).greaterAs(wholeAmount);
	}

	public boolean allIn() {
		return amount.equals(playerJetons.getStack());
	}

	public boolean notAllIn() {
		return !allIn();
	}
}
