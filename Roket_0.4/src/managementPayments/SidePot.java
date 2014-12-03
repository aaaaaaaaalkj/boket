package managementPayments;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

import common.IPlayer;

public class SidePot {
  private AmountOfJetons value;
  private List<@NonNull IPlayer> participants;

  public SidePot() {
    value = AmountOfJetons.ZERO;
    participants = new ArrayList<>();
  }

  // players who folded, contribute to the pot, but they cannot win the pot
  public final void add(final AmountOfJetons amount) {
    value = value.plus(amount);
  }

  public final void add(final IPlayer p, final AmountOfJetons amount) {
    value = value.plus(amount);
    if (participants.contains(p)) {
      throw new IllegalStateException("Player " + p
          + " contributes several times to one pot.");
    }
    participants.add(p);
  }

  public final AmountOfJetons getValue() {
    return value;
  }

  public final void setValue(final AmountOfJetons value) {
    this.value = value;
  }

  public final List<IPlayer> getParticipants() {
    return participants;
  }
}
