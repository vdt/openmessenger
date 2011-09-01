<head>
<meta name='layout' content='main' />
<title><g:message code="springSecurity.login.title" /></title>

</head>

<body id="page-login">
	
		<div class='inner'>
			<g:if test='${flash.message}'>
			<div class='login_message'>${flash.message}</div>
			</g:if>
			
		<div id="content">
			<h1>Open Messenger</h1>
			<form action='${postUrl}' method='POST' id='login' class='cssform' autocomplete='off'>
				<div id="username-input"><label>Username: </label><input type="text" name='j_username' id='username' size="30" maxlength="20" /></div>
				<div id="password-input"><label>Password: </label><input type="password" name='j_password' id='password' size="30" maxlength="20" /></div>
				<div id="username-input"><label for='remember_me'><g:message code="springSecurity.login.remember.me.label" /></label>
					<input type='checkbox' class='chk' name='${rememberMeParameter}' id='remember_me'
					<g:if test='${hasCookie}'>checked='checked'</g:if> />
				</div>
				<input id="submit-button" type="submit" value="Login">
				
			</form>
		</div>
			
			
			
		</div>
	
<script type='text/javascript'>
<!--
(function(){
	document.forms['loginForm'].elements['j_username'].focus();
})();
// -->
</script>
</body>
