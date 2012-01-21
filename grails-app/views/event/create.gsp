<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="openmessenger.Event.Status" %>
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title>Create New Even</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />        
</head>

<body>
	<div class="page-header">
          <h1>Event <small> All your messages is here</small></h1>
        </div>

        <div class="row">
          <div> 
			<form method="post" action="save">
				<fieldset>
					<legend>New Event</legend>
						<div class="clearfix">
							<label for="xlInput">Event Name</label>
								<div class="input">
									<g:textField class="xlarge" size="30" name="name" value="${eventInstance?.name}" />
								</div>
						</div>
						<div class="clearfix">
							<label for="xlInput">Description</label>
								<div class="input">
									<g:textArea class="xxlarge span5" rows="3" name="description" value="${eventInstance?.description}" />
								</div>
						</div>
						<g:if test="${eventType=='groupChat'}">
					    <div class="clearfix">
							<label for="xlInput">code name: </label>
							<div class="input">
							<g:textField name="codename" value="${eventInstance?.codename}" />
							</div>
						</div>    
						</g:if>						
						<div class="clearfix">
							<label for="normalSelect">Select</label>
								<div class="input">
									<g:select id="normalSelect" name="status" from="${Status.list()}" value="${eventInstance?.status}"/>									
								</div>
						</div>
						
						
						<div class="clearfix">
								<label for="xlInput">Options</label>
							<div class="input">
							<ul class="inputs-list">
							<li>
							<label>
							<g:checkBox name="isUnicode" value="${eventInstance?.isUnicode}" />
							<span id="input-unicode-id" title="Unicode" data-content="This is content">Unicode</span>
							</label>
							</li>
							<li>
							<label>
							<g:checkBox name="isSenderId" value="${eventInstance?.isSenderId}" />
							<span id="input-sender-id" title="Sender ID" data-content="This is content">Sender ID</span>
							</label>
							</li>
							</ul>
							<span class="help-block">
							<strong>Note:</strong>
							Labels surround all the options for much larger click areas and a more usable form.
							</span>
							</div>
						</div>
						
						<div class="clearfix">
						<label>Occurred Date</label>
						<div class="input">
						<div class="inline-inputs">
							<input id="startdatepicker" name="occuredDate" class="small" type="text"  value="${eventInstance?.occuredDate.format(message(code:'default.stringdate.format'))}" />
						to
							<input id="enddatepicker" name="enddatepicker" class="small" type="text" />
						<span class="help-block">All times are shown as Pacific Standard Time (GMT -08:00).</span>
						</div>
						</div>
						</div>
						<g:hiddenField name="type" value="${eventType}" />
						<div class="clearfix">
							<div class="input">
							<div class="inline-inputs">	
						<input class="btn primary" type="submit" value="Create">
						<button class="btn" type="reset">Cancel</button>
						</div>
						</div>
						</div>
												
				</fieldset>							
			</form>       
          </div>

        </div>
        <script>
			$('#input-sender-id').popover();
			$('#input-unicode-id').popover();
			$('#topbar').dropdown()
			$(function() {
				$( "#startdatepicker" ).datepicker({dateFormat:"${message(code:'default.datepicker.format')}" }); //<g:message code="my.localized.content" /> 
			});
			$(function() {
				$( "#enddatepicker" ).datepicker(); //
			});	
		</script>
</body>

</html>




