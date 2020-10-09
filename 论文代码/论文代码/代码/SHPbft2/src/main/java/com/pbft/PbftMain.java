package com.pbft;

import com.pbft.TimerManager;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class PbftMain {

	static Logger logger = LoggerFactory.getLogger(PbftMain.class);
	
	public static final int size =4;
	
	private static List<Pbft> nodes = Lists.newArrayList();
	
	private static Random r = new Random();
	
	private static long[] net = new long[9999];
	
	public static void main(String[] args) throws InterruptedException {
		
		
		
		for(int i=0;i<size;i++){
			nodes.add(new Pbft(i,size).start());
		}
		System.out.println("初始化模拟网络");
		// 初始化模拟网络
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(i != j){
					// 随机延时
					net[i*10+j] = RandomUtils.nextLong(10, 60);
				}else{
					net[i*10+j] = 10;
				}
			}
		}
		
		
		// 模拟请求端发送请求
		for(int i=0;i<1;i++){
			int node = r.nextInt(size);
			nodes.get(node).req("test"+i);
		}
		
//		Thread.sleep(10000);
//		System.out.println("9--------------------------------------------------------");
//		// 1秒后，主节点宕机
//		nodes.get(0).close();
//		for(int i=2;i<4;i++){
//			nodes.get(i).req("testD"+i);
//		}
//		//1秒后恢复
//		Thread.sleep(1000);
//		System.out.println("9--------------------------------------------------------");
//
//		nodes.get(0).back();
//		for(int i=1;i<2;i++){
//			nodes.get(i).req("testB"+i);
//		}
		
	}
	
	/**
	 * 广播消息
	 * @param msg
	 */
	public static void publish(PbftMsg msg){
		logger.info("publish广播消息[" +msg.getNode()+"]:"+ msg);
		for(Pbft pbft:nodes){
			// 模拟网络时延
			TimerManager.schedule(()->{
				pbft.push(new PbftMsg(msg));
				return null;
			}, net[msg.getNode()*10+pbft.getIndex()]);
		}
	}
	/**
	 * 发送消息到指定节点
	 * @param toIndex
	 * @param msg
	 */
	
	public static void send(int toIndex,PbftMsg msg){
		// 模拟网络时延
		TimerManager.schedule(()->{
			nodes.get(toIndex).push(msg);
			return null;
		}, net[msg.getNode()*10+toIndex]);
	}
	
}
