/*package com.example.servlet;

import java.io.IOException;
import java.util.ArrayList;

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

*//**
 * Servlet implementation class CreateCanvasServlet
 *//*
@WebServlet(description = "Devicess sends request to create the new canvas", urlPatterns = { "/CreateCanvasServlet" })
public class CreateCanvasServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    *//**
     * @see HttpServlet#HttpServlet()
     *//*
    public CreateCanvasServlet() {
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
		String creatorId = (String) req.getAttribute("creatorId");
		String password = (String) req.getAttribute("password");
		String canvasName = (String) req.getAttribute("canvasName");

		if (creatorId == null || password == null || canvasName == null) {
			// stop everything and return;
			System.out.println("CANNOT CREATE CANVAS DATA STAT: ->");
			System.out.println("\t creatorid: " + creatorId + "\t password: "
					+ password + "\t canvasName: " + canvasName);
		}
		User u = UserHelper.getUserById(Integer.parseInt(creatorId));
		Canvas c = new Canvas();
		Group g = new Group();
		g.setId(-1);
		c.setCreator(u);
		c.setData("");
		c.setEditor(u);
		c.setId(-1);
		c.setName(canvasName);
		c.setPassword(password);
		int canvasId = CanvasHelper.createCanvasReturnId(canvasName, password,
				u.getId(), u.getId(), -1, null, true, "");
		c.setId(canvasId);

		// create the group.
		int gid = GroupHelper.createGroupEntryReturnID(c, u);
		g.setId(gid);
		ArrayList<User> list = new ArrayList<User>();
		list.add(u);
		g.setList(list);
		
		//add groupt ot canvas
		c.setGroup(g);
		
		System.out.println("*****CANVAS CREATED SUCCESSFULLY************");
		System.out.println(c);
		
		
	}
}
*/