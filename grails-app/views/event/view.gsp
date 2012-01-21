<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="openmessenger.Event.Type" %>  
<%@ page import="openmessenger.GroupChat" %>  
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">

  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <title>News List | Open Messenger</title>
  </head>

  <body>
    <div class="page-header">
          <h1>${event.name} <small> All your messages is here</small></h1>
        </div>

        <div class="row">
          <div class="span10">
            <h2>Message to be sent</h2>     
            
            <div class="clearfix">
			<div class="input">
				<form id="send-message" method="post" action="../sendMessage">
				<g:hiddenField name="eventId" value="${event?.id}" />
				<textarea id="textarea2" class="xxlarge" rows="3" name="message"></textarea>
				<span class="help-block"> Block of help text to describe the field above if need be. </span>
				<input class="btn primary" type="submit" value="Send to subscribers">
				<!-- button class="btn" type="reset">Cancel</button-->
				</form>
			</div>
		</div>

<table id="sortTableExample"  class="zebra-striped">
<thead>
<tr>
<th class="header"></th>
<th class="blue header">Content</th>
<th class="blue header">Status</th>
<th class="blue header">Date</th>
</tr>
</thead>
              <tbody>
              	<g:each in="${messages}" var="message" status="i">
                <tr>
                  <td>${offset+i+1}</td>
                  <td>
                    <blockquote>
                    <p>${ message.content }</p>
                    <small>${message.createBy}</small>
                    </blockquote>
                  </td>
                  <td><span class="label success">Normal</span></td>
                  <td><g:formatDate format=" MMM dd, yyyy" date="${message.createdDate}"/></td>
                </tr>
                </g:each>                                                
              </tbody>
            </table>
    <div class="pagination">
    	<msngr:paginate id="${event?.id}" action="view" max="10" prev="&larr; Previous" next="Next &rarr;" total="${total}" />
    </div>            
          </div>
          <div class="span4">
            <h3>Information</h3>
            <div class="alert-message block-message info"><b>${event.description}</b></div>
            <div class="alert-message block-message info"><b>Created Date:</b><g:formatDate format=" MMM dd, yyyy" date="${event.occuredDate}"/></div>
            <div class="alert-message block-message info"><b>Number of subscriber:</b>${event.subscribers.size()}</div>
          </div>          
        </div>
  </body>
</html>