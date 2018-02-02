package com.sendemails.sendemailstrial.controllers;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sendemails.sendemailstrial.EmailDetails;
import com.sendemails.sendemailstrial.api.SendE_MG;
import com.sendemails.sendemailstrial.api.SendE_SG;

@RestController
@RequestMapping("/api")
public class SendEmailController {
	
	final String Error_4xx = "There was a problem with your request";
	final String UnknownError = "Error sending your email";
	
	@RequestMapping(value = "/sendemail", method = RequestMethod.POST, 
            consumes = "application/json", produces = "application/json")
	public Response SendEmail (@RequestBody String incomingData){
		
		String returnString = null;
		JSONArray jsonArray = new JSONArray();
		JSONObject jsonObject = new JSONObject();
		int code = 0;
		EmailDetails data = new EmailDetails(incomingData);
		String ErrorMessage = null;
		
		//Sending email using SendGrid
		try {
			
			SendE_SG.Using_Sendgrid(data);
			
			
		} catch (IOException ex) {
			// TODO Auto-generated catch block
			ErrorMessage = ex.getMessage();
			System.out.println(ErrorMessage);
			//Getting the Error Status code
	    	Matcher exMsgStatusCodeMatcher = Pattern.compile("^Request returned status Code (\\d+)").matcher(ex.getMessage());
          if(exMsgStatusCodeMatcher.find()) {
              code = Integer.parseInt(exMsgStatusCodeMatcher.group(1));
          } else {
              // There can be other types of exceptions not handled here
              System.out.println("Exception (" + ex.getClass().getSimpleName() + ") doesn't contain status code: " + ex);
          }
          
		}
		
		
		//Status 5xx responses indicate an error made by SendGrid
		//Problem with SendGrid's Server
		if (code >= 500 && code <=599)
		{
			//Sending email using Mailgun 
			try {
				
				SendE_MG.Using_Mailgun(data);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();	
				
				try {
					jsonObject.put("Status", "");
					jsonObject.put("Error", UnknownError);//e.getMessage());//"Error Geting Deal List");
					returnString = jsonArray.put(jsonObject).toString();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				
			}
		//If there is an error that is not server	
		} else if (code != 0) {
			
			try {
				jsonObject.put("Status", code);
				jsonObject.put("Error", Error_4xx);
				returnString = jsonArray.put(jsonObject).toString();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			return Response.status(code).entity(returnString).build();
			
			//Use this code to get the whole error message
//			try {
//				jsonObject.put("Code", code);
//				jsonObject.put("Error", ErrorMessage);
//				returnString = jsonArray.put(jsonObject).toString();
//			} catch (JSONException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			return Response.status(code).entity(returnString).build();
			
			//If the mail was sent successfully
		}else {
			try {
				jsonObject.put("Status", 200);
				jsonObject.put("MSG", "Mail was sent successfully");
				returnString = jsonArray.put(jsonObject).toString();
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return Response.ok(returnString).build();
		
	}

}
