

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
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="row">
          	<div class="span12">
          		<div class="wrapper">
					<g:form class="form-horizontal" action="save" >
						<fieldset>
						<div class="page-header">
							<h1>Create User</h1>
			        	</div>
						<div class="control-group">
							<label class="control-label" for="xlInput">Username</label>
								<div class="controls">
									<input id="xlInput" class="input-xlarge" type="text" size="30" name="username" value="${userInstance?.username}" />
								</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="xlInput">Password</label>
							<div class="controls">
								<input id="xlInput" class="input-xlarge" type="password" size="30" name="password" value="${userInstance?.password}" />
							</div>
						</div>

						<div class="control-group">
							<label class="control-label" for="xlInput">Firstname</label>
								<div class="controls">
									<input id="xlInput" class="input-xlarge" type="text" size="30" name="firstname" value="${userInstance?.firstname}" />
								</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="xlInput">Lastname</label>
							<div class="controls">
								<input id="xlInput" class="input-xlarge" type="text" size="30" name="lastname" value="${userInstance?.lastname}" />
							</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="xlInput">Email</label>
							<div class="controls">
								<input id="xlInput" class="input-xlarge" type="email" size="30" name="email" value="${userInstance?.email}" />
							</div>
						</div>						
						<div class="control-group">
							<label class="control-label" for="xlInput">Options</label>
							<div class="controls">								
								<label class="checkbox">
									<input type="checkbox" name="accountExpired" value="${userInstance?.accountExpired}" ${userInstance?.accountExpired?'checked=checked':null}/>
									<span id="input-unicode-id" title="Unicode" data-content="This is content">Is account expired</span>
								</label>								
								<label class="checkbox">
									<input type="checkbox" name="accountLocked" value="${userInstance?.accountLocked}" ${userInstance?.accountLocked?'checked=checked':null}/>
									<span id="input-sender-id" title="Sender ID" data-content="This is content">Is account locked</span>
								</label>								
								<label class="checkbox">
									<input type="checkbox" name="enabled" value="${userInstance?.enabled}" ${userInstance?.enabled?'checked=checked':null}"/>
									<span id="input-unicode-id" title="Unicode" data-content="This is content">Enabled</span>
								</label>								
								<label class="checkbox">
									<input type="checkbox" name="passwordExpired" value="${userInstance?.passwordExpired}" ${userInstance?.passwordExpired?'checked=checked':null}"/>
									<span id="input-sender-id" title="Sender ID" data-content="This is content">Is password expired</span>
								</label>								
								<span class="help-block">
								<strong>Note:</strong>
								Labels surround all the options for much larger click areas and a more usable form.
								</span>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="multiSelect">Roles</label>
							<div class="controls">
								<select id="multiSelect" class="medium" name="roles" multiple="multiple" size="5">
									<g:each var="auth" in="${Role.list().sort{it.authority}}">
										<option value="${auth.id}" >${auth.authority.encodeAsHTML()}</option>
									</g:each>
								</select>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="multiSelect">Events</label>
							<div class="controls">
								<select id="multiSelect" class="medium" name="events" multiple="multiple" size="7">
									<g:each var="event" in="${Event.list().sort{ it.name }}">
										<option value="${event.id}" >${event.name.encodeAsHTML()}</option>
									</g:each>
								</select>
							</div>
						</div>						
						
						<div class="control-group">
							<div class="controls">
								<div class="inline-inputs">	
									<input class="btn primary" type="submit" value="Create">
									<button class="btn" type="reset">Cancel</button>
								</div>
							</div>
						</div>
												
						</fieldset>							
					</g:form>     
				</div> <!-- wrapper --> 
          	</div> <!-- span12 -->
        </div>  <!-- row -->
    
    </body>
</html>
