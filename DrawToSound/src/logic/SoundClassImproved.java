package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.jsyn.JSyn;
//import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceFactory;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import objects.CoordinateObject;  
import objects.bufferList;
//import com.jsyn.JSyn;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundClassImproved {
	
	 private Vector<CoordinateObject> CoordinateArray;
	 
	 private int LowerFrequencyLimit;
	 private int UpperFrequencyLimit;
	 
	 private  AudioFormat af;
	 
	 private int frequency = 44100; //44100 sample points per 1 second
	 
	 private SourceDataLine sdl;
	 
	// private SourceDataLine sdl2;
	 
	 private int DurationPerX;
	 
	 private int muteFrequency;
	 
	 private List<byte[]> bufList;
	 
	 private List<bufferList> listOfAllBuffers;
	 
	 private List<SourceDataLine> allOutputLines;
	// private List<byte[]> bufList2;
	 
	 public SoundClassImproved () throws LineUnavailableException {
		 
		 LowerFrequencyLimit = 200;
		 UpperFrequencyLimit = 2000;
		 
		 DurationPerX = 20;
		 
		// muteFrequency = UpperFrequencyLimit + 200;
		 
		 muteFrequency = 0;
		 
		 listOfAllBuffers = new ArrayList<>();
		 allOutputLines = new ArrayList<>();
		 
		 af = new AudioFormat((float) frequency, 16, 1, true, false);
		 
		 sdl = AudioSystem.getSourceDataLine(af);
	 }
	
	 public void DrawAsAudio (Vector<CoordinateObject> givenCoordinateArray, double CanvasHeight, double CellHeight, double CanvasWidth, double CellWidth) throws LineUnavailableException {
		 
		 CoordinateArray = givenCoordinateArray;
		 
		/*  MakeSoundSin(3, LowerFrequencyLimit);
		 MakeSoundSin(3, UpperFrequencyLimit/3);
		 MakeSoundSin(3, UpperFrequencyLimit);*/
		 
		// startDataLine(af);
		 
		 int amountOfLines = (int) (CanvasHeight/CellHeight);
		 int widthOfEachLine = (int) (CanvasWidth/CellWidth);
		 
		 createBufferLists(amountOfLines);
		 createSourceDataLines(amountOfLines);
		 
		 startDataLines();
		 
		 resetSampleArrays();
		 
		 for (int i = 0; i < CoordinateArray.size(); i++) {
			  double xCoordinate = CoordinateArray.get(i).getXCoordinate();
			  double yCoordinate = CoordinateArray.get(i).getYCoordinate();
			  
			  int bufferArrayId;
			  
			  if (CoordinateArray.get(i).getIsSet()) {
				
				  double frequenciesForEachY = (UpperFrequencyLimit - LowerFrequencyLimit) / (CanvasHeight / CellHeight); 
				  double frequencyForThisY; 
				 
				  System.out.println("this one is set: " + xCoordinate + " " + yCoordinate);
				 
				  if (yCoordinate == 0) {
					  frequencyForThisY = LowerFrequencyLimit;
					  bufferArrayId = 0;
				  } else {
					  frequencyForThisY = (yCoordinate / CellHeight) * frequenciesForEachY; 
					  bufferArrayId = (int) (yCoordinate / CellHeight) - 1;
				  }
				 //System.out.println(yCoordinate + " vs the canvas height " + CanvasHeight);
				
				  listOfAllBuffers.get(bufferArrayId).addNewBuffListValues(generateSample(DurationPerX, (int) frequencyForThisY));
				  
				  System.out.println("at this buffer Array ID: " + bufferArrayId);
				 
				 //bufList = addSampleToSampleArray(generateSample(DurationPerX, (int) frequencyForThisY), bufList);
				 //bufList2 = addSampleToSampleArray(generateSample(DurationPerX, (int) frequencyForThisY - 50), bufList2);
			  } else {

				  if (yCoordinate == 0) {
					  bufferArrayId = 0;
				  } else {
					  bufferArrayId = (int) (yCoordinate / CellHeight) - 1;
				  }
				  
				  listOfAllBuffers.get(bufferArrayId).addNewBuffListValues(generateEmptySample(DurationPerX));
			 }
			  
			  //System.out.println(yCoordinate);
		 }
		
		 
		 playCollectedSamples();
		 
		 resetDataLines();
	 }
	 
	 private List<byte[]> generateEmptySample (int durationMs) {
		 return generateSample (durationMs, muteFrequency);
	 }
	
	 
	 private List<byte[]> generateSample (int durationMs, int Frequency) {
		 
		 List<byte[]> generatedSample = new ArrayList<>();
		 
		    for (int i = 0; i < durationMs * (float) 44100 / 1000; i++) { //1000 ms in 1 second
		      float numberOfSamplesToRepresentFullSin= (float) frequency / Frequency;
		      double angle = i / (numberOfSamplesToRepresentFullSin/ 2.0) * Math.PI;  // /divide with 2 since sin goes 0PI to 2PI
		      short a = (short) (Math.sin(angle) * 32767);  //32767 - max value for sample to take (-32767 to 32767)
		      byte[] buf = new byte[2];
		      buf[0] = (byte) (a & 0xFF); //write 8bits ________WWWWWWWW out of 16
		      buf[1] = (byte) (a >> 8); //write 8bits WWWWWWWW________ out of 16
		      generatedSample.add(buf);
		    }
		   return generatedSample;
	 }
	 
	 private List<byte[]> addSampleToSampleArray (List<byte[]> sampleToBeAdded, List<byte[]> addToThis) {
		 
		 for (int i = 0; i < sampleToBeAdded.size(); i++) {
			 addToThis.add(sampleToBeAdded.get(i));
		 }
		 
		 return addToThis;
	 }
	 
	 private void startDataLines () throws LineUnavailableException {
		   
		/* for (int i = 0; i < allOutputLines.size(); i++) {
			    
			allOutputLines.get(i).open();
			allOutputLines.get(i).start();
		} */
		 
		 sdl.open();
		 sdl.start();
	 }
	 
	 private void resetDataLines () throws LineUnavailableException {
		  /*  for (int i = 0; i < allOutputLines.size(); i++) {
			    
				allOutputLines.get(i).drain();
				allOutputLines.get(i).stop();
			} */
		 
		 sdl.drain();
		 sdl.stop();
	 }
	 
	 private void playCollectedSamples () {
		 
		/* for (int i = 0; i < allOutputLines.size(); i++) {
			 //byte[] toBeWritten; 
			 
			 for (int y = 0; y < listOfAllBuffers.get(i).getBufList().size(); y++) {
				 allOutputLines.get(i).write(listOfAllBuffers.get(i).getBufList().get(y), 0, 2);
			 }
		 } */
		 
		 List<byte[]> combinedBufList = new ArrayList<>();
		 
		 byte[] valueHolder = new byte[2];
		 
	/*	 for (int i = 0; i < listOfAllBuffers.size(); i++) {
			 
			 for (int y = 0; y < listOfAllBuffers.get(i).getBufList().size(); y++) {
			 	for (int z = 0; z < valueHolder.length; z++) {
			 		valueHolder[z] = add(listOfAllBuffers.get(i).getBufList().get(y)[z]);
			 	}
			 	
			 	combinedBufList.add(valueHolder);
			 }
		 }  */
		 
		 int bufListSize = 60; // ist die canvas width / cellwidth weil es ja so viele buffer gibt pro liste (also pro y line) wie zellen in der breite 
		 
		 for (int i = 0; i < bufListSize; i++) {
			 
			 for (int y = 0; y < listOfAllBuffers.size(); y++) {
				 for (int z = 0; z < valueHolder.length; z++) {
				 		valueHolder[z] = addBitwise(listOfAllBuffers.get(y).getBufList().get(i)[z], valueHolder[z]);
				 }
				 
			 }
			 combinedBufList.add(valueHolder);
			 valueHolder = new byte[2];
		 }
		 
		 
		 for (int i = 0; i < combinedBufList.size(); i++) {
			 sdl.write(combinedBufList.get(i), 0, 2);
			 System.out.println("this one has been written! " + combinedBufList.get(i));
		 }
		 
		 System.out.println("in total so many have been written: " + combinedBufList.size());

	 }
	 
	 private byte addBitwise (byte adder1, byte adder2) {
		 byte sum = 0;
		 
	        int carry;

	        while(adder2!=0)

	        {

	            carry = adder1 & adder2;

	            adder1 = (byte) (adder1 ^ adder2);

	            adder2 = (byte) (carry << 1);

	        }
		 
		 return adder1;
	 }
	 
	 private void createBufferLists (int amountOfBufferLists) {
		 
		 listOfAllBuffers = new ArrayList<>();
		 
		 for (int i = 0; i < amountOfBufferLists; i++) {
			 listOfAllBuffers.add(new bufferList());
		 }
	 }
	 
	 private void resetSampleArrays () {
		 //bufList = new ArrayList<>();
		// bufList2 = new ArrayList<>();
		 for (int i = 0; i < listOfAllBuffers.size(); i++) {
			 listOfAllBuffers.get(i).initializeBufferList();
		 }
	 }
	 
	 private void createSourceDataLines (int amountOfSourceDataLines) throws LineUnavailableException {
		 
		 allOutputLines = new ArrayList<>();
		 
		 for (int i = 0; i < amountOfSourceDataLines; i++) {
			 allOutputLines.add(AudioSystem.getSourceDataLine(af));
		 }
	 }
	  
	 
	 
	
}
