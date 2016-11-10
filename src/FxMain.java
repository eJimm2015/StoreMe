import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import com.sun.glass.ui.Screen;
import com.sun.javafx.geom.Rectangle;
import com.sun.org.apache.xml.internal.security.Init;
import com.sun.prism.paint.Color;
import com.sun.xml.internal.ws.api.pipe.NextAction;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.shape.*;


public class FxMain extends Application {

	public static Stage primary;
	public static Connection c = null;
	public static void main(String[] args) {
		try {
			
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement statement = c.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS produit (id VARCHAR(30) PRIMARY KEY NOT NULL, nom VARCHAR(30) NOT NULL, prix INT NOT NULL, quantite INT, date_peremption VARCHAR(15) NOT NULL);");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS vente (fk_id VARCHAR(30), nom VARCHAR(30),date_vente VARCHAR(15),quantite INT , PRIMARY KEY (fk_id,date_vente), FOREIGN KEY (fk_id) REFERENCES produit(id));");
			statement.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		primary = primaryStage;
		InputStream inputStream = FxMain.class.getResourceAsStream("glyphicons-275-beer.png");
		Image image = new Image(inputStream);
		primaryStage.getIcons().add(image);
		javafx.scene.control.ScrollPane scrollPane = (javafx.scene.control.ScrollPane) FXMLLoader.load(FxMain.class.getResource("Sample.fxml"));
		InitInterface.show();
		PauseTransition pauseTransition = new PauseTransition(Duration.seconds(4));
		pauseTransition.setOnFinished(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				InitInterface.close();
				primaryStage.setScene(new Scene(scrollPane));
				primaryStage.setOnCloseRequest(e -> {
					e.consume();
					ConfirmationBox.show(primaryStage);
				
				});
				primaryStage.show();
			}
		});
		pauseTransition.play();
	}
	

}
