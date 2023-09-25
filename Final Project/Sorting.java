package finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry; // You may need it to implement fastSort

public class Sorting {

	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n^2) as it uses bubble sort, where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> slowSort (HashMap<K, V> results) {
        ArrayList<K> sortedUrls = new ArrayList<K>();
        sortedUrls.addAll(results.keySet());	//Start with unsorted list of urls

        int N = sortedUrls.size();
        for(int i=0; i<N-1; i++){
			for(int j=0; j<N-i-1; j++){
				if(results.get(sortedUrls.get(j)).compareTo(results.get(sortedUrls.get(j+1))) <0){
					K temp = sortedUrls.get(j);
					sortedUrls.set(j, sortedUrls.get(j+1));
					sortedUrls.set(j+1, temp);					
				}
			}
        }
        return sortedUrls;                    
    }
    
    
	/*
	 * This method takes as input an HashMap with values that are Comparable. 
	 * It returns an ArrayList containing all the keys from the map, ordered 
	 * in descending order based on the values they mapped to. 
	 * 
	 * The time complexity for this method is O(n*log(n)), where n is the number 
	 * of pairs in the map. 
	 */
    public static <K, V extends Comparable> ArrayList<K> fastSort(HashMap<K, V> results) {
		ArrayList<K> list = new ArrayList<K>();
		list.addAll(results.keySet());
		return mergeSort(list,results);
    }

	private static <K, V extends Comparable> ArrayList<K> mergeSort(ArrayList<K> list, HashMap<K, V> results){
		if(list.size() == 1)
			return list;
		else {
			int mid = (list.size()-1)/2;
			ArrayList<K> list1 = new ArrayList<K>();
			ArrayList<K> list2 = new ArrayList<K>();
			for (int i = 0;i<list.size();i++){
				if(i<=mid)
					list1.add(list.get(i));
				else
					list2.add(list.get(i));
			}
			list1 = mergeSort(list1,results);
			list2 = mergeSort(list2,results);
			return merge(list1,list2,results);
		}
	}

    private static <K, V extends Comparable> ArrayList<K> merge(ArrayList<K> l1, ArrayList<K> l2, HashMap<K, V> results){
		ArrayList<K> mergedList = new ArrayList<K>();
    	while(!l1.isEmpty() && !l2.isEmpty()){
    		if(results.get(l1.get(0)).compareTo(results.get(l2.get(0)))>0)
    			mergedList.add(l1.remove(0));
    		else
				mergedList.add(l2.remove(0));
		}
    	while (!l1.isEmpty())
			mergedList.add(l1.remove(0));
		while (!l2.isEmpty())
			mergedList.add(l2.remove(0));
		return mergedList;
	}

}