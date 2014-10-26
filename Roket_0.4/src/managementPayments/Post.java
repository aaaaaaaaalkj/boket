package managementPayments;

public class Post {
	private final AmountOfJetons stack;
	private final AmountOfJetons highestBid;
	private final AmountOfJetons lastRaise;
	private final AmountOfJetons amount;
	private final AmountOfJetons wholeAmount;

	public Post(AmountOfJetons stack, AmountOfJetons post,
			AmountOfJetons highestBid,
			AmountOfJetons lastRaise, AmountOfJetons amount) {
		this.stack = stack;
		this.highestBid = highestBid;
		this.lastRaise = lastRaise;
		this.amount = amount;
		this.wholeAmount = amount.plus(post);
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
		return amount.equals(stack);
	}

	public boolean notAllIn() {
		return !allIn();
	}
}
