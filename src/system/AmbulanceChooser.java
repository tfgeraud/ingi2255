package system;

import java.util.LinkedList;

public interface AmbulanceChooser {

	public int chooseBestAmbulance(int incidentInfoId,
			LinkedList exclusionSet);

}