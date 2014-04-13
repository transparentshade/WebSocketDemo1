package com.example.util;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class MessageDecoder implements Decoder.Text<Message>{

	@Override
	public void destroy() {
		System.out.println(" decoder : destroyed");
		
	}

	@Override
	public void init(EndpointConfig config) {
		// TODO Auto-generated method stub
		System.out.println(" Decoder : init");
	}

	@Override
	public Message decode(String s) throws DecodeException {
		// TODO Auto-generated method stub
		Message m = new Message();
		m = Message.decodeJson(s);
		System.out.println("Decoder : decode");
		return m;
	}

	@Override
	public boolean willDecode(String s) {
		// TODO Auto-generated method stub
		return false;
	}

}
