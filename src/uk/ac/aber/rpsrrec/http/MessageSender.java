package uk.ac.aber.rpsrrec.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class MessageSender {

	private HttpClient defaultClient;
	private HttpPost postServer;
	
	private MultipartEntityBuilder multipartMime;
	
	private String responseBody;
	
	public MessageSender(MultipartEntityBuilder multipartMime) throws ClientProtocolException, IOException{
		this.multipartMime = multipartMime;
		defaultClient = new DefaultHttpClient();
		buildMessage();
		sendMessage();
	}
	
	public void buildMessage(){
		postServer = new HttpPost("url");
		postServer.setHeader("enctype", "multipart/mixed");
	}
	
	public void sendMessage() throws ClientProtocolException, IOException{
		postServer.setEntity(multipartMime.build());
		HttpResponse serverResponse = defaultClient.execute(postServer);
		
		responseBody = EntityUtils.toString(serverResponse.getEntity());
	}
}
