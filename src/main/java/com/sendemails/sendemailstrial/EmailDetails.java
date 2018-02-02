package com.sendemails.sendemailstrial;

import org.json.JSONException;
import org.json.JSONObject;

public class EmailDetails {

	private String from;
	private String to;
	private String subject;
	private String text;
	private String cc;
	private String bcc;
	
	public EmailDetails (String from, String to, String subject, String text) {
		this.from = from;
		this.to= to;
		this.subject = subject;
		this.text = text;
	}
	
	public EmailDetails (String incomingData) {
		
		JSONObject partsData = null;
		try {
			partsData = new JSONObject(incomingData);
			
			this.from = partsData.optString("from");
			this.to= partsData.optString("to");
			this.subject = partsData.optString("subject");
			this.text = partsData.optString("text");
			this.cc = partsData.optString("cc");
			this.bcc = partsData.optString("bcc");
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public String getfrom () {
		return from;
	}
	
	public String getto () {
		return to;
	}
	
	public String getsubject () {
		return subject;
	}
	
	public String gettext () {
		return text;
	}
	
	public String getcc () {
		return cc;
	}
	
	public String getbcc () {
		return bcc;
	}
	
	
}
