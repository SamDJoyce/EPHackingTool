package hackingtool.logging;

import java.util.ArrayDeque;
import java.util.Deque;

public class Logger implements Observer {
	Deque<Event> eventLog;
	
	public Logger() {
		eventLog = new ArrayDeque<>();
	}

	@Override
	public void update(Event event) {
		eventLog.addFirst(event);
	}
	
	public Deque<Event> getEventLog(){
		return eventLog;
	}
	
	public void clearEventLog() {
		eventLog.clear();
	}
}
