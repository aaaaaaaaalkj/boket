package managementPayments;

public class StateInfo {
	public boolean all_in = false;
	public boolean raised = false;

	public StateInfo(boolean all_in, boolean raised) {
		this.all_in = all_in;
		this.raised = raised;
	}

	public StateInfo() {
		this.all_in = false;
		this.raised = false;
	}
}
