package com.pbft;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * 异步延时执行
 * 需要调用延迟执行的时候可使用，避免在代码里面直接调用Thread.sleep()方法
 * 
 * @author andylo25 wrote on 2018/6/11.
 */
public class TimerManager {

	private volatile static ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
	
	public static void schedule(Supplier<?> action, long delay){
		executorService.schedule(new Runnable() {
			@Override
			public void run() {
				action.get();
			}
		}, delay, TimeUnit.MILLISECONDS);
	}
	
	public static void scheduleAtFixedRate(Supplier<?> action,long initialDelay, long period ){
		executorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				action.get();
			}
		}, initialDelay,period, TimeUnit.MILLISECONDS);
	}
	
	public static void scheduleWithFixedDelay(Supplier<?> action,long initialDelay, long period ){
		executorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				action.get();
			}
		}, initialDelay,period, TimeUnit.MILLISECONDS);
	}
	
	
}
