import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import Tweet; 
public class RetrievalSystem {

    public static void main(String args[]) throws IOException {

        // private ArrayList<Tweet> tweets new ArrayList<>(); 
        try {
            File input = new File("Trec_microblog11 copy.txt");
            
            FileReader fr = new FileReader(input);
            BufferedReader bufferReader = new BufferedReader(fr); 
            
            String line; 
            while ((line = bufferReader.readLine()) != null) {
                System.out.print(line.substring(0,17));

                Tweet tweet = new Tweet(line.substring(0,17), line.substring(18)); 
                System.out.print("\n");
                System.out.print(line.substring(18));
                System.out.print("\n");
                // System.out.print(line.substring(18));
                
            }


            bufferReader.close(); 


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



        // // for term in list of terms in preprossesing words
        // // create index 
        // InvertedIndex invertedIndex = new InvertedIndex();

        // for (Tweet tweet : tweets) {
        //     for (String word: tweet) {
        //         invertedIndex.addToIndex(word, new documentTfTuple(tweet.getID));

        //     }
        //     // search how many times word 
            
        // }

    }



    
}
