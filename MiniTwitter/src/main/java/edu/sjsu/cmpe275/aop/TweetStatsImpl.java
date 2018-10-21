package edu.sjsu.cmpe275.aop;

import java.util.HashMap;
import java.util.TreeMap;
import java.util.TreeSet;

//import java.util.HashMap;

public class TweetStatsImpl implements TweetStats {
    /***
     * Following is a dummy implementation.
     * You are expected to provide an actual implementation based on the requirements.
     */
	
	public static int lengthOfLongestTweet = 0;
	public static String mostPopularMessage = null;
	public static String mostProductiveUser = null;
	public static String mostBlockedFollower = null;
	public static String mostFollowedUser = null;
	public static int popularMessageUserCount = 0;
	public static String mostPopularMessageUser = null;
	
	// Map to contain the total length of tweets for every user
	public static TreeMap<String, Integer> mapMostProductiveUser = new TreeMap<String, Integer>();
	
	// Map to contain the total followers of the user
	public static TreeMap<String, Integer> mapMostFollowedUser = new TreeMap<String, Integer>();
	
	// Map to contain the total blocks for the user
	public static TreeMap<String, Integer> mapMostBlockedFollower = new TreeMap<String, Integer>();
	
	// Map to contain user and list of users who have followed them
	public static HashMap<String, TreeSet<String>> mapUserFollowerList = new HashMap<String, TreeSet<String>>();
	
	// Map to contain a user and list of users he/she has blocked
	public static HashMap<String, TreeSet<String>> mapBlockedUsersList = new HashMap<String, TreeSet<String>>();
	
	@Override
	public void resetStatsAndSystem() {
		// TODO Auto-generated method stub
		System.out.println("Reset all the System Variables");
		
		lengthOfLongestTweet = 0;
		mostPopularMessage = null;
		mostProductiveUser = null;
		mostBlockedFollower = null;
		mostFollowedUser = null;
		popularMessageUserCount = 0;
		mostPopularMessageUser = null;
		
		if (mapMostProductiveUser.size() != 0)
			mapMostProductiveUser.clear();
		
		if (mapMostFollowedUser.size() != 0)
			mapMostFollowedUser.clear();
		
		if (mapMostBlockedFollower.size() != 0)
			mapMostBlockedFollower.clear();
		
		if (mapUserFollowerList.size() != 0)
			mapUserFollowerList.clear();
		
		if (mapBlockedUsersList.size() != 0)
			mapBlockedUsersList.clear();
		
	}
    
	@Override
	public int getLengthOfLongestTweet() {
		return lengthOfLongestTweet;
	}

	@Override
	public String getMostFollowedUser() {
		return mostFollowedUser;
	}

	@Override
	public String getMostPopularMessage() {
		return mostPopularMessage;
	}
	
	@Override
	public String getMostProductiveUser() {
		return mostProductiveUser;
	}

	@Override
	public String getMostBlockedFollower() {
		return mostBlockedFollower;
	}
}



