import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;

public class MyControler {

    @FXML
    private GridPane mainGrid;

    @FXML
    private Rectangle r1;

    @FXML
    private Rectangle r2;

    @FXML
    private Rectangle r4;

    @FXML
    private Rectangle r5;

    @FXML
    private Rectangle r3;

    @FXML
    private ImageView shop;

    @FXML
    private ImageView sells;

    @FXML
    private ImageView products;

    @FXML
    private ImageView peremptions;

    @FXML
    private ImageView stock;
    
    @FXML
    private Label ventesLab;

    @FXML
    private Label produitsLab;

    @FXML
    private Label vendreLab;

    @FXML
    private Label peremptionsLab;

    @FXML
    private Label stockLabel;
    

    @FXML
    void onMousePressedRec(MouseEvent event) throws Exception {
    	
		if(event.getSource() == r3 || event.getSource() == vendreLab) FxMain.primary.setScene(new Scene((GridPane) FXMLLoader.load(MyControler.class.getResource("Vente.fxml"))));
		else if(event.getSource() == r1 || event.getSource() == ventesLab) FxMain.primary.setScene(new Scene((GridPane) FXMLLoader.load(MyControler.class.getResource("Historique.fxml"))));
		else if(event.getSource() == r2 || event.getSource() == produitsLab) FxMain.primary.setScene(new Scene((GridPane) FXMLLoader.load(MyControler.class.getResource("Produits.fxml"))));
		else if(event.getSource() == r4 || event.getSource() == peremptionsLab) FxMain.primary.setScene(new Scene((GridPane) FXMLLoader.load(MyControler.class.getResource("Peremptions.fxml"))));
		else if(event.getSource() == r5 || event.getSource() == stockLabel) FxMain.primary.setScene(new Scene((GridPane) FXMLLoader.load(MyControler.class.getResource("Stock.fxml"))));

		
    }

}
