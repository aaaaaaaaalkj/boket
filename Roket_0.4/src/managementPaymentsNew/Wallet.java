package managementPaymentsNew;

public class Wallet {
	private final Integer player;
	private int amount;

	private Wallet(Integer player, int amount) {
		this.player = player;
		this.amount = amount;
	}

	public static Wallet newInstance(Integer player, int amount) {
		return new Wallet(player, amount);
	}

	public void transferTo(Wallet w, int amount) {
		assert amount >= 0 && amount <= this.amount;
		assert this.player == w.player;

		this.amount -= amount;
		w.amount += amount;
	}

	public void transferTo(SidePot s, int amount) {
		assert amount >= 0 && amount <= this.amount;

		this.amount -= amount;
		s.add(player, amount);
	}

	public Integer getPlayer() {
		return player;
	}

	public int getAmount() {
		return amount;
	}

}
