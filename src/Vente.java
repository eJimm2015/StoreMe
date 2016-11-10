import java.time.LocalDate;

public class Vente {
	private int quantité;
	private String id;
	private LocalDate date_vente;
	
	public Vente(String id, int quantité, LocalDate date_vente) {
		super();
		this.id = id;
		this.quantité = quantité;
		this.date_vente = date_vente;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	

}
