package server;
import java.util.Map;
public class HLRTHREAD extends Thread{
	@Override
	public void run() {
		super.run();
		try {
			while(true) {
				if(ChatServer.ENDSWITCH) {
					break;
				}else if(!ChatServer.deviceList.isEmpty()){
					for(Map.Entry<String,Status>listUp:ChatServer.deviceList.entrySet()) {
						if(listUp.getValue().getGATEWAYLIST().isEmpty()) {
							if(ChatServer.MSGVIEWSWITCH) {
								System.out.println("HLR UPDATE : "+listUp.getKey()+" - DELETE");
							}
							ChatServer.deviceList.remove(listUp.getKey());
							break;
						}
					}
				}
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}
}