package objects;

public class CoordinateObject {

	
	private double XCoordinate;
	private double YCoordinate; 
	
	private boolean isSet;
	
	private int id;
	
	public CoordinateObject (double InitialXCoordinate, double InitialYCoordinate, boolean InitialSet) {
		XCoordinate = InitialXCoordinate;
		YCoordinate = InitialYCoordinate;
		isSet = InitialSet;
		
	}
	
	public CoordinateObject (double InitialXCoordinate, double InitialYCoordinate, boolean InitialSet, int InitialId) {
		XCoordinate = InitialXCoordinate;
		YCoordinate = InitialYCoordinate;
		isSet = InitialSet;
		id = InitialId;
		
	}
	
	public void setXCoordinate (double newX) {
		XCoordinate = newX;
	}
	
	public void setYCoordinate (double newY) {
		YCoordinate = newY; 
	}
	
	public void setIsSet (boolean newSet) {
		isSet = newSet;
	}
	
	public void setID(int newID) {
		id = newID;
	}
	
	public double getXCoordinate () {
		return XCoordinate;
	}
	
	public double getYCoordinate () {
		return YCoordinate;
	}
	
	public boolean getIsSet () {
		return isSet;
	}
	
	public int getID () {
		return id;
	}
	
}
