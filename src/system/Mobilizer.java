package system;

public interface Mobilizer {

	public boolean mobilize(int incidentInfoId, int ambulanceId);

	public boolean demobilize(int incidentInfoId, int ambulanceId);

}