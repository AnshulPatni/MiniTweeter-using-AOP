package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import edu.sjsu.cmpe275.aop.TweetStatsImpl;

@Aspect
@Order(0)
public class StatsAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advises as needed.
     */

	@Autowired TweetStatsImpl stats;
		
	@After("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
	public void afterTweet(JoinPoint joinPoint) throws IOException {
		System.out.printf("\nAfter the execution of the method %s\n", joinPoint.getSignature().getName());
		
		String userName = (String) joinPoint.getArgs()[0];
		String userMessage = (String) joinPoint.getArgs()[1];
		
		// Check and update the longest tweet
		if(TweetStatsImpl.lengthOfLongestTweet < userMessage.length()) {
			TweetStatsImpl.lengthOfLongestTweet = userMessage.length();	
		}
		
		if(userMessage.length() <= 140) {
			// Check and update the most productive user
			if (TweetStatsImpl.mapMostProductiveUser.containsKey(userName))
				TweetStatsImpl.mapMostProductiveUser.put(userName, TweetStatsImpl.mapMostProductiveUser.get(userName) + userMessage.length());
			else
				TweetStatsImpl.mapMostProductiveUser.put(userName, userMessage.length());
			
			TweetStatsImpl.mostProductiveUser = Collections.max(TweetStatsImpl.mapMostProductiveUser.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
			
			// Check and update the most popular message	
			int noOfFollowers = 0;
			int blockedUsers = 0;
			int actualFollowersCount = 0;

			if(TweetStatsImpl.mapUserFollowerList.size() != 0) {
				if(TweetStatsImpl.mapUserFollowerList.containsKey(userName)) {
					TreeSet<String> followersList = TweetStatsImpl.mapUserFollowerList.get(userName);
					if(TweetStatsImpl.mapBlockedUsersList.containsKey(userName)) {
						TreeSet<String> blockedUsersList = TweetStatsImpl.mapBlockedUsersList.get(userName);
						for(String follower : followersList) {
							if(blockedUsersList.contains(follower)) {
								blockedUsers++;
							}
						}
					}
					
					noOfFollowers = TweetStatsImpl.mapUserFollowerList.get(userName).size();
					
					actualFollowersCount = noOfFollowers - blockedUsers;
					System.out.println(userName + "'s actual followers count: " + (actualFollowersCount));
					if(TweetStatsImpl.popularMessageUserCount == 0 ||  actualFollowersCount > TweetStatsImpl.popularMessageUserCount) {
						TweetStatsImpl.mostPopularMessageUser = userName;
						TweetStatsImpl.mostPopularMessage = userMessage;
						TweetStatsImpl.popularMessageUserCount = actualFollowersCount;
					} else if(actualFollowersCount == TweetStatsImpl.popularMessageUserCount) {
						if(userName.compareToIgnoreCase(TweetStatsImpl.mostPopularMessageUser) < 0) {
							TweetStatsImpl.mostPopularMessageUser = userName;
							TweetStatsImpl.mostPopularMessage = userMessage;
							TweetStatsImpl.popularMessageUserCount = actualFollowersCount;
						}
					}
				}
			}
		}
	}
	

	@After("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.follow(..))")
	public void afterFollow(JoinPoint joinPoint) {
		System.out.printf("\nAfter the execution of the method %s\n", joinPoint.getSignature().getName());
		
		String follower = (String) joinPoint.getArgs()[0];
		String followee = (String) joinPoint.getArgs()[1];
		
		if(follower.equals(followee)) {
			System.out.println("WARNING!!!: A user cannot follow to himself/herself.");
		} else {		
			TreeSet<String> followersList = new TreeSet<String>();
			
			// Update the UserFollowerList; Check and update the most followed user
			if (TweetStatsImpl.mapUserFollowerList.containsKey(followee)) {
				followersList = TweetStatsImpl.mapUserFollowerList.get(followee);
			}
			followersList.add(follower);
			TweetStatsImpl.mapUserFollowerList.put(followee, followersList);	
			
			int mostFollowedUserCount = 0;
			String mostFollowedUser = null;
			for(HashMap.Entry<String, TreeSet<String>> tempMapPair :  TweetStatsImpl.mapUserFollowerList.entrySet()) {
				if(tempMapPair.getValue().size() > mostFollowedUserCount) {
					mostFollowedUserCount = tempMapPair.getValue().size();
					mostFollowedUser = tempMapPair.getKey();
				}
			}
			TweetStatsImpl.mostFollowedUser = mostFollowedUser;
		}
	}
	
	@After("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.block(..))")
	public void afterBlock(JoinPoint joinPoint) {
		System.out.printf("\nAfter the execution of the method %s\n", joinPoint.getSignature().getName());
		
		String user = (String) joinPoint.getArgs()[0];
		String follower = (String) joinPoint.getArgs()[1];
		
		if(user.equals(follower)) {
			System.out.println("WARNING!!!: A user cannot block himself/herself.");
		} else {
			
			// Check and update the most blocked follower
			int mostBlockedUserCount = 0;
			String mostBlockedUser = null;
			if(TweetStatsImpl.mapMostBlockedFollower.size() > 0) {
				if(TweetStatsImpl.mapMostBlockedFollower.containsKey(follower))	{
					TweetStatsImpl.mapMostBlockedFollower.put(follower, TweetStatsImpl.mapMostBlockedFollower.size() + 1);
				} else {
					TweetStatsImpl.mapMostBlockedFollower.put(follower, 1);
				}
			} else {
				TweetStatsImpl.mapMostBlockedFollower.put(follower, 1);
			}
			
			mostBlockedUserCount = TweetStatsImpl.mapMostBlockedFollower.values().stream().max(Integer::compare).get();

			for (Entry<String, Integer> entry : TweetStatsImpl.mapMostBlockedFollower.entrySet()) {
	            if (entry.getValue().equals(mostBlockedUserCount)) {
	            	mostBlockedUser = entry.getKey();
	            }
	        }
			
			TweetStatsImpl.mostBlockedFollower = mostBlockedUser;

			// Update the BlockedUsersList
			TreeSet<String> blockedUsersList = new TreeSet<String>();
			if (TweetStatsImpl.mapBlockedUsersList.containsKey(user)) {
				blockedUsersList = TweetStatsImpl.mapBlockedUsersList.get(user);
			}
			blockedUsersList.add(follower);
			TweetStatsImpl.mapBlockedUsersList.put(user, blockedUsersList);

		}
	}

}
