package com.justeat.model;



	public class Rider extends Utente{
		
		public Rider(String username, String password, String telefono, String nome, String cognome, String indirizzo, String cap) {
			super(username, password, telefono, 1, nome, cognome,indirizzo,cap);
		}
			
		public Rider(long id,String username, String password, String telefono, String nome, String cognome, String indirizzo, String cap) {
			this(username, password, telefono, nome, cognome,indirizzo,cap);
			this.id=id;
		}

		@Override
		public String toString() {
			return "Rider [id=" + id + ", username=" + username + ", password=" + password + ", indirizzo=" + indirizzo
					+ ", cap=" + cap + ", nome=" + nome + ", cognome=" + cognome + ", telefono=" + telefono + ", tipo="
					+ tipo + "]";
		}
}
