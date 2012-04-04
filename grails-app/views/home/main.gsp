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
			          	<h1>Main Panel</h1>
			        </div>
		        </div> <!-- wrapper -->
		    </div> <!-- span12 -->
	    </div> <!-- row -->
		    

        <div class="row">
          <div class="span12">

          	<div class="wrapper wrapper-no-border">
          	<div class="tabbable">
				<ul class="nav nav-tabs nav-border">
					<li class="active"><a href="#1" data-toggle="tab">Event</a></li>
					<li><a href="#2" data-toggle="tab">Group Chat</a></li>	
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
									<sec:ifAnyGranted roles="ROLE_ADMINS">
										<th class="green header">Setting</th>
									</sec:ifAnyGranted>
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
			                  		<td><span class="label label-success">${event.messages.size()}</span></td>
			                  		<td><g:formatDate format="MMM dd, yyyy" date="${event.occuredDate}"/></td>
			                  		<sec:ifAnyGranted roles="ROLE_ADMINS">
			                  		<td><a href="${createLink(controller:'event', action:'edit', params:[id:event.id])}"><i class="icon-edit"></i></a>edit</td>
			                  		</sec:ifAnyGranted>
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
									<sec:ifAnyGranted roles="ROLE_ADMINS">
										<th class="blue header">Setting</th>
									</sec:ifAnyGranted>
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
			                  		<sec:ifAnyGranted roles="ROLE_ADMINS">
			                  		<td><a href="${createLink(controller:'event', action:'edit', params:[id:group.id])}"><i class="icon-edit"></i></a>edit</td>
			                  		</sec:ifAnyGranted>
			                	</tr>
			                	</g:each>	                	                             
			              </tbody>
			            </table>				
						</g:if>
					</div>					
				</div>

			</div> <!-- tabbable -->
          	</div> <!-- wrapper -->
    		</div> <!-- span12 -->	    	        
        </div> <!-- row -->
    
    <script src="${resource(dir:'js',file:'bootstrap-tab.js')}"></script> 
    <!-- </div> -->
    <script>
   	
    </script>
  </body>
</html>