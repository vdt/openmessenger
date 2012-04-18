
<%@ page import="openmessenger.User" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
    
  
        <div class="row">
          <div class="span12">
            <div class="wrapper">
              <div class="page-header">
                <h1>Users
              </div>
            </div> <!-- wrapper -->
          </div>
        </div> <!-- /row -->
            
        <div class="row">
          	<div class="span12">
	          	<div class="wrapper wrapper-no-border">
	          	
							
					<table id="sortTableExample"  class="table table-striped">
						<thead>
							<tr>
								<th class="header"></th>
								<th class="green header">Name</th>
								<th class="green header">Status</th>
								<th class="green header">Roles</th>
								</tr>
						</thead>
				        <tbody>
				        	<g:each in="${userInstanceList}" var="user" status="i">
				           	<tr>
				           		<td>${i+1+offset}</td>
				           		<td>
				           			<a href="${createLink(controller:'user', action:'view', params:[id:user.id])}">${user.firstname+' '+user.lastname}</a>
				           		</td>
				           		<td><span class="label label-success">${user.enabled?'ACTIVE':'INACTIVE' }</span></td>
				           		<td>${user.getAuthoritiesString()}</td>
				           	</tr>
				           	</g:each>	                	                             
				        </tbody>
				    </table>
				    <div class="pagination">
	                	<msngr:paginate id="${event?.id}" action="list" max="10" prev="&larr; Previous" next="Next &rarr;" total="${userInstanceTotal}" />
	                </div>				    
	          	</div> <!-- wrapper -->
    		</div> <!-- span12 -->        
        </div> <!-- row -->        
    </body>
</html>
