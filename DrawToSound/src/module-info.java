module DrawToSound {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires jsyn;
	requires java.desktop;
	requires javafx.swing;
	//requires jsyn;
	
	opens application to javafx.graphics, javafx.fxml;
}
