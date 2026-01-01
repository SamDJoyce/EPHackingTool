package hackingtool.services;

import java.util.Deque;

import hackingtool.logging.Event;

public interface LoggingService {
	// Event methods
	Event createEvent(int deviceID);
	Event createEvent(Event event);
	Event getEvent(int eventID);
	Deque<Event> getEventsByNode(int nodeID);
	Event updateEvent(Event event);
	Boolean deleteEvent(int eventID);
	Boolean deleteEventsByNode(int nodeID);
	// EventString methods
	Boolean createEventString(int eventID, String content);
	String getEventString(int stringID);
	Deque<String> getEventsStrings(int eventID);
	String updateString(int stringID);
	Boolean deleteString(int stringID);
	Boolean deleteStringsByEvent(int eventID);
}
