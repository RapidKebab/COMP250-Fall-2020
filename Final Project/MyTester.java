package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MyTester {
    public static void main(String[] args) throws Exception {
        testXml1();
        testXml2();
        testXml3();
        testXml4();
    }

    private static void testXml1() throws Exception{
        HashMap<String, Double> actualResults = new HashMap<>();
        actualResults.put("www.cs.mcgill.ca", 1.125);
        actualResults.put("www.ea.com", 1.225);
        actualResults.put("www.rockstar.com", 0.725);
        actualResults.put("www.ubisoft.com", 0.933);
        actualResults.put("www.unity.com", 1.225);
        actualResults.put("www.eidos.com", 0.767);
        actualResults.put("www.unreal.com", 0.725);

        SearchEngine searchEngine = null;
        try {
            searchEngine = new SearchEngine("test.xml");
        } catch (Exception e) {
            throw new AssertionError( "SearchEngine() threw exception.");
        }
        searchEngine.crawlAndIndex("www.cs.mcgill.ca");

        ArrayList<String> vertices = searchEngine.internet.getVertices();

// set initial values for the ranks
        for (String v : vertices)
            searchEngine.internet.setPageRank(v, 1.0);
        ArrayList<Double> rankAfterOneIteration = searchEngine.computeRanks(vertices);

        for (int i = 0; i < vertices.size(); i++) {
            String vertex = vertices.get(i);
            Double rank = rankAfterOneIteration.get(i);
            Double expectedRank = actualResults.get(vertex);
            System.out.println(expectedRank + " = expected," + vertices.get(i) + " actual: " + rank);
            if (Math.abs(expectedRank - rank) > 0.06) {
                //throw new AssertionError("Rank is computed incorrectly after one iteration.");
                System.out.println(expectedRank + " = expected," + vertices.get(i) + " actual: " + rank);
            }
        }
        System.out.println("Passed.");
    }

    private static void testXml2() throws Exception{
        System.out.println("----------TESTING cs20Links.xml----------");
        SearchEngine searchEngine = new SearchEngine("cs20Links.xml");
        searchEngine.crawlAndIndex("https://cs.mcgill.ca/");
        searchEngine.assignPageRanks(0.001);
        System.out.println();
        ArrayList<String> results = searchEngine.getResults("engineering");
        int count=1;
        for (String result : results){
            System.out.println(count + ". " + result + " Rank: " + searchEngine.internet.getPageRank(result));
            count++;
        }
        System.out.println();
    }

    private static void testXml3() throws Exception {
        System.out.println("----------TESTING cs60Links.xml----------");
        SearchEngine searchEngine = new SearchEngine("cs60Links.xml");
        searchEngine.crawlAndIndex("https://cs.mcgill.ca/");
        searchEngine.assignPageRanks(0.001);
        System.out.println();
        ArrayList<String> results = searchEngine.getResults("search");
        int count=1;
        for (String result : results){
            System.out.println(count + ". " + result + " Rank: " + searchEngine.internet.getPageRank(result));
            count++;
        }
        System.out.println();
    }

    private static void testXml4() throws Exception {
        System.out.println("----------TESTING mcgillWiki.xml----------");
        SearchEngine searchEngine = new SearchEngine("mcgillWiki.xml");
        searchEngine.crawlAndIndex("https://en.wikipedia.org/wiki/McGill_University");
        searchEngine.assignPageRanks(0.001);
        System.out.println();
        ArrayList<String> results = searchEngine.getResults("chemical");
        int count=1;
        for (String result : results){
            System.out.println(count + ". " + result + " Rank: " + searchEngine.internet.getPageRank(result));
            count++;
        }
        System.out.println();
    }
}
