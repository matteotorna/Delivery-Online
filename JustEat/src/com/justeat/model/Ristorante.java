package com.justeat.model;

public class Ristorante extends Utente{
	
	public Ristorante(long id,String username, String password, String telefono,String nome, String indirizzo, String cap) {
		super(username, password, telefono, 0, nome, indirizzo,cap);
		this.id=id;
		this.indirizzo = indirizzo;
		this.cap = cap;
		this.nome = nome;
	}
	public Ristorante(String username, String password, String telefono,String nome, String indirizzo, String cap) {
		super(username, password, telefono, 0, nome, indirizzo,cap);
		this.indirizzo = indirizzo;
		this.cap = cap;
		this.nome = nome;
	}
	@Override
	public String toString() {
		return "Ristorante [id=" + id + ", username=" + username + ", password=" + password + ", indirizzo=" + indirizzo
				+ ", cap=" + cap + ", nome=" + nome + ", cognome=" + cognome + ", telefono=" + telefono + ", tipo="
				+ tipo + "]";
	}
	
	

}
	
	
	
	
	

