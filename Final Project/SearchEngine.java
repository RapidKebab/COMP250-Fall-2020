package finalproject;

import java.util.HashMap;
import java.util.ArrayList;

public class SearchEngine {
	public HashMap<String, ArrayList<String> > wordIndex;   // this will contain a set of pairs (String, LinkedList of Strings)	
	public MyWebGraph internet;
	public XmlParser parser;

	public SearchEngine(String filename) throws Exception{
		this.wordIndex = new HashMap<String, ArrayList<String>>();
		this.internet = new MyWebGraph();
		this.parser = new XmlParser(filename);
	}
	
	/* 
	 * This does a graph traversal of the web, starting at the given url.
	 * For each new page seen, it updates the wordIndex, the web graph,
	 * and the set of visited vertices.
	 * 
	 * 	This method will fit in about 30-50 lines (or less)
	 */
	public void crawlAndIndex(String url) throws Exception {
		internet.addVertex(url);
		internet.setVisited(url, true);
		internet.setPageRank(url, 1);
		//indexing
		ArrayList<String> words = new ArrayList<String>();
		words.addAll(parser.getContent(url));//list of content
		while(!words.isEmpty()){
			if(!wordIndex.containsKey(words.get(0).toLowerCase())){
				wordIndex.put(words.get(0).toLowerCase(), new ArrayList<String>());
				wordIndex.get(words.get(0).toLowerCase()).add(url);
			}
			else if(!wordIndex.get(words.get(0).toLowerCase()).contains(url))
				wordIndex.get(words.get(0).toLowerCase()).add(url);
			words.remove(0);
		}
		//indexed
		ArrayList<String> verts = parser.getLinks(url);
		for (String vert:verts) {
			if(!internet.getVisited(vert)){
				crawlAndIndex(vert);
			}
			internet.addEdge(url,vert);
		}
		//crawled in my skin
	}
	
	
	
	/* 
	 * This computes the pageRanks for every vertex in the web graph.
	 * It will only be called after the graph has been constructed using
	 * crawlAndIndex(). 
	 * To implement this method, refer to the algorithm described in the 
	 * assignment pdf.
	 * 
	 * This method will probably fit in about 30 lines.
	 */
	public void assignPageRanks(double epsilon) {
		// TODO : Add code here
		boolean converg = false;
		ArrayList<Double> initialRanks = new ArrayList<Double>();
		ArrayList<String> verts = internet.getVertices();
		for(int i = 0; i < verts.size(); i++){
			initialRanks.add(i,internet.getPageRank(verts.get(i)));
		}
		while(converg == false){
			converg = true;
			ArrayList<Double> newRanks = computeRanks(verts);
			for(int i = 0; i < verts.size(); i++){
				if(Math.abs(newRanks.get(i)-initialRanks.get(i))>epsilon)
					converg = false;
				initialRanks.set(i,newRanks.get(i));
			}
		}
	}

	/*
	 * The method takes as input an ArrayList<String> representing the urls in the web graph 
	 * and returns an ArrayList<double> representing the newly computed ranks for those urls. 
	 * Note that the double in the output list is matched to the url in the input list using 
	 * their position in the list.
	 */
	public ArrayList<Double> computeRanks(ArrayList<String> vertices) {
		double d = 0.5;
		ArrayList<Double> ranks = new ArrayList<Double>();
		
		for (int i = 0; i<vertices.size(); i++) {
			double mult = 0;
			ArrayList<String> inFactors = internet.getEdgesInto(vertices.get(i));
			for (String ifw:inFactors) {
				mult += internet.getPageRank(ifw)/internet.getOutDegree(ifw);
			}
			internet.setPageRank(vertices.get(i),(1-d)+d*mult);
			ranks.add(i,internet.getPageRank(vertices.get(i)));
		}
		return ranks;
	}

	
	/* Returns a list of urls containing the query, ordered by rank
	 * Returns an empty list if no web site contains the query.
	 * 
	 * This method should take about 25 lines of code.
	 */
	public ArrayList<String> getResults(String query) {
		query = query.toLowerCase();
		HashMap<String,Double> results = new HashMap<String, Double>();
		ArrayList<String> sites = wordIndex.get(query);
		if(sites==null){
			return new ArrayList<String>();
		}
		for (String url: sites) {
			results.put(url,internet.getPageRank(url));
		}
		//return Sorting.slowSort(results);
		return Sorting.fastSort(results);
	}
}
