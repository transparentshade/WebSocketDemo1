package com.example.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bean.Canvas;
import com.example.bean.Group;
import com.example.bean.User;
import com.example.db.CanvasHelper;
import com.example.db.GroupHelper;
import com.example.db.UserHelper;
import com.example.server.ServerEndpointDemo;

/**
 * Servlet implementation class CreateCanvasServlet2
 */
@WebServlet("/CreateCanvasServlet2")
public class CreateCanvasServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public CreateCanvasServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
   	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
   			throws ServletException, IOException {
   		// TODO Auto-generated method stub
   	}

   	@Override
   	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
   			throws ServletException, IOException {
   		// TODO Auto-generated method stub
   		String creatorId = (String) req.getParameter("creatorId");
   		String password = (String) req.getParameter("password");
   		String canvasName = (String) req.getParameter("canvasName");

   		if (creatorId == null || password == null || canvasName == null) {
   			// stop everything and return;
   			System.out.println("CANNOT CREATE CANVAS DATA STAT: ->");
   			System.out.println("\t creatorid: " + creatorId + "\t password: "
   					+ password + "\t canvasName: " + canvasName);
   			resp.getWriter().write("ERROR OCCURED creator id or password or canvas Name is null ");
   			resp.getWriter().flush();
   			return;
   			
   		}
   		User u = UserHelper.getUserById(Integer.parseInt(creatorId));
   		Canvas c = new Canvas();
   		c.setName(canvasName);
   		c.setPassword(password);
   		c.setId(-1);
   		c.setCreator(u);
   		c.setEditor(u);
   		c.setValid(true);
   		c.setData("{\"Y\":\"0\",\"X\":\"0\"}");
   		int canvasId = CanvasHelper.createCanvasReturnId(canvasName, password,
   				u.getId(), u.getId(), -1, null, true, "");
   		c.setId(canvasId);
   		if(canvasId==-1) {
   			resp.getWriter().write("ERROR OCCURED while updateing DB canvas ");
   			resp.getWriter().flush();
   			return;
   		}
   		// create the group.
   		int gid = GroupHelper.createGroupEntryReturnID(c, u);
   		/*if(gid==-1) {
   			resp.getWriter().write("ERROR OCCURED while updating DB Group ");
   			resp.getWriter().flush();
   			return;
   		}*/
   		Group g = new Group();
   		g.setId(c.getId());
   		HashMap<Integer, User> list = new HashMap<Integer,User>();
   		list.put(u.getId(),u);
   		g.setList(list);
   		
   		//add groupt ot canvas
   		c.setGroup(g);
   		
   		System.out.println("*****CANVAS CREATED SUCCESSFULLY************");
   		System.out.println(c);
   		
   		//crete json to send
   		resp.setContentType("text/html");
   		resp.getWriter().write(""+c.getId());
   		resp.getWriter().flush();
   		ServerEndpointDemo.onlineCanvas.put(c.getGroup().getId(), c);
   		System.out.println("current size of onlineCanvas : "+ServerEndpointDemo.onlineCanvas.size());
   		
   	}
   }
