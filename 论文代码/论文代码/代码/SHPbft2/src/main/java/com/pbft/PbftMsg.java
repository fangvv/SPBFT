package com.pbft;

public class PbftMsg {

	private boolean isOk; // 模拟校验是否通过
	
	private int type; // 消息类型
	private int node; // 节点
	private int onode; // 发起请求的节点
	private int vnum; // 视图编号
	private int no; // 序列号
	private long time; // 时间戳
	private String data; // 数据,表示数据的hash,必须唯一
	
	public PbftMsg(int type,int node) {
		this.type = type;
		this.node = node;
		this.onode = node;
		this.time = System.currentTimeMillis();
		this.isOk = true;
	}
	
	public PbftMsg(PbftMsg msg) {
		this.isOk = msg.isOk;
		this.type = msg.type;
		this.node = msg.node;
		this.onode = msg.onode;
		this.vnum = msg.vnum;
		this.data = msg.data;
		this.time = msg.time;
		this.no = msg.no;
	}
	
	public boolean isOk() {
		return isOk;
	}

	public void setOk(boolean isOk) {
		this.isOk = isOk;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	} 
	

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getVnum() {
		return vnum;
	}

	public void setVnum(int vnum) {
		this.vnum = vnum;
	}

	public int getNode() {
		return node;
	}

	public void setNode(int node) {
		this.node = node;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String getKey(){
		return getDataKey()+"|@|"+getNode();
	}
	
	public String getDataKey(){
		return getData()+"|@|"+getNo();
	}
	

	@Override
	public String toString() {
		return "PbftMsg [isOk=" + isOk + ", type=" + type + ", node=" + node + ", vnum=" + vnum + ", time=" + time
				+ ", data=" + data + ", no=" + no + "]"+", onode=" + onode + "]";
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getOnode() {
		return onode;
	}

	public void setOnode(int onode) {
		this.onode = onode;
	}
	
	
	
}
