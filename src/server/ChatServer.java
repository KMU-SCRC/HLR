package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

public class ChatServer {

	public static ArrayList<PrintWriter> m_OutputList;
	public static String UserID;
	public static boolean ENDSWITCH=false;
	public static boolean VIEWSWITCH=false;
	public static boolean MSGVIEWSWITCH=false;
	public static boolean AUTOSWITCH=false;
	public static boolean DELAYSWITCH=true;
	public static long DELAY=1000;
	public static TreeMap<String,Status>deviceList=new TreeMap<String,Status>();
	public static TreeMap<String,HashSet<String>>checkList=new TreeMap<String,HashSet<String>>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		m_OutputList = new ArrayList<PrintWriter>();
		
			try {
				ServerSocket s_socket = new ServerSocket(8888);
				HostSendThread hostSendThread=new HostSendThread();
				hostSendThread.start();
				HLRTHREAD hlrthread=new HLRTHREAD();
				hlrthread.start();
				AutoListThread autoListThread=new AutoListThread();
				autoListThread.start();
				while(true)
				{
					Socket c_socket = s_socket.accept();
					ClientManagerThread c_thread = new ClientManagerThread();
					c_thread.setSocket(c_socket);
					
					m_OutputList.add(new PrintWriter(c_socket.getOutputStream()));
					
					c_thread.start();
					if(ENDSWITCH) {
						break;
					}
				}
				s_socket.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
