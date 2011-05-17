<p><a href='#' id="create_event_link">Create Event</a></p>
<div id="create_event">

<g:form action="createEvent">
<table>
<tbody>
<tr>
	<td><label for="title">Title</label></td>
	<td><g:textField name="title" size="30" /></td>
</tr>
<tr>
	<td><label for="description">Description</label></td>
	<td><g:textArea name="description" style='height: 50px; width: 400px'/></td>
</tr>
<tr>
	<td><label for="location">Location</label></td>
	<td><g:textField name="location" size="30" /></td>
</tr>
<tr>
	<td><label for="startTime">Start Time</label></td>
	<td><g:textField name="startTime" /> (yyyy-mm-ddThh:mm:ss)</td>
</tr>
<tr>
	<td><label for="endTime">End Time</label></td>
	<td><g:textField name="endTime" /> (yyyy-mm-ddThh:mm:ss)</td>
</tr>
<tr>
	<td><label for="allDay">All day</label></td>
	<td><g:checkBox name="allDay"/></td>
</tr>
<tr>
	<td colspan='2'><input value="Create" type="submit"/></td>
</tr>
</tbody>
</table>

</g:form>

</div>

<script>
$(document).ready(function() {
	/*
	$('#startTime').jdPicker({
	     date_format: "YYYY/MM/dd"
	});
	$('#endTime').jdPicker({
	     date_format: "YYYY/MM/dd"
	});
	*/
});
</script>
