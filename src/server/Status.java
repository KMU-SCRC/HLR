package server;

import java.util.TreeMap;

public class Status {
	private String ID="Default ID";
	private String Channel="Default Channel";
	private String Status="Default Status";
	private long Time=System.currentTimeMillis();
	private long ContactTime=System.currentTimeMillis();
	private TreeMap<String,String>GATEWAYLIST=new TreeMap<String,String>();
	public Status() {
		super();
		Time=System.currentTimeMillis();
		ContactTime=System.currentTimeMillis();
	}
	public Status(String iD) {
		super();
		ID = iD;
		Time=System.currentTimeMillis();
		ContactTime=System.currentTimeMillis();
	}
	public Status(String iD, String channel) {
		super();
		ID = iD;
		Channel = channel;
		Time=System.currentTimeMillis();
		ContactTime=System.currentTimeMillis();
	}
	public Status(String iD, String channel, String status) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time=System.currentTimeMillis();
		ContactTime=System.currentTimeMillis();
	}
	public Status(String ID, String CHANNEL, String GATEWAYID, String STATUS) {
		super();
		this.ID = ID;
		this.Channel = CHANNEL;
		this.GATEWAYLIST.put(GATEWAYID,STATUS);
		Time=System.currentTimeMillis();
		ContactTime=System.currentTimeMillis();
	}
	public Status(long time) {
		super();
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, long time) {
		super();
		ID = iD;
		Time = time;
		ContactTime=time;
	}
	public Status(long time, String iD) {
		super();
		ID = iD;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, String channel, long time) {
		super();
		ID = iD;
		Channel = channel;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, long time, String channel) {
		super();
		ID = iD;
		Channel = channel;
		Time = time;
		ContactTime=time;
	}
	public Status(long time, String iD, String channel) {
		super();
		ID = iD;
		Channel = channel;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, String channel, String status, long time) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, String channel, long time, String status) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, long time, String channel, String status) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time = time;
		ContactTime=time;
	}
	public Status(long time, String iD, String channel, String status) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time = time;
		ContactTime=time;
	}
	public Status(String iD, String channel, String status, long time, TreeMap<String, String> gATEWAYLIST) {
		super();
		ID = iD;
		Channel = channel;
		Status = status;
		Time = time;
		ContactTime=time;
		GATEWAYLIST = gATEWAYLIST;
	}
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
		Time=System.currentTimeMillis();
	}
	public String getChannel() {
		return Channel;
	}
	public void setChannel(String channel) {
		Channel = channel;
		Time=System.currentTimeMillis();
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
		Time=System.currentTimeMillis();
	}
	public long getTime() {
		return Time;
	}
	public void setTime(long time) {
		Time = time;
	}
	public TreeMap<String, String> getGATEWAYLIST() {
		return GATEWAYLIST;
	}
	public void setGATEWAYLIST(TreeMap<String, String> gATEWAYLIST) {
		GATEWAYLIST = gATEWAYLIST;
		Time=System.currentTimeMillis();
	}
	public long getContactTime() {
		return ContactTime;
	}
	public void setContactTime(long contactTime) {
		ContactTime = contactTime;
	}
}