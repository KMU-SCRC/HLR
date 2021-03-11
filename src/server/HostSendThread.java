package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Map;

public class HostSendThread extends Thread{
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			BufferedReader tmpbuf = new BufferedReader(new InputStreamReader(System.in));
			
			String sendString;
			long time=5000;
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			System.out.println("장비 식별 ID를 입력해주세요.");
			ChatServer.UserID = tmpbuf.readLine();
			
			while(true)
			{
				sendString = tmpbuf.readLine();

				if(sendString.equalsIgnoreCase("exit"))
				{
					sendString="서버를 종료합니다.";
					for(int i = 0; i < ChatServer.m_OutputList.size(); ++i)
					{
						ChatServer.m_OutputList.get(i).println(ChatServer.UserID + "> "+ sendString);
						ChatServer.m_OutputList.get(i).flush();
					}
					ChatServer.ENDSWITCH=true;
					break;
				}
				else if(sendString.equalsIgnoreCase("help")) {
					System.out.println("==================");
					System.out.println("도움말");
					System.out.println("==================");
					System.out.println("exit - 종료");
					System.out.println("view - 클라이언트(DN)에서 올라오는 유아트 통신 기록 보기");
					System.out.println("noview - 클라이언트(DN)에서 올라오는 유아트 통신 기록 안 보기");
					System.out.println("msgview - 장비간의 메세지 보기");
					System.out.println("nomsgview - 장비간의 메세지 안 보기");
					System.out.println("list -  클라이언트(DN)에서 올라오는 모든 매칭된 장비 목록 (HLR)");
					System.out.println("auto - 자동으로 리스트 보이기");
					System.out.println("noauto - 자동으로 리스트 보이지 않기");
					System.out.println("delay - 자동 리스트 주기 설정");
					System.out.println("nodelay - 자동 리스트 주기 끄기");
					System.out.println("del - 장비 목록 수동 삭제");
					System.out.println("==================");
					continue;
				}
				else if(sendString.equalsIgnoreCase("view")) {
					ChatServer.VIEWSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("noview")) {
					ChatServer.VIEWSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("msgview")) {
					ChatServer.MSGVIEWSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("nomsgview")) {
					ChatServer.MSGVIEWSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("list")) {
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
				else if(sendString.equalsIgnoreCase("auto")) {
					ChatServer.AUTOSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("noauto")) {
					ChatServer.AUTOSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("delay")) {
					System.out.println("자동 리스트 주기 시간을 설정해주세요. 기본설정은 1초(1000)입니다.");
					sendString = tmpbuf.readLine();
					try {
						time=Long.parseLong(sendString);
						ChatServer.DELAY=time;
						System.out.println("설정이 완료되었습니다.");
					}catch (Exception e) {
						System.out.println("숫자만 입력해주세요.");
					}
					ChatServer.DELAYSWITCH=true;
					continue;
				}
				else if(sendString.equalsIgnoreCase("nodelay")) {
					ChatServer.DELAYSWITCH=false;
					continue;
				}
				else if(sendString.equalsIgnoreCase("del")) {
					System.out.println("삭제할 장비를 입력해주세요. 전체 삭제를 원할시 all 입력");
					sendString = tmpbuf.readLine();
					if(sendString.equalsIgnoreCase("all")) {
						if(ChatServer.deviceList.isEmpty()) {
							System.out.println("삭제할 장비가 없습니다.");
						}else {
							ChatServer.deviceList.clear();
							System.out.println("정상적으로 전체 삭제되었습니다.");
						}
					}else if(ChatServer.deviceList.containsKey(sendString)) {
						ChatServer.deviceList.remove(sendString);
						System.out.println("정상적으로 삭제되었습니다.");
					}else {
						System.out.println("해당 장비가 없습니다.");
					}
					continue;
				}
				else {
					for(int i = 0; i < ChatServer.m_OutputList.size(); ++i)
					{
						ChatServer.m_OutputList.get(i).println(ChatServer.UserID + "> "+ sendString);
						ChatServer.m_OutputList.get(i).flush();
					}
				}
			}
			tmpbuf.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
