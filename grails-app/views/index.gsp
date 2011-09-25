<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="openmessenger.Event" %>
<%@ page import="openmessenger.User" %>
<%@ page import="openmessenger.UserEvent" %>
<%@ page import="org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils"%>
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title>Open Messenger</title>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<!--<link rel="stylesheet" type="text/css" href="lib/jquery_ui/themes/base/jquery.ui.all.css" />-->
	<script type="text/javascript" src="js/jquery-1.6.2.js"></script>
	<script type="text/javascript" src="lib/jquery_ui/ui/jquery.ui.core.js"></script>
	<script type="text/javascript" src="lib/jquery_ui/ui/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="lib/jquery_ui/ui/jquery.ui.accordion.js"></script>
	<script type="text/javascript" src="js/build.js"></script>
</head>

<body id="page-front">
<div id="content-wrapper">
	<div class="content-col col-1">
		<h1>Open Messenger</h1>
		<h4 style="margin: 20px;">
			<sec:ifLoggedIn>
			Hello! <sec:username/>
			</sec:ifLoggedIn>
		</h4>
		<div id="menus" class="accordion">
			<div id="accordion_title-about" class="accordion_title">
				<h3><a href="#">About us</a></h3>
			</div>
			<div id="accordion_content-about" class="accordion_content">
				<p><strong>OpenMessenger is a flexible messaging platform for group communication and data collection via mobile phone.</strong></p><p>OpenMessenger is developed by the collaboration between <a href="http://opendream.co.th">Opendream Co., Ltd.</a>, Bangkok, Thailand based ICT social enterprise, and <a href="http://oxfam.org.uk">Oxfam GB</a>.</p><p>This system is currently in close alpha test. Please contact info@opendream.co.th for more information.</p>
			</div>
			<!-- 
			<div id="accordion_title-services" class="accordion_title">
				<h3><a href="#">Services</a></h3>
			</div>
			<div id="accordion_content-services" class="accordion_content">
				<p></p>
			</div>
			-->		
			<!-- div id="accordion_title-clients" class="accordion_title">
				<h3><a href="#">Clients</a></h3>
			</div>
			<div id="accordion_content-clients" class="accordion_content">
				<p><strong>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec rutrum augue in est euismod consectetur.</strong></p><p>Duis id nisl purus. Nullam congue turpis vitae dolor pellentesque a blandit augue lobortis. Vestibulum tempus arcu sit amet arcu dapibus eget viverra nibh sollicitudin. Donec in dolor a turpis faucibus facilisis eu eu arcu.</p>
			</div>		
			<div id="accordion_title-contacts" class="accordion_title">
				<h3><a href="#">Contacts</a></h3>
			</div>
			<div id="accordion_content-contacts" class="accordion_content">
				<p><strong>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec rutrum augue in est euismod consectetur.</strong></p><p>Duis id nisl purus. Nullam congue turpis vitae dolor pellentesque a blandit augue lobortis. Vestibulum tempus arcu sit amet arcu dapibus eget viverra nibh sollicitudin. Donec in dolor a turpis faucibus facilisis eu eu arcu.</p>
			</div-->
			<p></p>
			<p>
			<h4 style="margin: 20px;">
				<sec:ifLoggedIn>
				<g:link controller='logout'>Logout</g:link>
				</sec:ifLoggedIn>
				<sec:ifNotLoggedIn>
				<g:link controller='login' action='auth'>Login</g:link>
				</sec:ifNotLoggedIn>
			</h4>
			</p>		
		</div>
	</div>
	<div class="content-col col-2">
		<img alt="" title="" src="images/openmessenger-startup-screen.jpg" />
	</div>
	<div class="content-col col-3">		
		<div id="sets" class="accordion">
			<!-- sec:access expression="hasRole('ROLE_ADMINS')"-->
			<sec:ifLoggedIn>
			<div id="accordion_title-set1" class="accordion_title">
				<h3>Event</h3>
				<p></p>
			</div>
			<div id="accordion_content-set1" class="accordion_content">
				<p><strong><g:link controller="event" action="listAllEvents">List All Events</g:link></strong></p>
				<p><strong><g:link controller="event" action="create" params="[type:'event']">Create New Event</g:link></strong></p>
			</div>
			<div id="accordion_title-set1" class="accordion_title">
				<h3>Group Chat</h3>
				<p></p>
			</div>
			<div id="accordion_content-set1" class="accordion_content">
				<p><strong><g:link controller="event" action="listAllEvents">List All Group Chat</g:link></strong></p>
				<p><strong><g:link controller="event" action="create" params="[type:'groupChat']">Create New Group Chat</g:link></strong></p>
			</div>
			<div id="accordion_title-set1" class="accordion_title">
				<h3>Poll</h3>
				<p></p>
			</div>
			<div id="accordion_content-set1" class="accordion_content">
				<p><strong><g:link controller="event" action="listAllEvents">List All Events</g:link></strong></p>
			</div>			


			</sec:ifLoggedIn>	
			<!-- /sec:access-->
							
		</div>
		
	</div>
</div>
</body>

</html>