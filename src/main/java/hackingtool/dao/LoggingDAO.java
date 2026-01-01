package hackingtool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Deque;

import hackingtool.logging.Event;
import hackingtool.services.LoggingService;

public class LoggingDAO implements LoggingService {

	private static final String EVENTS_TABLE = "events";
	private static final String EVENT_STRINGS_TABLE = "eventStrings";
	
	public LoggingDAO() {
	}

	// ========== Event Methods ========== \\
	@Override
	public Event createEvent(int deviceID) {
		Event event = new Event(deviceID);
		final String createEvent = "INSERT INTO " + EVENTS_TABLE
								+ " (nodeID, time) "
								+ "VALUES (?,?)";
		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		ResultSet		  generatedKeys = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(createEvent,  Statement.RETURN_GENERATED_KEYS);
			// Convert dateTime to Timestamp
			Timestamp timestamp = Timestamp.valueOf(event.getTime());
			statement.setInt(1, deviceID);
			statement.setTimestamp(2, timestamp);
			statement.executeUpdate();
			generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				event.setID(generatedKeys.getInt(1));
				event.setLog(getEventsStrings(event.getID()));
			}
			
			
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (generatedKeys != null) {
					generatedKeys.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return event;
	}

	@Override
	public Event createEvent(Event event) {
		final String createEvent = "INSERT INTO " + EVENTS_TABLE
								+ " (nodeID, time) "
								+ "VALUES (?,?)";
		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		ResultSet		  generatedKeys = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(createEvent,  Statement.RETURN_GENERATED_KEYS);
			// Convert dateTime to Timestamp
			Timestamp timestamp = Timestamp.valueOf(event.getTime());
			statement.setInt(1, event.getDeviceID());
			statement.setTimestamp(2, timestamp);
			statement.executeUpdate();
			generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				int id = generatedKeys.getInt(1);
				event.setID(id);
				for (String s : event.getLog()) {
					createEventString(id,s);
				}
			}
		}  catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (generatedKeys != null) {
					generatedKeys.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return event;
	}

	@Override
	public Event getEvent(int eventID) {
		Event event = null;
		final String getEvent = "SELECT * FROM " + EVENTS_TABLE
							  + " WHERE eventID = ?";
		Connection 		  connection = null;
		PreparedStatement statement	 = null;
		ResultSet		  resSet     = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getEvent);
			statement.setInt(1, eventID);
			resSet = statement.executeQuery();
			
			if (resSet.next()) {
				Timestamp timestamp = resSet.getTimestamp("time");
				LocalDateTime time = timestamp.toLocalDateTime();
				event = new Event(resSet.getInt("id"),			// eventID
								  resSet.getInt("nodeID"), 	// nodeID
								  time,							// timestamp
								  getEventsStrings(eventID));	// deque of strings making up the event
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resSet!=null) {
					resSet.close();
				}
				if (statement!=null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return event;
	}
	
	@Override
	public Deque<Event> getEventsByNode(int nodeID) {
		Deque<Event> eventLog = new ArrayDeque<>();
		Connection 		  connection = null;
		PreparedStatement statement	 = null;
		ResultSet		  resSet     = null;
		String getEvents = "SELECT * FROM " + EVENTS_TABLE
						 + " WHERE nodeID = ?";
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getEvents);
			statement.setInt(1, nodeID);
			resSet = statement.executeQuery();
			
			Timestamp timestamp;
			LocalDateTime time = null;
			

			
			while (resSet.next()) {
				if (resSet.getTimestamp("time") != null) {
					timestamp = resSet.getTimestamp("time");
					timestamp.toLocalDateTime();
				}
				eventLog.addFirst( new Event(resSet.getInt("id"),			// eventID
						               resSet.getInt("nodeID"), 			// nodeID
						               time,								// timestamp
						               getEventsStrings(resSet.getInt("id"))// deque of strings making up the event);
						               ));	
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resSet!=null) {
					resSet.close();
				}
				if (statement!=null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return eventLog;
	}

	@Override
	public Event updateEvent(Event event) {
		Connection 		  connection = null;
		PreparedStatement statement	 = null;
		String updateEvent = "UPDATE " + EVENTS_TABLE + " SET "
				 		   + "deviceID = ?, "
				 		   + "time = ?";
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(updateEvent);
			LocalDateTime time = event.getTime();
			Timestamp timestamp = Timestamp.valueOf(time);
			
			statement.setInt(1, event.getDeviceID());
			statement.setTimestamp(2, timestamp);
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return event;
	}

	@Override
	public Boolean deleteEvent(int eventID) {
		Connection 		  connection = null;
		PreparedStatement statement	 = null;
		String deleteEvent = "DELETE FROM " + EVENTS_TABLE + " WHERE id = ?";
		
		try {
			if (deleteStringsByEvent(eventID)) {
				connection = DBConnection.getInstance().getConnection();
				statement = connection.prepareStatement(deleteEvent);
				statement.setInt(1, eventID);
				statement.executeUpdate();
				return true;
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement!=null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	@Override
	public Boolean deleteEventsByNode(int nodeID) {
		Connection 		  connection = null;
		PreparedStatement statement	 = null;
		String deleteEvents = "DELETE FROM " + EVENTS_TABLE + " WHERE nodeID = ?";
		
		try {
			// Delete all the strings associated with each event
			Deque<Event> events = getEventsByNode(nodeID);
			for (Event e : events) {
				deleteStringsByEvent(e.getID());
			}
			// Delete the events themselves
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(deleteEvents);
			statement.setInt(1, nodeID);
			statement.executeUpdate();
			return true;
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (statement!=null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	// ========== EventString Methods ========== \\
	@Override
	public Boolean createEventString(int eventID, String content) {
		final String createString = "INSERT INTO " + EVENT_STRINGS_TABLE
								  + " (eventID, content) "
								  + "VALUES (?,?)";
		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		ResultSet		  generatedKeys = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(createString);
			statement.setInt(1, eventID);
			statement.setString(2, content);
			statement.executeUpdate();

			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (generatedKeys != null) {
					generatedKeys.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}

	@Override
	public String getEventString(int stringID) {
		String content = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		String getString = "SELECT * FROM " + EVENT_STRINGS_TABLE + " WHERE id = ?";
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getString);
			statement.setInt(1, stringID);
			resSet = statement.executeQuery();
			
			if (resSet.next()) {
				content = resSet.getString("content");
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resSet!=null) {
					resSet.close();
				}
				if (statement!=null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return content;
	}
	
	@Override
	public Deque<String> getEventsStrings(int eventID) {
		Deque<String> strings = new ArrayDeque<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		String getString = "SELECT * FROM " + EVENT_STRINGS_TABLE + " WHERE eventID = ? ORDER BY id ASC";
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getString);
			statement.setInt(1, eventID);
			resSet = statement.executeQuery();
			
			while (resSet.next()) {
				strings.add(resSet.getString("content")); 
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resSet!=null) {
					resSet.close();
				}
				if (statement!=null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return strings;
	}

	@Override
	public String updateString(int stringID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteString(int stringID) {
		Connection connection = null;
		PreparedStatement statement = null;
		String deleteString = "DELETE FROM " + EVENT_STRINGS_TABLE + " WHERE id = ?";
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(deleteString);
			statement.setInt(1, stringID);
	        statement.executeUpdate();
	        
	        return true;
		} catch (SQLException e) {
	        e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	@Override
	public Boolean deleteStringsByEvent(int eventID) {
		Connection connection = null;
		PreparedStatement statement = null;
		String deleteStrings = "DELETE FROM " + EVENT_STRINGS_TABLE + " WHERE eventID = ?";
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(deleteStrings);
			statement.setInt(1, eventID);
	        statement.executeUpdate();
	        return true;
		} catch (SQLException e) {
	        e.printStackTrace();
		} finally {
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}
