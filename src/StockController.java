import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class StockController implements Initializable{

    @FXML
    private ImageView homeImageId;

    @FXML
    private TableView<Produit> tableProdPerempId;

    @FXML
    private TableColumn<Produit, String> nomProdId;

    @FXML
    private TableColumn<Produit, Integer> QuantitéStockCollId;

    @FXML
    private ChoiceBox<String> choiceNomId;

    @FXML
    ObservableList<Produit> data = FXCollections.observableArrayList();
    
    @FXML 
    ObservableList<String> listeNom = FXCollections.observableArrayList();

    @FXML
    private Button searchPerempId;

    @FXML
    void refreshList(MouseEvent event) {
    	choiceNomId.getSelectionModel().clearSelection();
    	tableProdPerempId.setItems(data);
    }

    @FXML
    void onHomeIconPressed(MouseEvent event) throws Exception {
    	FxMain.primary.setScene(new Scene((ScrollPane) FXMLLoader.load(MyControler.class.getResource("Sample.fxml"))));
    }

    @FXML
    void onSearchClicked(ActionEvent event) {
    	String nom = choiceNomId.getSelectionModel().getSelectedItem();
    	if (nom == null) tableProdPerempId.setItems(data);
    	else {
    		ObservableList<Produit> data2 = FXCollections.observableArrayList();
    		int i;
    		for (i=0;i<data.size();i++){
    			if (data.get(i).getNom().equals(nom) && data.get(i).getNom().length() == nom.length()) data2.add(data.get(i));
    		}
    		tableProdPerempId.setItems(data2);
    		
    	}
		
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomProdId.setCellValueFactory(new PropertyValueFactory<>("nom"));
		QuantitéStockCollId.setCellValueFactory(new PropertyValueFactory<>("prix"));
		
		ResultSet resultSet,resultSet2;
		
		try {
			Class.forName("org.sqlite.JDBC");
			FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement statement = FxMain.c.createStatement();
			resultSet = statement.executeQuery("SELECT produit.id,produit.nom,produit.quantite,produit.date_peremption,SUM(vente.quantite) cumul FROM produit,vente WHERE id=fk_id GROUP BY id");
			
			while(resultSet.next()){
				data.add(new Produit(resultSet.getString("id"), resultSet.getString("nom"), resultSet.getInt("quantite") - resultSet.getInt("cumul"), resultSet.getInt("quantite"), LocalDate.parse(resultSet.getString("date_peremption"))));
				
			}
		
			tableProdPerempId.setItems(data);
			resultSet = statement.executeQuery("SELECT DISTINCT vente.nom FROM vente, produit WHERE fk_id=id");
			while(resultSet.next()) listeNom.add(resultSet.getString(1));
			choiceNomId.setItems(listeNom);
			statement.close();
			FxMain.c.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
