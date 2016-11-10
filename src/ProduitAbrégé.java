
public class ProduitAbrégé {
private String nom;
private int quantité, prix;
public ProduitAbrégé(String nom, int quantité, int prix) {
	this.nom = nom;
	this.quantité = quantité;
	this.prix = prix;
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
public int getPrix() {
	return prix;
}
public void setPrix(int prix) {
	this.prix = prix;
}



}
