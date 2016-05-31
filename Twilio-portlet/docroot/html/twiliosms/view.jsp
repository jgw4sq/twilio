<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<portlet:defineObjects />

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/html/twiliosms/sent.jsp"></portlet:param>
</portlet:renderURL>

<portlet:actionURL name="sendSMS" var="sendSMSURL"></portlet:actionURL>

<aui:form action="<%= sendSMSURL %>" name="<portlet:namespace />fm">

        <aui:fieldset>

            <aui:input name="To"></aui:input>
            <aui:input name="From"></aui:input>
              <aui:input name="Message"></aui:input>
            

        </aui:fieldset>

        <aui:button-row>

            <aui:button value ="Send Message" type="submit" onClick="<%= viewURL.toString() %>"></aui:button>

        </aui:button-row>
</aui:form>
