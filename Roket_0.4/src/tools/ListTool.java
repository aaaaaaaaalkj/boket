package tools;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.NonNull;

public class ListTool<E> {
	private List<@NonNull E> list;

  public ListTool(final List<@NonNull E> list) {
		this.list = list;
	}

  public static <E> ListTool<E> of(final List<@NonNull E> list) {
		ListTool<E> tool = new ListTool<E>(list);
		return tool;
	}

  public static <E> ListTool<E> copyOf(final List<@NonNull E> list) {
		ListTool<E> tool = new ListTool<E>(new ArrayList<>(list));
		return tool;
	}

  public final ListTool<E> without(final List<@NonNull E> community) {
		list.removeAll(community);
		return this;
	}

  public final List<@NonNull E> toList() {
		return list;
	}

}
