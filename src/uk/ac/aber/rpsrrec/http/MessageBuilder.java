package uk.ac.aber.rpsrrec.http;

import java.io.File;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

/**
 * This class builds a MIME MultiPart message using Apache libraries.
 * This message contains the previously made XML file and image
 * files connected within.
 * 
 * @author Jon Shire, jos56@aber.ac.uk
 *
 */
public class MessageBuilder {
	private int imageCount;
	private MultipartEntityBuilder messageBuilder;
	private File xmlFile;
	
	/**
	 * The constructor just passes the given variables into the system
	 * before calling the message building methods.
	 * 
	 * @param xmlFile The file path of the given XML file.
	 * @param imageCount
	 */
	public MessageBuilder(String xmlFile, int imageCount){
		this.xmlFile = new File(xmlFile);
		this.imageCount = imageCount;
		buildMessage();
		addImagesToMessage();
	}
	
	/**
	 * This method builds the original message to be sent to the server, first by
	 * setting the content type for the XML portion to the correct MIME Type before
	 * creating the message builder and adding the given file.
	 */
	public void buildMessage(){
		ContentType xmlType = ContentType.TEXT_XML;
		messageBuilder = MultipartEntityBuilder.create();
		messageBuilder.addBinaryBody(xmlFile.getName(), xmlFile, xmlType, null);
	}
	
	/**
	 * This method adds the number of images as dictated in the provided image count
	 * to the message, based on the XML's file name, plus an underscore and the image
	 * number. This will loop until the given count of images are added.
	 */
	public void addImagesToMessage(){
		ContentType imgType = ContentType.create("Image/jpg");
		for(int i = 0; i < imageCount; i++){
			String imageName = xmlFile.getName()+"_"+i;
			File imageFile = new File(imageName);
			messageBuilder.addBinaryBody(imageFile.getName(), imageFile, imgType, null);			
		}
	}
}