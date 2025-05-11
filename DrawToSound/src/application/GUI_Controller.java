package application;

import java.util.EventListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.CoordinateObject;    
import logic.SoundClass;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;

import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;

public class GUI_Controller {

	
	private static BufferedImage PixelZero;
	private static BufferedImage PixelOne;
	private static BufferedImage PixelTwo;
	private static BufferedImage PixelThree;
	private static BufferedImage PixelFour;
	private static BufferedImage PixelFive;
	private static BufferedImage PixelSix;
	private static BufferedImage PixelSeven;
	private static BufferedImage PixelEight;
	private static BufferedImage PixelNine;
	private static BufferedImage PixelColon;
	
	private static BufferedImage PixelZeroBig;
	private static BufferedImage PixelOneBig;
	private static BufferedImage PixelTwoBig;
	private static BufferedImage PixelThreeBig;
	private static BufferedImage PixelFourBig;
	private static BufferedImage PixelFiveBig;
	private static BufferedImage PixelSixBig;
	private static BufferedImage PixelSevenBig;
	private static BufferedImage PixelEightBig;
	private static BufferedImage PixelNineBig;
	private static BufferedImage PixelColonBig;
   
    private Stage mainStage;
    private SoundClass SoundProcessor;
    
    
    private int CellWidth;
    private int CellHeight;
    
    private Timer clockTimer;
    
    private LocalTime timeObject;
    
    private int standardWidthForCharacterInCells;
    
    private Vector<CoordinateObject> CoordinateArray;
    
    public void initialize() throws LineUnavailableException, IOException {
    	
    	initializeFont();
    	
    	CellWidth = 10;
    	CellHeight = 10;
    	
    	standardWidthForCharacterInCells = PixelZero.getWidth();
    	
    	CoordinateArray = new Vector<CoordinateObject>();
    	fillCoordinateArray();
    	
    	DrawingCanvas.getGraphicsContext2D().setFill(Color.WHITE);
    	DrawingCanvas.getGraphicsContext2D().fillRect(0, 0, DrawingCanvas.getWidth(), DrawingCanvas.getHeight());
    	DrawingCanvas.getGraphicsContext2D().setFill(Color.BLACK);
    	
    	SoundProcessor = new SoundClass();
    	
    }
    
    
    
    private void initializeFont () throws IOException {
    	
    	PixelZero = ImageIO
				.read(new File("src/PixelFont/0.png"));
    	
    	PixelZeroBig = ImageIO
				.read(new File("src/PixelFont/0Big.png"));
    	
    	PixelOne = ImageIO
				.read(new File("src/PixelFont/1.png"));
    	
    	PixelOneBig = ImageIO
				.read(new File("src/PixelFont/1Big.png"));
    	
    	PixelTwo = ImageIO
				.read(new File("src/PixelFont/2.png"));
    	
    	PixelTwoBig = ImageIO
				.read(new File("src/PixelFont/2Big.png"));
    	
    	PixelThree = ImageIO
				.read(new File("src/PixelFont/3.png"));
    	
    	PixelThreeBig = ImageIO
				.read(new File("src/PixelFont/3Big.png"));
    	
    	PixelFour = ImageIO
				.read(new File("src/PixelFont/4.png"));
    	
    	PixelFourBig = ImageIO
				.read(new File("src/PixelFont/4Big.png"));
    	
    	PixelFive = ImageIO
				.read(new File("src/PixelFont/5.png"));
    	
    	PixelFiveBig = ImageIO
				.read(new File("src/PixelFont/5Big.png"));
    	
    	PixelSix = ImageIO
				.read(new File("src/PixelFont/6.png"));
    	
    	PixelSixBig = ImageIO
				.read(new File("src/PixelFont/6Big.png"));
    	
    	PixelSeven = ImageIO
				.read(new File("src/PixelFont/7.png"));
    	
    	PixelSevenBig = ImageIO
				.read(new File("src/PixelFont/7Big.png"));
    	
    	PixelEight = ImageIO
				.read(new File("src/PixelFont/8.png"));
    	
    	PixelEightBig = ImageIO
				.read(new File("src/PixelFont/8Big.png"));
    	
    	PixelNine = ImageIO
				.read(new File("src/PixelFont/9.png"));
    	
    	PixelNineBig = ImageIO
				.read(new File("src/PixelFont/9Big.png"));
    	
    	PixelColon = ImageIO
				.read(new File("src/PixelFont/COLON.png"));
    	
    	PixelColonBig = ImageIO
				.read(new File("src/PixelFont/COLONBig.png"));
    }
    
    
    
    public void setStage (Stage inputStage) {
    	mainStage = inputStage;
    }
    
    
    
    @FXML
    private Button ClearAllBtn;

    @FXML
    private Button ClearCanvasBtn;

    @FXML
    private Button DrawFromDataBtn;
    
    @FXML
    private Canvas DrawingCanvas;

    @FXML
    private TextField FrequencyInput;
    
    @FXML
    private Button SetDrawingBtn;

    @FXML
    private BorderPane fxBPane;

    @FXML
    private Button setFrequencyBTN;
    
    @FXML
    private ToggleButton ToggleDrawBtn;
    
    @FXML
    private Button setClockBtn;
    
    
    
    private void fillCoordinateArray () {
    	
    	double CanvasHeight = DrawingCanvas.getHeight();
    	double CanvasWidth = DrawingCanvas.getWidth();
    	
    	int ID = 0;
    	
    	 for (int x = 0; x < CanvasWidth; x += CellWidth) {
    		 for (int y = 0; y < CanvasHeight; y += CellHeight){
    			CoordinateArray.add(new CoordinateObject(x, y, false, ID));
    			ID++;
    		}
    	} 
    }
 
    
 
    private CoordinateObject getCoordinateObjectByXAndY (double searchX, double searchY) {
		
    	CoordinateObject returnCoordinateObject = new CoordinateObject(searchX, searchY, false);
    	
    	for (int i = 0; i < CoordinateArray.size(); i++) {
    		if (CoordinateArray.get(i).getXCoordinate() == searchX && CoordinateArray.get(i).getYCoordinate() == searchY) {
    			returnCoordinateObject = CoordinateArray.get(i);
    		}
    	}
    	
    	return returnCoordinateObject;
    
    }
    
    
    
    private int getArrayPositionByCoordinateObjectID (int CoordinateObjectId) {
    	
    	int ArrayPosition = 0;
    	
    	for (int i = 0; i < CoordinateArray.size(); i++) {
    		if (CoordinateArray.get(i).getID() == CoordinateObjectId) {
    			ArrayPosition = i;
    		}
    	}
    	
    	return ArrayPosition;
    }
    
    
    
    private void ClearCanvas () {
    	GraphicsContext CanvasGraphics = DrawingCanvas.getGraphicsContext2D();
    	CanvasGraphics.clearRect(0, 0, DrawingCanvas.getWidth(), DrawingCanvas.getHeight());
    	
    	DrawingCanvas.getGraphicsContext2D().setFill(Color.WHITE);
    	DrawingCanvas.getGraphicsContext2D().fillRect(0, 0, DrawingCanvas.getWidth(), DrawingCanvas.getHeight());
    	DrawingCanvas.getGraphicsContext2D().setFill(Color.BLACK);
    }
    
    
    
    private void ClearData () {
    	for (int i = 0; i < CoordinateArray.size(); i++) {
    		CoordinateArray.get(i).setIsSet(false);
    	}
    }
    
    
    
    private void DrawFromData () {
    	GraphicsContext CanvasGraphics = DrawingCanvas.getGraphicsContext2D();
    	for (int i = 0; i < CoordinateArray.size(); i++) {
    		if (CoordinateArray.get(i).getIsSet()) {
    			CanvasGraphics.fillRect(CoordinateArray.get(i).getXCoordinate(), CoordinateArray.get(i).getYCoordinate(), CellWidth, CellHeight);
    		}
    	}
    }
    
    
    
    @FXML
    void ClearAllPressed(ActionEvent event) {
    	
    	ClearCanvas();
    	ClearData();
    }

    
    
    @FXML
    void ClearCanvasPressed(ActionEvent event) {
    	
    	ClearCanvas();
    }

    
    
    @FXML
    void DrawFromDataPressed(ActionEvent event) {
    	
    	DrawFromData();
    	
    }
    
    
    
    @FXML
    void DrawingCanvasDragEntered(DragEvent event) {
    	double DragX = event.getSceneX();
    	double DragY = event.getSceneY();
    	System.out.println("Here's the Drag: X Cooridnate: " + DragX + " Y Coordinate: " + DragY);
    }

    
    
    @FXML
    void DrawingCanvasMouseClicked(MouseEvent event) {
    	double DragX = event.getSceneX();
    	double DragY = event.getSceneY();
    	System.out.println("Here's the Click: X Cooridnate: " + DragX + " Y Coordinate: " + DragY);

    	double CanvasOffsetX = DrawingCanvas.getLayoutX();
    	double CanvasOffsetY = DrawingCanvas.getLayoutY();
    	
    	GraphicsContext CanvasGraphics = DrawingCanvas.getGraphicsContext2D();
    	
    	
    	double ActualCanvasX = DragX-CanvasOffsetX;
    	double ActualCanvasY = DragY-CanvasOffsetY;
    	
    	double PositionInCellX = ActualCanvasX % CellWidth;
    	double PositionInCellY = ActualCanvasY % CellHeight;
    	
    	double XAdjustedToCell = 0;
    	double YAdjustedToCell = 0;
    	
    	if (PositionInCellX == 0) {
    		XAdjustedToCell = ActualCanvasX;
    	} else if (PositionInCellX <= CellWidth / 2) {
    		XAdjustedToCell = ActualCanvasX - PositionInCellX;
    	} else if (PositionInCellX > CellWidth / 2) {
    		XAdjustedToCell = ActualCanvasX + (CellWidth - PositionInCellX);
    	}
    	
    	if (PositionInCellY == 0) {
    		YAdjustedToCell = ActualCanvasY;
    	} else if (PositionInCellY <= CellHeight / 2) {
    		YAdjustedToCell = ActualCanvasY - PositionInCellY;
    	} else if (PositionInCellY > CellHeight / 2) {
    		YAdjustedToCell = ActualCanvasY + (CellHeight - PositionInCellY);
    	} 
    	
    	CanvasGraphics.fillRect(XAdjustedToCell, YAdjustedToCell, CellHeight, CellWidth);
    	
    	CoordinateObject CurrentCoordinateObject = getCoordinateObjectByXAndY(XAdjustedToCell, YAdjustedToCell);
    	int CurrentCoordinateObjectID = CurrentCoordinateObject.getID();
    	CurrentCoordinateObject.setIsSet(true);
    	int positionInArrayOfCurrentCoordinateObject = getArrayPositionByCoordinateObjectID(CurrentCoordinateObjectID);
    	System.out.println("The Coordinate Object with the ID: " + CurrentCoordinateObjectID + " has been set to " + CoordinateArray.get(positionInArrayOfCurrentCoordinateObject).getIsSet());
    }

    
    
    @FXML
    void DrawingCanvasMouseDragged(MouseEvent event) {
    	double DragX = event.getSceneX();
    	double DragY = event.getSceneY();
    	System.out.println("Here's the Drag: X Cooridnate: " + DragX + " Y Coordinate: " + DragY);
    	
    	double CanvasOffsetX = DrawingCanvas.getLayoutX();
    	double CanvasOffsetY = DrawingCanvas.getLayoutY();
    	
    	GraphicsContext CanvasGraphics = DrawingCanvas.getGraphicsContext2D();
    	
    	double ActualCanvasX = DragX-CanvasOffsetX;
    	double ActualCanvasY = DragY-CanvasOffsetY;
    	
    	double PositionInCellX = ActualCanvasX % CellWidth;
    	double PositionInCellY = ActualCanvasY % CellHeight;
    	
    	double XAdjustedToCell = 0;
    	double YAdjustedToCell = 0;
    	
    	if (PositionInCellX == 0) {
    		XAdjustedToCell = ActualCanvasX;
    	} else if (PositionInCellX <= CellWidth / 2) {
    		XAdjustedToCell = ActualCanvasX - PositionInCellX;
    	} else if (PositionInCellX > CellWidth / 2) {
    		XAdjustedToCell = ActualCanvasX + (CellWidth - PositionInCellX);
    	}
    	
    	if (PositionInCellY == 0) {
    		YAdjustedToCell = ActualCanvasY;
    	} else if (PositionInCellY <= CellHeight / 2) {
    		YAdjustedToCell = ActualCanvasY - PositionInCellY;
    	} else if (PositionInCellY > CellHeight / 2) {
    		YAdjustedToCell = ActualCanvasY + (CellHeight - PositionInCellY);
    	} 
    	
    	CanvasGraphics.fillRect(XAdjustedToCell, YAdjustedToCell, CellHeight, CellWidth);
    	
    	CoordinateObject CurrentCoordinateObject = getCoordinateObjectByXAndY(XAdjustedToCell, YAdjustedToCell);
    	int CurrentCoordinateObjectID = CurrentCoordinateObject.getID();
    	CurrentCoordinateObject.setIsSet(true);
    	int positionInArrayOfCurrentCoordinateObject = getArrayPositionByCoordinateObjectID(CurrentCoordinateObjectID);
    	System.out.println("The Coordinate Object with the ID: " + CurrentCoordinateObjectID + " has been set to " + CoordinateArray.get(positionInArrayOfCurrentCoordinateObject).getIsSet());
    }

    
    
    @FXML
    void DrawingCanvasMouseMoved(MouseEvent event) {
    	
    }
    
    
    
    @FXML
    void SetClockPressed(ActionEvent event) throws LineUnavailableException {
    	
    	if (SoundProcessor.getToggleClockMode() == false) { 
    		setClockBtn.setText("stop Clock Mode");
    		ToggleDrawBtn.setDisable(true);
    		SetDrawingBtn.setDisable(true);
    		
    		TimerTask task = new TimerTask()
			{
			        public void run()
			        { 
						HandleClockGraphics();
			        }

			};
			clockTimer = new Timer();
			clockTimer.scheduleAtFixedRate(task, 0, 1000);
			 
    		SoundProcessor.setToggleClockMode(true);
    		
    	} else {
    		setClockBtn.setText("start Clock Mode");
    		ToggleDrawBtn.setDisable(false);
    		SetDrawingBtn.setDisable(false);
    		
    		clockTimer.cancel();
			clockTimer.purge();
    		
    		SoundProcessor.setToggleClockMode(false);
    	}
    	
    	SoundProcessor.HandleClockMode(CoordinateArray, DrawingCanvas.getHeight(), CellHeight, DrawingCanvas.getWidth(), CellWidth);
    }
    
    
    
    private void HandleClockGraphics () {
    	
    	ClearCanvas();
    	ClearData();
    	
    	double startingX = 20;
    	//double startingY = (DrawingCanvas.getHeight()/2) - ((PixelZero.getHeight() * CellHeight)/2); 
    	double startingY = DrawingCanvas.getHeight()/2;
    	
    	timeObject = LocalTime.now();
    	String CleanTimeString = CleanTimeString(timeObject.toString());
    	
    	System.out.println(CleanTimeString);
    	
    	String[] SplittedTimeString = CleanTimeString.split("\\:");
    	
    	System.out.println(SplittedTimeString[0] + " " + SplittedTimeString[1] + " " + SplittedTimeString[2]);
    	

    	GraphicsContext CanvasGraphics = DrawingCanvas.getGraphicsContext2D();
    	
    	for (int i = 0; i < SplittedTimeString.length; i++) {
    		
    		for (int z = 0; z < SplittedTimeString[i].length(); z++) {
    			DrawNumber(Integer.parseInt(SplittedTimeString[i].split("")[z]), startingX, startingY, CanvasGraphics);
    			startingX += (standardWidthForCharacterInCells * CellWidth);
    		}
    		
    		if (i < SplittedTimeString.length-1) {
    			drawColon(startingX, startingY, CanvasGraphics);
    			startingX += (standardWidthForCharacterInCells * CellWidth);
    		}
    	}
    }
   
    
    
    private String CleanTimeString (String TimeStringWithNanos) {
    	
    	String cleanedTime = TimeStringWithNanos.split("\\.")[0];
    	return cleanedTime;
    }
    
    
    
    private void DrawNumber (int numberToBeDrawn, double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	switch (numberToBeDrawn) {
    		
    	case (1) : {
    		
    		drawNumberOne(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	case (2) : {
    		
    		drawNumberTwo(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	case (3) : {
    		
    		drawNumberThree(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	case (4) : {
    		
    		drawNumberFour(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	case (5) : {
    		
    		drawNumberFive(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	case (6) : {
    		
    		drawNumberSix(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	case (7) : {
    		
    		drawNumberSeven(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	case (8) : {
    		
    		drawNumberEight(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	case (9) : {
    		
    		drawNumberNine(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	case (0) : {
    		
    		drawNumberZero(xCoordinate, yCoordinate, drawingCanvas);
    		break;
    	}
    	
    	}
    }
    
    
    
    private void drawExternalImageIntoCoordinateArray (BufferedImage imageToDraw, double xCoordinate, double yCoordinate) {
    	
    	xCoordinate = xCoordinate + ((imageToDraw.getWidth() - standardWidthForCharacterInCells) * CellWidth);
    	
    	for (int x = 0; x < imageToDraw.getWidth(); x++) {
    		for (int y = 0; y < imageToDraw.getHeight(); y++) {
    			if (isPixelOfImageAtCoordinatesSet(imageToDraw, x, y)) {
    				double calculatedX = (xCoordinate + (x * CellWidth));
    				double calculatedY = (yCoordinate + (y * CellHeight));
    				
    				getCoordinateObjectByXAndY(calculatedX, calculatedY).setIsSet(true);
    				
    				System.out.println("Coordinates Set in array at: " + calculatedX + " " + calculatedY);
    			}
    		}
    	}
    }
    
    
    
    private void drawExternalImageIntoCoordinateArray (BufferedImage imageToDraw, double xCoordinate, double yCoordinate, int scale) {
    	
    	for (int x = 0; x < imageToDraw.getWidth(); x++) {
    		for (int y = 0; y < imageToDraw.getHeight(); y++) {
    			if (isPixelOfImageAtCoordinatesSet(imageToDraw, x, y)) {
    				for (int i = 1; i <= scale; i++) {
    					double calculatedX = (xCoordinate + (x * CellWidth));
    					double calculatedY = (yCoordinate + (y * CellHeight));
    				
    					getCoordinateObjectByXAndY(calculatedX, calculatedY).setIsSet(true);
    				
    					System.out.println("Coordinates Set in array at: " + calculatedX + " " + calculatedY);
    				}
    			}
    		}
    	}
    }
    
    
    
    private boolean isPixelOfImageAtCoordinatesSet (BufferedImage imageToCheck, int xImageCoordinate, int yImageCoordinate) {
    	
    	boolean isSet = false;
    	
    	if (imageToCheck.getRGB(xImageCoordinate, yImageCoordinate) == java.awt.Color.BLACK.getRGB()) {
    		isSet = true;
    	}
    	
    	return isSet;
    }
 
    
    
    private void drawNumberOne (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelOne, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelOneBig, xCoordinate, yCoordinate);
    	
    	System.out.print(1);
    }
    
    
    
    private void drawNumberTwo (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelTwo, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelTwoBig, xCoordinate, yCoordinate);
    	
    	System.out.print(2);
    }
    
    
    
    private void drawNumberThree (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelThree, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelThreeBig, xCoordinate, yCoordinate);
    	
    	System.out.print(3);
    }
    
    
    
    private void drawNumberFour (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelFour, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelFourBig, xCoordinate, yCoordinate);
    	
    	System.out.print(4);
    }
    
    
    
    private void drawNumberFive (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelFive, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelFiveBig, xCoordinate, yCoordinate);
    	
    	System.out.print(5);
    }
    
    
    
    private void drawNumberSix (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelSix, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelSixBig, xCoordinate, yCoordinate);
    	
    	System.out.print(6);
    }
    
    
    
    private void drawNumberSeven (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelSeven, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelSevenBig, xCoordinate, yCoordinate);
    	
    	System.out.print(7);
    }
    
    
    
    private void drawNumberEight (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelEight, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelEightBig, xCoordinate, yCoordinate);
    	
    	System.out.print(8);
    }
    
    
    
    private void drawNumberNine (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelNine, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelNineBig, xCoordinate, yCoordinate);
    	
    	System.out.print(9);
    }
    
    
    
    private void drawNumberZero (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelZero, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelZeroBig, xCoordinate, yCoordinate);
    	
    	System.out.print(0);
    }
    
    
    
    private void drawColon (double xCoordinate, double yCoordinate, GraphicsContext drawingCanvas) {
    	
    	drawingCanvas.drawImage(SwingFXUtils.toFXImage(PixelColon, null), xCoordinate, yCoordinate, PixelZero.getWidth() * CellWidth, PixelZero.getHeight() * CellHeight);
    	
    	drawExternalImageIntoCoordinateArray(PixelColonBig, xCoordinate, yCoordinate);
    	
    	System.out.print(":");
    }
    
    
    
    @FXML
    void SetDrawing(ActionEvent event) throws LineUnavailableException {
    	//SoundProcessor.DrawAsAudio(CoordinateArray, DrawingCanvas.getHeight(), CellHeight);
    	SoundProcessor.DrawAsAudio(CoordinateArray, DrawingCanvas.getHeight(), CellHeight, DrawingCanvas.getWidth(), CellWidth);
    }

    
    
    @FXML
    void ToggleDrawPressed(ActionEvent event) throws LineUnavailableException {
    	
    	SoundProcessor.DrawContinuously(CoordinateArray, DrawingCanvas.getHeight(), CellHeight, DrawingCanvas.getWidth(), CellWidth);
    	
    }
    
    
    
    @FXML
    void setFrequency(ActionEvent event) {

    }
  

}
