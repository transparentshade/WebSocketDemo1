package com.example.util;

import java.io.IOException;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message>{

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("client side destroy");
	}

	@Override
	public void init(EndpointConfig config) {
		System.out.println("client side ENpoint Config-- INit");
		
	}

	@Override
	public String encode(Message object) throws EncodeException {
		// TODO Auto-generated method stub
		try {
			return Message.encodeJson(object);
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
