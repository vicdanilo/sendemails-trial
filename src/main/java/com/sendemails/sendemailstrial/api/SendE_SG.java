package com.sendemails.sendemailstrial.api;

import java.io.IOException;

import com.sendemails.sendemailstrial.EmailDetails;
import com.sendgrid.Content;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

public class SendE_SG {
	
	//Key
	//Add your key
	private static String key_sg = "";
	
	public static void Using_Sendgrid (EmailDetails data) throws IOException {
		
	    
//	    SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
	    SendGrid sg = new SendGrid(key_sg);
	    Request request = new Request();
	    Response response = null;
	    
	    //Preparing and sending the mail
	    try {
	    	
	    	Mail mail = buildEmail_SG(data);
	    	request.setMethod(Method.POST);
	    	request.setEndpoint("mail/send");
	    	request.setBody(mail.build());
	    	response = sg.api(request);
	    
	    } catch (IOException ex) {
            throw ex;
	    }
		
		
	}
		
		
	public static Mail buildEmail_SG(EmailDetails data) throws IOException {
			
		Mail mail = new Mail();

		Email fromEmail = new Email();
		fromEmail.setEmail(data.getfrom());
		mail.setFrom(fromEmail);

		mail.setSubject(data.getsubject());
			
		Personalization personalization = new Personalization();
		    
		    
		Email to = new Email();
		for (String temp : data.getto().split("\\s") ) 
		  {
			if(temp.isEmpty() || temp == null || temp == "") continue;
		    to.setEmail(temp);
		    personalization.addTo(to);
		  }
		    
		Email cc = new Email();
		for (String temp : data.getcc().split("\\s") ) 
		  {
			if(temp.isEmpty() || temp == null || temp == "") continue;
		    cc.setEmail(temp);
		    personalization.addCc(cc);
		  }
		    	
		Email bcc = new Email();
		for (String temp : data.getbcc().split("\\s") ) 
		  {
			if(temp.isEmpty() || temp == null || temp == "") continue;
		    bcc.setEmail(temp);
		    personalization.addBcc(bcc);
		  }
			
		mail.addPersonalization(personalization);
		    
		Content content = new Content();
		content.setType("text/plain");
		content.setValue(data.gettext());
		mail.addContent(content);
		    
		return mail;
	}

}
