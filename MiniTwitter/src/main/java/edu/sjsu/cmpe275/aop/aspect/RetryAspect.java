package edu.sjsu.cmpe275.aop.aspect;

import java.io.IOException;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.aspectj.lang.annotation.Around;

@Aspect
@Order(1)
public class RetryAspect {
    /***
     * Following is a dummy implementation of this aspect.
     * You are expected to provide an actual implementation based on the requirements, including adding/removing advices as needed.
     */

	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.tweet(..))")
	public void tweetExceptionHandling(ProceedingJoinPoint joinPoint) throws Throwable {
//		System.out.printf("Prior to the execution of the method %s\n", joinPoint.getSignature().getName());
		Object result = null;
		try {
			result = joinPoint.proceed();
//			System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
		} catch (IOException i1) {
			try {
				result = joinPoint.proceed();
//				System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
			} catch (IOException i2) {
				try {
					result = joinPoint.proceed();
//					System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
				} catch (IOException i3) {
					System.out.println(i3);
				}
			}
		} catch (IllegalArgumentException e) {
			System.out.printf("\nLength of the tweet by the user " + e.getMessage() + " is greater than 140.\n");
		}
	}
	
	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.follow(..))")
	public void followExceptionHandling(ProceedingJoinPoint joinPoint) throws Throwable {
//		System.out.printf("Prior to the execution of the method %s\n", joinPoint.getSignature().getName());
		Object result = null;
		try {
			result = joinPoint.proceed();
//			System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
		} catch (IOException i1) {
			try {
				result = joinPoint.proceed();
//				System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
			} catch (IOException i2) {
				try {
					result = joinPoint.proceed();
//					System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
				} catch (IOException i3) {
					System.out.println(i3);
				}
			}
		} 
	}
	
	@Around("execution(public void edu.sjsu.cmpe275.aop.TweetServiceImpl.block(..))")
	public void blockExceptionHandling(ProceedingJoinPoint joinPoint) throws Throwable {
//		System.out.printf("Prior to the execution of the method %s\n", joinPoint.getSignature().getName());
		Object result = null;
		try {
			result = joinPoint.proceed();
//			System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
		} catch (IOException i1) {
			try {
				result = joinPoint.proceed();
//				System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
			} catch (IOException i2) {
				try {
					result = joinPoint.proceed();
//					System.out.printf("Finished the execution of the method %s with result %s\n", joinPoint.getSignature().getName(), result);
				} catch (IOException i3) {
					System.out.println(i3);
				}
			}
		}
	}
}
