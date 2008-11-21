package system;

public interface Incident {

	public int getAmbulanceKindNeeded(int incidentInfoId);

	public Coord getCoord(int incidentInfoId);

	public MobOrder getMobOrder(int incidentInfoId);

	public DemobOrder getDemobOrder(int incidentInfoId);
	
	public int getAge(int incidentInfoId);
	
	public boolean getPregnant(int incidentInfoId);
	
	public String getLocalisation(int incidentInfoId);
	
	public Coord getPosition(int incidentInfoId);
	
	public String getDescription(int incidentInfoId);
	
	public int getChosenAmbulance(int incidentInfoId);
	
	public int getMobilizedAmbulance(int incidentInfoId);
	
	public void setChosenAmbulance(int incidentInfoId);
	
	public void setMobilizedAmbulance(int incidentInfoId);
	
	public void setAsResolved(int incidentInfoId);
}