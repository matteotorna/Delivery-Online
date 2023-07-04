package com.justeat.test;

import java.util.ArrayList;
import java.util.Scanner;

import com.justeat.db.MioDatabase;
import com.justeat.model.Cliente;
import com.justeat.model.Ordine;
import com.justeat.model.Piatto;
import com.justeat.model.Rider;
import com.justeat.model.Ristorante;
import com.justeat.model.Utente;



public class Test {
	private static Utente loggato=null;

	public static void main(String[] args) {
		menuSceltaUtentiNonLoggati();
	}
	
	private static void login() {
		Scanner sc = new Scanner(System.in);
		System.out.println("dimmi lo username: ");
		String username=sc.next();
		System.out.println("dimmi la password: ");
		String password=sc.next();
		loggato = MioDatabase.getInstance().login(username,password);
		//System.out.println(loggato.getTipo());
	}
	
	private static void registraRistorante() {
		Scanner sc = new Scanner(System.in);
		System.out.println("dimmi il nome: ");
		String nome=sc.nextLine();
		System.out.println("dimmi lo username: ");
		String username=sc.nextLine();
		System.out.println("dimmi la password: ");
		String password=sc.nextLine();
		System.out.println("dimmi il numero di telefono: ");
		String telefono=sc.nextLine();
		System.out.println("dimmi l'indirizzo: ");
		String indirizzo=sc.nextLine();
		System.out.println("dimmi il cap: ");
		String cap=sc.nextLine();
		Utente u = new Ristorante(username,password,telefono,nome,indirizzo,cap);
		MioDatabase.getInstance().registraUtente(u);
	}
	
	private static void registraCliente() {
		Scanner sc = new Scanner(System.in);
		System.out.println("dimmi il nome: ");
		String nome=sc.nextLine();
		System.out.println("dimmi il cognome: ");
		String cognome=sc.nextLine();
		System.out.println("dimmi lo username: ");
		String username=sc.nextLine();
		System.out.println("dimmi la password: ");
		String password=sc.nextLine();
		System.out.println("dimmi l'indirizzo: ");
		String indirizzo=sc.nextLine();
		System.out.println("dimmi il cap: ");
		String cap=sc.nextLine();
		System.out.println("dimmi il numero di telefono: ");
		String telefono=sc.nextLine();
		Utente u = new Cliente(username,password,telefono,nome,cognome,indirizzo,cap);
		MioDatabase.getInstance().registraUtente(u);
	}
	
	private static void registraRider() {
		Scanner sc = new Scanner(System.in);
		System.out.println("dimmi il nome: ");
		String nome=sc.nextLine();
		System.out.println("dimmi il cognome: ");
		String cognome=sc.nextLine();
		System.out.println("dimmi lo username: ");
		String username=sc.nextLine();
		System.out.println("dimmi la password: ");
		String password=sc.nextLine();
		System.out.println("dimmi l'indirizzo: ");
		String indirizzo=sc.nextLine();
		System.out.println("dimmi il cap: ");
		String cap=sc.nextLine();
		System.out.println("dimmi il numero di telefono: ");
		String telefono=sc.nextLine();
		Utente u = new Rider(username,password,telefono,nome,cognome,indirizzo,cap);
		MioDatabase.getInstance().registraUtente(u);
	}
	
	private static void menuSceltaUtentiNonLoggati() {
		Scanner sc = new Scanner(System.in);
		int scelta=4;
		
		do {
			
			if(loggato!=null) {
				
				if(loggato.getTipo()==0) {
					menuRistoranteLoggato();
				} else if(loggato.getTipo()==1) {
					menuClienteLoggato();
				} else {
					menuRiderLoggato();
				}
				
			} else {
				System.out.println("Dimmi cosa vuoi fare: ");
				System.out.println("1 -> Login");
				System.out.println("2 -> Registrati come cliente");
				System.out.println("3 -> Registrati come ristorante");
				System.out.println("4 -> Registrati come rider");
				System.out.println("5 -> logout");
				scelta=sc.nextInt();
				switch(scelta) {
					case 1:
						login();
						break;
					case 2:
						registraCliente();
						break;
					case 3:
						registraRistorante();
						break;
					case 4:
						registraRider();
						break;
					case 5:
						loggato=null;
						System.out.println("Arrivederci");
				}
					
			}
			
		} while(scelta!=5);
	}
	
	private static void menuRistoranteLoggato() {
		Scanner sc = new Scanner(System.in);
		int scelta;
		
		do {
			System.out.println("Dimmi cosa vuoi fare: ");
			System.out.println("1 -> Aggiungi piatto al menu");
			System.out.println("2 -> Rimuovi piatto del menu");
			System.out.println("3 -> Modifica piatto del menu");
			System.out.println("4 -> Visualizza menu");
			System.out.println("5 -> Accetta ordine");
			System.out.println("6 -> Visualizza ordini in corso");
			System.out.println("7 -> Visualizza cronologia ordini");
			System.out.println("8 -> logout");
			scelta=sc.nextInt();
			
			switch(scelta) {
				case 1:
					aggiungiPiattoAlMenu();
					break;
				case 2:
					rimuoviPiattoDaMenu();
					break;
				case 3:
					modificaPiatto();
					break;
				case 4:
					MioDatabase.getInstance().visualizzaMenu(loggato).forEach(System.out::println);
					break;
				case 5:
					accettaOrdine();
					break;
				case 6:
					MioDatabase.getInstance().visualizzaOrdiniInCorso(loggato);
					break;
				case 7: 
					MioDatabase.getInstance().visualizzaOrdiniPassati(loggato);
					break;
				case 8:
					loggato=null;
					System.out.println("Arrivederci");
			}
			
		} while(scelta!=8);
		
	}
	
	private static void menuClienteLoggato() {
		Scanner sc = new Scanner(System.in);
		int scelta;
		
		do {
			System.out.println("Dimmi cosa vuoi fare: ");
			System.out.println("1 -> Visualizza Ristoranti");
			System.out.println("2 -> Ricerca ristorante per nome");
			System.out.println("3 -> Visualizza ordini in corso");
			System.out.println("4 -> Visualizza ordini passati");
			System.out.println("5 -> logout");
			scelta=sc.nextInt();
			
			switch(scelta) {
				case 1:
					ArrayList<Ristorante> ristoranti = MioDatabase.getInstance().visualizzaRistoranti();
					
					for(Ristorante r : ristoranti) {
						System.out.println(r.toString());
					}
					
					break;
				case 2:
					ricercaRistorante();
					break;
				case 3:
					MioDatabase.getInstance().visualizzaOrdiniInCorso(loggato);
					break;
				case 4:
					MioDatabase.getInstance().visualizzaOrdiniPassati(loggato);
					break;
				case 5:
					loggato=null;
					System.out.println("Arrivederci");
			}
			
		} while(scelta!=5);
		
	}
	
	private static void menuRiderLoggato() {
		Scanner sc = new Scanner(System.in);
		int scelta;
		
		do {
			System.out.println("Dimmi cosa vuoi fare: ");
			System.out.println("1 -> Visualizza nuovi ordini");
			System.out.println("2 -> Visualizza ordini in corso");
			System.out.println("3 -> Visualizza ordini consegnati");
			System.out.println("4 -> logout");
			scelta=sc.nextInt();
			
			switch(scelta) {
				case 1:
					ArrayList<Ordine> nuovi_ordini = MioDatabase.getInstance().nuoviOrdini(loggato);
					ArrayList<Long> ids = new ArrayList<Long>();
					
					for(Ordine o : nuovi_ordini) {
						System.out.println(o.toString());
						ids.add(o.getId());
					}
					
					System.out.println("Vuoi prendere in carico un ordine? Premi 1 per sì");
					long pc = sc.nextLong();
					
					if(pc == 1) {
						System.out.println("inserisci l'id ordine da accettare");
						pc = sc.nextLong();
						
						for(long i : ids) {
							
							if(pc == i) {
								MioDatabase.getInstance().accettaOrdine(pc, loggato);
								System.out.println("Ordine preso in carico!");
								return;
							}
							
						}
						
						System.out.println("Id errato!");
						
					}
					
					break;
				case 2:
					ArrayList<Long> ids2 = new ArrayList<Long>();
					ids2 = MioDatabase.getInstance().ordiniInCorsoRider(loggato);
					
					for(Long j : ids2) {
						MioDatabase.getInstance().visualizzaOrdine(j);
					}
					
					System.out.println("Vuoi consegnare un ordine? Premi 1 per sì");
					long con = sc.nextLong();
					
					if(con == 1) {
						System.out.println("inserisci l'id ordine da consegnare");
						pc = sc.nextLong();
						
						for(long jj : ids2) {
							
							if(pc == jj) {
								MioDatabase.getInstance().consegnaOrdine(pc, loggato);
								return;
							}
							
						}
						
						System.out.println("Id erratto!");
					}
					
					break;
				case 3:
					ArrayList<Long> ids3 = new ArrayList<Long>();
					ids3 = MioDatabase.getInstance().ordiniConsegnatiRider(loggato);
					
					for(Long k : ids3) {
						MioDatabase.getInstance().visualizzaOrdine(k);
					}
					
					break;
				case 4:
					loggato=null;
					System.out.println("Arrivederci");
			}
			
		} while(scelta!=4);
		
	}
	
	private static void aggiungiPiattoAlMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("dimmi il nome: ");
		String nome=sc.nextLine();
		System.out.println("dimmi la descrizione: ");
		String descrizione=sc.nextLine();
		System.out.println("dimmi il tipo [Primo piatto(0), Secondo piatto(1), Dolce(2), Bevande(3), Pizze(4): ");
		int tipo=sc.nextInt();
		System.out.println("dimmi il prezzo: ");
		double prezzo=sc.nextDouble();
		Piatto p = new Piatto(nome,descrizione,tipo,prezzo);
		MioDatabase.getInstance().aggiungiPiatto(p, loggato);
	}
	
	private static void rimuoviPiattoDaMenu() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nome piatto: ");
		String nome=sc.nextLine();
		MioDatabase.getInstance().rimuoviPiattoDaMenu(nome, loggato);
	}
	private static void modificaPiatto() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Nome piatto: ");
		String nome=sc.nextLine();
		
		if(MioDatabase.getInstance().piattoExists(nome, loggato) != 0L) {
			System.out.println("Inserisci il nuovo nome");
			String nuovo_nome = sc.nextLine();
			System.out.println("Inserisci la nuova descrizione");
			String nuova_descrizione = sc.nextLine();
			System.out.println("Inserisci il nuovo tipo");
			int nuovo_tipo = sc.nextInt();
			System.out.println("Inserisci il nuovo prezzo");
			double nuovo_prezzo = sc.nextDouble();
			MioDatabase.getInstance().modificaPiatto(MioDatabase.getInstance().piattoExists(nome, loggato), nuovo_nome, nuova_descrizione, nuovo_tipo, nuovo_prezzo);
		} else {
			System.out.println("Errore! Nome piatto sbagliato!");
		}
		
	}
	
	private static void accettaOrdine() {
		
		MioDatabase.getInstance().nuoviOrdini(loggato).forEach(System.out::println);
		
		Scanner sc = new Scanner(System.in);
		System.out.println("Per accettare tutti gli ordini, premi 0; per accettare un ordine premi 1; inserisci un altro numero per uscire");
		int scelta = sc.nextInt();
		
		switch(scelta) {
			case 0:
				for(Ordine o : MioDatabase.getInstance().nuoviOrdini(loggato)) {
					MioDatabase.getInstance().accettaOrdine(o.getId(), loggato);
				}
				break;
			case 1:
				System.out.println("Inserisci l'id dell'ordine da accettare");
				long id = sc.nextLong();
				MioDatabase.getInstance().accettaOrdine(id, loggato);
			default: 
				return;
		}
		
	}
	
	private static void ricercaRistorante() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Inserisci il nome del ristorante");
		String nome = sc.nextLine();
		Ristorante r = MioDatabase.getInstance().ricercaRistorante(loggato, nome);
		System.out.println("Desideri visualizzare il menu? Premi 1 per sì");
		int m = sc.nextInt();
		
		if(m == 1) {
			
			MioDatabase.getInstance().visualizzaMenu(r).forEach(System.out::println);
			
			System.out.println("Desideri ordinare da questo ristorante? Premi 1 per sì");
			int or = sc.nextInt();
			if(or == 1) {
				Ordine o = MioDatabase.getInstance().creaOrdine(loggato, r);
				int c;
				
				do {
					 sc = new Scanner(System.in);
					System.out.println("Inserisci il nome del piatto");
					String nome_piatto = sc.nextLine();
					
					for(Piatto p : MioDatabase.getInstance().visualizzaMenu(r)) {
						
						if(nome_piatto.toLowerCase() == p.getNome().toLowerCase()) {
							MioDatabase.getInstance().aggiungiPiattoAOrdine(p, loggato, o);
							System.out.println("Piatto aggiunto!");
						}
					
				}
				
				System.out.println("Desisideri aggiungere altro? Premi 1 per aggiungere, premi 2 per annullare ordine, premi qualsiasi altro numero per confermare.");
				c = sc.nextInt();
				} while(c == 1);
				
				if(c == 2) {
					MioDatabase.getInstance().eliminaOrdine(o);
				} else {
					System.out.println("Ordine confermato!");
				}
				
			}
			
		} 
		
	}
			
}

