package managementpaymentsnewtmp;

public final class Wallet {
	private final Integer player;
	private Integer amount;

  private Wallet(final Integer player, final Integer amount) {
		this.player = player;
		this.amount = amount;
	}

  public static Wallet newInstance(final Integer player, final Integer amount) {
		return new Wallet(player, amount);
	}

  public void transferTo(final Wallet w, final Integer amount) {
		assert amount >= 0 && amount <= this.amount;
		assert this.player == w.player;

		this.amount -= amount;
		w.amount += amount;
	}

  public void transferTo(final SidePot s, final Integer amount) {
		assert amount >= 0 && amount <= this.amount;

		this.amount -= amount;
		s.add(player, amount);
	}

	public Integer getPlayer() {
		return player;
	}

	public Integer getAmount() {
		return amount;
	}

}
