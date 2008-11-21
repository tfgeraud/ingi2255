package system;

import java.util.LinkedList;

import system.event.EventImpl;


public class IncidentProcessorImpl implements IncidentProcessor {

	private AmbulanceChooser chooser;
	private Mobilizer mobilizer;
	private Resolver resolver;
	
	/* (non-Javadoc)
	 * @see system.IncidentProcessor#main(int)
	 */
	public void main(int incidentInfoId) {
		
		int ambulanceId = -1;
		LinkedList exclusionSet = new LinkedList();
		boolean e = false;
		
		do {
			do {
				do {
					ambulanceId = chooser.chooseBestAmbulance(incidentInfoId, exclusionSet);
				} while (ambulanceId == -1);
			
				exclusionSet.add(new Integer(ambulanceId));
				
			} while (mobilizer.mobilize(incidentInfoId, ambulanceId) == false);
			
			e = resolver.closeIncident(incidentInfoId,ambulanceId);
			
			exclusionSet = new LinkedList();
			exclusionSet.add(new Integer(ambulanceId));
			mobilizer.demobilize(incidentInfoId,ambulanceId);

		} while (e == false);
			
	}
}
