import java.net.URL;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.ResourceBundle;

import com.sun.glass.events.KeyEvent;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import sun.management.counter.Variability;

public class produitsBddControler implements Initializable{

    @FXML
    private ImageView homeIconId;

    @FXML
    private TableView<Produit> tableProduitsId;

    @FXML
    private TableColumn<Produit, String> codeBarreColId;

    @FXML
    private TableColumn<Produit, String> nomProduitColId;

    @FXML
    private TableColumn<Produit, Integer> prixColId;

    @FXML
    private TableColumn<Produit, Integer> quantitéColId;

    @FXML
    private TableColumn<Produit, LocalDate> datePéremptionColId;

    @FXML
    private TextField codeBarreId;

    @FXML
    private TextField nomProduitId;

    @FXML
    private TextField prixId;

    @FXML
    private TextField quantitéId;

    @FXML
    private DatePicker datePéremId;

    @FXML
    private Button ajoutBoutId;

    @FXML
    private Button supprBoutId;
    
    @FXML
    
    private ObservableList<Produit> data =FXCollections.observableArrayList();
   
    @FXML
    void onAddRecordAction(ActionEvent event) {
    	
    	if (codeBarreId.getText().equals("") || nomProduitId.getText().equals("") || prixId.getText().equals("") || quantitéId.getText().equals("") || datePéremId.getValue().equals(null))
    		MessageBox.show("Données manquantes !", "Erreur");
    	else {
    		String id = codeBarreId.getText(),nom = nomProduitId.getText();
        	int prix = Integer.parseInt(prixId.getText()),quantite = Integer.parseInt(quantitéId.getText());
        	LocalDate date_peremption = datePéremId.getValue();
        	Produit produit = new Produit(id, nom, prix, quantite, date_peremption);
    		int i = 0; 
        	Boolean exists = false;
        	while (i < data.size() & exists == false){
        		if(data.get(i).compareTo(produit)==0) exists = true;
        		else i++;
        	}
        	
    	if (exists) MessageBox.show("Code barre existant !", "Erreur");
    	else {
    		data.add(produit);
    		try {
    			//Date date = Date.valueOf(date_peremption);
    			
    			Class.forName("org.sqlite.JDBC");
    			FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
    			Statement statement = FxMain.c.createStatement();
    			statement.executeUpdate("INSERT INTO produit VALUES('"+id+"','"+nom+"',"+prix+","+quantite+",'"+date_peremption.toString()+"');");
    			statement.close();
    			FxMain.c.close();
    			codeBarreId.clear(); 
    			nomProduitId.clear();
    			prixId.clear();
    			quantitéId.clear();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	}
    	
    	
    	

    }

    @FXML
    void onDeleteRecordAction(ActionEvent event) {
    	ObservableList<Produit> items = tableProduitsId.getSelectionModel().getSelectedItems();
    	for (Produit produit : items) {
			data.remove(produit);
			try
			{
				Class.forName("org.sqlite.JDBC");
				FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
				Statement statement = FxMain.c.createStatement();
				statement.executeUpdate("DELETE FROM produit WHERE id = '"+produit.getId()+"';");
				statement.close();
				FxMain.c.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
    }

    @FXML
    void onHomeIconPressed(MouseEvent event) throws Exception {
    	FxMain.primary.setScene(new Scene((ScrollPane) FXMLLoader.load(MyControler.class.getResource("Sample.fxml"))));
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		codeBarreColId.setCellValueFactory(new PropertyValueFactory<Produit, String>("id"));
		nomProduitColId.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
		prixColId.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix"));
		quantitéColId.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("quantite"));
		datePéremptionColId.setCellValueFactory(new PropertyValueFactory<Produit, LocalDate>("date_péremption"));
		datePéremId.setValue(LocalDate.now());
		try {
			Class.forName("org.sqlite.JDBC");
			FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
			Statement statement = FxMain.c.createStatement();
			
			ResultSet resultSet = statement.executeQuery("SELECT* from produit");
			
			data = FXCollections.observableArrayList();
			
			while(resultSet.next()){
				
				data.add(new Produit(resultSet.getString("id"), resultSet.getString("nom"), resultSet.getInt("prix"), resultSet.getInt("quantite"), LocalDate.parse(resultSet.getString("date_peremption"))));
	
			}
			tableProduitsId.setItems(data);
			statement.close();
			FxMain.c.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}

}
