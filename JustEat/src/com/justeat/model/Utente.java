package com.justeat.model;

public abstract class Utente {
	
	protected long id;
	protected String username, password, indirizzo, cap, nome, cognome, telefono;
	protected int tipo;
	
	public Utente(String username, String password, String telefono, int tipo, String nome, String cognome, String indirizzo, String cap) {
		this(username,password,telefono,tipo,nome,indirizzo,cap);
		this.cognome = cognome;
	}
	
	public Utente(String username, String password, String telefono, int tipo, String nome, String indirizzo, String cap) {
		this.username = username;
		this.password = password;
		this.telefono = telefono;
		this.tipo = tipo;
		this.nome=nome;
		this.indirizzo=indirizzo;
		this.cap=cap;
	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	
}
