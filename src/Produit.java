import java.time.LocalDate;

public class Produit implements Comparable<Produit>{
	
private int  prix, quantite;
private String nom,id;
private LocalDate date_péremption;

public Produit(String id,String nom, int prix,int quantite, LocalDate data_péremption) {
	this.id = id;
	this.nom = nom;
	this.prix = prix;
	this.quantite = quantite;
	this.date_péremption = data_péremption;
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
public LocalDate getDate_péremption() {
	return date_péremption;
}
public void setDate_péremption(LocalDate date_péremption) {
	this.date_péremption = date_péremption;
}
public int getQuantite() {
	return quantite;
}
public void setQuantité(int quantite) {
	this.quantite = quantite;
}
@Override
public int compareTo(Produit o) {
	return id.compareTo(o.getId());
}

}
