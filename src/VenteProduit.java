import java.time.LocalDate;

public class VenteProduit {
	private String nom;
	private int quantité,prix;
	private LocalDate date_vente;
	
	public VenteProduit(String nom, int quantité, LocalDate date_vente, int prix) {
		
		this.nom = nom;
		this.prix = prix;
		this.quantité = quantité;
		this.date_vente = date_vente;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getQuantité() {
		return quantité;
	}

	public void setQuantité(int quantité) {
		this.quantité = quantité;
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
