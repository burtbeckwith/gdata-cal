<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="layout" content="calendar" />

<script>
var eventsLink = '${createLink(action: "events")}';
var deleteEventLink = '${createLink(action: "deleteEvent")}';
</script>
</head>

<body>

<g:render template='createEvent'/>
<g:render template='showEvent'/>

<!-- preload the images -->
<div style='display:none'>
	<img src='${resource(dir: 'images', file: 'x.png', plugin: 'gdata-cal')}' alt='' />
</div>

<g:if test="${flash.message}">
<div class="message">${flash.message}</div>
</g:if>

<div id='calendar'></div>

</body>
</html>
