import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


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
                Tweet tweet = new Tweet(line.substring(0,17), tweetMessage);

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
