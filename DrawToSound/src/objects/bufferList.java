package objects;

import java.util.ArrayList;
import java.util.List;

public class bufferList {

	private List<byte[]> bufList;
	
	public void bufferList () {
		
		bufList = new ArrayList<>();
		
	}
	
	
	public void initializeBufferList () {
		
		bufList = new ArrayList<>();
	}
	
	public void addElement (byte[] newBufferByte) {
		
		bufList.add(newBufferByte);
	}
	
	public void addNewBuffListValues (List<byte[]> newBufValues) {
		
		//bufList = newBufValues;
		
		 for (int i = 0; i < newBufValues.size(); i++) {
			 bufList.add(newBufValues.get(i));
		 }
		 
	}
	
	public List<byte[]> getBufList () {
		
		return bufList;
	}
}
