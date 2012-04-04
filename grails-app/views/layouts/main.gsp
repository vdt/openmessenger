<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>OpenMessenger</title>
    <meta name="description" content="">
    <meta name="author" content="">

    
  
    <!-- bootstrap 2.0.2 -->    
    <script src="${resource(dir:'js',file:'jquery/jquery-1.7.1.min.js')}"></script>
    <script src="${resource(dir:'js',file:'bootstrap-dropdown.js')}"></script>
    
    <link href="${resource(dir:'css',file:'themes/bootstrap/bootstrap.css')}" rel="stylesheet"/>    
    <link href="${resource(dir:'css',file:'themes/style.css')}" rel="stylesheet">  
     
    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" type="image/x-icon" href="${resource(dir:'images',file:'messenger.ico')}"/>
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png"/>
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png"/>
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png"/>
    
  </head>

  <body>

      <div class="navbar navbar-static-top">
          <div class="navbar-inner">
              <div class="container">
                  <a class="brand" href="${createLink(controller:'home', action:'main')}">OpenMessenger</a>
                      <ul class="nav">
                          <li class="active"><a href="${createLink(controller:'home', action:'main')}">Home</a></li>
                          <li><a href="#">About</a></li>
                          <li><a href="#">Contact</a></li>                          
                      </ul>
                      <ul class="nav menu pull-right">
                          <li class="dropdown" id="fat">            
                            <a href="#fat" class="dropdown-toggle" data-toggle="dropdown">Log in as <i class="icon-user icon-white"></i> <sec:username/> <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><msngr:userLink></msngr:userLink></li>
                                <sec:ifAnyGranted roles="ROLE_ADMINS">
                                    <li><a href="${createLink(controller:'user', action:'list')}">List All Users</a></li>
                                    <li class="divider"></li>
                                    <li><a href="${createLink(controller:'event', action:'create')}">Create New Event</a></li>
                                    <li><a href="${createLink(controller:'user', action:'create')}">Create New User</a></li>
                                </sec:ifAnyGranted>
                                <li class="divider"></li>
                                <li><a href="${createLink(controller:'logout')}">Logout</a></li>
                            </ul>
                          </li>
                      </ul>          
              </div>
            </div> <!-- navbar inner -->
      </div> <!-- navbar -->

      <div class="container">
    	    <div class="content">
    	    	  <g:layoutBody />
          </div>
      </div>
  	    
  		</div>
          <footer>
              <p align="center">&copy; Oxfam</p>
          </footer>    
      </div>
  </body>
</html>