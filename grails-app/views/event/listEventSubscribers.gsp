<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page import="openmessenger.Event.Type" %>  
<%@ page import="openmessenger.GroupChat" %>  
<html lang="en-US" xml:lang="en-US" xmlns="http://www.w3.org/1999/xhtml">

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title>${event.name} | Open Messenger</title>
    </head>

    <body>
        <!-- Header -->
        <div class="row">
          <div class="span12">
            <div class="wrapper">
              <div class="page-header">
                <h1><g:link controller="event" action="view" id="${event.id}">${event.name}</g:link> <small> Subscribers</small></h1>
              </div>
            </div> <!-- wrapper -->
          </div>
        </div> <!-- row -->              

        <div class="row">
            <div class="span8">
                <div class="wrapper wrapper-rborder">
            
                <div class="well">
              			<fieldset>
                    		<g:form class="form-vertical" id="send-message" method="post" action="subscribeToEvent">
                    				<g:hiddenField name="eventId" value="${event?.id}" />
                    				<div id="control-group">
                                <textarea id="textarea2" class="input-xlarge span7" rows="3" name="msisdn"></textarea>
                                <span class="help-block"> eg. 123456789 </span>
                            </div>
                    				<button class="btn btn-primary" type="submit" >Add Subscriber</button>
                    				<!-- button class="btn" type="reset">Cancel</button-->
                    		</g:form>
              			</fieldset>
    		        </div> <!-- well -->

                <table id="sortTableExample"  class="table table-striped">
                    <thead>
                        <tr>
                            <th class="header"></th>
                            <th class="blue header">Content</th>
                            <th class="blue header">Status</th>
                            <th class="blue header">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                    	  <g:each status="i" in="${event.subscribers}" var="subscriber" >
                        <tr>
                            <td>${i+1}</td>
                            <td>${subscriber.msisdn}</td>
                            <td><span class="label label-success">${subscriber.active}</span></td> 
                            <td><p class="help-block"><g:link action="unsubscribeFromEvent" id="${event.id}" params="[msisdn:subscriber.msisdn]" ><i class="icon-trash"></i></g:link></p></td>                 
                        </tr>
                        </g:each>                                                
                    </tbody>
                </table>

                <div class="pagination">
                	  <msngr:paginate id="${event?.id}" action="view" max="10" prev="&larr; Previous" next="Next &rarr;" total="10" />
                </div> 
                </div> <!-- wrapper wrapper-rborder -->           
            </div> <!-- span8 -->

            <div class="span4">
                <div class="wrapper wrapper-rsidebar">
                <div class="well">
                    <h3>Information</h3>
                    <ul class="list-sidebar unstyled">
                        <li>
                            <i class="icon-search"></i>
                            <b>${event.description}</b>
                        </li>
                        <li>
                            <i class="icon-calendar"></i>
                            <b>Created Date:</b><g:formatDate format=" MMM dd, yyyy" date="${event.occuredDate}"/>
                        </li>
                        <li>
                            <i class="icon-user"></i>
                            <b>Number of subscriber:</b>${event.subscribers.size()}
                        </li>
                    </ul>
                </div>
                </div>
            </div> <!-- span4-->        
      </div> <!-- row -->
  </body>
</html>