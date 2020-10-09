package com.pbft;

import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

public class HQMain {
static Logger logger = LoggerFactory.getLogger(HQMain.class);
	
	public static final int size =22;
	
	
	private static List<HQ> nodes = Lists.newArrayList();
	
	private static List<HQ> HQnodes = Lists.newArrayList();
	private static List<HQ> Bnodes = Lists.newArrayList();
	
	private static Random r = new Random();
	
	private static long[] net = new long[9999];
	
	public static void main(String[] args) throws InterruptedException {
		
		
		
		for(int i=0;i<size;i++){
			nodes.add(new HQ(i,size,false,true));
		}
		int hqnum = size - (size-1)/3;
		start(hqnum);
		
		nodes.get(1).setByzt();
		// 初始化模拟网络
		for(int i=0;i<size;i++){
			for(int j=0;j<size;j++){
				if(i != j){
					// 随机延时
					net[i*10+j] = 10;
				}else{
					net[i*10+j] = 5;
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
	public static void HQpublish(HQMsg msg){
		logger.info("HQpublish广播消息[" +msg.getNode()+"]:"+ msg);
		for(HQ hq:HQnodes){
			// 模拟网络时延
			TimerManager.schedule(()->{
				hq.push(new HQMsg(msg));
				return null;
			}, net[msg.getNode()*10+hq.getIndex()]);
		}
	}
	
	public static void publish(HQMsg msg){
		logger.info("publish广播消息[" +msg.getNode()+"]:"+ msg);
		for(HQ hq:nodes){
			// 模拟网络时延
			TimerManager.schedule(()->{
				hq.push(new HQMsg(msg));
				return null;
			}, net[msg.getNode()*10+hq.getIndex()]);
		}
	}
	
	public static void start(int hqnum) {
		int num=0;
		for(int i=0;i<size;i++) {
			if(num == hqnum) {
				break;
			}else {
				if(nodes.get(i).getCredit() == 100) {
					num++;
					HQnodes.add(nodes.get(i));
				}else {
					Bnodes.add(nodes.get(i));
				}
			}
		}
		for(int i=num;i<size;i++) {
			Bnodes.add(nodes.get(i));
		}
		
		for(int i=0;i<hqnum;i++) {
			HQnodes.get(i).start();
		}
		
	}
//	public static void start(int hqnum) {
//		int num=0;
//		for(int i=0;i<size;i++) {
//			if(num == hqnum) {
//				break;
//			}else {
//				if(nodes.get(i).getCredit() == 100) {
//					num++;
//					HQnodes.add(nodes.get(i));
//				}else {
//					nodes.get(i).setHQ(false);
//					Bnodes.add(nodes.get(i));
//				}
//			}
//		}
//		for(int i=num;i<size;i++) {
//			nodes.get(i).setHQ(false);
//			Bnodes.add(nodes.get(i));
//		}
//		
//		for(int i=0;i<size;i++) {
//			nodes.get(i).start();
//		}
//		
//	}
	
	public static void Bstart() {
		for(int i=0;i<Bnodes.size();i++) {
			//Bnodes.get(i).setHQ(true);
			Bnodes.get(i).start();
		}
	}
	
	
	/**
	 * 发送消息到指定节点
	 * @param toIndex
	 * @param msg
	 */
	
	public static void send(int toIndex,HQMsg msg){
		// 模拟网络时延
		
		
		TimerManager.schedule(()->{
			nodes.get(toIndex).push(msg);
			return null;
		}, net[msg.getNode()*10+toIndex]);
	}
	
	
	

}
