<html> 
	<head>
		<title>All Active Events</title>
		<meta name="layout" content="main"/> 
	</head>
	<body> 
		<h1>All Active Events</h1>
		<div class="allPosts"> 
		<g:each in="${events}" var="event">
			<div class="postEntry"> 
				<div class="postText">${event.name} </div>
				<div class="postDate"> ${event.description}</div> 
			</div>
		</g:each> 
		</div>
	</body> 
</html>