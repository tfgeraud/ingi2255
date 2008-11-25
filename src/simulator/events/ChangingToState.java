package simulator.events;

import events.Event;

public class ChangingToState extends Event {
	
	private String stateName;
	
	public ChangingToState(String simObject, String stateName) {
		super(simObject);
		this.stateName = stateName;
	}
	
	public String getStateName() {
		return stateName;
	}
}
