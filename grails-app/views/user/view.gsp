
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
			          	<h1>User Details </h1>
			    </div>
			    <g:if test="${flash.message}">
		            <div class="message">${flash.message}</div>
		        </g:if>

          		<form class="form-horizontal">
          			<div class="tabbable">
	                  	<ul class="nav nav-tabs nav-border">
	                    	<li class="active">
	                      		<a data-toggle="tab" href="#tab-main">Main</a>
	                    	</li>
	                    	<sec:ifAnyGranted roles="ROLE_ADMINS">
		                    	<li>
		                      		<a data-toggle="tab" href="#tab-roles">Roles</a>
		                    	</li>
	                    	</sec:ifAnyGranted>
	                    	<sec:ifAnyGranted roles="ROLE_ADMINS,ROLE_MANAGER,ROLE_USER">
		                    	<li>
		                      		<a data-toggle="tab" href="#tab-events">Events</a>
		                    	</li>
	                    	</sec:ifAnyGranted>
	                  	</ul><!-- nav-tabs -->

				
						<div class="tab-content tab-content-border tab-content-control">
                    		<div id="tab-main" class="tab-pane active">
								<div class="control-group">
									<label class="control-label" for="xlInput">Username</label>
									<div class="controls">				
										<span class="input-xlarge uneditable-input">	
											${fieldValue(bean: userInstance, field: "username")}
										</span>
									</div>									
								</div>	
													
								<div class="control-group">
									<label class="control-label" for="xlInput">Password</label>
									<div class="controls">				
										<span class="input-xlarge uneditable-input">*************</span>
									</div>						
								</div>
								<div class="control-group">
									<label class="control-label" for="xlInput">Firstname</label>
									<div class="controls">				
										<span class="input-xlarge uneditable-input">
											${fieldValue(bean: userInstance, field: "firstname")}
										</span>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label" for="xlInput">Lastname</label>
									<div class="controls">				
										<span class="input-xlarge uneditable-input">	
											${fieldValue(bean: userInstance, field: "lastname")}
										</span>
									</div>
								</div>						
								<div class="control-group">
									<label class="control-label" for="xlInput">Email</label>
									<div class="controls">				
										<span class="input-xlarge uneditable-input">	
											${fieldValue(bean: userInstance, field: "email")}
										</span>
									</div>
								</div>						
								<div class="control-group">
									<label class="control-label" for="xlInput">Options</label>
									
									<div class="controls">				
										
										<label class="checkbox">
										<input type="checkbox" name="accountExpired" value="${userInstance?.accountExpired}" ${userInstance?.accountExpired?'checked=checked':null} disabled=true />
										<span id="input-unicode-id" title="Unicode" data-content="This is content">Is account expired</span>
										</label>
										
										<label class="checkbox">
										<input type="checkbox" name="accountLocked" value="${userInstance?.accountLocked}" ${userInstance?.accountLocked?'checked=checked':null} disabled=true />
										<span id="input-sender-id" title="Sender ID" data-content="This is content">Is account locked</span>
										</label>
										
										<label class="checkbox">
										<input type="checkbox" name="enabled" value="${userInstance?.enabled}" ${userInstance?.enabled?'checked=checked':null}" disabled=true />
										<span id="input-unicode-id" title="Unicode" data-content="This is content">Enabled</span>
										</label>
										
										<label class="checkbox">
										<input type="checkbox" name="passwordExpired" value="${userInstance?.passwordExpired}" ${userInstance?.passwordExpired?'checked=checked':null}" disabled=true />
										<span id="input-sender-id" title="Sender ID" data-content="This is content">Is password expired</span>
										</label>
										
									</div>
								</div>
							</div><!-- #tab-main -->

							<sec:ifAnyGranted roles="ROLE_ADMINS">
							<div id="tab-roles" class="tab-pane">
								<div class="control-group">
									<label class="control-label" for="multiSelect">Roles</label>
									<div class="controls">	
											<ul class="unstyled">						
											<g:each var="auth" in="${userInstance.authorities.sort{it.authority}}">	
												<li>									
												<span class="input-xlarge uneditable-input">
												${auth.authority.encodeAsHTML()}
												</span>
												</li>	
											</g:each>
											</ul>								
									</div>
								</div>
							</div><!-- #tab-roles -->
							</sec:ifAnyGranted>
                    
                    		<sec:ifAnyGranted roles="ROLE_ADMINS,ROLE_MANAGER,ROLE_USER">
                    		<div id="tab-events" class="tab-pane">				
								<div class="control-group">
									<label class="control-label" for="multiSelect">Events</label>
									<div class="controls">
											<ul class="unstyled">
											<g:each var="event" in="${userInstance.events.sort{it.name}}">
												<li>
													<span class="input-xlarge uneditable-input">
													${event.name.encodeAsHTML()}
													</span>
												</li>
											</g:each>
											</ul>
									</div>
								</div>	
							</div><!-- #tab-events -->
							</sec:ifAnyGranted>
                  		</div><!-- tab-content -->
                	</div><!-- tabbable -->

					<div class="form-actions">	
		                			
				        <g:hiddenField name="id" value="${userInstance?.id}" />
				            <span class="button"><g:actionSubmit class="btn btn-primary" action="edit" value="${message(code: 'default.button.edit.label', default: 'Edit')}" /></span>
				            <sec:ifAnyGranted roles="ROLE_ADMINS">
				               	<span class="button"><g:actionSubmit class="btn" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
				            </sec:ifAnyGranted>
		            </div> <!-- form-actions -->
				</form>
				</div> <!-- wrapper -->				     
          	</div> <!-- span12 -->
        </div> <!-- row -->
        <script src="${resource(dir:'js',file:'bootstrap-tab.js')}"></script> 
    </body>
</html>
