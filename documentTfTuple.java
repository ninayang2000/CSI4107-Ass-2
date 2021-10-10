
public class documentTfTuple {


    private Long tweetID;
    private int termFrequency;

    public documentTfTuple(Long tweetID, int termFrequency ) {
        this.tweetID = tweetID;
        this.termFrequency = termFrequency;
    }

    public Long getTweetID() {
        return tweetID;
    }

    public void setTweetID (Long tweetID) {
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
