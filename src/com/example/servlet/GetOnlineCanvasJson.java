package com.example.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.bean.Canvas;
import com.example.db.CanvasHelper;
import com.example.util.CanvasListMessage;

/**
 * Servlet implementation class GetOnlineCanvasJson
 */
@WebServlet("/GetOnlineCanvasJson")
public class GetOnlineCanvasJson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOnlineCanvasJson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("ONLINE CANVAS LIST REQUEST ARRIVED ");
		ArrayList<CanvasListMessage> list = CanvasHelper.getAllOnlineCanvasList();
		String message = CanvasListMessage.encodeJson(list);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.write(message);
		out.flush();
	}

}
