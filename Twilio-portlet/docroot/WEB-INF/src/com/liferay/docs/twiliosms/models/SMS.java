package com.liferay.docs.twiliosms.models;

public class SMS {
	
	private String To;
	private String From;
	private String Message;

	public SMS(){
		this.To= null;
		this.From= null;
		this.Message= null;
	}
	
	 public SMS (String to, String from, String message) {
		 this.To = to;
		 this.From=from;
		 this.Message=message;
	    }

	    public String getTo() {

	        return this.To;

	    }

	    public void setTo(String to) {

	        this.To = to;

	    }

	    public String getMessage() {

	        return this.Message;

	    }

	    public void setMessage(String message) {

	        this.Message = message;

	    }
	    
	    public void setFrom(String from){
	    	this.From=from;
	    }
	    public String getFrom(){
	    	return this.From;
	    }
	    }

