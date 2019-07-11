package it.polito.tdp.borders.db;

import it.polito.tdp.borders.model.Adiacenza;
import it.polito.tdp.borders.model.Country;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BordersDAO {
	
	public List<Country> loadAllCountries(Map<Integer, Country> idMap) {
		
		String sql = 
				"SELECT ccode,StateAbb,StateNme " +
				"FROM country " +
				"ORDER BY StateAbb " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c = new Country(
						rs.getInt("ccode"),
						rs.getString("StateAbb"), 
						rs.getString("StateNme")) ;
				
				list.add(c) ;
				idMap.put(c.getcCode(),c);
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public List<Country> getVertex(Integer anno, Map<Integer, Country> idMap) {
		String sql = 
				"SELECT DISTINCT co.state1no " + 
				"FROM contiguity co " + 
				"WHERE co.YEAR<=?  " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery() ;
			
			List<Country> list = new LinkedList<Country>() ;
			
			while( rs.next() ) {
				
				Country c= idMap.get(rs.getInt("co.state1no"));
				
				list.add(c) ;
				
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;
	}
	
	public List<Adiacenza> getEdges(Integer anno) {
		
		String sql = 
				"SELECT co.state1no, co.state2no " + 
				"FROM contiguity co " + 
				"WHERE co.YEAR<=? AND co.conttype=1 AND co.state1no> co.state2no " ;

		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet rs = st.executeQuery() ;
			
			List<Adiacenza> list = new LinkedList<Adiacenza>() ;
			
			while( rs.next() ) {
				
				Adiacenza a= new Adiacenza(rs.getInt("co.state1no"), rs.getInt("co.state2no"),0);
				
				list.add(a) ;
				
			}
			
			conn.close() ;
			
			return list ;
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null ;

	}
	
	public static void main(String[] args) {
		List<Country> list ;
		BordersDAO dao = new BordersDAO() ;
		//list = dao.loadAllCountries() ;
		//for(Country c: list) {
		//	System.out.println(c);
		}

	
	}

	
	
	

