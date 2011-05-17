function showEventDetails(event) {
	$('#event_desc').html(event.description);
	$('#event_title').html(event.title);
	$('#delete_event').html("<a href='" + deleteEventLink + "?id=" + encodeURIComponent(event.id) + "'>Delete</a>");
	$('#show_event').modal();
}

$(document).ready(function() {

	$('#calendar').fullCalendar({
		editable: true,
		disableDragging: true,
		disableResizing: true,

		header: {
			left: 'prev,next today',
			center: 'title',
			right: 'month,agendaWeek,agendaDay'
		},

		defaultView: 'agendaWeek',

		eventClick: function(event, jsEvent, view) {
			showEventDetails(event);
		},

		events: eventsLink,

		dayClick: function() {
			alert('a day has been clicked!');
		}
	});

	$('#create_event_link').click(function (e) {
		$('#create_event').modal();
		return false;
	});
});
