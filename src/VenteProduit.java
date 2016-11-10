import java.time.LocalDate;

public class VenteProduit {
	private String nom;
	private int quantit�,prix;
	private LocalDate date_vente;
	
	public VenteProduit(String nom, int quantit�, LocalDate date_vente, int prix) {
		
		this.nom = nom;
		this.prix = prix;
		this.quantit� = quantit�;
		this.date_vente = date_vente;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getQuantit�() {
		return quantit�;
	}

	public void setQuantit�(int quantit�) {
		this.quantit� = quantit�;
	}

	public LocalDate getDate_vente() {
		return date_vente;
	}

	public void setDate_vente(LocalDate date_vente) {
		this.date_vente = date_vente;
	}

	public int getPrix() {
		return prix;
	}

	public void setPrix(int prix) {
		this.prix = prix;
	}
	
}
