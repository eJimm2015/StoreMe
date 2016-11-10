import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationBox 
{
	public static void show(Stage arg) 
	{
		Stage stage = new Stage(); 
		stage.initModality(Modality.APPLICATION_MODAL); 
		stage.setTitle("Attention !"); 
		stage.setMinWidth(250); 
		Label lbl = new Label(); 
		lbl.setText("Voulez-vous vraiment quitter le programme ?");
		Button btnOK = new Button("Confirmer"); 
		Button btnCancel = new Button("Annuler");
		btnOK.setOnAction(e->{
			stage.close();
			arg.close();
		});
		btnCancel.setOnAction(e->stage.close());
		VBox pane = new VBox(20); 
		pane.setPadding(new Insets(10));
		HBox hBox = new HBox(10, btnOK,btnCancel);
		hBox.setAlignment(Pos.CENTER);
		pane.getChildren().addAll(lbl, hBox);
		pane.setAlignment(Pos.CENTER);
		Scene scene = new Scene(pane); 
		stage.setScene(scene);
		stage.showAndWait();
		
	}
}