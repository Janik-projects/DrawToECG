    package application;


import java.util.EventListener;
import java.util.Vector;

import javax.sound.sampled.LineUnavailableException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import objects.CoordinateObject;    
import logic.SoundClass;
import javafx.scene.control.ToggleButton;

public class GUI_Controller {

   
    private Stage mainStage;
    
    private SoundClass SoundProcessor;
    
    //private SoundClassImproved SoundProcessor;
    
    private int CellWidth;
    private int CellHeight;
    
    private Vector<CoordinateObject> CoordinateArray;
    
    public void initialize() throws LineUnavailableException {
    	CellWidth = 10;
    	CellHeight = 10;
    	
    	CoordinateArray = new Vector<CoordinateObject>();
    	fillCoordinateArray();
    	
    	DrawingCanvas.getGraphicsContext2D().setFill(Color.WHITE);
    	DrawingCanvas.getGraphicsContext2D().fillRect(0, 0, DrawingCanvas.getWidth(), DrawingCanvas.getHeight());
    	DrawingCanvas.getGraphicsContext2D().setFill(Color.BLACK);
    	
    	SoundProcessor = new SoundClass();
    	//SoundProcessor = new SoundClassImproved();
    	
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
    	
    	/*
    	for (int y = 0; y <= CanvasHeight; y += CellHeight) {
   		 for (int x = 0; x <= CanvasWidth; x += CellWidth) {
   			CoordinateArray.add(new CoordinateObject(x, y, false, ID));
   			ID++;
   		} 
   	}*/
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
    	
    	//double CanvasOffsetX = DrawingCanvas.getScene().getX();
    	//double CanvasOffsetY = DrawingCanvas.getScene().getY();
    	
    	
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
    	//double DragX = event.getSceneX();
    	//double DragY = event.getSceneY();
    	//System.out.println("Here's the Movement: X Cooridnate: " + DragX + " Y Coordinate: " + DragY);
    }
    
    
    
    @FXML
    void SetDrawing(ActionEvent event) throws LineUnavailableException {
    	//SoundProcessor.DrawAsAudio(CoordinateArray, DrawingCanvas.getHeight(), CellHeight);
    	SoundProcessor.DrawAsAudio(CoordinateArray, DrawingCanvas.getHeight(), CellHeight, DrawingCanvas.getWidth(), CellWidth);
    }

    
    
    @FXML
    void ToggleDrawPressed(ActionEvent event) throws LineUnavailableException {
    	
    	System.out.println(event.getEventType().getName());
    	
    	SoundProcessor.DrawContinously(CoordinateArray, DrawingCanvas.getHeight(), CellHeight, DrawingCanvas.getWidth(), CellWidth);
    	
    }
    
    
    
    @FXML
    void setFrequency(ActionEvent event) {

    }
  

}
