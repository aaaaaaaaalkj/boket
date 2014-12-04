package managementpaymentstmp;

public class Post {
	private final AmountOfJetons stack;
	private final AmountOfJetons highestBid;
	private final AmountOfJetons lastRaise;
	private final AmountOfJetons amount;
	private final AmountOfJetons wholeAmount;

  public Post(final AmountOfJetons stack, final AmountOfJetons post,
      final AmountOfJetons highestBid,
      final AmountOfJetons lastRaise, final AmountOfJetons amount) {
		this.stack = stack;
		this.highestBid = highestBid;
		this.lastRaise = lastRaise;
		this.amount = amount;
		this.wholeAmount = amount.plus(post);
	}

  public final boolean moreThanCall() {
		return wholeAmount.greaterAs(highestBid);
	}

  public final boolean lessThanHighestBid() {
		return highestBid.greaterAs(wholeAmount);
	}

  public final boolean lessThanRaise() {
		return highestBid.plus(lastRaise).greaterAs(wholeAmount);
	}

  public final boolean allIn() {
		return amount.equals(stack);
	}

  public final boolean notAllIn() {
		return !allIn();
	}
}
