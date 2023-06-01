module CheckMeProject {
	requires java.sql;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.controls;
	exports application;
	opens application;
}