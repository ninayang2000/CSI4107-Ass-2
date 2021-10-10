import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;


import java.io.BufferedReader;
import java.io.FileReader;
public class RetrievalSystem {

    public static void main(String args[]) throws IOException {

        List<Tweet> collection = new ArrayList<Tweet>();


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
                Long twtID = new Long(Long.parseLong(line.substring(0,17)));
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
        File f = new File("preprocessed.txt");

        FileReader fr = new FileReader(f);
        BufferedReader bufferReader = new BufferedReader(fr);


        String line;
        while ((line = bufferReader.readLine()) != null) {

            for (Tweet tweet: collection ) {
                int wordFreq = 0;
                for (String wordInTweet: tweet.getTweet()) {
                    if (wordInTweet.toLowerCase().equals(line)) {
                        wordFreq++;

                    }

                }
                if (!(wordFreq == 0)){
                  invertedIndex.addToIndex(line, new documentTfTuple(tweet.getTweetID(), wordFreq));
                }

            }

        }

        invertedIndex.printIndex();
        bufferReader.close();

        //get the user input for the query
        Scanner userinput = new Scanner(System.in);
        String query = userinput.nextLine(); //read the string

        //from this point on, all the lines of code can be in a sepcialized class
        String[] queryWords = query.split("\\s+");
        Map<Long, Float> ranking = new TreeMap<Float,Long>();
        Set<documentTfTuple> setQueryDocs = new HashSet <documentTfTuple>();

        //looping on the list of words in the query
        for (String word: queryWords) {
            if(invertedIndex.getMap().containsKey(word)){ //check if a word in the query is found in the index
              setQueryDocs.addAll(invertedIndex.getMap().get(word).getPostingList());//addAll works like a union operation
              //addAll doesn't duplicate elements
            }
        }

        for(documentTfTuple doc:setQueryDocs){
          /*
          tf = 1 + log(tf) tf = 1 + log(documentTfTuple.getTermFrequency())
          idf= log(N/df)   idf = log(collection.size()/invertedIndex.getMap().get(TERM).getDocf())
          tf-idf =  (1 + log(tf))*(log(N/df))

          divide the weight of the tf of the query words with the maximum frequency in the query
          example query: new airport in new york -> maximum frequency is 2 for new. divide the term frequency of all words in the query by 2
          so for all other words it's going to have a tf of 1/2. a word in the query and in the doc will have both of their term freqcies multiplied
          then divided by the idf.

          after the dot product has been found
          query example
          save world petitions technology question rethink positive outlook technology staffing specialist

          */
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
