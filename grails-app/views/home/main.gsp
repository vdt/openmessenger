<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>OpenMessenger</title>
    </head>
    <body>
    	<div class="row">
		    <div class="span12">
			    <div class="wrapper">
			        <div class="page-header">
			          	<h1>Main Panel <small> All your messages is here</small></h1>
			        </div>
		        </div> <!-- wrapper -->
		    </div> <!-- span12 -->
	    </div> <!-- row -->
		    

        <div class="row">
          <div class="span8">

          	<div class="wrapper wrapper-rborder">
          	<div class="tabbable">
				<ul class="nav nav-tabs nav-border">
					<li class="active"><a href="#1" data-toggle="tab">Event</a></li>
					<li><a href="#2" data-toggle="tab">Group Chat</a></li>
					<sec:ifAnyGranted roles="ROLE_ADMINS">
						<li><a href="#3" data-toggle="tab">Settings</a></li>
					</sec:ifAnyGranted>
					
				</ul>
				<div class="tab-content tab-content-border">

					<div class="tab-pane active" id="1">
						<g:if test="${events.size()>0}" >
						<table id="sortTableExample"  class="table table-striped">
							<thead>
								<tr>
									<th class="header"></th>
									<th class="green header">Name</th>
									<th class="green header">Messages</th>
									<th class="green header">Last Updated</th>
									<th class="green header">Setting</th>
								</tr>
							</thead>
			              	<tbody>
			              		<g:each in="${events}" var="event" status="i">
			                	<tr>
			                  		<td>${i+1}</td>
			                  		<td>
			                    		<blockquote>
			                    			<g:link controller="event" action="view" id="${event.id}"><p>${event.name}</p></g:link>
			                    			<!-- small>Created By: Dr. Julius Hibbert</small-->
			                    		</blockquote>
			                  		</td>
			                  		<td><span class="label label success">${event.messages.size()}</span></td>
			                  		<td><g:formatDate format="MMM dd, yyyy" date="${event.occuredDate}"/></td>
			                  		<td><a href="${createLink(controller:'event', action:'edit', params:[id:event.id])}"><i class="icon-edit"></i></a>edit</td>
			                	</tr>
			                	</g:each>	                	                             
			              </tbody>
			            </table>				
						</g:if>
					</div>
					
					<div class="tab-pane" id="2">
						<g:if test="${groupChats.size()>0}" >				
						<table id="sortTableExample"  class="table table-striped">
							<thead>
								<tr>
									<th class="header"></th>
									<th class="green header">Name</th>
									<th class="green header">Messages</th>
									<th class="green header">Last Updated</th>
									<th class="blue header">Setting</th>
								</tr>
							</thead>
			              	<tbody>
			              		<g:each in="${groupChats}" var="group" status="i">
			                	<tr>
			                  		<td>${i+1}</td>
			                  		<td>
			                    		<blockquote>
			                    			<g:link controller="event" action="view" id="${group.id}"><p>${group.name}</p></g:link>
			                    		</blockquote>
			                  		</td>
			                  		<td><span class="label success">${group.messages.size()}</span></td>
			                  		<td><g:formatDate format="MMM dd, yyyy" date="${group.occuredDate}"/></td>
			                  		<td><a href="${createLink(controller:'event', action:'edit', params:[id:group.id])}"></a>edit</td>
			                	</tr>
			                	</g:each>	                	                             
			              </tbody>
			            </table>				
						</g:if>
					</div>
					<sec:ifAnyGranted roles="ROLE_ADMINS">
					<div class="tab-pane" id="3">
						
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
			              		<g:each in="${users}" var="user" status="i">
			                	<tr>
			                  		<td>${i+1}</td>
			                  		<td>
			                    			<a href="${createLink(controller:'user', action:'view', params:[id:user.id])}">${user.firstname+' '+user.lastname}</a>
			                  		</td>
			                  		<td><span class="label success">${user.enabled?'ACTIVE':'INACTIVE' }</span></td>
			                  		<td>${user.getAuthoritiesString()}</td>
			                	</tr>
			                	</g:each>	                	                             
			              </tbody>
			            </table>				
						
					</div>
					</sec:ifAnyGranted>
				</div>

			</div> <!-- tabbable -->
          	</div> <!-- wrapper -->
    		</div> <!-- span8 -->

	    	<!--
	    	<div class="span4">
	    		<h3></h3>
	    		<sec:ifAnyGranted roles="ROLE_ADMINS">
	        		<div class="alert-message block-message info abc"><a class="btn small" href="${createLink(controller:'event', action:'create')}">Create New Event</a></div>
	        		<div class="alert-message block-message info abc"><a class="btn small" href="${createLink(controller:'user', action:'create')}">Create New User</a></div>
	           	</sec:ifAnyGranted>
	        </div> --> <!-- span4 -->
	        
        </div> <!-- row -->
    
    
    <!-- </div> -->
     

    
	<script>
		/*$(function () {
			$('.tabs').tabs().bind('change', function (e) {
			e.target // activated tab
			e.relatedTarget // previous tab
			})
		})*/
	</script>
  </body>
</html>