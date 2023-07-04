package com.justeat.model;

public class Piatto {
	private long id;
	private String nome, descrizione;
	private int tipo;
	private double prezzo;
	
	public Piatto(String nome, String descrizione, int tipo, double prezzo) {
		this.nome = nome;
		this.descrizione = descrizione;
		this.tipo = tipo;
		this.prezzo = prezzo;
	}
	public Piatto(long id, String nome, String descrizione, int tipo, double prezzo) {
		this(nome, descrizione, tipo, prezzo);
		this.id=id;
	}
	public long getId() {
		return id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}
	@Override
	public String toString() {
		return "Piatto [id=" + id + ", nome=" + nome + ", descrizione=" + descrizione + ", tipo=" + tipo + ", prezzo="
				+ prezzo + "]";
	}
	
}