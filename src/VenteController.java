import java.net.URL;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class VenteController implements Initializable {

    @FXML
    private TextField codeField;

    @FXML
    private Label CodeText;

    @FXML
    private TextField QuantField;

    @FXML
    private Label QuantText;

    @FXML
    private Button addSell;

    @FXML
    private Button validateTrans;
    
    @FXML
    private Label prixProdLabel;

    @FXML
    private Label totalLabel;


    @FXML
    private TableView<Produit> tableId;

    @FXML
    private TableColumn<Produit, String> idNom;

    @FXML
    private TableColumn<Produit, Integer> prix;

    @FXML
    private TableColumn<Produit, Integer> idQuantité;

    @FXML
    private Button reinitAll;

    @FXML
    private Button cancelSell;

    @FXML
    private ImageView homeIcon;
    
    @FXML
    private int cumul;
    
    @FXML
    
    private ObservableList<Produit> data = FXCollections.observableArrayList();

    @FXML
    void onActionAdd(ActionEvent event) {
    	if (codeField.getText().equals("") || QuantField.getText().equals("")) MessageBox.show("Informations manquantes", "Erreur");
    	else {
    		try {
    			Class.forName("org.sqlite.JDBC");
    			FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
    			Statement statement = FxMain.c.createStatement();
    			ResultSet count = statement.executeQuery("SELECT COUNT(*) FROM produit WHERE id='"+codeField.getText()+"';");
    			if (count.getInt(1) == 0) MessageBox.show("Produit non existant !", "Erreur");
    			else {
    				count = statement.executeQuery("SELECT* FROM produit WHERE id='"+codeField.getText()+"';");
    				String id = count.getString("id"), nom = count.getString("nom");
    				int prix = count.getInt("prix"), quantité = Integer.parseInt(QuantField.getText());
    				data.add(new Produit(id,nom,prix, quantité, LocalDate.now()));
    				cumul += prix*quantité;
    				totalLabel.setText(""+cumul);
    				codeField.clear();
    				QuantField.clear();
    			}
    			statement.close();
    			FxMain.c.close();
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}

    }

    @FXML
    void onActionCancel(ActionEvent event) {
    	Produit produit = data.get(data.size() - 1);
    	cumul -= produit.getPrix()*produit.getQuantite();
    	data.remove(data.size() - 1);
    	totalLabel.setText(""+cumul);
    	
    }

    @FXML
    void onActionReinit(ActionEvent event) {
    	data.remove(0, data.size());
    	cumul = 0;
    	totalLabel.setText(""+cumul);
    	prixProdLabel.setText("0");
    }

    @FXML
    void onActionValidateSell(ActionEvent event) {
    	try{
    		Class.forName("org.sqlite.JDBC");
    		    			FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
    		    			
    		int i;
    		for (i=0; i<data.size(); i++){
    		Statement statement = FxMain.c.createStatement();
    		 ResultSet count = statement.executeQuery("SELECT COUNT(*) FROM vente WHERE fk_id='"+data.get(i).getId()+"' AND date_vente = '"+data.get(i).getDate_péremption().toString()+"';");
    		    				if (count.getInt(1) == 0) {
    		    			
    		    					statement.executeUpdate("INSERT INTO vente VALUES('"+data.get(i).getId()+"','"+data.get(i).getNom()+"','"+data.get(i).getDate_péremption().toString()+"',"+data.get(i).getQuantite()+");");
    		    				}
    		    				else {
    		    					count = statement.executeQuery("SELECT * FROM vente WHERE fk_id='"+data.get(i).getId()+"' AND date_vente = '"+data.get(i).getDate_péremption().toString()+"';");
    		    		
    		    					int cumul_quantité = count.getInt("quantite") + data.get(i).getQuantite();
    		    			
    		    					statement.executeUpdate("UPDATE vente SET quantite="+cumul_quantité+" WHERE fk_id='"+data.get(i).getId()+"' AND date_vente = '"+data.get(i).getDate_péremption().toString()+"';");
    		    					
    		    				}
    		 statement.close();
    		 
    		}
    		data.remove(0, data.size());
    		prixProdLabel.setText("0");
    		totalLabel.setText("0");
    		cumul = 0;
    		FxMain.c.close();



    		} catch (Exception e) {
    		    			// TODO Auto-generated catch block
    		    			e.printStackTrace();
    		    		}
    }

    @FXML
    void onHomeIconPressed(MouseEvent event) throws Exception {
    	FxMain.primary.setScene(new Scene((ScrollPane) FXMLLoader.load(MyControler.class.getResource("Sample.fxml"))));
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cumul =0;
		idNom.setCellValueFactory(new PropertyValueFactory<Produit, String>("nom"));
		prix.setCellValueFactory(new PropertyValueFactory<Produit, Integer>("prix"));
		idQuantité.setCellValueFactory(new PropertyValueFactory<Produit,Integer>("quantite"));
	 	tableId.setItems(data);
		codeField.focusedProperty().addListener((observable, oldvalue, newvalue)->{
			if(!newvalue && !codeField.getText().equals("")){
				try {
					Class.forName("org.sqlite.JDBC");
					FxMain.c = DriverManager.getConnection("jdbc:sqlite:test.db");
					Statement statement = FxMain.c.createStatement();
					ResultSet count = statement.executeQuery("SELECT COUNT(*) FROM produit WHERE id='"+codeField.getText()+"';");
	    			if (count.getInt(1) == 0) {
	    				MessageBox.show("Produit non existant !", "Erreur");
	    				addSell.setDisable(true);
	    			}
	    			else {
	    				addSell.setDisable(false);
	    				count = statement.executeQuery("SELECT* FROM produit WHERE id='"+codeField.getText()+"';");
	    				prixProdLabel.setText(""+count.getInt("prix"));
	    				
	    			
	    			}
					statement.close();
					FxMain.c.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		
	
	}
    

}
