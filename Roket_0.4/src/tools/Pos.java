package tools;

public class Pos {
  public int x, y;

  public Pos(final int x, final int y) {
    this.x = x;
    this.y = y;
  }

  public final String toString() {
    return "[" + x + "," + y + "]";
  }

  public final double dist(final Pos p) {
    double a = x - p.x;
    double b = y - p.y;
    return Math.sqrt(a * a + b * b);
  }

  public final void print() {
    System.out.println(this);
  }

  public final Pos plus(final Pos p) {
    return new Pos(x + p.x, y + p.y);
  }

  public final Pos plus(final int x1, final int y1) {
    return new Pos(x + x1, y + y1);
  }

  public final Pos minus(final Pos p) {
    return new Pos(x - p.x, y - p.y);
  }

  public final Pos minus(final int x, final int y) {
    return new Pos(this.x - x, this.y - y);
  }
}
