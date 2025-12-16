package hackingtool.logging;

import java.util.ArrayDeque;
import java.util.Deque;

public class Event {
	
	// Fields
	Deque<String> log;
	
	// Constructor
	public Event() {
		log = new ArrayDeque<>();
	}
	
	// Getters/setters
	public Deque<String> getLog() {
		return log;
	}

	public void setLog(Deque<String> log) {
		this.log = log;
	}
	
	public void add(String event) {
		log.add(event);
	}
	
	public void clear() {
		log.clear();
	}

}
