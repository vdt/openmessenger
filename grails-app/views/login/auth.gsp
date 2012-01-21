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
	<script src="${resource(dir:'js',file:'bootstrap-dropdown.js')}"></script>
    <link href="${resource(dir:'css',file:'bootstrap.css')}" rel="stylesheet">

    <style type="text/css">
      /* Override some defaults */
      html, body {
        background-color: #eee;
      }
      body {
        padding-top: 40px; /* 40px to make the container go all the way to the bottom of the topbar */
      }
      .container > footer p {
        text-align: center; /* center align it with the container */
      }


      /* The white background content wrapper */
      .container > .content {
        background-color: #fff;
        padding: 20px;
        margin: 0 -20px; /* negative indent the amount of the padding to maintain the grid system */
        -webkit-border-radius: 0 0 6px 6px;
           -moz-border-radius: 0 0 6px 6px;
                border-radius: 0 0 6px 6px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.15);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.15);
                box-shadow: 0 1px 2px rgba(0,0,0,.15);
      }

      /* Page header tweaks */
      .page-header {
        background-color: #f5f5f5;
        padding: 20px 20px 10px;
        margin: -20px -20px 20px;
      }

      /* Styles you shouldn't keep as they are for displaying this base example only */
      .content .span10,
      .content .span4 {
        min-height: 500px;
      }
      /* Give a quick and non-cross-browser friendly divider */
      .content .span4 {
        margin-left: 0;
        padding-left: 19px;
        border-left: 1px solid #eee;
      }

      .topbar .btn {
        border: 0;
      }

    </style>

    <!-- Le fav and touch icons -->
    <link rel="shortcut icon" href="images/favicon.ico">
    <link rel="apple-touch-icon" href="images/apple-touch-icon.png">
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">
  </head>

  <body>

    <div class="topbar">
      <div class="fill">
        <div class="container">
          <a class="brand" href="#">OpenDream™</a>
          <ul class="nav">
            <li><a href="#"></a></li>
            <li><a href="./about-app.html"></a></li>
            <li><a href="#contact"></a></li>
          </ul>
          <p class="pull-right"><a href="#"></a>
          </p>
        </div>
      </div>
    </div>

	<div class="container">
		<div class="row show-grid" title="Half and half">
			<div class="span8"><img class="thumbnail" src="../images/logoopenmessenger.png" alt=""></div>
			<div class="span8">
				<div class="well">
			<form action='${postUrl}' method='POST' id='login'>
				<fieldset>
					<legend>Login Form</legend>
						<div class="clearfix">
							<label for="xlInput">Username</label>
								<div class="input">
									<input id="xlInput" class="span3" type="text" size="10" name="j_username">
								</div>
						</div>
						<div class="clearfix">
							<label for="xlInput">Password</label>
								<div class="input">
									<input id="xlInput" class="span3" type="password" size="10" name="j_password">
									<g:hiddenField name="${rememberMeParameter}" id='remember_me' value="true"/>
								</div>
						</div>
						<div class="clearfix">
							<div class="input">
							<div class="inline-inputs">	
						<input id="login-button" class="btn primary" type="submit" value="Login">
						<button class="btn" type="reset">Cancel</button>
						</div>
						</div>
						</div>
												
				</fieldset>							
			</form>       
				</div>
			</div>
		</div>

      <footer>
        <p>OpenMessenger is developed by the collaboration between Opendream Co., Ltd., Bangkok, Thailand based ICT social enterprise, and Oxfam GB.

This system is currently in close <span class="label notice">alpha</span> test. Please contact <span class="label notice">info@opendream.co.th</span> for more information.</p>
      </footer>

	</div>

  </body>