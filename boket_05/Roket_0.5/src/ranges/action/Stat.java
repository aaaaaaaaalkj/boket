package ranges.action;

public final class Stat {
  private int played;
  private int won;

  public Stat() {
    played = 0;
    won = 0;
  }

  public void won(final boolean won2) {
    if (won2) {
      won++;
    }
    played++;
  }

  public void lost() {
    played++;
  }

  public int getPlayed() {
    return played;
  }

  public int getWon() {
    return won;
  }

}
