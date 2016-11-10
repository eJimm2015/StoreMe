import java.io.IOException;

import org.omg.PortableServer.ServantActivator;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InitInterface {
	public static Stage stage;
public static void show() throws IOException{
	stage = new Stage();
	stage.initStyle(StageStyle.UNDECORATED);
	GridPane gridPane = (GridPane) FXMLLoader.load(FxMain.class.getResource("Intro.fxml"));
	stage.setScene(new Scene(gridPane));
	stage.show();
}
public static void close(){
	stage.close();
}
}
