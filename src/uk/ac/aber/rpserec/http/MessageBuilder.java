package uk.ac.aber.rpserec.http;

import java.io.File;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;


public class MessageBuilder {
	private int imageCount;
	private MultipartEntityBuilder messageBuilder;
	private HttpPost httpPost;
	private File xmlFile;
	
	public MessageBuilder(String xmlFile, int imageCount){
		this.xmlFile = new File(xmlFile);
		this.imageCount = imageCount;
		buildMessage();
		addImagesToMessage();
	}
	
	public void buildMessage(){
		ContentType xmlType = ContentType.TEXT_XML;
		messageBuilder = MultipartEntityBuilder.create();
		messageBuilder.addBinaryBody(xmlFile.getName(), xmlFile, xmlType, null);
	}
	
	public void addImagesToMessage(){
		ContentType imgType = ContentType.create("Image/jpg");
		for(int i = 0; i < imageCount; i++){
			String imageName = xmlFile.getName()+"_"+i;
			File imageFile = new File(imageName);
			messageBuilder.addBinaryBody(imageFile.getName(), imageFile, imgType, null);			
		}
	}
}