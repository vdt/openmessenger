<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="openmessenger.Event.Status" %>
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title>Create New Even</title>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<link rel="stylesheet" href="${resource(dir:'css',file:'style.css')}" />        
</head>

<body id="page-login">
<div id="content-wrapper">
	<div id="content">
		<h1>Create New Event</h1>
		<form id="login" method="post" action="save">
			<div id="username-input" class="general-field">
				<label>name: </label>    
				 <g:textField name="name" value="${eventInstance?.name}" /> 
			</div>
			<div id="password-input" class="general-field">
				<label>description: </label>
				 <g:textField name="description" value="${eventInstance?.description}" />
			</div>    
			<div id="status-input" class="general-field">
				<label>status: </label>
				<g:select name="status" from="${Status.list()}"/>
			</div>
			<div id="occurred-input" class="general-field date">
				<label>occurred date: </label>
				<g:datePicker name="occuredDate" precision="day" value="${eventInstance?.occuredDate}" >
			  </g:datePicker>
			</div>						     
			<input id="submit-button" type="submit" value="Create"> 
		</form>
	</div>
</div>
</body>

</html>




