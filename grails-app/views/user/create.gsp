

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
          			<div class="page-header">
							<h1>Create User</h1>
			        </div>
					<g:form class="form-horizontal" action="save" >
						<div class="tabbable">
                  <ul class="nav nav-tabs nav-border">
                    <li class="active">
                      <a data-toggle="tab" href="#tab-main">Main</a>
                    </li>
                    <li>
                      <a data-toggle="tab" href="#tab-roles">Roles</a>
                    </li>
                    <li>
                      <a data-toggle="tab" href="#tab-events">Events</a>
                    </li>
                  </ul><!-- nav-tabs -->

                  	<div class="tab-content tab-content-border tab-content-control">
                    <div id="tab-main" class="tab-pane active">
												
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
									<g:checkBox name="accountExpired" value="${userInstance?.accountExpired}" ${userInstance?.accountExpired?'checked=checked':null}/>
									<span id="input-unicode-id" title="Unicode" data-content="This is content">Is account expired</span>
								</label>								
								<label class="checkbox">
									<g:checkBox name="accountLocked" value="${userInstance?.accountLocked}" ${userInstance?.accountLocked?'checked=checked':null}/>
									<span id="input-sender-id" title="Sender ID" data-content="This is content">Is account locked</span>
								</label>								
								<label class="checkbox">
									<g:checkBox name="enabled" value="${userInstance?.enabled}" ${userInstance?.enabled?'checked=checked':null}"/>
									<span id="input-unicode-id" title="Unicode" data-content="This is content">Enabled</span>
								</label>								
								<label class="checkbox">
									<g:checkBox name="passwordExpired" value="${userInstance?.passwordExpired}" ${userInstance?.passwordExpired?'checked=checked':null}"/>
									<span id="input-sender-id" title="Sender ID" data-content="This is content">Is password expired</span>
								</label>								
								<span class="help-block">
								<strong>Note:</strong>
								Labels surround all the options for much larger click areas and a more usable form.
								</span>
							</div>
						</div>
						
						</div><!-- #tab-main -->

						
						<div id="tab-roles" class="tab-pane">

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
						</div><!-- #tab-roles -->
						

						<div id="tab-events" class="tab-pane">
						<div class="control-group">
							<label class="control-label" for="multiSelect">Events</label>
							<div class="controls">
								<select id="multiSelect" class="medium" name="events" multiple="multiple" size="15">
									<g:each var="event" in="${Event.list().sort{ it.name }}">
										<option value="${event.id}" >${event.name.encodeAsHTML()}</option>
									</g:each>
								</select>
							</div>
						</div>	

						</div><!-- #tab-events -->
                  </div><!-- tab-content -->
						</div><!-- tabbable -->					
						
						<div class="form-actions">
							<button class="btn btn-primary" type="submit">Create</button>
							<button class="btn" type="reset">Cancel</button>
						</div>
												
												
					</g:form>     
				</div> <!-- wrapper --> 
          	</div> <!-- span12 -->
        </div>  <!-- row -->
    
    <script src="${resource(dir:'js',file:'bootstrap-tab.js')}"></script> 
    </body>
</html>
