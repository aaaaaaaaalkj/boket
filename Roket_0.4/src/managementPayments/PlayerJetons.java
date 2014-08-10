package managementPayments;

public class PlayerJetons {
	private AmountOfJetons stack;
	private AmountOfJetons post;

	public PlayerJetons(AmountOfJetons value) {
		this.stack = value;
		this.post = AmountOfJetons.ZERO;
	}

	public AmountOfJetons getStack() {
		return stack;
	}

	public AmountOfJetons getPost() {
		return post;
	}

	public void setStack(AmountOfJetons amount) {
		this.stack = amount;
	}

	public void setPost(AmountOfJetons amount) {
		this.post = amount;
	}

	public void removeFromStack(AmountOfJetons amount) {
		this.stack = this.stack.minus(amount);
	}

	public void addToPost(AmountOfJetons amount) {
		this.post = this.post.plus(amount);
	}

	public AmountOfJetons removeFromPostAtMost(AmountOfJetons amount) {
		AmountOfJetons res = post.min(amount);
		post = post.minus(res);
		return res;
	}

	public void won(AmountOfJetons amount) {
		stack = stack.plus(amount);
	}
}
