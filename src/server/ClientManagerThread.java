package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Map;

public class ClientManagerThread extends Thread{

	private Socket m_socket;
	private String m_ID;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		try {
			BufferedReader tmpbuffer = new BufferedReader(new InputStreamReader(m_socket.getInputStream()));
			PrintWriter sendWriter=new PrintWriter(m_socket.getOutputStream());
			String text;
			String[] split;
			do {
				text = tmpbuffer.readLine();
				if(!text.equals("REGUIHOEDNRHUIOERHNOSUUIGBVIERGBIVIDURRBGYIFVBI")) {
					System.out.println(m_socket.getInetAddress()+"가 인증되지 않은 프로그램으로 접속을 시도하였습니다.");
					sendWriter.println("인증된 프로그램으로 접속을 시도해주세요.");
					sendWriter.flush();
					break;
				}
				
				sendWriter.println("장비 식별 ID를 입력해주세요.");
				sendWriter.flush();
				text = tmpbuffer.readLine();
				if(text==null||text.isEmpty()||text.equals("")||text.equals(" ")||text.equals("null")) {
					System.out.println(m_socket.getInetAddress()+"가 잘못된 ID로 접속을 시도하였습니다.");
					sendWriter.println("잘못된 ID입니다.");
					sendWriter.flush();
					break;
				}else {
					m_ID = text;
				}
				
				sendWriter.println("인증 번호를 입력해주세요.");
				sendWriter.flush();
				text = tmpbuffer.readLine();
				if(!text.equals("ASDN9989")) {
					System.out.println(m_ID + "(" + m_socket.getInetAddress() + ")" +"가 잘못된 인증을 시도하였습니다.");
					sendWriter.println("인증번호가 틀렸습니다.");
					sendWriter.flush();
					break;
				}
				
				System.out.println(m_ID + "(" + m_socket.getInetAddress() + ")" + " - 연결 시작");
				for(int i = 0; i < ChatServer.m_OutputList.size(); ++i)
				{
					ChatServer.m_OutputList.get(i).println(m_ID + " - 연결 시작");
					ChatServer.m_OutputList.get(i).flush();
				}
				
				while(true)
				{
					if(ChatServer.ENDSWITCH) {
						break;
					}
					text = tmpbuffer.readLine();
					if(text == null)
					{
						System.out.println(m_ID + " - 연결 종료");
						for(int i = 0; i < ChatServer.m_OutputList.size(); ++i)
						{
							ChatServer.m_OutputList.get(i).println(m_ID + " - 연결 종료");
							ChatServer.m_OutputList.get(i).flush();
						}
						break;
					}
					split=text.split("VLRMSG:");
					if(split.length >= 2)
					{
						if(ChatServer.MSGVIEWSWITCH) {
							System.out.println(text);
						}
						split=split[1].split(":");
						if(split[1].equalsIgnoreCase("LIST")) {
							if(split[2].equalsIgnoreCase("NULL")) {
								if(ChatServer.checkList.containsKey(split[0])) {
									ChatServer.checkList.remove(split[0]);
									if(ChatServer.MSGVIEWSWITCH) {
										System.out.println("정보 갱신 : "+split[0]+" - 연결된 장비 없음");
									}
								}
								for(Map.Entry<String,Status>listUp:ChatServer.deviceList.entrySet()) {
									if(listUp.getValue().getGATEWAYLIST().containsKey(split[0])) {
										listUp.getValue().getGATEWAYLIST().remove(split[0]);
										if(ChatServer.MSGVIEWSWITCH) {
											System.out.println("HLR UPDATE : "+listUp.getKey()+" - "+split[0]+" 연결 정보 삭제");
										}
									}
								}
							}else if(split[2].equalsIgnoreCase("START")) {
								if(ChatServer.checkList.containsKey(split[0])) {
									ChatServer.checkList.get(split[0]).clear();
									if(ChatServer.MSGVIEWSWITCH) {
										System.out.println("정보 갱신 : "+split[0]+" - 연결된 장비 초기화");
									}
								}else {
									ChatServer.checkList.put(split[0],new HashSet<String>());
									if(ChatServer.MSGVIEWSWITCH) {
										System.out.println("정보 갱신 : "+split[0]+" - 새 연결된 장비 감지");
									}
								}
							}else if(split[2].equalsIgnoreCase("STOP")) {
								if(ChatServer.checkList.containsKey(split[0])) {
									for(Map.Entry<String,Status>listUp:ChatServer.deviceList.entrySet()) {
										if(!ChatServer.checkList.get(split[0]).contains(listUp.getKey())) {
											if(listUp.getValue().getGATEWAYLIST().containsKey(split[0])) {
												listUp.getValue().getGATEWAYLIST().remove(split[0]);
												if(ChatServer.MSGVIEWSWITCH) {
													System.out.println("HLR UPDATE : "+listUp.getKey()+" - "+split[0]+" 연결 정보 삭제");
												}
											}
										}
									}
								}else {
									for(Map.Entry<String,Status>listUp:ChatServer.deviceList.entrySet()) {
										if(listUp.getValue().getGATEWAYLIST().containsKey(split[0])) {
											listUp.getValue().getGATEWAYLIST().remove(split[0]);
											if(ChatServer.MSGVIEWSWITCH) {
												System.out.println("HLR UPDATE : "+listUp.getKey()+" - "+split[0]+" 연결 정보 삭제");
											}
										}
									}
								}
							}
						}else if(split[1].equalsIgnoreCase("UPDATE")) {
							if(ChatServer.checkList.containsKey(split[0])) {
								ChatServer.checkList.get(split[0]).add(split[2]);
								if(ChatServer.MSGVIEWSWITCH) {
									System.out.println("정보 갱신 : "+split[0]+" 목록 갱신 - "+split[2]+" 장비와 연결 확인");
								}
							}else {
								ChatServer.checkList.put(split[0],new HashSet<String>());
								ChatServer.checkList.get(split[0]).add(split[2]);
								if(ChatServer.MSGVIEWSWITCH) {
									System.out.println("정보 갱신 : "+split[0]+" 목록 생성 - "+split[2]+" 장비와 연결 확인");
								}
							}
							if(ChatServer.deviceList.containsKey(split[2])) {
								Status status=ChatServer.deviceList.get(split[2]);
								status.setChannel(split[3]);
								if(ChatServer.deviceList.get(split[2]).getGATEWAYLIST().containsKey(split[0])) {
									ChatServer.deviceList.get(split[2]).getGATEWAYLIST().replace(split[0],split[4]);
								}else {
									ChatServer.deviceList.get(split[2]).getGATEWAYLIST().put(split[0],split[4]);
								}
								if(ChatServer.MSGVIEWSWITCH) {
									System.out.println("HLR UPDATE : "+split[2]+" - UPDATE");
								}
							}else {
								ChatServer.deviceList.put(split[2],new Status(split[2],split[3],split[0],split[4]));
								if(ChatServer.MSGVIEWSWITCH) {
									System.out.println("HLR UPDATE : "+split[2]+" - CREATE");
								}
							}
						}
//						else if(split[1].equalsIgnoreCase("DEL")) {
//							if(ChatServer.deviceList.containsKey(split[2])&&ChatServer.deviceList.get(split[2]).getGATEWAYLIST().containsKey(split[0])) {
//								ChatServer.deviceList.get(split[2]).getGATEWAYLIST().remove(split[0]);
//							}
//							if(ChatServer.deviceList.get(split[2]).getGATEWAYLIST().isEmpty()) {
//								ChatServer.deviceList.remove(split[2]);
//							}
//						}
//						if(ChatServer.MSGVIEWSWITCH) {
//							System.out.println("HLR UPDATE");
//						}
						continue;
					}
					split=text.split("UHSDM ->>");
					if(split.length<2||ChatServer.VIEWSWITCH) {
						System.out.println(m_ID + "> "+ text);
					}
					for(int i = 0; i < ChatServer.m_OutputList.size(); ++i)
					{
						ChatServer.m_OutputList.get(i).println(m_ID + "> "+ text);
						ChatServer.m_OutputList.get(i).flush();
					}
				}
			}while(false);
			sendWriter.println("접속을 종료합니다.");
			sendWriter.flush();
			sendWriter.close();
			tmpbuffer.close();
			ChatServer.m_OutputList.remove(new PrintWriter(m_socket.getOutputStream()));
			if(!m_socket.isClosed()) {
				m_socket.close();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setSocket(Socket _socket)
	{
		m_socket = _socket;
	}
}
