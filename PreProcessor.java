import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;
import java.util.regex.Matcher;

public class PreProcessor {

    public static void main(String args[]) throws IOException {

        Set<String> words = new HashSet<String>();
        ArrayList<String> stopWords = getStopWords();

        try {

            // File input = new File("test.txt");

            File input = new File("Trec_microblog11.txt");

            FileReader fr = new FileReader(input);
            BufferedReader bufferReader = new BufferedReader(fr);


            String line;
            while ((line = bufferReader.readLine()) != null) {

                // remove urls and punctuation
                line = line.replaceAll("http://[^\\s]+", "");
                line = line.replaceAll("[^a-zA-Z]"," ");
      

                // split lines by space
                String[] message = line.split("\\s+");
                for (String word: message) {
                    word = word.toLowerCase();
                    if (word.length() > 0 && !word.contains("http")) {
                        words.add(word);

                    }

                }
            }

              // remove all the stop words
            words.removeAll(stopWords);

            // write words into new file 
            Writer writer = new FileWriter("preprocessingresult.txt", false); //overwrites file
            for (String word: words) { 
                writer.write(word + System.lineSeparator());
            
            }

            writer.close();

        }   catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
            return;

        }
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
