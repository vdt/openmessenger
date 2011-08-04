<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title>Login | Open Messenger</title>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>

<body id="page-front">
<div id="content-wrapper">
	<div id="content">
		<h1>Open Messenger</h1>
		<form id="login" method="post" action="save">
			<div id="username-input">
				<label>name: </label>    
				 <g:textField name="name" value="${eventInstance?.name}" /> 
			</div>
			<div id="password-input">
				<label>description: </label>
				 <g:textField name="description" value="${eventInstance?.description}" />
			</div>    
			<div id="password-input">
				<label>occurred date: </label>
				<g:datePicker name="occuredDate" precision="day" value="${eventInstance?.occuredDate}" >
	          </g:datePicker>
			</div>
			<div id="password-input">
				<label>status: </label>
				 <g:textField name="status" value="${eventInstance?.status}" />
			</div>			
			<input id="submit-button" type="submit" value="Login">
		</form>
	</div>
</div>
</body>

</html>