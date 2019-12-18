package com.projector.edu.findflight.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Component;

@Component
public class TimeProfileUtil {

private long startGeneralMethodTime;	
	
	private Map<String, MethodProfileHolder> methodProfileMap;	
	
	public void initMethodProfileMap(List<String> profiledMethodNames) {
		methodProfileMap = new HashMap<String, MethodProfileHolder>();
		startGeneralMethodTime = System.nanoTime();		
		for (String profiledMethodName : profiledMethodNames) {
			methodProfileMap.put(profiledMethodName, new MethodProfileHolder());
		}
	}
	
//	public Object profileMethod(ProceedingJoinPoint pjp, String methodName) throws Throwable {
//		long start = System.currentTimeMillis();
//        Object output = pjp.proceed();
//        long elapsedTime = System.currentTimeMillis() - start;
//        System.out.println("Method " + methodName + " execution time: " + elapsedTime + " nanoseconds.");
//        increaseProfileVariables(elapsedTime, methodName);
//        return output;
//    }
	
	public void profileMethod(long start, String methodName) {
		try {			
			long elapsedTime = System.nanoTime() - start;			
			increaseProfileVariables(elapsedTime, methodName);
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
    }
	
	public void finithProfiling() {
		System.out.println("Finish profiling.");		
		long elapsedTime = (System.nanoTime() - startGeneralMethodTime) / 1000000;
		System.out.println("Duration of general method: " + elapsedTime + " milliseconds.");
		showAllProfileStatistics();
	}	
	
	private void increaseProfileVariables(long internalMethodDuration, String profiledMethodName) {
		MethodProfileHolder methodProfileHolder = methodProfileMap.get(profiledMethodName);
		if (methodProfileHolder != null) {
			methodProfileHolder.setInternalMethodTotalDuration(methodProfileHolder.getInternalMethodTotalDuration() + internalMethodDuration);
			methodProfileHolder.setInternalMethodCallNumber(methodProfileHolder.getInternalMethodCallNumber() + 1);
		}
	}
	
	private void showAllProfileStatistics() {
		for (Entry<String, MethodProfileHolder> methodProfileMapEntry : methodProfileMap.entrySet()) {
			String methodName = methodProfileMapEntry.getKey();
			MethodProfileHolder methodProfileHolder = methodProfileMapEntry.getValue();
			showMethodProfileStatistics(methodProfileHolder, methodName);
		}	
	}
	
	private void showMethodProfileStatistics(MethodProfileHolder methodProfileHolder, String methodName) {
		long internalMethodTotalDuration = methodProfileHolder.getInternalMethodTotalDuration() / 1000000;
		int internalMethodCallNumber = methodProfileHolder.getInternalMethodCallNumber();		
		System.out.println("Total duration of internal method " + methodName + " " + internalMethodTotalDuration + " milliseconds.");
		System.out.println("Internal method " + methodName + " has been called " + internalMethodCallNumber + " times.");
		double methodAvarageDurationTime = internalMethodCallNumber != 0 ? internalMethodTotalDuration/internalMethodCallNumber : 0 ;
		System.out.println("Average duration time: " + Math.round(methodAvarageDurationTime));
	}
	
	private class MethodProfileHolder {
		
		private int internalMethodCallNumber = 0;
		
		private long internalMethodTotalDuration = 0;

		public int getInternalMethodCallNumber() {
			return internalMethodCallNumber;
		}

		public void setInternalMethodCallNumber(int internalMethodCallNumber) {
			this.internalMethodCallNumber = internalMethodCallNumber;
		}

		public long getInternalMethodTotalDuration() {
			return internalMethodTotalDuration;
		}

		public void setInternalMethodTotalDuration(
				long internalMethodTotalDuration) {
			this.internalMethodTotalDuration = internalMethodTotalDuration;
		}
		
	}
	
}
