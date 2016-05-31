package com.liferay.docs.twiliosms.portlet;

import com.liferay.docs.twiliosms.models.SMS;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

/**
 * Portlet implementation class TwilioSMS
 */
public class TwilioSMS extends MVCPortlet {
	public static final String ACCOUNT_SID = "AC4e3c68dfa43b870497b841c8f83992d2"; 
    public static final String AUTH_TOKEN = "97a41ac9350a752f082ceb100cd0696e"; 
	public void sendSMS(ActionRequest request, ActionResponse response) throws TwilioRestException {

		 try {

		       PortletPreferences prefs = request.getPreferences();

		       String[] twilioMessages = prefs.getValues("twilio-message",
		          new String[1]);

		       ArrayList<String> messages = new ArrayList<String>();

		       if (twilioMessages != null) {

		         messages = new ArrayList<String>(Arrays.asList(prefs.getValues(
		              "twilio-message", new String[1])));

		       }

		       String to = ParamUtil.getString(request, "To");
		       String from = ParamUtil.getString(request, "From");
		       String message = ParamUtil.getString(request, "Message");
		       String entry = to + "^" + from+"^"+message;
		       
		       /**
		        TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
		       
		         // Build the parameters 
		         List<org.apache.http.NameValuePair> params = new ArrayList<org.apache.http.NameValuePair>(); 
		         params.add(new org.apache.http.message.BasicNameValuePair("To", to)); 
		         params.add(new org.apache.http.message.BasicNameValuePair("From", from)); 
		         params.add(new org.apache.http.message.BasicNameValuePair("Body", message)); 
		       
		         MessageFactory messageFactory = client.getAccount().getMessageFactory(); 
		         Message send = messageFactory.create(params); 
		         System.out.println("This is the body:"+send.getBody());
		         System.out.println("This is to: "+send.getTo());
		         System.out.println("This is from: "+send.getFrom());
		         System.out.println(send.getSid());
		         */
		         messages.clear();
		       messages.add(entry);

		       String[] array = messages.toArray(new String[messages.size()]);

		       prefs.setValues("twilio-message", array);

		       try {

		         prefs.store();

		       } catch (IOException ex) {

		         Logger.getLogger(TwilioSMS.class.getName()).log(
		              Level.SEVERE, null, ex);

		       } catch (ValidatorException ex) {

		         Logger.getLogger(TwilioSMS.class.getName()).log(
		              Level.SEVERE, null, ex);

		       }

		    } catch (ReadOnlyException ex) {

		       Logger.getLogger(TwilioSMS.class.getName()).log(
		          Level.SEVERE, null, ex);

		    }

		}
	@Override
	public void render (RenderRequest renderRequest, RenderResponse renderResponse) 
	        throws PortletException, IOException {

	    PortletPreferences prefs = renderRequest.getPreferences();
	    String[] sms = prefs.getValues("twilio-message",
	            new String[1]);

	    if (sms != null) {

	        List<SMS> messages = parseSMS(sms);

	        renderRequest.setAttribute("messages", messages);
	    }

	    super.render(renderRequest, renderResponse);

	}
	private List<SMS> parseSMS (String[] sms) {

	    ArrayList<SMS> messages = new ArrayList();

	    for (String message : sms) {
	        String[] parts = message.split("\\^", 3);
	        SMS gbEntry = new SMS(parts[0], parts[1],parts[2]);
	        messages.add(gbEntry);
	    }

	    return messages;
	}

}
