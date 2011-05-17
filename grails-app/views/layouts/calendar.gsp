<html>

<head>
<title><g:layoutTitle default="Grails" /></title>
<link rel="stylesheet" href="${resource(dir:'css', file:'main.css', plugin: 'none')}" />
<link rel="shortcut icon" href="${resource(dir:'images', file:'favicon.ico', plugin: 'gdata-cal')}" type="image/x-icon" />

<link rel="stylesheet" href="${resource(dir: 'css/fullcalendar', file: 'fullcalendar.css', plugin: 'gdata-cal')}" type='text/css' />
<link rel="stylesheet" href="${resource(dir: 'css/fullcalendar', file: 'fullcalendar.print.css', plugin: 'gdata-cal')}" type='text/css' media='print' />
<link rel="stylesheet" href="${resource(dir: 'css', file: 'jdpicker.css', plugin: 'gdata-cal')}" type='text/css' />
<link rel="stylesheet" href="${resource(dir: 'css', file: 'gdata.calendar.css', plugin: 'gdata-cal')}" type='text/css' />

<g:javascript library="jquery" plugin="jquery" />
<g:javascript src="fullcalendar/fullcalendar.min.js" />
<g:javascript src='gdata.calendar.js' />
<g:javascript src='jquery.simplemodal.1.4.1.min.js' />
<g:javascript src='jquery.jdpicker.js' />

<g:layoutHead />
</head>

<body>
<g:layoutBody />
</body>

</html>