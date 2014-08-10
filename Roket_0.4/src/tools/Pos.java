package tools;

public class Pos {
	public int x, y;

	public Pos(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public String toString() {
		return "[" + x + "," + y + "]";
	}

	public double dist(Pos p) {
		double a = x - p.x;
		double b = y - p.y;
		return Math.sqrt(a * a + b * b);
	}

	public void print() {
		System.out.println(this);
	}

	public Pos plus(Pos p) {
		return new Pos(x + p.x, y + p.y);
	}

	public Pos plus(int x1, int y1) {
		return new Pos(x + x1, y + y1);
	}

	public Pos minus(Pos p) {
		return new Pos(x - p.x, y - p.y);
	}

	public Pos minus(int x, int y) {
		return new Pos(this.x - x, this.y - y);
	}
}
