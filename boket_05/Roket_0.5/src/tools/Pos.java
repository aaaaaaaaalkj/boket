package tools;

public final class Pos {
  private int x;
  private int y;

  public Pos(final int x, final int y) {
    this.setX(x);
    this.setY(y);
  }

  @Override
  public String toString() {
    return "[" + getX() + "," + getY() + "]";
  }

  public double dist(final Pos p) {
    double a = getX() - p.getX();
    double b = getY() - p.getY();
    return Math.sqrt(a * a + b * b);
  }

  public void print() {
    System.out.println(this);
  }

  public Pos plus(final Pos p) {
    return new Pos(getX() + p.getX(), getY() + p.getY());
  }

  public Pos plus(final int x1, final int y1) {
    return new Pos(getX() + x1, getY() + y1);
  }

  public Pos minus(final Pos p) {
    return new Pos(getX() - p.getX(), getY() - p.getY());
  }

  public Pos minus(final int x1, final int y1) {
    return new Pos(this.getX() - x1, this.getY() - y1);
  }

  public int getY() {
    return y;
  }

  public void setY(final int y) {
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(final int x) {
    this.x = x;
  }
}
