package hackingtool.logging;

import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

public class Event {
	
	// Fields
	int id;
	int deviceID;
	LocalDateTime time;
	Deque<String> log;
	
	// Constructor
	public Event(int deviceID) {
		this.deviceID = deviceID;
		log = new ArrayDeque<>();
		time = LocalDateTime.now();
	}
	
	public Event(int id, int deviceID, LocalDateTime time, Deque<String> log) {
		this.id = id;
		this.deviceID = id;
		this.time = time;
		this.log = log;
	}
	
	// Getters/setters
	public Deque<String> getLog() {
		return log;
	}

	public void setLog(Deque<String> log) {
		this.log = log;
	}
	
	public void add(String event) {
		log.addLast(event);
	}
	
	public void clear() {
		log.clear();
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}

	public int getDeviceID() {
		return deviceID;
	}

	public void setDeviceID(int deviceID) {
		this.deviceID = deviceID;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

}
