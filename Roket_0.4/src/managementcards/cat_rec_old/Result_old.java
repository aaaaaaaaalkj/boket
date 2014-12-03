package managementcards.cat_rec_old;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import managementcards.cards.Rank;
import managementcards.catrecnew.Cathegory;
import managementcards.catrecnew.IResult;
import managementcards.catrecnew.ResultImpl;

import org.eclipse.jdt.annotation.Nullable;

public final class Result_old {
  private Cathegory cathegory;
  private int[] tieBreaker;

  private Result_old(final Cathegory cat, final int[] tieBreaker) {
    this.cathegory = cat;
    this.tieBreaker = tieBreaker;
  }

  public ResultImpl toNewResult() {
    List<Rank> tie = new ArrayList<>();
    for (int i = 0; i < tieBreaker.length; i++) {
      tie.add(Rank.VALUES.get(tieBreaker[i]));
    }
    return new ResultImpl(cathegory, tie);
  }

  public static Result_old newInstance(final Cathegory cat, final int... b) {
    return new Result_old(cat, b);
  }

  public String toString() {
    return cathegory + "" + Arrays.toString(tieBreaker) + "";
  }

  public void print() {
    System.out.println(this);
  }

  public boolean equals2(final Result_old res) {
    if (cathegory != res.cathegory) {
      return false;
    }
    for (int i = 0; i < tieBreaker.length; i++) {
      if (tieBreaker[i] != res.tieBreaker[i]) {
        return false;
      }
    }
    return true;
  }

  public boolean betterAs(final Result_old res) {
    if (cathegory.ordinal() > res.cathegory.ordinal()) {
      return true;
    }
    if (cathegory.ordinal() < res.cathegory.ordinal()) {
      return false;
    }
    // if (tieBreaker.length != res.tieBreaker.length) {
    // cathegory.print();
    // res.cathegory.print();
    // X.print(tieBreaker);
    // X.print(res.tieBreaker);
    // }
    for (int i = 0; i < tieBreaker.length; i++) {
      if (tieBreaker[i] > res.tieBreaker[i]) {
        return true;
      }
      if (tieBreaker[i] < res.tieBreaker[i]) {
        return false;
      }
    }
    return false;
  }

  public int compareTo(final IResult res2) {
    if (res2.getClass() == Result_old.class) {
      Result_old res = (Result_old) res2;
      if (cathegory.ordinal() > res.cathegory.ordinal()) {
        return 1;
      }
      if (cathegory.ordinal() < res.cathegory.ordinal()) {
        return -1;
      }
      for (int i = 0; i < tieBreaker.length; i++) {
        if (tieBreaker[i] > res.tieBreaker[i]) {
          return 1;
        }
        if (tieBreaker[i] < res.tieBreaker[i]) {
          return -1;
        }
      }
      return 0;
    } else if (res2.getClass() == ResultImpl.class) {
      return toNewResult().compareTo(res2);
    }
    throw new IllegalArgumentException("Cant compare " + res2.getClass()
        + " to " + this.getClass());
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result
        + (cathegory.hashCode());
    result = prime * result + Arrays.hashCode(tieBreaker);
    return result;
  }

  @Override
  public boolean equals(@Nullable final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (!(obj instanceof IResult)) {
      return false;
    }
    IResult other = (IResult) obj;
    return this.compareTo(other) == 0;

  }

  public Cathegory getCathegory() {
    return cathegory;
  }

}
