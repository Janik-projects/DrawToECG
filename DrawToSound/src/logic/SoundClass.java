package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.devices.AudioDeviceFactory;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import objects.CoordinateObject;  
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class SoundClass {
	
	 private Vector<CoordinateObject> CoordinateArray;
	 
	 private int LowerFrequencyLimit;
	 private int UpperFrequencyLimit;
	 private int frequency;
	 private SourceDataLine sdl;
	 private int DurationPerX;
	 private List<byte[]> bufList;
	 private int amountOfEntriesForBufList;
	 private int muteFrequency;
	 private int ClearFrequency;
	 private boolean toggleContinousDraw;
	 private Timer timer;
	 
	 
	 
	 public SoundClass () {

		 frequency = 44100; //44100 sample points per 1 second
		 LowerFrequencyLimit = 200;
		 UpperFrequencyLimit = 2000;
		 DurationPerX = 25;		// alternatively 40
		 muteFrequency = UpperFrequencyLimit + 5000;	
		 ClearFrequency = 3;
		 toggleContinousDraw = false;
		 
		 timer = new Timer();
		 
	 }
	
	 
	 
	 private AudioFormat CreateNewAudioFormat () {
		 
		 AudioFormat af = new AudioFormat((float) frequency, 16, 1, true, false);
		 return af;
	 }
	 
	 
	 
	 public void DrawAsAudio (Vector<CoordinateObject> givenCoordinateArray, double CanvasHeight, double CellHeight, double CanvasWidth, double CellWidth) throws LineUnavailableException {
		 
		 startDataLine(CreateNewAudioFormat());
		 
		 resetSampleArray();
		 
		 DrawToSampleArray(givenCoordinateArray, CanvasHeight, CellHeight, CanvasWidth, CellWidth);
		 
		 playCollectedSamples();
		  
		 resetDataLine();
	 }
	 
	 
	 
	 public void DrawContinously (Vector<CoordinateObject> givenCoordinateArray, double CanvasHeight, double CellHeight, double CanvasWidth, double CellWidth) throws LineUnavailableException {
		
		startDataLine(CreateNewAudioFormat());

 		resetSampleArray();
		DrawToSampleArray(givenCoordinateArray, CanvasHeight, CellHeight, CanvasWidth, CellWidth);
		 
		 
		 if (toggleContinousDraw) {
			 toggleContinousDraw = false;
			 resetDataLine();
			 timer.cancel();
			 timer.purge();
		 } else {
			 toggleContinousDraw = true; 
			 
			 TimerTask task = new TimerTask()
			 {
			         public void run()
			         { 
			        	 try {
							startDataLine(CreateNewAudioFormat());
						} catch (LineUnavailableException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			        	 playCollectedSamples();
			        	 resetDataLine();
			         }

			 };
			 timer = new Timer();
			 timer.scheduleAtFixedRate(task, 0, 3000);
		 }
		
	 }
	 
	 
	 
	 private void DrawToSampleArray (Vector<CoordinateObject> givenCoordinateArray, double CanvasHeight, double CellHeight, double CanvasWidth, double CellWidth) throws LineUnavailableException {
		 
		 CoordinateArray = givenCoordinateArray;
		 
		 amountOfEntriesForBufList = (int) (CanvasWidth / CellWidth); 
		 
		 int amountOfSampleByteArraysPerDuration = 0;
		 
		 for (int i = 0; i < DurationPerX * (float) 44100 / 1000; i++) {
			 amountOfSampleByteArraysPerDuration++;
		 }
		 
		 System.out.println("so many sample byte arrays per duration: " + amountOfSampleByteArraysPerDuration);
		 
		 CoordinateArray = removeUnnecessaryVerticalSets(CoordinateArray);
		 
		 
		 double previousEmptyColumnX = -1;
		 
		 double frequenciesForEachY = (UpperFrequencyLimit - LowerFrequencyLimit) / (CanvasHeight / CellHeight); 
		 
		 for (int i = 0; i < CoordinateArray.size(); i++) {
			  double xCoordinate = CoordinateArray.get(i).getXCoordinate();
			  double yCoordinate = CoordinateArray.get(i).getYCoordinate();
			  
			  int cellCoordinate = (int) (xCoordinate / CellWidth);
			  if (CoordinateArray.get(i).getIsSet()) {
				 
				  int numberOfSetEelementsForThisX = getNumberOfSetElementsForThisX(CoordinateArray, xCoordinate);
				  
				  int DurationForThisX = DurationPerX;
				  
				  if (numberOfSetEelementsForThisX >= 1) {
					  DurationForThisX = (int) DurationPerX / numberOfSetEelementsForThisX;
				  }
				  
				  double frequencyForThisY; 
				 
				  if (yCoordinate == 0) {
					  frequencyForThisY = LowerFrequencyLimit;
				  } else {
					  frequencyForThisY = (yCoordinate / CellHeight) * frequenciesForEachY; 
				  }
				  bufList = addSampleToSampleArrayOld(generateSample(DurationForThisX, (int) frequencyForThisY), bufList, cellCoordinate, amountOfSampleByteArraysPerDuration);
				 
			  } else {
				 
				  boolean isEmptyColumn = true;
				 
				  if (xCoordinate > previousEmptyColumnX) {
					  for (int y = 0; y < CoordinateArray.size(); y++) {
						 
						  if (CoordinateArray.get(y).getIsSet() && CoordinateArray.get(y).getXCoordinate() == xCoordinate) {
							  isEmptyColumn = false;
						  }
						 
					  } 
				  }
				 
				 
				 if (isEmptyColumn && xCoordinate > previousEmptyColumnX) {
					 bufList = addSampleToSampleArrayOld(generateEmptySample(DurationPerX), bufList, cellCoordinate, amountOfSampleByteArraysPerDuration);
					 previousEmptyColumnX = xCoordinate;
					 System.out.println("New empty sample added for: x: " + xCoordinate);
				 }
			 }
		 }
	 }
	 
	 
	 
	 private int getNumberOfSetElementsForThisX(Vector<CoordinateObject> CoordinateArray, double xOfCurrentElement) {
		 
		 int setElementCounter = 0;
		 
		 for (int z = 0; z < CoordinateArray.size(); z++) {
			  if (CoordinateArray.get(z).getXCoordinate() == xOfCurrentElement) {
				  	if (CoordinateArray.get(z).getIsSet()) {
				  		setElementCounter++;
				  	}
			  } else if (CoordinateArray.get(z).getXCoordinate() > xOfCurrentElement) {
				  z = CoordinateArray.size();
			  }
		  }
		 
		 return setElementCounter;
	 }
	 
	 
	 
	 private Vector<CoordinateObject> removeUnnecessaryVerticalSets (Vector<CoordinateObject> toBeFiltered) {
		 
		 int FilterSetMinimum = 3;
		 
		 for (int i = 0; i < toBeFiltered.size(); i++) {
			 
			 double xCoordinate = toBeFiltered.get(i).getXCoordinate();
			 double yCoordinate = toBeFiltered.get(i).getYCoordinate();
			 
			 if (toBeFiltered.get(i).getIsSet()) {
				 
				 int IndexModifier = 0;
				 int VerticalSetCounter = 0;
				 
				 while (toBeFiltered.get(i + IndexModifier).getIsSet()) {
					 
					 if (xCoordinate == toBeFiltered.get(i + IndexModifier).getXCoordinate()) {
						 VerticalSetCounter++;
					 }
					 
					 IndexModifier++;
				 }
				 
				 if (VerticalSetCounter > FilterSetMinimum) {
					 
					 for (int z = (i + 1); z < (i + VerticalSetCounter - 1); z+=ClearFrequency) {
						 toBeFiltered.get(z).setIsSet(false);
					 }
				 }
			 }
		 }
		 
		 return toBeFiltered;
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
		 
		 bufList.addAll(sampleToBeAdded);
			
	 }
	 
	 
	 
	 private List<byte[]> addSampleToSampleArrayOld (List<byte[]> sampleToBeAdded, List<byte[]> addToThis, int idPosition, int widthPerSegment) {
		 
		 	for (int i = 0; i < sampleToBeAdded.size(); i++) {
				 addToThis.add(sampleToBeAdded.get(i));
			 }
			 
		
			 return addToThis;
		 }

	 
	 
	 private  List<byte[]> generateEmptySample (int durationMs) throws LineUnavailableException {
		 
		 List<byte[]> generatedSample = generateSample(durationMs, muteFrequency);
		 
		 return generatedSample;
	 }
	 
	 
	 
	 private void startDataLine (AudioFormat inputAF) throws LineUnavailableException {
		 	sdl = AudioSystem.getSourceDataLine(inputAF);
		    sdl.open();
		    sdl.start();
		    
	 }
	 
	 
	 
	 private void resetDataLine () {
		 	sdl.drain();
		    sdl.stop();
		    
	 }
	 
	 
	 
	 private void playCollectedSamples () {
		 for (int i = 0; i < bufList.size(); i++) {
		        sdl.write(bufList.get(i), 0, 2);
		        
		 }

	 }
	 
	 private void resetSampleArray () throws LineUnavailableException {
		 bufList = new ArrayList<>();
		 
		 for (int i = 0; i < amountOfEntriesForBufList; i++) {
			 bufList.addAll(generateEmptySample(DurationPerX));
		 }
	 }
	  
	 
	 
	
}
