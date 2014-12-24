package io.screen;

import java.util.Arrays;

import org.eclipse.jdt.annotation.Nullable;

public final class Pattern {

  private boolean[] value;

  @SuppressWarnings("null")
  public Pattern(final int num) {
    value = new boolean[num];
    for (int i = 0; i < value.length; i++) {
      value[i] = false;
    }
  }

  @SuppressWarnings("null")
  public Pattern(final int... k) {
    value = new boolean[k.length];
    for (int i = 0; i < k.length; i++) {
      value[i] = k[i] == 1;
    }
  }

  public void set(final int k) {
    value[k] = true;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(value);
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
    if (getClass() != obj.getClass()) {
      return false;
    }
    Pattern other = (Pattern) obj;
    if (!Arrays.equals(value, other.value)) {
      return false;
    }
    return true;
  }

  @Override
  @SuppressWarnings("null")
  public String toString() {
    return Arrays.toString(value);
  }
}
