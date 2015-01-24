package uk.ac.aber.rpsrrec.http;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;

public class MessageSender {

	private HttpClient defaultClient;
	private HttpPost postServer;
	
	private MultipartEntityBuilder multipartMime;
	
	public MessageSender(MultipartEntityBuilder multipartMime){
		this.multipartMime = multipartMime;
		defaultClient = new DefaultHttpClient();
		buildMessage();		
	}
	
	public void buildMessage(){
		postServer = new HttpPost("url");
		postServer.setHeader("enctype", "multipart/mixed");
		postServer.setEntity(multipartMime.build());
	}
	
	public void sendMessage(){
		
	}
}
