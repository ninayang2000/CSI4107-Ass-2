package CSI4107;
import java.util.*;


public class InvertedIndex {
    


    // inverted index will be a hshmap 

    // private Map<String, documentTfTuple> invertedIndex = new Hashmap<>();

    private Map<String, List<documentTfTuple>> invertedIndex = new HashMap<String, List<documentTfTuple>>();


    public void addToIndex(String indexTerm, documentTfTuple tuple) {
        // if index term is not yet in hashmap 
        if(!invertedIndex.containsKey(indexTerm)) {
            // add the term in the hashmap 
            List<documentTfTuple> array = new ArrayList<documentTfTuple>();
            array.add(tuple);
            invertedIndex.put(indexTerm, array);
        }
        // if term already in hash map -- only add the tuple 
        invertedIndex.get(indexTerm).add(tuple);
    }





}