
<%@ page import="openmessenger.User" %>
<%@ page import="openmessenger.Role" %>
<%@ page import="openmessenger.Event" %>
<%@ page import="openmessenger.UserEvent" %>
<%@ page import="openmessenger.UserRole" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.view.label" args="[entityName]" /></title>
    </head>
    <body>
    
    	<div class="page-header">
          <h1>Setting <small> All your messages is here</small></h1>
    	</div>
    	
    	<g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
        </g:if>
            
            <div class="row">
          	<div>
          		<g:form >				
				<g:hiddenField name="id" value="${userInstance?.id}" />
				<fieldset>
					<legend>User Details</legend>
						<div class="clearfix">
							<label for="xlInput">Username</label>							
							<label class="inline-form">								
								${fieldValue(bean: userInstance, field: "username")}
							</label>								
						</div>						
						<div class="clearfix">
							<label for="xlInput">Password</label>
							<label class="inline-form">*************								
							</label>							
						</div>
						<div class="clearfix">
							<label for="xlInput">Firstname</label>
							<label class="inline-form">								
								${fieldValue(bean: userInstance, field: "firstname")}
							</label>
						</div>
						<div class="clearfix">
							<label for="xlInput">Lastname</label>
							<label class="inline-form">								
								${fieldValue(bean: userInstance, field: "lastname")}
							</label>
						</div>						
						<div class="clearfix">
							<label for="xlInput">Email</label>
							<label class="inline-form">								
								${fieldValue(bean: userInstance, field: "email")}
							</label>
						</div>						
						<div class="clearfix">
								<label for="xlInput">Options</label>
							<div class="input">
							<ul class="inputs-list">
							<li>
							<label>
							<input type="checkbox" name="accountExpired" value="${userInstance?.accountExpired}" ${userInstance?.accountExpired?'checked=checked':null} disabled=true />
							<span id="input-unicode-id" title="Unicode" data-content="This is content">Is account expired</span>
							</label>
							</li>
							<li>
							<label>
							<input type="checkbox" name="accountLocked" value="${userInstance?.accountLocked}" ${userInstance?.accountLocked?'checked=checked':null} disabled=true />
							<span id="input-sender-id" title="Sender ID" data-content="This is content">Is account locked</span>
							</label>
							</li>
							<li>
							<label>
							<input type="checkbox" name="enabled" value="${userInstance?.enabled}" ${userInstance?.enabled?'checked=checked':null}" disabled=true />
							<span id="input-unicode-id" title="Unicode" data-content="This is content">Enabled</span>
							</label>
							</li>
							<li>
							<label>
							<input type="checkbox" name="passwordExpired" value="${userInstance?.passwordExpired}" ${userInstance?.passwordExpired?'checked=checked':null}" disabled=true />
							<span id="input-sender-id" title="Sender ID" data-content="This is content">Is password expired</span>
							</label>
							</li>							
							</ul>
							<span class="help-block">
							<strong>Note:</strong>
							Labels surround all the options for much larger click areas and a more usable form.
							</span>
							</div>
						</div>
						
						<div class="clearfix">
							<label for="multiSelect">Roles</label>
							<div class="input">
								<ul class="inputs-list">
									<g:each var="auth" in="${userInstance.authorities.sort{it.authority}}">
										<li>
										<label>
										${auth.authority.encodeAsHTML()}
										</label>
										</li>
										
									</g:each>
								</ul>
							</div>
						</div>
						
						<div class="clearfix">
							<label for="multiSelect">Events</label>
							<div class="input">
								<ul class="inputs-list">
									<g:each var="event" in="${userInstance.events.sort{it.name}}">
										<li>
										<label>
										${event.name.encodeAsHTML()}
										</label>
										</li>
							
										
									</g:each>
								</ul>
							</div>
						</div>						
						</g:form>
						
						<div class="clearfix">
							<div class="input">
							<div class="inline-inputs">	
                <g:form>
                    <g:hiddenField name="id" value="${userInstance?.id}" />
                    <span class="button"><g:actionSubmit class="btn primary" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
                    <span class="button"><g:actionSubmit class="btn" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </g:form>
            </div>
						</div>
						</div>
            											
				</fieldset>	
							     
          </div>

        </div>

    </body>
</html>
