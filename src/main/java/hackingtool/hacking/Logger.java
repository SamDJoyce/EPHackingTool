package hackingtool.hacking;

import java.util.ArrayDeque;
import java.util.Deque;

public class Logger implements Observer {
	Deque<String> eventLog;
	
	public Logger() {
		eventLog = new ArrayDeque<>();
	}

	@Override
	public void update(String event) {
		eventLog.addFirst(event);
	}
	
	public Deque<String> getEventLog(){
		return eventLog;
	}
	
	public void clearEventLog() {
		eventLog.clear();
	}
}
