package com.justeat.model;


	
	public class Ordine {
	private long id;
	private String stato= "In elaborazione";
	public Ordine(long id, String stato) {
		this.id = id;
		this.stato = stato;
	}
	
	public long getId() {
		return id;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}

	@Override
	public String toString() {
		return "Ordine [id=" + id + ", stato=" + stato + "]";
	}
	
	
	
}