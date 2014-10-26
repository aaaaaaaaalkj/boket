package managementPaymentsNew;

public class Wallet {
	private final int player;
	private int amount;

	private Wallet(int player, int amount) {
		this.player = player;
		this.amount = amount;
	}

	public static Wallet newInstance(int player, int amount) {
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

	public int getPlayer() {
		return player;
	}

	public int getAmount() {
		return amount;
	}

}
