import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.example.db.CanvasHelper;
import com.example.db.DBConnection;
import com.example.server.BroadCast;
import com.example.util.CanvasListMessage;
import com.example.util.Message;
import com.example.util.Point;


public class Main {
	public static void main(String[] args) {
		Connection con = DBConnection.getConnection();
		Statement stmt;
		int cnt = 0;
			/*User u = UserHelper.getUser("nikhil");
			if(u!=null)System.out.println(u.getEmailId());*/
		
		//System.out.println(CanvasHelper.createCanvasReturnId("myCanvas","password", 1,1,-1,new Date(), true, ""));
	/*	 Canvas c = CanvasHelper.getCanvas(3);
		 if(c!=null)System.out.println(c.getName());*/
		 
		/* Group g = GroupHelper.getGroup(2);
		 if(g.getList().size()!=0){
			 System.out.println(g.getList().get(0).getUsername());
		 }*/
		 
		/*// BroadCast.createCanvasGroup(1, "canvas1","blonde");
		Message m = new Message();
		m.setSenderId(1+"");
		m.setGroupId(1+"");
		m.setPassword("passwordk");
		m.setOperationCode("OPECODE");
		//m.setCurves(new ArrayList<ArrayList<Point>>());
		
		String str;
		try {
			ArrayList<ArrayList<Point>> i = m.getCurves();
			ArrayList<Point> p = new ArrayList<Point>();
			for(int i1=0;i1<5;i1++) {
				Point p1 = new Point();
				p1.setX(i1);
				p1.setY(i1);
				p.add(p1);
			}
			m.getCurves().add(p);
			System.out.println("Message: "+Message.encodeJson(m));
			String data = "{\"X\":73.18245,\"Y\":265.7298}";//{\"X\":83.82769,\"Y\":285.14548},{\"X\":88.0,\"Y\":292.0}";
			String str2 = Message.encodeJson(m, data);
			Message m2 = Message.decodeJson(str2);
			System.out.println(m2.getGroupId());
			System.out.println(Message.encodeJson(m2));
			//System.out.println(m2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//System.out.println(m);
		
		ArrayList<CanvasListMessage> list = CanvasHelper.getAllOnlineCanvasList();
		if(list==null) System.out.println("NOT FOUND ANY CANVAS ONLINE");
		else {
			for(CanvasListMessage c: list) {
				System.out.println(c);
			}
		}
		try {
			System.out.println("ENcode messsage : "+CanvasListMessage.encodeJson(list));
			ArrayList<CanvasListMessage> list2 = CanvasListMessage.decodeJson(CanvasListMessage.encodeJson(list));
			for(CanvasListMessage c2 : list2 ){
				System.out.println(c2);
			}
					
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
