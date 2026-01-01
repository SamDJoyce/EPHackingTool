package hackingtool.logging;

import java.util.ArrayDeque;
import java.util.Deque;

import hackingtool.dao.LoggingDAO;
import hackingtool.services.LoggingService;

public class Logger implements Observer {
	private final static LoggingService logServ = new LoggingDAO();
	Deque<Event> eventLog;
	
	public Logger() {
		eventLog = new ArrayDeque<>();
	}

	@Override
	public void update(Event event) {
		eventLog.addFirst(event);
		logServ.createEvent(event);
	}
	
	public Deque<Event> getEventLog(){
		return eventLog;
	}
	
	public void clearEventLog() {
		eventLog.clear();
	}
}
