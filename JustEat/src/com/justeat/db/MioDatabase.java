package com.justeat.db;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.justeat.model.Cliente;
import com.justeat.model.Ordine;
import com.justeat.model.Piatto;
import com.justeat.model.Rider;
import com.justeat.model.Ristorante;
import com.justeat.model.Utente;

public class MioDatabase {
private static MioDatabase instance = new MioDatabase();
	
	private MioDatabase() {}
	
	public static MioDatabase getInstance() {
		return instance;
	}
	
	private Connection getConnection() throws SQLException{
		String url = "jdbc:mysql://localhost:3306/justeat?servereTimezone=Europe/Rome";
		String username = "root";
		String password = "root";
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return DriverManager.getConnection(url, username, password);
	}
	
	public boolean registraUtente(Utente u) {
		int righe=0;
		if(u instanceof Ristorante) {
			String query="insert into utente(username,password,indirizzo,cap,nome,telefono,tipo) values (?,?,?,?,?,?,?)";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setString(1, u.getUsername());
				st.setString(2, u.getPassword());
				st.setString(3, u.getIndirizzo());
				st.setString(4, u.getCap());
				st.setString(5, u.getNome());
				st.setString(6, u.getTelefono());
				st.setInt(7, 0);
				righe = st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(u instanceof Cliente) {
			String query="insert into utente(username,password,indirizzo,cap,nome,cognome,telefono,tipo) values (?,?,?,?,?,?,?,?)";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setString(1, u.getUsername());
				st.setString(2, u.getPassword());
				st.setString(3, u.getIndirizzo());
				st.setString(4, u.getCap());
				st.setString(5, u.getNome());
				st.setString(6, u.getCognome());
				st.setString(7, u.getTelefono());
				st.setInt(8, 1);
				righe = st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(u instanceof Rider) {
			String query="insert into utente(username,password,indirizzo,cap,nome,cognome,telefono,tipo) values (?,?,?,?,?,?,?,?)";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setString(1, u.getUsername());
				st.setString(2, u.getPassword());
				st.setString(3, u.getIndirizzo());
				st.setString(4, u.getCap());
				st.setString(5, u.getNome());
				st.setString(6, u.getCognome());
				st.setString(7, u.getTelefono());
				st.setInt(8, 2);
				righe = st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return righe==1;
		
		}
	
	public boolean rimuoviUtente(String username) {
			String query="delete from Utente where username = ?";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setString(1, username);
				if(st.executeUpdate()==1) return true;
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return false;
	}
	
	public Utente login(String username, String password) {
		String query ="select * from utente where username = ? and password = ?";
		try (Connection c = getConnection(); PreparedStatement st = c.prepareStatement(query)){
			st.setString(1, username);
			st.setString(2, password);
			ResultSet rs =st.executeQuery();
				if(rs.next()) {
						long id =rs.getLong("id");
						String telefono=rs.getString("telefono");
						String indirizzo = rs.getString("indirizzo");
						String cap = rs.getString("cap");
						String nome = rs.getString("nome");
						if(rs.getInt("tipo")==1) {
							String cognome = rs.getString("cognome");
							return new Cliente(id,username,password,telefono,nome,cognome,indirizzo,cap);
						}else if(rs.getInt("tipo")==0) {
							return new Ristorante(id, username, password,telefono,nome,indirizzo,cap);
						}else if(rs.getInt("tipo")==2) {
							String cognome = rs.getString("cognome");
							return new Rider(id,username,password,telefono,nome,cognome,indirizzo,cap);
						}
				}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Password o Username errati!");
		return null;
	}
	
	public void aggiungiPiatto (Piatto p, Utente u) {
		if(u instanceof Ristorante) {
			Ristorante r = (Ristorante)u;
			String query="insert into Piatto(nome,descrizione,tipo, id_utente, prezzo) values (?,?,?,?,?)";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setString(1, p.getNome());
				st.setString(2, p.getDescrizione());
				st.setInt(3, p.getTipo());
				st.setLong(4, u.getId());
				st.setDouble(5, p.getPrezzo());
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else System.out.println("Non puoi modificre il menu");
	}
	
	public void aggiungiPiattoAOrdine(Piatto p, Utente u, Ordine o) {
		if(u instanceof Cliente) {
		String query="insert into Ordine_Piatto(id_ordine,id_piatto) values (?,?)";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, o.getId());
				st.setLong(2, p.getId());
				st.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}else System.out.println("Non puoi modificare un ordine");

	}
	
	public void rimuoviPiattoDaMenu(String nome, Utente u) {
		if(u instanceof Ristorante) {
				String query="select id from Piatto where nome=? and id_utente=?";
				String query1="delete from Piatto where id=?";
				String query2="select id_ordine from ordine_piatto where id_piatto = ?";
				String query3 = "update ordine set isAttivo = 1 where id_ordine =?";
				try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query); PreparedStatement st1 = c.prepareStatement(query1);
						PreparedStatement st2 = c.prepareStatement(query2);PreparedStatement st3 = c.prepareStatement(query3)){
					st.setString(1,nome);
					st.setLong(2, u.getId());
					long id;
					ResultSet rs = st.executeQuery();
					if(rs.next()) {
						id=rs.getLong("id");
						st1.setLong(1, id );
						st1.executeUpdate();
						st2.setLong(1, id);
						rs = st2.executeQuery();
						if(rs.next()) {
							id=rs.getLong("id");
							st3.setLong(1, id);
							st3.executeUpdate();
						}
						
					}
					System.out.println("Piatto eliminato!");
				}catch(SQLException e) {
					e.printStackTrace();
				}
		}else System.out.println("Non puoi modificare il menu");
	}
	
	public void modificaPiatto(long id, String nuovo_nome, String nuova_descrizione, int nuovo_tipo, double nuovo_prezzo) {
			String query = "update piatto set  nome = ?, descrizione = ? , tipo = ?, prezzo=? where id = ?";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setString(1, nuovo_nome);
				st.setString(2, nuova_descrizione);
				st.setInt(3, nuovo_tipo);
				st.setDouble(4, nuovo_prezzo);
				st.setLong(5, id);
				st.executeUpdate();
			}catch(SQLException e) {
				e.printStackTrace();
			}
	}
	public ArrayList<Ordine> nuoviOrdini(Utente u) {
		
		if(u instanceof Ristorante) {
			ArrayList<Ordine> ordini = new ArrayList<Ordine>();
			String query = "select * from ordine where id_utente=? and isaccettato=1";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, u.getId());
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					ordini.add(new Ordine(rs.getLong("id"), rs.getString("stato")));
				}
				return ordini;
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return null;
		} else if(u instanceof Rider) {
			ArrayList<Ordine> ordini = new ArrayList<Ordine>();
			ArrayList<Ordine> filtrati = new ArrayList<Ordine>();
			String query = "select * from ordine where isaccettato=0";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					ordini.add(new Ordine(rs.getLong("id"), rs.getString("stato")));
				}
				filtrati = filtraOrdine(ordini);
				return filtrati;
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return null;
		}
		return null;
	 }
	private ArrayList<Ordine> filtraOrdine(ArrayList<Ordine> ordini) {
		ArrayList<Ordine> filtrati = new ArrayList<Ordine>();
		ArrayList<Long> da_filtrare = new ArrayList<Long>();
		String query = "select * from ordine_rider";
		try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				da_filtrare.add(rs.getLong("id_ordine"));
			}
			
			boolean v;
			
			for(Ordine o : ordini) {
				v = false;
				
				for(long o2 : da_filtrare) {
					
					if(o.getId() == o2) {
						v = true;
					}
					
				}
				
				if(v == false) {
					filtrati.add(o);
				}
				
			}
			
			return filtrati;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void accettaOrdine (long id, Utente u) {
		if(u instanceof Ristorante) {
			String query = "update ordine set isAccettato=0, isInCorso=0 where id = ?";
			try(Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, id);
				int v = st.executeUpdate();
				
				if(v == 1) {
					System.out.println("Ordine accettato!");
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else if(u instanceof Rider) {
			String query="insert into ordine_rider (id_ordine,id_utente, consegnato) values (?,?,1)";
			try(Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, id);
				st.setLong(2, u.getId());
				st.executeUpdate();
				accettaOrdineParteDue(id, u);
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void accettaOrdineParteDue(long id, Utente u) {
		
		if(u instanceof Rider) {
			String query = "update ordine set stato='in consegna' where id=?";
			
			try(Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, id);
				int v = st.executeUpdate();
				
				if(v == 1) {
					System.out.println("Stato in consegna!");
				}
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	public ArrayList<Long> ordiniInCorsoRider(Utente u) {
		
		if(u instanceof Rider) {
			ArrayList<Long> ids = new ArrayList<Long>();
			String query = "select * from ordine_rider where id_utente=? and consegnato=1";
			try(Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, u.getId());
				ResultSet rs = st.executeQuery();
				
				while(rs.next()) {
					ids.add(rs.getLong("id_ordine"));
				}
				
				return ids;
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public void visualizzaOrdine(long id) {
		String query = "select * from ordine where id=?";
		try(Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
			st.setLong(1, id);
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				long id_o = rs.getLong("id");
				String stato_o = rs.getString("stato");
				Ordine o = new Ordine(id_o, stato_o);
				System.out.println(o.toString());
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void consegnaOrdine(long id, Utente u) {
		
		if(u instanceof Rider) {
			String query = "update ordine set stato='consegnato', isattivo=0, isincorso=1 where id=?";
			try(Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, id);
				int v = st.executeUpdate();
				
				if(v == 1) {
					System.out.println("Consegnato!");
				}
				
				consegnaOrdineParteDue(id, u);
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void consegnaOrdineParteDue(long id, Utente u) {
		String query = "update ordine_rider set consegnato=0 where id_utente=? and id_ordine=?";
		try(Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
			st.setLong(1, u.getId());
			st.setLong(2, id);
			int v = st.executeUpdate();
			
			if(v == 1) {
				System.out.println("Registrato come consegnato!");
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<Long> ordiniConsegnatiRider(Utente u) {
		
		if(u instanceof Rider) {
			ArrayList<Long> ids = new ArrayList<Long>();
			String query = "select * from ordine_rider where id_utente=? and consegnato=0";
			try(Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, u.getId());
				ResultSet rs = st.executeQuery();
				
				while(rs.next()) {
					ids.add(rs.getLong("id_ordine"));
				}
				
				return ids;
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public void visualizzaOrdiniPassati (Utente u) {
		if(u instanceof Ristorante) {
			String query = "select * from Ordine where id_utente =? and isInCorso=1";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, u.getId());
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					long id = rs.getLong("id");
					String stato = rs.getString("stato");
					Ordine o = new Ordine(id,stato);
					System.out.println(o.toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else if(u instanceof Cliente) {
			String query = "select * from Ordine where id_cliente =? and isInCorso=1";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, u.getId());
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					long id = rs.getLong("id");
					String stato = rs.getString("stato");
					Ordine o = new Ordine(id,stato);
					System.out.println(o.toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void visualizzaOrdiniInCorso (Utente u) {
		if(u instanceof Ristorante) {
			String query = "select * from Ordine where id_utente =? and isInCorso=0";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, u.getId());
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					long id = rs.getLong("id");
					String stato = rs.getString("stato");
					Ordine o = new Ordine(id,stato);
					System.out.println(o.toString());
					
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}else if(u instanceof Cliente) {
			String query = "select * from Ordine where id_cliente =? and isInCorso=0";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, u.getId());
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					long id = rs.getLong("id");
					String stato = rs.getString("stato");
					Ordine o = new Ordine(id,stato);
					System.out.println(o.toString());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public void visualizzaStatoOrdine (Utente u) {
		if (u instanceof Cliente) {
			String query = "select * from Ordine where id_cliente = ?";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setLong(1, u.getId());
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					long id = rs.getLong("id");
					String stato = rs.getString("stato");
					Ordine o = new Ordine(id,stato);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	public Ristorante ricercaRistorante(Utente u, String nome) {
		if(u instanceof Cliente) {
			Ristorante ristorante;
			String query = "select * from utente where nome= ?";
			try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
				st.setString(1, nome);
				ResultSet rs = st.executeQuery();
				while(rs.next()) {
					ristorante = new Ristorante(rs.getLong("id"),rs.getString("username"), rs.getString("password"), rs.getString("telefono"),nome, rs.getString("indirizzo"), rs.getString("cap"));
					System.out.println(ristorante.toString());
					return ristorante;
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Ristorante non trovato");
			return null;
		}
		return null;
	}
	
	public ArrayList<Piatto> visualizzaMenu (Utente u) {
		ArrayList<Piatto> menu = new ArrayList<Piatto>();
		String query = "select * from piatto where id_utente=?";
		try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
			st.setLong(1, u.getId());
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				menu.add(new Piatto(rs.getLong("id"), rs.getString("nome"), rs.getString("descrizione"), rs.getInt("tipo"), rs.getDouble("prezzo")));
			}
			return menu;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public ArrayList<Ristorante> visualizzaRistoranti() {
		ArrayList<Ristorante> r = new ArrayList<Ristorante>();
		String query="select * from utente where tipo=0";
		try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				r.add(new Ristorante(rs.getLong("id"), rs.getString("username"), rs.getString("password"), rs.getString("telefono"), rs.getString("nome"), rs.getString("indirizzo"), rs.getString("cap")));
			}
			return r;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public long piattoExists(String nome, Utente u) {
		if(u instanceof Ristorante) {
		String query = "select * from piatto where nome=? and id_utente=?";
		try (Connection c=getConnection(); PreparedStatement st = c.prepareStatement(query)){
			st.setString(1, nome);
			st.setLong(2, u.getId());
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				long id = rs.getLong("id");
				return id;
			}
			
			if(st.executeUpdate()==1)
				return 0L;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
		return 0L;
	}
	
	public Ordine creaOrdine(Utente u, Utente r) {
		if(u instanceof Cliente) {
			String query = "insert into Ordine (isincorso, isaccettato, stato, id_utente, id_cliente, isattivo) values(1,1,'in elaborazione', ?, ?,1)";
			
			try (Connection c = getConnection(); PreparedStatement st = c.prepareStatement(query)){
						st.setLong(1, r.getId());
						st.setLong(2, u.getId());
						st.executeUpdate();
			} catch(SQLException e) {
				e.printStackTrace();
			}
			
			String query2 = "select * from ordine where id_utente=? and id_cliente=? order by id desc limit 1";
			Ordine o;
			
			try (Connection c = getConnection(); PreparedStatement st1 = c.prepareStatement(query2)){
				st1.setLong(1, r.getId());
				st1.setLong(2, u.getId());
				ResultSet rs = st1.executeQuery();
				
				if(rs.next()) {
					o = new Ordine(rs.getLong("id"), rs.getString("stato"));
					return o;
				}
				
			} catch(SQLException e) {
				e.printStackTrace();
				return null;
			}
			
		}
		
		return null;
	}
	
	public void eliminaOrdine (Ordine o){
		String query = "delete from Ordine where id = ?";
		String query1 = "select * from ordine_piatto where id_ordine=?";
		String query2 = "delete from ordine_piatto where id_ordine =?";
		try (Connection c = getConnection(); PreparedStatement st = c.prepareStatement(query); 
		PreparedStatement st1 = c.prepareStatement(query1); PreparedStatement st2 = c.prepareStatement(query2)){
			st.setLong(1, o.getId());
			st.executeUpdate();
			st1.setLong(1, o.getId());
			ResultSet rs =st1.executeQuery();
				while(rs.next()) {
						st2.setLong(1,o.getId());
						st2.executeUpdate();
				}
			System.out.println("Ordine annullato!");
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

}



		
		
		
		
		
		