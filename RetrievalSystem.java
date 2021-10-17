import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.io.FileWriter;

import java.io.BufferedReader;
import java.io.FileReader;
public class RetrievalSystem {

    public static void main(String args[]) throws IOException {
        Set<String> words = new HashSet<String>();
        ArrayList<String> stopWords = getStopWords();
        List<Tweet> collection = new ArrayList<Tweet>();

        try {

            File input = new File("Trec_microblog11 copy.txt");

            FileReader fr = new FileReader(input);
            BufferedReader bufferReader = new BufferedReader(fr);


            String line;
            boolean isFirstLine = true;
            while ((line = bufferReader.readLine()) != null) {
                if(isFirstLine) {
                    isFirstLine = false;
                    line = line.substring(1);
                }
  

                // split lines by space
                String[] message = line.toLowerCase().substring(18).replaceAll("http://[^\\s]+", "").replaceAll("[^a-zA-Z]"," ").split("\\s+");
                List<String> tweetMessage = Arrays.asList(message);
                for (String word: tweetMessage) {
                    words.add(word);
                }

                Long twtID = Long.valueOf(line.substring(0,17));
              
                Tweet tweet = new Tweet(twtID, tweetMessage);

                collection.add(tweet);
            }

              // remove all the stop words
            words.removeAll(stopWords);
            
            Writer writer = new FileWriter("preprocessingresult.txt", false); //overwrites file
            for (String word: words) { 
                writer.write(word + System.lineSeparator());
            
            }

            writer.close();



            bufferReader.close();
            System.out.println("collection end");


        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;

        }



        InvertedIndex invertedIndex = new InvertedIndex();
        for (Tweet tweet: collection ) {
            // System.out.println(tweet.getTweet());

            int wordFreq = 0;
            for (String wordInTweet: tweet.getTweet()) {
                wordInTweet = wordInTweet.toLowerCase(); 
                wordFreq = tweet.getWordFrequency(wordInTweet);

                if (words.contains(wordInTweet)) {
                    if (!invertedIndex.getInvertedIndex().containsKey(wordInTweet)) {
                        
                        invertedIndex.addNewTermToIndex(wordInTweet, new documentTfTuple(tweet.getTweetID(), wordFreq));
    
                    } else {
                        if (!invertedIndex.tupleExists(wordInTweet, tweet.getTweetID())) {
                            invertedIndex.addExistingTermToIndex(wordInTweet, new documentTfTuple(tweet.getTweetID(), wordFreq));

                        }


                    }

                }
            }
            

        

        }
        invertedIndex.printIndex();


        //get the user input for the query
        Scanner userinput = new Scanner(System.in);
        String query = userinput.nextLine(); //read the string

        //from this point on, all the lines of code can be in a sepcialized class
        //List<String> queryWords = Arrays.asList(query.split("\\s+"));
        String[] queryWords = query.split("\\s+");
        int N = collection.size();
        RtrvlRank rankingOperations = new RtrvlRank();
        double queryLength=0.0;

        Map<String, Integer> queryFrequencies = rankingOperations.freq(queryWords);
        Map.Entry<String, Integer> maxFreq = rankingOperations.maxTF(queryFrequencies);


        NavigableMap<Double, List<Long>> ranking = new TreeMap<Double,List<Long>>();
      /*ranking.put(new Float(0.67F),new Long(56378954123L));
        ranking.put(new Float(0.79F),new Long(657487233244L));
        ranking.put(new Float(0.99F),new Long(7852636378524L));
        ranking.put(new Float(0.87F),new Long(83524352424L));
        rankingOperations.showResults(ranking);*/

        Map <Long, ScoreLength> score_length = new HashMap <Long, ScoreLength>();

        Set<String> word_set = new HashSet<String>();
        for (int i = 0; i<queryWords.length;i++){
          word_set.add(queryWords[i]);
        }



        //looping on the list of words in the query
        for (String word: word_set) {
            if(invertedIndex.getMap().containsKey(word)){ //check if a word in the query is found in the index

              //get posting list and DF
              Posting posting = invertedIndex.getMap().get(word);

              //Document Frequence of the current term
              int word_docFrequency = posting.getDocf();
              //The maximum Term Frequence in the Query NOT the Collection
              int maxQueryFreq = maxFreq.getValue();

              //Computing the weight of the term with respect to the query words
              double termWeight = rankingOperations.weight(N,queryFrequencies.get(word),word_docFrequency,true,maxQueryFreq);
              //We will take the square root of this number later.
              queryLength = queryLength + Math.pow(termWeight,2);

              // System.out.print(word);
              // System.out.println(": "+queryFrequencies.get(word) + " "+termWeight);


              for(documentTfTuple doc:posting.getPostingList()){//for each document in the posting list

                int word_termFrequency = doc.getTermFrequency();//term frequency of the word in the document
                Long docID = doc.getTweetID();//get the ID

                //compute the weight of the word in terms of the document
                double docWeight = rankingOperations.weight(N,word_termFrequency,word_docFrequency,false,1);


                if(score_length.containsKey(docID)){

                    ScoreLength score_length_pair = score_length.get(docID);

                    double newScore = score_length_pair.getScore() + (docWeight*termWeight);
                    score_length.get(docID).setScore(newScore);

                    double newLength = score_length_pair.getLength() + Math.pow(docWeight,2);
                    score_length.get(docID).setLength(newLength);
                }else{
                  double newScore =  docWeight*termWeight;
                  double newLength = Math.pow(docWeight,2);
                  score_length.put(docID,new ScoreLength(newScore,newLength));
                }
              }

            }
        }

        for(Map.Entry<Long,ScoreLength> entry:score_length.entrySet()){

            Long docIDRanking = entry.getKey();
            ScoreLength value_node = entry.getValue();
            double final_score = (value_node.getScore())/(Math.sqrt(queryLength*value_node.getLength()));

            System.out.println(final_score + ", ID: " + docIDRanking);
            Double ranking_key = Double.valueOf(final_score);
            if(!ranking.containsKey(ranking_key)){
              List<Long> rank_array = new ArrayList<Long>();
                rank_array.add(docIDRanking);
                ranking.put(ranking_key,rank_array);
            }else{
                  ranking.get(ranking_key).add(docIDRanking);
            }

        }

        rankingOperations.showResults(ranking);


    }
    private static ArrayList<String> getStopWords() throws IOException{
        // extract stop words from stopwords.txt and store in array
        ArrayList<String> stopWords = new ArrayList<String>();

        // open file and store words in array
        try {
            File input = new File("StopWords.txt");
            Scanner scanner = new Scanner(input);

            while (scanner.hasNext()) {
                String word = scanner.next();
                stopWords.add(word);
            }

            // close scanner
            scanner.close();
        }   catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
        return stopWords;


    }
}


