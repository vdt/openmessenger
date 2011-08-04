<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title>Event List | Open Messenger</title>
	<meta content="text/html; charset=utf-8" http-equiv="Content-Type">
	<link rel="stylesheet" href="${resource(dir:'css',file:'style.css')}" />
</head>

<body id="page-event-list">
<div id="content-wrapper">
	<h1>Event List</h1>
	<form id="search" method="post" action="">
		<!--div id="search-input"><input type="text" name="search" value="" size="20" maxlength="20" /></div>
		<input id="submit-button" type="submit" value="Search"-->
	</form>
	
	<table class="event-container">        
		<thead>
			<tr>
				<th class="event-title"></th>
				<th class="event-news"></th>
				<th class="event-subscriber"></th>
				<th class="event-update"><g:link action="create">Create New Event</g:link></th>
			</tr>
		</thead>
		<thead>
			<tr>
				<th class="event-title">Event</th>
				<th class="event-news">News</th>
				<th class="event-subscriber">Subscribers</th>
				<th class="event-update">Update</th>
			</tr>
		</thead>
		<tbody>
		<g:each in="${events}" var="event">			
			<tr class="item-row odd">
				<td class="event-title"><g:link action="view" id="${event.id}">${fieldValue(bean: event, field: "id")}</g:link>${event.name}</td>
				<td class="event-news">${event.messages.size()}</td>
				<td class="event-subscriber">${event.subscribers.size()}</td>
				<td class="event-update">${event.occuredDate}</td> 
			</tr>
		</g:each>	
		</tbody>
	</table>
	<div class="pager-wrapper">
	<ul class="pager">
		<li class="prev"><a href="#">previous</a></li>
		<li class="page-number current"><a href="#">1</a></li>
		<li class="page-number"><a href="#">2</a></li>
		<li class="page-number"><a href="#">3</a></li>
		<li class="dot">...</li>
		<li class="page-number"><a href="#">10</a></li>
		<li class="next"><a href="#">next</a></li>
	</ul>
	</div>
	
</div>
</body>

</html>