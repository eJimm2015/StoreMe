import java.time.LocalDate;

public class Vente {
	private int quantit�;
	private String id;
	private LocalDate date_vente;
	
	public Vente(String id, int quantit�, LocalDate date_vente) {
		super();
		this.id = id;
		this.quantit� = quantit�;
		this.date_vente = date_vente;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	

}
