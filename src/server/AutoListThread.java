package server;

import java.text.SimpleDateFormat;
import java.util.Map;

public class AutoListThread extends Thread{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		long time=System.currentTimeMillis();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		while(true) {
			if(ChatServer.ENDSWITCH) {
				break;
			}else if(ChatServer.AUTOSWITCH) {
				if(ChatServer.DELAYSWITCH) {
					if((System.currentTimeMillis()-time)<ChatServer.DELAY) {
						continue;
					}else {
						time=System.currentTimeMillis();
					}
				}
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("");
				System.out.println("==================");
				System.out.println("기록시간 : "+sdf.format(System.currentTimeMillis()));
				System.out.println("==================");
				System.out.println("HLR("+ChatServer.UserID+") 장비목록");
				System.out.println("==================");
				if(ChatServer.deviceList.isEmpty()) {
					System.out.println("없음");
				}else {
					for(Map.Entry<String,Status>listUp:ChatServer.deviceList.entrySet()) {
						System.out.println("##################");
						System.out.println("ID : "+listUp.getValue().getID());
						System.out.println("채널 : "+listUp.getValue().getChannel());
						System.out.println("가입시간 : "+sdf.format(listUp.getValue().getContactTime()));
						System.out.println("갱신시간 : "+sdf.format(listUp.getValue().getTime()));
						for(Map.Entry<String,String>gateWayListUp:listUp.getValue().getGATEWAYLIST().entrySet()) {
							System.out.println("++++++++++++++++++");
							System.out.println("GW : "+gateWayListUp.getKey());
							System.out.println("상태 : "+gateWayListUp.getValue());
							System.out.println("++++++++++++++++++");
						}
						System.out.println("##################");
					}
				}
				System.out.println("==================");
				continue;
			}
		}
	}
}