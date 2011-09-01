
<%@ page import="openmessenger.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    
  
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            
  	<table class="event-container">        
		<thead>
			<tr>
				<th class="event-title"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></th>
				<th class="event-news"></th>
				<th class="event-subscriber"></th>
				<th class="event-update"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th class="event-title">User Name</th>
				<th class="event-news">Name</th>
				<th class="event-subscriber">Email</th>
				<th class="event-update">Enabled</th>
				
			</tr>
		</thead>
		<tbody>
		<g:each in="${userInstanceList}" var="user" status="i">			
			<tr class="item-row odd">
				<td class="event-title"><g:link action="view" id="${user.id}">${i+1}. ${user.firstname} ${user.lastname}</g:link></td>
				<td class="event-news">${user.username}</td>
				<td class="event-subscriber">${user.email}</td>
				<td class="event-update">${user.enabled}</td> 
			</tr>
		</g:each>	
		</tbody>
	</table>
            
            
            
            
            
            
            <div class="paginateButtons">
                <g:paginate total="${userInstanceTotal}" />
            </div>
        </div>
        
    </body>
</html>
