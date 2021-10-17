import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


import java.io.BufferedReader;
import java.io.FileReader;
public class RetrievalSystem {

    public static void main(String args[]) throws IOException {

        List<Tweet> collection = new ArrayList<Tweet>();
        List<Query> queryList = new ArrayList<Query>(49);


        try {
           File input = new File("Trec_microblog11 copy.txt");
            // File input = new File("Trec_microblog11.txt");
            FileReader fr = new FileReader(input);
            BufferedReader bufferReader = new BufferedReader(fr);


            String line;
            boolean isFirstLine = true;
            while ((line = bufferReader.readLine()) != null) {
                if(isFirstLine){
                  isFirstLine = false;
                  line = line.substring(3);
                }

                String[] message = line.substring(18).split("\\s+");
                List<String> tweetMessage = Arrays.asList(message);
                for (String word: tweetMessage) {
                    word = word.replaceAll("[^a-zA-Z]","");
                }

                //changed from 17 to 15 
                Long twtID = Long.valueOf(line.substring(0,15));
              
                Tweet tweet = new Tweet(twtID, tweetMessage);

                collection.add(tweet);

            }


            bufferReader.close();


        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;

        }



        InvertedIndex invertedIndex = new InvertedIndex();

        //File f = new File("Processed.txt");
        File f = new File("preprocessingresult.txt");

        FileReader fr = new FileReader(f);
        BufferedReader bufferReader = new BufferedReader(fr);


        String line;
        // each line of preprocessing results
        while ((line = bufferReader.readLine()) != null) {

            for (Tweet tweet: collection ) {
                int wordFreq = 0;
                //count how many time word from preprocessingresults is in the tweet
                for (String wordInTweet: tweet.getTweet()) {
                    if (wordInTweet.toLowerCase().equals(line)) {
                        wordFreq++;

                    }

                }

                //if not 0, add to the index 
                if (!(wordFreq == 0)){
                  invertedIndex.addToIndex(line, new documentTfTuple(tweet.getTweetID(), wordFreq));
                  // System.out.println(tweet.getTweetID());
                }

            }

        }
        invertedIndex.printIndex();
        bufferReader.close();


        //get the user input for the query
        // change this to for loop 
        // Scanner userinput = new Scanner(System.in);
        // String query = userinput.nextLine(); //read the string

        File input = new File("topics_MB1-49.txt");
        FileReader reader = new FileReader(input);
        BufferedReader bReader = new BufferedReader(reader);

        String row;
        String query = "";
        int queryID= -1;

        while ((row = bReader.readLine()) != null) {

          if(row.startsWith("<title>")){
				
            //find the </title> index
            int i = row.indexOf("</title>");  
            query = row.substring(8, i);
            // System.out.println(query);
        } else if (row.startsWith("<num>")) {
          
				
            //find the MB index
            int i = row.indexOf("MB");
            queryID = Integer.parseInt(row.substring(i+2, i+5));

            
            // queryID = Integer.parseInt(_num);
            // System.out.println(queryID);

        } else if(row.startsWith("</top>")){
				          
          //end of test query, create a query test and add to result
          queryList.add(new Query(queryID,query));
          
        }
      }

      for (Query q: queryList) {
        // System.out.println(q.getqueryID());
        // System.out.println(q.getquery());
        query = q.getquery();
         //from this point on, all the lines of code can be in a sepcialized class
        //List<String> queryWords = Arrays.asList(query.split("\\s+"));
        String[] queryWords = query.split("\\s+");
        // for (String i: queryWords) {
        //   System.out.println("\\" + i);
        // }
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

            // System.out.println(final_score + ", ID: " + docIDRanking);
            Double ranking_key = Double.valueOf(final_score);
            if(!ranking.containsKey(ranking_key)){
              List<Long> rank_array = new ArrayList<Long>();
                rank_array.add(docIDRanking);
                ranking.put(ranking_key,rank_array);
            }else{
                  ranking.get(ranking_key).add(docIDRanking);
            }

        }

        // TO CHANGE: GET ACTUAL QUERY ID

        rankingOperations.showResults(ranking, "MB" + q.getqueryID());
 


    }

      }




       
  }

    




        //     // for each word from the preprocessor

        //     // loop through each tweet and note down how many times that word appears in each tweet


        //     // File input = new File("Trec_microblog11 copy.txt");
        //     // // File input = new File("Trec_microblog11.txt");

        //     // System.out.println("Opened successfully");
        //     // Scanner scanner = new Scanner(input);

        //     // // while (scanner.hasNext()) {
        //     // //     // change word to lower case
        //     // //     String word = scanner.next().toLowerCase();
        //     // //     System.out.println(word.charAt(0));


        //     // // }

        //     // while (scanner.hasNextLine()) {
        //     //     String tweetID = scanner.nextLine();


        //     //     String arr[] = tweetID.split("	", 2);
        //     //     System.out.println(arr[0]);

        //     // }
        //     // scanner.close();
