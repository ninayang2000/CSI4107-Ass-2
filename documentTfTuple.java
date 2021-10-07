import org.graalvm.compiler.graph.spi.Canonicalizable.Ternary;

public class documentTfTuple {
    

    private String tweetID; 
    private int termFrequency; 

    public documentTfTuple(String tweetID, int termFrequency ) {
        this.tweetID = tweetID; 
        this.termFrequency = termFrequency; 
    }

    public String getTweetID() {
        return tweetID;
    }

    public void setTweetID (String tweetID) {
        this.tweetID = tweetID;
    }

    public int getTermFrequency() {
        return termFrequency;
    }

    public void setTermFrequency(int termFrequency) {
        this.termFrequency = termFrequency;
    }

    public void printTuple() {
        System.out.print("(" + this.getTweetID() + ", "  + this.getTermFrequency() + ") ");
    }



}
