# Mini Twitter using Aspect Oriented Programming
This repository consists of a twitter application implemented using Aspect Oriented Programming.
___

In this project, we implement the retry and stats concerns to a tweeting service through Aspect Oriented Programming (AOP). 

The tweet service is defined as follows:

    package edu.sjsu.cmpe275.lab1;
    
    import java.io.IOException;
    
    public interface TweetService {
       /**
        * @throws IllegalArgumentException if the message is more than 140 characters as measured by string length.
        * @throws IOException if there is a network failure
        */
       void tweet(String user, String message) throws IllegalArgumentException, IOException;
    
       /**
        * @throws IOException if there is a network failure
        */
       void follow(String follower, String followee) throws IOException;
    
       /**
         * 
        * @throws IOException if there is a network failure
        */
       void block(String user, String followee) throws IOException;
    
    }

Since network failure happens relatively frequently, you are asked to add the feature to automatically retry for up to three times for a network failure (indicated by an IOException). (Please note the three retries are in addition to the original failed invocation.)

We also implement the following TweetStats service:

    public interface TweetStats {

       /**
        * reset all the measurements and all the following/blocking relationship as well.
        */
       void resetStatsandSystem();

       /**
        * @return the length of longest message successfully sent or attempted since the beginning or last reset. Can be more than 140. If no messages succeeded or attempted, return 0.
        * Failed messages are counted for this as well.
        */
       int getLengthOfLongestTweet();
       /**
        * @return the user who has been followed by the biggest number of different users since the beginning or last reset. If there is a tie,
        * return the 1st of such users based on alphabetical order. If the follow action did not succeed, it does not count toward the stats. If no users were successfully followed, return null.  Blocking or not does not affect this metric.
        */
       String getMostFollowedUser();

      /**
        * @return the message that has been shared with the biggest number of different followers when it is successfully tweaked. If the same message (based on string equality) has been tweeted more than once, it is considered as different message for this purpose.  Return based on dictionary order if there is a tie.
        */
       String getMostPopularMessage();

       /**
        * The most productive user is determined by the total length of all the messages successfully tweeted since the beginning
        * or last reset. If there is a tie, return the 1st of such users based on alphabetical order. If no users successfully tweeted, return null.
        * @return the most productive user.
        */
       String getMostProductiveUser();

    /**
         * @return the user who has been successfully blocked by the biggest number of
         *     	different users since the beginning or last reset. If there is a
         *     	tie, return the 1st of such users based on alphabetical order.
         *     	If no follower has been successfully blocked by anyone, return null.
         */
        String getMostBlockedFollower();
    }
    
Example Stats
The following examples are assuming stats are reset() before running every single example. Additional test cases will be used for grading.

          1. Tweet message as tweet(“foo”,”barbar”). Then getLengthOfLongestTweet() returns 6.
          2. Alice follows Bob, Carl follows Bob (but fails to do so), and Bob follows Alice.             getMostFollowedUser() returns “Alice”.
          3. Successfully tweet a message ("Alice","[any message <= 140 chars]"), then                    getMostProductiveUser() returns “Alice”.

