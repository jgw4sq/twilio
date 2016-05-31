<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>

<portlet:defineObjects />

<portlet:renderURL var="viewURL">
    <portlet:param name="mvcPath" value="/html/twiliosms/view.jsp"></portlet:param>
</portlet:renderURL>
</p>The following message was sent:</p>



<portlet:defineObjects />
<jsp:useBean id="messages" class="java.util.ArrayList" scope="request"/>
<liferay-ui:search-container>
	<liferay-ui:search-container-results
		results="<%= messages %>"
	/>

	<liferay-ui:search-container-row
		className="com.liferay.docs.twiliosms.models.SMS"
		modelVar="sms"
	>
	
		<liferay-ui:search-container-column-text property="to" />

		<liferay-ui:search-container-column-text property="from" />
		<liferay-ui:search-container-column-text property="message" />
		

		
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:button-row>

            <aui:button value ="Send Another Message" type="submit" onClick="<%= viewURL.toString() %>"></aui:button>

        </aui:button-row>