package uk.ac.aber.rpsrrec.http;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * This class sends the HTTP Post request to the server, as well as listens to the response.
 * This class contains some minor file handling to delete the file if the thing is sent
 * correctly.
 * 
 * @author Jon Shire, jos56@aber.ac.uk
 *
 */
public class MessageSender {

	private HttpClient defaultClient;
	private HttpPost postServer;
	private MultipartEntityBuilder multipartMime;
	private String responseBody;
	
	/**
	 * The constructor takes the given MultipartEntityBuilder into the system for compiling into
	 * the HTTPPost request, then calls the methods to build the message and send it.
	 * @param multipartMime
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public MessageSender(MultipartEntityBuilder multipartMime) throws ClientProtocolException, IOException{
		this.multipartMime = multipartMime;
		defaultClient = new DefaultHttpClient();
		buildMessage();
		sendMessage();
	}
	
	/**
	 * Creates the very basic HTTPPost setup with header.
	 */
	public void buildMessage(){
		postServer = new HttpPost("url"); //Change URL to whatever the server address is.
		postServer.setHeader("enctype", "multipart/mixed");
	}
	
	/**
	 * Sets the MultipartEntityBuilder as the content of the HTTPPost message,
	 * then sends it. It gives the server response back as a string.
	 * 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public void sendMessage() throws ClientProtocolException, IOException{
		postServer.setEntity(multipartMime.build());
		HttpResponse serverResponse = defaultClient.execute(postServer);
		
		responseBody = EntityUtils.toString(serverResponse.getEntity());
	}
	
	/**
	 * Use this to get the server's response.
	 * 
	 * @return Server response message.
	 */
	public String getResponse(){
		return responseBody;
	}
}
