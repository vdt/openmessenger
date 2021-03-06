<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="openmessenger.Event.Status" %>
<%@ page import="openmessenger.Event.Type" %>
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">

<head>
	<title>Edit Even</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />        
</head>

<body>
	<div class="row">
          	<div class="span12"> 
          		<div class="wrapper">
					<g:form class="form-horizontal" method="post" action="update">
						<fieldset>
						<g:hiddenField name="id" value="${eventInstance?.id}" />
						<div class="page-header">
							<h1>Update Event</h1>
			        	</div>
						<div class="control-group">
							<label class="control-label" for="xlInput">Event Name</label>
								<div class="controls">
									<g:textField class="input-xlarge" size="30" name="name" value="${eventInstance?.name}" />
								</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="xlInput">Description</label>
								<div class="controls">
									<g:textArea class="input-xlarge" rows="3" name="description" value="${eventInstance?.description}" />
								</div>
						</div>
						
					    <div class="control-group">
							<label class="control-label" for="xlInput">code name: </label>
							<div class="controls">
							<input class="input-xlarge" type="text" id="codename" name="codename" value="${eventInstance?.codename}" />
							</div>
						</div>    
											
						<div class="control-group">
							<label class="control-label" for="normalSelect">Status</label>
								<div class="controls">
									<g:select id="normalSelect" name="status" from="${Status.list()}" value="${eventInstance?.status}"/>									
								</div>
						</div>
						<div class="control-group">
							<label class="control-label" for="normalSelect">Type</label>
								<div class="controls">
									<g:select id="normalSelect" id="type" name="type" from="${Type.list()}" value="${eventInstance?.type}"/>									
								</div>
						</div>
						
						<div class="control-group">
							<label class="control-label" for="xlInput">Options</label>
							<div class="controls">								
								<label class="checkbox">
									<g:checkBox name="isUnicode" value="${eventInstance?.isUnicode}" />
									<span id="input-unicode-id" title="Unicode" data-content="This is content">Unicode</span>
								</label>									
								<label class="checkbox">
									<g:checkBox name="isSenderId" value="${eventInstance?.isSenderId}" />
									<span id="input-sender-id" title="Sender ID" data-content="This is content">Sender ID</span>
								</label>								
							<span class="help-block">
							<strong>Note:</strong>
							Labels surround all the options for much larger click areas and a more usable form.
							</span>
							</div>
						</div>
						
						<div class="control-group">
							<label class="control-label">Occurred Date</label>
							<div class="controls">
								<div class="inline-inputs">
									<input id="startdatepicker" name="occuredDate" class="small" type="text"  value="${eventInstance?.occuredDate?.format(message(code:'default.stringdate.format'))}" />
								to
									<input id="enddatepicker" name="enddatepicker" class="small" type="text" />
									<span class="help-block">All times are shown as Pacific Standard Time (GMT -08:00).</span>
								</div>
							</div>
						</div>
						
						<div class="control-group">
							<div class="controls">
								<div class="inline-inputs">	
									<input class="btn primary" type="submit" value="Update">
									<button class="btn" type="reset">Cancel</button>
								</div>
							</div>
						</div>												
						</fieldset>							
					</g:form>  
				</div> <!-- wrapper --> 
          	</div> <!-- span12 -->
        </div>  <!-- row -->

        <script src="${resource(dir:'js',file:'jquery/jquery.ui.core.js')}"></script>
        <script src="${resource(dir:'js',file:'jquery/jquery.ui.widget.js')}"></script>
        <script src="${resource(dir:'js',file:'jquery/jquery.ui.datepicker.js')}"></script>
        <link href="${resource(dir:'css',file:'themes/base/jquery.ui.theme.css')}" rel="stylesheet"/>
    	<link href="${resource(dir:'css',file:'themes/base/jquery.ui.base.css')}" rel="stylesheet"/>

        <script>
        	$('#codename').attr('disabled', ${eventInstance?.type==Type.GROUP_CHAT?false:true});        	
			$(function() {
				$( "#startdatepicker" ).datepicker({dateFormat:"${message(code:'default.datepicker.format')}" }); //<g:message code="my.localized.content" /> 
				$( "#enddatepicker" ).datepicker({dateFormat:"${message(code:'default.datepicker.format')}" });
			});
			$(function() {
				$( "#enddatepicker" ).datepicker(); //
			});
			$('#type').change( function() {
				if($('#type').val()=="${Type.GROUP_CHAT}") {
					$('#codename').attr('disabled', false);
				} else {
					$('#codename').attr('disabled', true);
				}
			}
			);
				
		</script>
</body>

</html>




