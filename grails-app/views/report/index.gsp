<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="openmessenger.Event" %> 
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Report | Open Messenger</title>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<link rel="stylesheet" href="${resource(dir:'css',file:'style.css')}" />
	<calendar:resources lang="en" theme="tiger"/>
</head>
<body id="page-event-list">
	<div id="content-wrapper">
	<div id="news-list">
		<h1><g:link controller="event" action="listAllEvents">Openmessenger</g:link></h1>
		<div>
		<g:select id="event" name='event.id' value="${event?.id}"
		    noSelection="${['null':'Select All']}"
		    from="${events}"
		    optionKey="id" optionValue="name"></g:select>
		
		<calendar:datePicker name="date" />		    		    
		
        </div>
	</div>
	</div>
</body>
</html>