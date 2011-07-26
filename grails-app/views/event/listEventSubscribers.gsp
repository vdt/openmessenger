<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
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
        <h1>${fieldValue(bean: event, field: "name")}</h1>
        <!--form id="search" method="post" action="">
          <div id="search-input"><input type="text" name="search" value="" size="20" maxlength="20" /></div>
          <input id="submit-button" type="submit" value="Search">
        </form-->

        <form id="send-message" method="post" action="../sendMessage">
        </form>

        <div id="news-wrapper">
          <div class="news-items">
            <g:each in="${event.subscribers}" var="subscriber">
              <div class="rows row-1">
                <div class="news-writer">-</div>					
                <div class="news-title">${subscriber.msisdn}</div>
                <div class="news-date">${subscriber.active}</div>
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
          <h2 class="event-title">Event</h2>
          <div class="event-description">${fieldValue(bean: event, field: "description")}</div>
          <div class="event-news-count"><strong>Totals:</strong></div>
          <div class="event-last-update"><strong>Last Update:</strong> 26 july 2011</div>
          <div class="event-subscriber-list">

            <h3> ${event.subscribers.size()} people subscribe to this event</h3>
            <ol>
            </ol>
          </div>
        </div>
      </div><!-- End Event Detail -->
    </div>
  </body>
</html>