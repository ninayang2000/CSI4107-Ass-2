import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.Math;
import java.util.*;
public class RtrvlRank{

  public RtrvlRank(){

  }

  public double weight(int N,int termFrequency,int docf, boolean isQueryWord, int maxTermFreq){
    double weightTF = 0.0;
    double divisor = 1.0;
    double N_db = (double) N;
    double docf_db = (double) docf;
    double tf_db = (double) termFrequency;
    if(isQueryWord){
      divisor = (double) maxTermFreq;
    }
    if (termFrequency != 0){
      weightTF = (1 + Math.log10(tf_db))/divisor;
    }
    double weightDF = Math.log10(N_db/docf_db);
     return weightTF*weightDF;
  }

  public Map<String, Integer> freq(String[] queryWords){

    Map<String, Integer> frequency = new HashMap<String,Integer>();
    for(int i = 0;i<queryWords.length;i++){
      if(!frequency.containsKey(queryWords[i])){
        frequency.put(queryWords[i],new Integer(1));
      }else{
          frequency.replace(queryWords[i],Integer.valueOf(frequency.get(queryWords[i]).intValue()+1));
      }
    }
    return frequency;
  }
  public Map.Entry<String,Integer> maxTF(Map<String, Integer> mapping){
    Map.Entry<String, Integer> maxEntry = null;
    for(Map.Entry<String,Integer> entry:mapping.entrySet()){
      if (maxEntry == null ||entry.getValue().compareTo(maxEntry.getValue()) > 0){
        maxEntry = entry;
      }
    }
    return maxEntry;
  }

  public void showResults(NavigableMap<Double, List<Long>> map, String queryID) throws IOException{
    Set keys = map.descendingKeySet();
    Iterator i = keys.iterator();
    int rank=1;
    int count = 0;
    Writer writer = new FileWriter("results.txt", true); //appends file
    
    while (i.hasNext()) {
      Double key = (Double) i.next();
      List<Long> value = (List<Long>) map.get(key);
      for(Long docID:value ){
        // System.out.println((queryID + " Q0 "+docID+ " " + rank+" "+ key+" myRun"));
        count++;

          writer.write((queryID + " Q0 "+docID+ " " + rank+" "+ key+" myRun" + System.lineSeparator()));
          rank = rank+1;
      }
    }
    // System.out.println(count);
    writer.close();
  }
}

