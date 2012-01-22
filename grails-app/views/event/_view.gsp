<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="openmessenger.Event.Type" %>  
<%@ page import="openmessenger.GroupChat" %>  
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">

  <head>
    <title>News List | Open Messenger</title>
    <meta content="text/html; charset=utf-8" http-equiv="Content-Type">
      <link rel="stylesheet" href="${resource(dir:'css',file:'style.css')}" />
  </head>

  <body id="page-news-list">
    <div id="content-wrapper">
      <!-- News List -->
      <div id="news-list">
        <h1><g:link action="listAllEvents">Openmessenger</g:link></h1>
        <!--form id="search" method="post" action="">
          <div id="search-input"><input type="text" name="search" value="" size="20" maxlength="20" /></div>
          <input id="submit-button" type="submit" value="Search">
        </form-->

        <form id="send-message" method="post" action="../sendMessage">
          <g:hiddenField name="eventId" value="${event?.id}" />
          <div id="message-input"><textarea id="edit-message" class="" name="message" rows="3" cols="60"></textarea></div>
          <input id="submit-button" type="submit" value="Send Message">
        </form>

        <div id="news-wrapper">
          <div class="news-items">
            <g:each in="${event?.messages}" var="message">
              <div class="rows row-1">
                <div class="news-writer">${message.title}</div>					
                <div class="news-title">${message.content}</div>
                <div class="news-date">                	
                	<g:if test="${event?.type==Type.GROUP_CHAT && message.createBy!=null}">
            			from:${message.createBy}  
          			</g:if>
          			${message.createdDate} 
                </div>
              </div>
            </g:each>
          </div>

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

      </div><!-- End News List -->

      <!-- Event Detail -->	
      <div id="event-detail">
        <div id="event-content">
          <h2 class="event-title">${fieldValue(bean: event, field: "name")}</h2>
          <div class="event-description">${fieldValue(bean: event, field: "description")}</div>
          <g:if test="${event?.type==Type.GROUP_CHAT}">
            <div class="event-news-count"><strong>Codename: ${GroupChat.get(event.id).codename}</strong></div>
          </g:if>
          <div class="event-news-count"><strong>Totals: ${event.messages.size()}</strong></div>
          <div class="event-last-update"><strong>Last Update:</strong> <g:formatDate format="dd MMM yyyy" date="${event.occuredDate}"/></div>
          <div class="event-subscriber-list">			
            <h3><sec:ifAnyGranted roles="ROLE_ADMINS,ROLE_MANAGER">
            		<g:link action="listEventSubscribers" id="${event.id}"> ${event.subscribers.size()} people subscribe to this event</g:link>
            	</sec:ifAnyGranted>
            	<sec:ifAllGranted roles="ROLE_USER">
            		 ${event.subscribers.size()} people subscribe to this event
            	</sec:ifAllGranted>
            </h3>
            <ol>
              <g:each in="${event.subscribers}" var="subscriber">		
                <li>${subscriber.msisdnx}</li>
              </g:each>	
            </ol> 
			
          </div>
        </div>
      </div><!-- End Event Detail -->
    </div>
  </body>
</html>