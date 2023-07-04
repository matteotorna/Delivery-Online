package com.justeat.model;

public class Cliente extends Utente {
	
	public Cliente(String username, String password, String telefono, String nome, String cognome, String indirizzo, String cap) {
		super(username, password, telefono, 1, nome, cognome,indirizzo,cap);
	}

	
	public Cliente(long id,String username, String password, String telefono, String nome, String cognome, String indirizzo, String cap) {
		super(username, password, telefono, 1, nome, cognome,indirizzo,cap);
	
	}


	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", cognome=" + cognome + ", indirizzo=" + indirizzo + ", cap=" + cap + "]";
	}
	
	


}
	
	
	
	

