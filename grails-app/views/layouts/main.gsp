<head>
    <meta charset="utf-8">
    <title>OpenMessenger</title>
    <meta name="description" content="">
    <meta name="author" content="">

    <!-- Le HTML5 shim, for IE6-8 support of HTML elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

    <!-- Le styles -->
	<script src="${resource(dir:'js',file:'jquery-1.7.1.min.js')}"></script>	
	<script src="${resource(dir:'js',file:'jquery.ui.core.js')}"></script>
	<script src="${resource(dir:'js',file:'jquery.ui.widget.js')}"></script>
	<script src="${resource(dir:'js',file:'jquery.ui.datepicker.js')}"></script>	
    <script src="${resource(dir:'js',file:'bootstrap-tabs.js')}"></script>    
    <script src="${resource(dir:'js',file:'bootstrap-twipsy.js')}"></script>
    <script src="${resource(dir:'js',file:'bootstrap-popover.js')}"></script>
    <script src="${resource(dir:'js',file:'bootstrap-dropdown.js')}"></script>
  
  <!-- bootstrap 2.0.1 -->  
  <script src="${resource(dir:'js',file:'bootstrap.js')}"></script>        
  <link href="${resource(dir:'css',file:'bootstrap/bootstrap.css')}" rel="stylesheet"/> 
   
  <link href="${resource(dir:'css',file:'themes/style.css')}" rel="stylesheet">  
   
    <link href="${resource(dir:'css',file:'themes/base/jquery.ui.theme.css')}" rel="stylesheet"/>
    <link href="${resource(dir:'css',file:'themes/base/jquery.ui.base.css')}" rel="stylesheet"/>    
    
    

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" type="image/x-icon" href="${resource(dir:'images',file:'messenger.ico')}"/>
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png"/>
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png"/>
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png"/>
    
  </head>

  <body>

      <div class="navbar">
          <div class="navbar-inner">
              <div class="container">
                  <a class="brand" href="${createLink(controller:'home', action:'main')}">OpenMessenger</a>
                      <ul class="nav">
                          <li class="active"><a href="${createLink(controller:'home', action:'main')}">Home</a></li>
                          <li><a href="#">About</a></li>
                          <li><a href="#">Contact</a></li>
                          <!--
                          <sec:ifLoggedIn>
                              <li><a href="${createLink(controller:'logout')}">Logout</a></li>
                          </sec:ifLoggedIn>
                          -->
                      </ul>
                      <ul class="nav menu pull-right">
                          <li class="dropdown" data-dropdown="dropdown">            
                            <a href="#" class="dropdown-toggle">Log in as <i class="icon-user icon-white"></i> <sec:username/> <b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a href="#">Manage Profile</a></li>
                                <sec:ifAnyGranted roles="ROLE_ADMINS">
                                <li><a href="#">List All Users</a></li>
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

      <script>
        $('.dropdown-toggle').dropdown()
        /*$(function () {
          $('.tabs').tabs().bind('change', function (e) {
          e.target // activated tab
          e.relatedTarget // previous tab
          })
        })*/
      </script>
  </body>
</html>