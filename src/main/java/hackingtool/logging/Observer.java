package hackingtool.logging;

import java.util.Deque;

public interface Observer {
	void update(Event event);
	Deque<Event> getEventLog();
}
