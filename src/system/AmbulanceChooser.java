package system;

import java.util.LinkedList;

/**
 * This interface
 * @author sibork
 *
 */
public interface AmbulanceChooser {

	public int chooseBestAmbulance(int incidentInfoId,LinkedList exclusionSet);

}