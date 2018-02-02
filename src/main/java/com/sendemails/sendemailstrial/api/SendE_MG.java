package com.sendemails.sendemailstrial.api;

import java.io.IOException;

import com.sendemails.sendemailstrial.EmailDetails;

import net.sargue.mailgun.Configuration;
import net.sargue.mailgun.Mail;
import net.sargue.mailgun.MailBuilder;

public class SendE_MG {

	//Domain
	//Add your domain
	private static String domain = "";

	//Key from the specific Domain
	//Add your key
	private static String key_gun = "";
	

	public static void Using_Mailgun (EmailDetails data) throws IOException {
		
		//Preparing the mail and sending the mail
		try {
			
			MailBuilder mail = BuildEmail_SG(data);
			mail.build().send();	
			
		}catch(IOException ex){
			throw ex;
		}
		
	}
	
	private static MailBuilder BuildEmail_SG (EmailDetails data) throws IOException {
		
		Configuration configuration = new Configuration()
			    .domain(domain)
			    .apiKey(key_gun)
			    .from(data.getfrom());
		
		MailBuilder mail = Mail.using(configuration);
		
		for (String temp : data.getto().split("\\s") ) 
		  {
			if(temp.isEmpty() || temp == null || temp == "") continue;
			mail.to(temp);
		  }
		    
		for (String temp : data.getcc().split("\\s") ) 
		  {
			if(temp.isEmpty() || temp == null || temp == "") continue;
			mail.cc(temp);
		  }
		    	
		for (String temp : data.getbcc().split("\\s") ) 
		  {
			if(temp.isEmpty() || temp == null || temp == "") continue;
			mail.bcc(temp);
		  }
		
		mail.subject(data.getsubject());
		mail.text(data.gettext());
		
		return mail;
			
		
	}
	
	
	
	

}
