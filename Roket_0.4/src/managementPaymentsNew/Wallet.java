package managementPaymentsNew;

public class Wallet {
	private final Integer player;
	private Integer amount;

	private Wallet(Integer player, Integer amount) {
		this.player = player;
		this.amount = amount;
	}

	public static Wallet newInstance(Integer player, Integer amount) {
		return new Wallet(player, amount);
	}

	public void transferTo(Wallet w, Integer amount) {
		assert amount >= 0 && amount <= this.amount;
		assert this.player == w.player;

		this.amount -= amount;
		w.amount += amount;
	}

	public void transferTo(SidePot s, Integer amount) {
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
