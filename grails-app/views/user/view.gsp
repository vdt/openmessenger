
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
    	
            
        <div class="row">
          	<div class="span12">
          		<div class="wrapper">
          		<div class="page-header">
			          	<h1>User Setting: <small> ${userInstance?.username}</small></h1>
			    </div>
			    <g:if test="${flash.message}">
		            <div class="message">${flash.message}</div>
		        </g:if>
          		<g:form class="form-horizontal">				
				<g:hiddenField name="id" value="${userInstance?.id}" />
				<fieldset>
					<legend class="noborder">User Details</legend>

						<div class="control-group">
							<label class="control-label" for="xlInput">Username</label>
							<div class="controls">				
								<label class="inline-form">					
									${fieldValue(bean: userInstance, field: "username")}
								</label>
							</div>												
						</div>	
											
						<div class="control-group">
							<label class="control-label" for="xlInput">Password</label>
							<div class="controls">				
								<label class="inline-form">*************</label>
							</div>						
						</div>
						<div class="control-group">
							<label class="control-label" for="xlInput">Firstname</label>
							<div class="controls">				
								<label class="inline-form">							
									${fieldValue(bean: userInstance, field: "firstname")}
								</label>
							</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="xlInput">Lastname</label>
							<div class="controls">				
								<label class="inline-form">							
									${fieldValue(bean: userInstance, field: "lastname")}
								</label>
							</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="xlInput">Email</label>
							<div class="controls">				
								<label class="inline-form">							
									${fieldValue(bean: userInstance, field: "email")}
								</label>
								</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="xlInput">Options</label>
							
							<div class="controls">				
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
						
						<div class="control-group">
							<label class="control-label" for="multiSelect">Roles</label>
							<div class="controls">				
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
						
						<div class="control-group">
							<label class="control-label" for="multiSelect">Events</label>
							<div class="controls">				
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
						
						<div class="control-group">
							<div class="controls">				
								<div class="inline-inputs">	
		                			<g:form>
				                    <g:hiddenField name="id" value="${userInstance?.id}" />
				                    <span class="button"><g:actionSubmit class="btn primary" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
				                    <span class="button"><g:actionSubmit class="btn" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
		                			</g:form>
	            				</div> <!-- inline-inputs -->	
							</div> <!-- input -->
						</div> <!-- clearfix -->
            											
				</fieldset>	
			
			</div> <!-- wrapper -->				     
          	</div> <!-- span12 -->

        </div> <!-- row -->

    </body>
</html>
