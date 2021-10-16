import java.util.ArrayList;
import java.util.List;

public class Tweet {


    private Long tweetID;
    private List<String> tweet = new ArrayList<String>();

    public List<String> getTweet() {
        return tweet;
    }

    public void setTweet(List<String> tweet) {
        this.tweet = tweet;
    }

    public Tweet(Long tweetID, List<String> tweet) {
        this.tweetID = tweetID;
        this.tweet = tweet;

    }

    public Long getTweetID() {
        return tweetID;
    }

    public void setTweetID(Long tweetID) {
        this.tweetID = tweetID;
    }

}
