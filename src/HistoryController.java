import java.net.URL;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class HistoryController implements Initializable{

    @FXML
    private TableView<Produit> historyTableId;

    @FXML
    private TableColumn<Produit, String> nom;

    @FXML
    private TableColumn<Produit, Integer> prix;
    
    @FXML
    private TableColumn<Produit, LocalDate> dateVenteId;

    @FXML
    private TableColumn<Produit, Integer> quantitéId;

    @FXML
    private Label search;

    @FXML
    private ChoiceBox<String> choiceboxId;

    @FXML
    private Label name;

    @FXML
    private DatePicker datePickerId;
    
    @FXML
    private ImageView homeIconId;
    
    @FXML
    private ObservableList<Produit> data = FXCollections.observableArrayList();
    
    @FXML
    private ObservableList<String> listeNom = FXCollections.observableArrayList();

    @FXML
    void onHomeIconClick(MouseEvent event) throws Exception {
    	FxMain.primary.setScene(new Scene((ScrollPane) FXMLLoader.load(MyControler.class.getResource("Sample.fxml"))));

    }

    @FXML
    void onSearchAction(ActionEvent event) {
    	ObservableList<Produit> data2 = FXCollections.observableArrayList();
    	
    	String nom = choiceboxId.getSelectionModel().getSelectedItem();
    	LocalDate localDate = datePickerId.getValue();
    	if (nom == null && localDate == null) historyTableId.setItems(data);
    	else {
    		data2.remove(0, data2.size());
    		String query = "";
    		ResultSet resultSet;
    		if (nom == null) query = "SELECT * FROM vente,produit WHERE date_vente = '"+localDate+"' AND fk_id=id";
    		else if (localDate == null)	query = "SELECT * FROM vente,produit WHERE vente.nom = '"+nom+"' AND fk_id=id";
    		else query = "SELECT * FROM vente,produit WHERE vente.nom = '"+nom+"' AND date_vente ='"+localDate+"' AND fk_id=id";
    			
    		try {
				Class.forName("org.sqlite.JDBC");
				FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
				Statement statement = FxMain.c.createStatement();
				resultSet = statement.executeQuery(query);
				while(resultSet.next()){
					data2.add(new Produit(resultSet.getString("fk_id"), resultSet.getString("nom"), resultSet.getInt("prix"), resultSet.getInt("quantite"), LocalDate.parse(resultSet.getString("date_vente"))));
				}
				
				statement.close();
				FxMain.c.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		historyTableId.setItems(data2);
    		choiceboxId.getSelectionModel().clearSelection();
			datePickerId.setValue(null);
    	}
    }
    
    @FXML
    void refreshList(MouseEvent event) {
    	historyTableId.setItems(data);
    	choiceboxId.getSelectionModel().clearSelection();
    	datePickerId.setValue(null);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		nom.setCellValueFactory(new PropertyValueFactory<Produit,String>("nom"));
		prix.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("prix"));
		dateVenteId.setCellValueFactory(new PropertyValueFactory<Produit, LocalDate>("date_péremption"));
		quantitéId.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
		
		ResultSet resultSet;
		
		try {
			Class.forName("org.sqlite.JDBC");
			FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement statement = FxMain.c.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM vente,produit WHERE fk_id=id");
			while(resultSet.next()){
				data.add(new Produit(resultSet.getString("fk_id"), resultSet.getString("nom"), resultSet.getInt("prix"), resultSet.getInt("quantite"), LocalDate.parse(resultSet.getString("date_vente"))));
			}
		
			historyTableId.setItems(data);
			resultSet = statement.executeQuery("SELECT DISTINCT vente.nom FROM vente, produit WHERE fk_id=id");
			while(resultSet.next()) listeNom.add(resultSet.getString(1));
			
			choiceboxId.setItems(listeNom);
			statement.close();
			FxMain.c.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
