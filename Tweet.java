import java.util.ArrayList;

public class Tweet {


    private String tweetID; 
    private String tweet; 

    public Tweet(String tweetID, String tweet) {
        this.tweetID = tweetID; 
        this.tweet = tweet; 

    }
    
    public String getTweetID() {
        return tweetID;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public String getTweet() {
        return tweet;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }
}
