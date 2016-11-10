import java.time.LocalDate;

public class Produit implements Comparable<Produit>{
	
private int  prix, quantite;
private String nom,id;
private LocalDate date_p�remption;

public Produit(String id,String nom, int prix,int quantite, LocalDate data_p�remption) {
	this.id = id;
	this.nom = nom;
	this.prix = prix;
	this.quantite = quantite;
	this.date_p�remption = data_p�remption;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public int getPrix() {
	return prix;
}
public void setPrix(int prix) {
	this.prix = prix;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public LocalDate getDate_p�remption() {
	return date_p�remption;
}
public void setDate_p�remption(LocalDate date_p�remption) {
	this.date_p�remption = date_p�remption;
}
public int getQuantite() {
	return quantite;
}
public void setQuantit�(int quantite) {
	this.quantite = quantite;
}
@Override
public int compareTo(Produit o) {
	return id.compareTo(o.getId());
}

}
