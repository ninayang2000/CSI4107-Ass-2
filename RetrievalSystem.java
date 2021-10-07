import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.graalvm.compiler.word.WordOperationPlugin;

import java.io.BufferedReader;
import java.io.FileReader;
public class RetrievalSystem {

    public static void main(String args[]) throws IOException {

        List<Tweet> collection = new ArrayList<Tweet>(); 


        // private ArrayList<Tweet> tweets new ArrayList<>(); 
        try {
            File input = new File("Trec_microblog11 copy.txt");
            
            FileReader fr = new FileReader(input);
            BufferedReader bufferReader = new BufferedReader(fr); 

            
            String line; 
            while ((line = bufferReader.readLine()) != null) {


                String[] message = line.substring(18).split("\\s+");
                List<String> tweetMessage = Arrays.asList(message);
                System.out.println(tweetMessage);
                Tweet tweet = new Tweet(line.substring(0,17), tweetMessage);
                collection.add(tweet);
                
            }

            for (Tweet t : collection ) {
                System.out.println(t.getTweetID());
            
            }


            bufferReader.close(); 

            // for each word from the preprocessor 

            // loop through each tweet and note down how many times that word appears in each tweet


            // File input = new File("Trec_microblog11 copy.txt");
            // // File input = new File("Trec_microblog11.txt");

            // System.out.println("Opened successfully");
            // Scanner scanner = new Scanner(input);

            // // while (scanner.hasNext()) {
            // //     // change word to lower case
            // //     String word = scanner.next().toLowerCase();
            // //     System.out.println(word.charAt(0));

                
            // // }
        
            // while (scanner.hasNextLine()) {
            //     String tweetID = scanner.nextLine(); 

                
            //     String arr[] = tweetID.split("	", 2);
            //     System.out.println(arr[0]);

            // }
            // scanner.close();
        }catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;

        } 



        // for term in list of terms in preprossesing words
        // create index 
        InvertedIndex invertedIndex = new InvertedIndex();

        for (String word : preProcessedWords) {
            for (Tweet tweet: collection ) {
                int wordFreq = 0; 
                for (String wordInTweet: tweet.getTweet()) {
                    if (wordInTweet.equals(word)) {
                        wordFreq++; 
                    }
                }
                invertedIndex.addToIndex(word, new documentTfTuple(tweet.getTweetID(), wordFreq)); 
            }
            
        }

    }



    
}
