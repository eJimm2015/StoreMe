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
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import jdk.nashorn.internal.runtime.ECMAErrors;

public class PerempController implements Initializable{

    @FXML
    private ImageView homeImageId;

    @FXML
    private TableView<Produit> tableProdPerempId;

    @FXML
    private TableColumn<Produit, String> nomProdId;

    @FXML
    private TableColumn<Produit, Integer> NbreJrsId;
    
    @FXML
    private Label search;

    @FXML
    private ChoiceBox<String> choiceNomId;

  

    @FXML
    private Button searchPerempId;
    
    @FXML
    private ObservableList<Produit> data = FXCollections.observableArrayList();
    
    @FXML
    void refreshList(MouseEvent event) {
    	tableProdPerempId.setItems(data);
    	choiceNomId.getSelectionModel().clearSelection();
    	
    }

    @FXML
    void onHomeIconPressed(MouseEvent event) throws Exception {
    	FxMain.primary.setScene(new Scene((ScrollPane) FXMLLoader.load(MyControler.class.getResource("Sample.fxml"))));

    }

    @FXML
    void onSearchClicked(ActionEvent event) {
    	
    	ObservableList<Produit> data2 = FXCollections.observableArrayList();
    	String nom = choiceNomId.getSelectionModel().getSelectedItem();
    	if (nom == null) tableProdPerempId.setItems(data);
    	else {
    		data2.remove(0, data2.size());
    		ResultSet resultSet;
    		try {
    			Class.forName("org.sqlite.JDBC");
    			FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
    			Statement statement = FxMain.c.createStatement();
    			resultSet = statement.executeQuery("SELECT * FROM produit WHERE nom ='"+nom+"';");
    			while(resultSet.next()){
    				data2.add(new Produit(resultSet.getString("id"), resultSet.getString("nom"), (int)ChronoUnit.DAYS.between(LocalDate.now(),LocalDate.parse(resultSet.getString("date_peremption"))), resultSet.getInt("quantite"), LocalDate.parse(resultSet.getString("date_peremption"))));
    			}
    		
    			tableProdPerempId.setItems(data2);
    			statement.close();
    			FxMain.c.close();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

    	
    	

    }
    
    @FXML
    private ObservableList<String> listeNom = FXCollections.observableArrayList();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nomProdId.setCellValueFactory(new PropertyValueFactory<>("nom"));
		NbreJrsId.setCellValueFactory(new PropertyValueFactory<>("prix"));
ResultSet resultSet;
		
		try {
			Class.forName("org.sqlite.JDBC");
			FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement statement = FxMain.c.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM produit");
			while(resultSet.next()){
				data.add(new Produit(resultSet.getString("id"), resultSet.getString("nom"), (int)ChronoUnit.DAYS.between(LocalDate.now(),LocalDate.parse(resultSet.getString("date_peremption"))), resultSet.getInt("quantite"), LocalDate.parse(resultSet.getString("date_peremption"))));
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
