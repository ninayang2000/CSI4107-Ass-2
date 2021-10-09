import java.util.ArrayList;
import java.util.List;

public class Tweet {


    private String tweetID;
    private List<String> tweet = new ArrayList<String>();

    public List<String> getTweet() {
        return tweet;
    }

    public void setTweet(List<String> tweet) {
        this.tweet = tweet;
    }

    public Tweet(String tweetID, List<String> tweet) {
        this.tweetID = tweetID;
        this.tweet = tweet;

    }

    public String getTweetID() {
        return tweetID;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

}
