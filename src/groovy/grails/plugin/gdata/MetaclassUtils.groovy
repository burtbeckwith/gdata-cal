package grails.plugin.gdata

import com.google.gdata.data.PlainTextConstruct
import com.google.gdata.data.calendar.CalendarEntry
import com.google.gdata.data.calendar.CalendarEventEntry
import com.google.gdata.data.calendar.ColorProperty
import com.google.gdata.data.calendar.HiddenProperty
import com.google.gdata.data.calendar.TimeZoneProperty
import com.google.gdata.data.extensions.Recurrence

class MetaclassUtils {

	static void enhanceAll(ctx) {
		enhanceCalendarEntry()
		enhanceCalendarEventEntry()
		enhanceDate(ctx)
	}

	static void enhanceCalendarEntry() {
		def mc = CalendarEntry.metaClass

		mc.setTitle = { String s -> delegate.setTitle(new PlainTextConstruct(s)) }
		mc.setSummary = { String s -> delegate.setSummary(new PlainTextConstruct(s)) }
		mc.setContent = { String s -> delegate.setContent(new PlainTextConstruct(s)) }
		mc.setRights = { String s -> delegate.setRights(new PlainTextConstruct(s)) }

		// hex color, e.g. ff0000
		mc.setColor = { String c -> delegate.setColor(new ColorProperty('#' + c)) }

		mc.setHidden = { boolean b -> delegate.setHidden(b ? HiddenProperty.TRUE : HiddenProperty.FALSE) }
		mc.isHidden = { -> delegate.getHidden() == HiddenProperty.TRUE }
		mc.setSelected = { boolean b -> delegate.setSelected(b ? HiddenProperty.TRUE : HiddenProperty.FALSE) }
		mc.isSelected = { -> delegate.getSelected() == HiddenProperty.TRUE }

		mc.setTimeZone = { String s -> delegate.setTimeZone(new TimeZoneProperty(s)) }
	}

	static void enhanceCalendarEventEntry() {
		def mc = CalendarEventEntry.metaClass

		mc.setTitle = { String s -> delegate.setTitle(new PlainTextConstruct(s)) }
		mc.setSummary = { String s -> delegate.setSummary(new PlainTextConstruct(s)) }
		mc.setContent = { String s -> delegate.setContent(new PlainTextConstruct(s)) }
		mc.setRights = { String s -> delegate.setRights(new PlainTextConstruct(s)) }

		mc.setRecurrence = { String s -> delegate.setRecurrence(new Recurrence(s)) }

		mc.getStartTime = { -> new Date(delegate.getTimes()[0].getStartTime().getValue()) }
		mc.getEndTime = { ->
			def end = delegate.getTimes()[0].getEndTime()
			end ? new Date(end.getValue()) : null
		}
	}

	static void enhanceDate(ctx) {
		Date.metaClass.createCalendarEntry = { Map properties ->
			ctx.calendarService.createEvent delegate, null, TimeZone.default, false, properties
		}
	}
}
