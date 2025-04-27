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
//import com.jsyn.JSyn;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundClassNew {
	
	 private Vector<CoordinateObject> CoordinateArray;
	 
	 private int LowerFrequencyLimit;
	 private int UpperFrequencyLimit;
	 
	 private int frequency = 44100; //44100 sample points per 1 second
	 
	 private SourceDataLine sdl;
	 
	// private SourceDataLine sdl2;
	 
	 private int DurationPerX;
	 
	 private List<byte[]> bufList;
	 
	 private int amountOfEntriesForBufList;
	 
	 private int muteFrequency;
	 
	 private FloatControl gainControl;
	 
	// private List<byte[]> bufList2;
	 
	 public SoundClassNew () {
		 
		 LowerFrequencyLimit = 200;
		 UpperFrequencyLimit = 2000;
		 
		 DurationPerX = 20;
		 
		 muteFrequency = 0;	 
		 
	 }
	
	 public void DrawAsAudio (Vector<CoordinateObject> givenCoordinateArray, double CanvasHeight, double CellHeight, double CanvasWidth, double CellWidth) throws LineUnavailableException {
		 
		 CoordinateArray = givenCoordinateArray;
		 
		 amountOfEntriesForBufList = (int) (CanvasWidth / CellWidth); 
		 
		/*  MakeSoundSin(3, LowerFrequencyLimit);
		 MakeSoundSin(3, UpperFrequencyLimit/3);
		 MakeSoundSin(3, UpperFrequencyLimit);*/
		 
		 AudioFormat af = new AudioFormat((float) frequency, 16, 1, true, false);
		 startDataLine(af);
		 
		 resetSampleArray();
		 
		 gainControl = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
		 
		 int amountOfSampleByteArraysPerDuration = 0;
		 
		 for (int i = 0; i < DurationPerX * (float) 44100 / 1000; i++) {
			 amountOfSampleByteArraysPerDuration++;
		 }
		 
		 System.out.println("so many sample byte arrays per duration: " + amountOfSampleByteArraysPerDuration);
		 
		 for (int i = 0; i < CoordinateArray.size(); i++) {
			  double xCoordinate = CoordinateArray.get(i).getXCoordinate();
			  double yCoordinate = CoordinateArray.get(i).getYCoordinate();
			  
			  int cellCoordinate = (int) (xCoordinate / CellWidth);
			 if (CoordinateArray.get(i).getIsSet()) {
				
				 double frequenciesForEachY = (UpperFrequencyLimit - LowerFrequencyLimit) / (CanvasHeight / CellHeight); 
				 double frequencyForThisY; 
				 
				
				 
				 if (yCoordinate == 0) {
					 frequencyForThisY = LowerFrequencyLimit;
				 } else {
					 frequencyForThisY = (yCoordinate / CellHeight) * frequenciesForEachY; 
				 }
				 //System.out.println(cellCoordinate + " vs the new sample " + generateSample(DurationPerX, (int) frequencyForThisY));
				
				 addSampleToSampleArray(generateSample(DurationPerX, (int) frequencyForThisY), cellCoordinate, amountOfSampleByteArraysPerDuration);
				 
				 //bufList2 = addSampleToSampleArray(generateSample(DurationPerX, (int) frequencyForThisY - 50), bufList2);
			 } else {
				 //generateEmptySample(DurationPerX);
				 addSampleToSampleArray(generateEmptySample(DurationPerX), cellCoordinate, amountOfSampleByteArraysPerDuration);
			 }
		 }
		 
		
		/* generateSample(100, UpperFrequencyLimit/3);
		 generateSample(10, UpperFrequencyLimit); 
		 generateSample(10, LowerFrequencyLimit);
		 generateSample(100, UpperFrequencyLimit/3);
		 generateSample(100, UpperFrequencyLimit); 
		 generateSample(10, LowerFrequencyLimit);
		 generateSample(100, UpperFrequencyLimit/3);
		 generateSample(10, UpperFrequencyLimit);  */
		 
		 playCollectedSamples();
		  
		 resetDataLine();
	 }
	 
	
	 
	 private List<byte[]> generateSample (int durationMs, int Frequency) throws LineUnavailableException {
		 
		 List<byte[]> generatedSample = new ArrayList<>();
		 
		    //int durationMs = 5000;
		    //int numberOfTimesFullSinFuncPerSec = 441; //number of times in 1sec sin function repeats  
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
	 
	 private void addSampleToSampleArray (List<byte[]> sampleToBeAdded, int idPosition, int widthPerSegment) {
		 
		/* for (int i = 0; i < sampleToBeAdded.size(); i++) {
			 addToThis.add(sampleToBeAdded.get(i));
		 }
		 
		 
		 for (int i = idPosition * widthPerSegment; i < (idPosition + 1) * widthPerSegment; i++) {
		 
		 */ 
		 //System.out.println("x coordinate: " + idPosition + " target: " + (idPosition + 1) * widthPerSegment);
		 
		// double divider = 1 / Math.sqrt(2);
		 
			 for (int y = 0; y < sampleToBeAdded.size(); y++) {
				 
				 byte[] addedByte = new byte[2];
				 
				 int bufListPosition = ((idPosition * widthPerSegment) + y);
				 
				 for (int z = 0; z < sampleToBeAdded.get(y).length; z++) {
					 
					 addedByte[z] = (byte) (addBitWise (bufList.get(bufListPosition)[z], sampleToBeAdded.get(y)[z]));
					 
					 // addedByte[z] = (byte) ( divider * (addBitWise (bufList.get(bufListPosition)[z], sampleToBeAdded.get(y)[z])));
					 
					 if (bufList.get(bufListPosition)[z] != 0 && sampleToBeAdded.get(y)[z] != 0) {
						 
						 gainControl.setValue(-5.0f);
					 } else {
						 gainControl.setValue(0.0f);
					 }
					 
					 //something along the lines with this gain control for said issue:
					 
					 // hier irgendwo ein * 0.5 fuer das added byte, da es eventuell zu krass sein kann, da ja zwei channel zusammengefuegt werden
				 }
				 //System.out.println("new sample added at: " + bufListPosition);
				 bufList.set(bufListPosition, addedByte);
			 }
			
	 }
	 
	 
	 private List<byte[]> addSampleToSampleArrayOld (List<byte[]> sampleToBeAdded, List<byte[]> addToThis, int idPosition, int widthPerSegment) {
		 
		 	for (int i = 0; i < sampleToBeAdded.size(); i++) {
				 addToThis.add(sampleToBeAdded.get(i));
			 }
			 
		
			 return addToThis;
		 }
	 
	 private int addBitWise (int adder1, int adder2) {
		 
	        int carry;

	        while(adder2!=0)

	        {

	            carry = adder1 & adder2;

	            adder1 = adder1 ^ adder2;

	            adder2 = carry << 1;

	        }
		 
		 return adder1;
	 }
	 
	 private  List<byte[]> generateEmptySample (int durationMs) throws LineUnavailableException {
		 
		 List<byte[]> generatedSample = new ArrayList<>();
		 
		 for (int i = 0; i < durationMs * (float) 44100 / 1000; i++) { //1000 ms in 1 second
		      float numberOfSamplesToRepresentFullSin= (float) frequency / muteFrequency;
		      double angle = i / (numberOfSamplesToRepresentFullSin/ 2.0) * Math.PI;  // /divide with 2 since sin goes 0PI to 2PI
		      short a = (short) (Math.sin(angle) * 32767);  //32767 - max value for sample to take (-32767 to 32767)
		      byte[] buf = new byte[2];
		      buf[0] = (byte) (a & 0xFF); //write 8bits ________WWWWWWWW out of 16
		      buf[1] = (byte) (a >> 8); //write 8bits WWWWWWWW________ out of 16
		      generatedSample.add(buf);
		    }
		   return generatedSample;
	 }
	 
	 private void startDataLine (AudioFormat inputAF) throws LineUnavailableException {
		 	sdl = AudioSystem.getSourceDataLine(inputAF);
		    sdl.open();
		    sdl.start();
		    
		    //sdl2 = AudioSystem.getSourceDataLine(inputAF);
		   // sdl2.open();
		    //sdl2.start();
	 }
	 
	 private void resetDataLine () {
		 	sdl.drain();
		    sdl.stop();
		    
		    //sdl2.drain();
		    //sdl2.stop();
	 }
	 
	 private void playCollectedSamples () {
		 for (int i = 0; i < bufList.size(); i++) {
		        sdl.write(bufList.get(i), 0, 2);
		       // sdl2.write(bufList2.get(i), 0, 2);
		    	//System.out.println(bufList.get(i)[0] + bufList.get(i)[1]);
		        
		 }

	 }
	 
	 private void resetSampleArray () throws LineUnavailableException {
		 bufList = new ArrayList<>();
		 
		 for (int i = 0; i < amountOfEntriesForBufList; i++) {
			 bufList.addAll(generateEmptySample(DurationPerX));
		 }
		// bufList2 = new ArrayList<>();
	 }
	  
	 
	 
	
}
