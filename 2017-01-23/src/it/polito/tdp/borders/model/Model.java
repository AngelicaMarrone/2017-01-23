package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
	//inserire tipo di dao

		private BordersDAO dao;

		

		//scelta valore mappa

		private Map<Integer,Country> idMap;

		

		//scelta tipo valori lista

		private List<Country> vertex;

		

		//scelta tra uno dei due edges

		private List<Adiacenza> edges;

		

		//scelta tipo vertici e tipo archi

		private Graph<Country,DefaultEdge> graph;


		public Model() {

			

			//inserire tipo dao

			dao  = new BordersDAO();

			//inserire tipo values

			idMap = new HashMap<Integer,Country>();

		}

		

		public String creaGrafo(Integer anno) {

			

			//scelta tipo vertici e archi

			graph = new SimpleGraph<Country,DefaultEdge>(DefaultEdge.class);
			
			dao.loadAllCountries(idMap);
			

			//scelta tipo valori lista

			vertex = new ArrayList<Country>(dao.getVertex(anno,idMap));

			Graphs.addAllVertices(graph,vertex);

			

			edges = new ArrayList<Adiacenza>(dao.getEdges(anno));

			

			for(Adiacenza a : edges) {

				

				//CASO BASE POTRESTI DOVER AGGIUNGERE CONTROLLI

				Country source = idMap.get(a.getId1());

				Country target = idMap.get(a.getId2());

			

			graph.addEdge(source, target);

				System.out.println("AGGIUNTO ARCO TRA: "+source.toString()+" e "+target.toString());

				

			}

			

			System.out.println("#vertici: "+graph.vertexSet().size());

			System.out.println("#archi: "+graph.edgeSet().size());

			String ris= trovaConfinanti();
			
			return ris;
		}



		private String trovaConfinanti() {
			
			List<ViciniPeso> vp= new LinkedList<>();
			for(Country c: vertex)
			{
				
				int confinanti= Graphs.neighborListOf(graph, c).size();
				ViciniPeso v= new ViciniPeso(c,confinanti);
				vp.add(v);
				
				
			}
			
			Collections.sort(vp);
			
			String ris= "Nazione e confinanti in ordine decrescente:\n";
			
			for (ViciniPeso p: vp)
			{
				ris+= p.getC().getStateName()+ " " + p.getPeso()+ "\n";
			}
			
			return ris;
		}

}
