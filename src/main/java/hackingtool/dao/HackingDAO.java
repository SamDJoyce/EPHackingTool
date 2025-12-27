package hackingtool.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import hackingtool.devices.Account;
import hackingtool.devices.Alerts;
import hackingtool.devices.Device;
import hackingtool.devices.Hackable;
import hackingtool.devices.IntruderStatus;
import hackingtool.devices.OS;
import hackingtool.devices.Privileges;
import hackingtool.devices.User;
import hackingtool.dice.Types;
import hackingtool.services.HackingService;

public class HackingDAO implements HackingService {
	
	private static final String NODES_TABLE		= "nodes";
	private static final String OS_TABLE 		= "os";
	private static final String USERS_TABLE 	= "users";
	private static final String ACCOUNTS_TABLE 	= "accounts";
	private static final String LINKS_TABLE		= "links";
	
	public HackingDAO() {
	}

	// Node Methods
	@Override
	public Hackable createNode(String name, Boolean mindware, Boolean defended, Boolean visible, OS os, Alerts alert) {
		Hackable device = new Device.Builder()
								  .setSystemName(name)
								  .setMindware(mindware)
								  .setDefended(defended)
								  .setVisible(visible)
								  .setOS(os)
								  .setAlert(alert)
								  .build();
		final String newDevice = "INSERT INTO " + NODES_TABLE 
							   +" (name, mindware, defended, visible, alert) "
							   + "VALUES (?,?,?,?,?)";
		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		ResultSet		  generatedKeys = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(newDevice,  Statement.RETURN_GENERATED_KEYS);
			
			statement.setString (1, device.getName());
			statement.setBoolean(2, device.isMindware());
			statement.setBoolean(3, device.isDefended());
			statement.setBoolean(4, device.isVisible());
			statement.setInt	(5, device.getAlertLevel());
			
			statement.executeUpdate();
			generatedKeys = statement.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				// If the insertion was successful, assign the ID and insert the OS
				int generatedID = generatedKeys.getInt(1);
				device.setID(generatedID);
				createOS(device.getOS());
			}
			return device;
			
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
		return null;
	}
	
	public Hackable createNode(Hackable device) {
		final String newDevice = "INSERT INTO " + NODES_TABLE 
				   +" (name, mindware, defended, visible, alert) "
				   + "VALUES (?,?,?,?,?)";
		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		ResultSet		  generatedKeys = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(newDevice,  Statement.RETURN_GENERATED_KEYS);
			
			statement.setString (1, device.getName());
			statement.setBoolean(2, device.isMindware());
			statement.setBoolean(3, device.isDefended());
			statement.setBoolean(4, device.isVisible());
			statement.setInt	(5, device.getAlertLevel());
			
			statement.executeUpdate();
			generatedKeys = statement.getGeneratedKeys();
			if (generatedKeys.next()) {
				int generatedID = generatedKeys.getInt(1);
				device.setID(generatedID);
				createOS(device.getOS());
			}
			return device;
			
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
		return null;
	}

	@Override
	public Hackable updateNode(Hackable device) {
		String updateNode = "UPDATE " + NODES_TABLE + " SET "
						  + "name = ?, "
						  + "mindware = ?, "
						  + "defended = ?, "
						  + "visible = ?, "
						  + "alert = ? "
						  + "WHERE id = ?";
		Connection 		  connection    = null;
		PreparedStatement statement     = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(updateNode);
			
			statement.setString(1, device.getName());
			statement.setBoolean(2, device.isMindware());
			statement.setBoolean(3, device.isDefended());
			statement.setBoolean(4, device.isVisible());
			statement.setInt(5, device.getAlertLevel());
			statement.setInt(6, device.getID());
			statement.executeUpdate();
			updateOS(device.getOS());
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
		return device;
	}

	@Override
	public Hackable getNode(int id) {
		Hackable device = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		String getNode = "SELECT * FROM " + NODES_TABLE + " WHERE id = ?";
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getNode);
			statement.setInt(1, id);
			resSet = statement.executeQuery();
			
			if (resSet.next()) {
				device = new Device.Builder()
									.setID(resSet.getInt("id"))
									.setSystemName(resSet.getString("name"))
									.setMindware(resSet.getBoolean("mindware"))
									.setDefended(resSet.getBoolean("defended"))
									.setVisible(resSet.getBoolean("visible"))
									.setOS(getOS(id))
									.setAlert(Alerts.fromLevel(resSet.getInt("alert")))
									.setAccounts(getAllNodeAccounts(id))
									.setLinkedNodes(getLinkedNodes(id))
									.build();
			}
			//
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
		return device;
	}

	@Override
	public Boolean deleteNode(int id) {
		String deleteNode = "DELETE FROM " + NODES_TABLE + " WHERE id = ?";
		Connection connection = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = null;
			
			// Delete the system OS and the node's links before deleting the node
			if (deleteOS(id) && deleteLinks(id) && deleteAccountsByDevice(id)) {
				statement = connection.prepareStatement(deleteNode);
				statement.setInt(1, id);
		        statement.executeUpdate();
			}
			return true;
	    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		}
	}

	@Override
	public List<Hackable> getAllNodes() {
		List<Hackable> allDevices = new ArrayList<>();
		String getAllNodes = "SELECT * FROM " + NODES_TABLE;
		Connection connection = null;
		Statement  statement  = null;
		ResultSet  resSet     = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			statement  = connection.createStatement();
			resSet     = statement.executeQuery(getAllNodes);
			
			while (resSet.next()) {
				allDevices.add( new Device.Builder()
									.setID(resSet.getInt("id"))
									.setSystemName(resSet.getString("name"))
									.setMindware(resSet.getBoolean("mindware"))
									.setDefended(resSet.getBoolean("defended"))
									.setVisible(resSet.getBoolean("visible"))
									.setOS(getOS(resSet.getInt("id")))
									.setAlert(Alerts.fromLevel(resSet.getInt("alert")))
									.setAccounts(getAllNodeAccounts(resSet.getInt("id")))
									.setLinkedNodes(getLinkedNodes(resSet.getInt("id")))
									.build());
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resSet != null) {
					resSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return allDevices;
	}

	// OS Methods
	
	@Override
	public OS createOS(OS os) {
		
		final String newOS = "INSERT INTO " + OS_TABLE
				   + " (id, type, durability, damage, wounds, armor, firewall, infosec, defended)"
				   + " VALUES (?,?,?,?,?,?,?,?,?)";

		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(newOS);
			
			statement = connection.prepareStatement(newOS);
			statement.setInt(1, os.getID());			// ID
			statement.setString(2,os.getType());		// Type
			statement.setInt(3, os.getDurability());	// Durability
			statement.setInt(4, 0);						// Damage
			statement.setInt(5, 0);						// Wounds
			statement.setInt(6, 0);						// Armor
			statement.setInt(7, os.getFirewall());		// Firewall
			statement.setInt(8, os.getFirewall());		// Infosec
			statement.setBoolean(9, false);				// Defended
			
			statement.executeUpdate();
		} catch (SQLException e) {
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
		return null;
	}
	
	@Override
	public OS getOS(int id) {
		OS os = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		String getOS = "SELECT * FROM " + OS_TABLE + " WHERE id = ?";
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getOS);
			statement.setInt(1, id);
			resSet = statement.executeQuery();
			
			if (resSet.next()) {
				os = new OS (
							resSet.getInt("id"),
							resSet.getString("type"),
							resSet.getInt("durability"),
							resSet.getInt("wounds"),
							resSet.getInt("damage"),
							resSet.getInt("armor"),
							resSet.getInt("firewall"),
							resSet.getInt("infosec"),
							resSet.getBoolean("defended")
							);
				return os;
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
		return os;
	}
	
	@Override
	public OS updateOS(OS os) {
		Connection connection = null;
		PreparedStatement statement = null;
		String updateOS = "UPDATE " + OS_TABLE + " SET "
				+ "type = ?, "
				+ "durability = ?, "
				+ "damage = ?, "
				+ "wounds = ?, "
				+ "armor = ?, "
				+ "firewall = ?, "
				+ "infosec = ?, "
				+ "defended = ? "
				+ "WHERE id = ?";
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(updateOS);
			statement.setString(1, os.getType());
			statement.setInt(2, os.getDurability());
			statement.setInt(3, os.getDamage());
			statement.setInt(4, os.getWounds());
			statement.setInt(5, os.getArmor());
			statement.setInt(6, os.getFirewall());
			statement.setInt(7, os.getInfosec());
			statement.setBoolean(8, os.isDefended());
			statement.setInt(9,os.getID());
			statement.executeUpdate();
			
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
		return os;
	}
	@Override
	public Boolean deleteOS(int id) {
		String deleteOS = "DELETE FROM " + OS_TABLE + " WHERE id = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(deleteOS);
			statement.setInt(1, id);
	        statement.executeUpdate();
	        return true;
	    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		}
	}
	
	
	// Account Methods
	@Override
	public Account createAccount(
								User user,
								int deviceID, 
								IntruderStatus status, 
								Privileges priv, 
								int dur, 
								Boolean defended,
								Types dmgDice, 
								int numDmgDice ) {
		final String newAccount = "INSERT INTO " + ACCOUNTS_TABLE 
								+ " (userID, deviceID, status, privileges, durability, wounds, damage, armor, defended, dice, numDice) "
								+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		Account account = new Account.Builder()
									.setUser(user)
									.setDeviceID(deviceID)
									.setStatus(status)
									.setPriv(priv)
									.setDur(dur)
									.setDefended(defended)
									.setDmgDice(dmgDice)
									.setNumDmgDice(numDmgDice)
									.build();
		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		ResultSet		  generatedKeys = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(newAccount,  Statement.RETURN_GENERATED_KEYS);
			
			statement.setInt(1, user.getID());				  // User ID
			statement.setInt(2, account.getDeviceID());		  // Device ID
			statement.setInt(3, account.getStatusLevel());	  // Status level
			statement.setInt(4, account.getPrivLevel());	  // Privilege level
			statement.setInt(5, account.getDurability());	  // Durability
			statement.setInt(6, 0);							  // Wounds
			statement.setInt(7, 0);							  // Damage
			statement.setInt(8, 0);							  // Armor
			statement.setBoolean(9, account.isDefended());	  // Defended
			statement.setInt(10, account.getDmgDice().get()); // Damage Dice type
			statement.setInt(11, account.getNumDmgDice());	  // Number of Damage Dice
			
			statement.executeUpdate();
			generatedKeys = statement.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				int generatedID = generatedKeys.getInt(1);
				account.setID(generatedID);
			}
			return account;
			
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
		
		return null;
	}
	
	@Override
	public Account createAccount (Account account) {
		final String newAccount = "INSERT INTO " + ACCOUNTS_TABLE 
				+ " (userID, deviceID, status, privileges, durability, wounds, damage, armor, defended, dice, numDice) "
				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		ResultSet		  generatedKeys = null;
		
		try {
		connection = DBConnection.getInstance().getConnection();
		statement = connection.prepareStatement(newAccount,  Statement.RETURN_GENERATED_KEYS);
		
		statement.setInt(1, account.getUser().getID());				  // User ID
		statement.setInt(2, account.getDeviceID());		  // Device ID
		statement.setInt(3, account.getStatusLevel());	  // Status level
		statement.setInt(4, account.getPrivLevel());	  // Privilege level
		statement.setInt(5, account.getDurability());	  // Durability
		statement.setInt(6, account.getWounds());		  // Wounds
		statement.setInt(7, account.getDamage());		  // Damage
		statement.setInt(8, account.getArmor());		  // Armor
		statement.setBoolean(9, account.isDefended());	  // Defended
		statement.setInt(10, account.getDmgDice().get()); // Damage Dice type
		statement.setInt(11, account.getNumDmgDice());	  // Number of Damage Dice
		
		statement.executeUpdate();
		generatedKeys = statement.getGeneratedKeys();
		
		if (generatedKeys.next()) {
			int generatedID = generatedKeys.getInt(1);
			account.setID(generatedID);
		}
		return account;
		
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
		
		return null;
	}
	
	@Override
	public Account updateAccount(Account account) {
		final String updateAccount = "UPDATE " + ACCOUNTS_TABLE + " SET "
				+ "userID = ?, "
				+ "deviceID = ?, "
				+ "status = ?, "
				+ "privileges = ?, "
				+ "durability = ?, "
				+ "wounds = ?, "
				+ "damage = ?, "
				+ "armor = ?, "
				+ "defended = ?, "
				+ "dice = ?, "
				+ "numDice = ? "
				+ "WHERE id = ?";

		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		
		try {
		connection = DBConnection.getInstance().getConnection();
		statement = connection.prepareStatement(updateAccount);
		
		statement.setInt(1, account.getUser().getID());	  // User ID
		statement.setInt(2, account.getDeviceID());		  // Device ID
		statement.setInt(3, account.getStatusLevel());	  // Status level
		statement.setInt(4, account.getPrivLevel());	  // Privilege level
		statement.setInt(5, account.getDurability());	  // Durability
		statement.setInt(6, account.getWounds());		  // Wounds
		statement.setInt(7, account.getDamage());		  // Damage
		statement.setInt(8, account.getArmor());		  // Armor
		statement.setBoolean(9, account.isDefended());	  // Defended
		statement.setInt(10, account.getDmgDice().get()); // Damage Dice type
		statement.setInt(11, account.getNumDmgDice());	  // Number of Damage Dice
		statement.setInt(12, account.getID());			  // Account ID
		
		statement.executeUpdate();
		return account;
		
		}  catch (SQLException e) {
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
		
		return null;
	}

	@Override
	public Account getAccount(int id) {
		Account account = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		String getAccount = "SELECT * FROM " + ACCOUNTS_TABLE + " WHERE id = ?";
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getAccount);
			statement.setInt(1, id);
			resSet = statement.executeQuery();
			
			if (resSet.next()) {
				account = new Account.Builder()
									.setID(id)
									.setDeviceID(resSet.getInt("deviceID"))
									.setUser(getUser(resSet.getInt("userID")))
									.setStatus(IntruderStatus.fromLevel(resSet.getInt("status")))
									.setPriv(Privileges.fromLevel(resSet.getInt("privileges")))
									.setDur(resSet.getInt("durability"))
									.setWounds(resSet.getInt("wounds"))
									.setDamage(resSet.getInt("damage"))
									.setArmor(resSet.getInt("armor"))
									.setDmgDice(Types.fromInt(resSet.getInt("dice")))
									.setNumDmgDice(resSet.getInt("numDice"))
									.build();
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
		return account;
	}

	@Override
	public Boolean deleteAccount(int id) {
	String deleteAccount = "DELETE FROM " + ACCOUNTS_TABLE + " WHERE id = ?";
		
		try (Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(deleteAccount)) {
			statement.setInt(1, id);
	        statement.executeUpdate();
	        return true;
			
	    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		}
		
	}
	
	@Override
	public Boolean deleteAccountsByDevice(int deviceID) {
		String deleteAccount = "DELETE FROM " + ACCOUNTS_TABLE + " WHERE deviceID = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(deleteAccount);
			statement.setInt(1, deviceID);
	        statement.executeUpdate();
	        return true;
			
	    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		}
		
	}

	@Override
	public List<Account> getAllNodeAccounts(int nodeID) {
		List<Account> accounts = new ArrayList<>();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		String getAccount = "SELECT * FROM " + ACCOUNTS_TABLE + " WHERE deviceID = ?";
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getAccount);
			statement.setInt(1, nodeID);
			resSet = statement.executeQuery();
			
			while (resSet.next()) {
				accounts.add( new Account.Builder()
									.setID(resSet.getInt("id"))
									.setDeviceID(resSet.getInt("deviceID"))
									.setUser(getUser(resSet.getInt("userID")))
									.setStatus(IntruderStatus.fromLevel(resSet.getInt("status")))
									.setPriv(Privileges.fromLevel(resSet.getInt("privileges")))
									.setDur(resSet.getInt("durability"))
									.setWounds(resSet.getInt("wounds"))
									.setDamage(resSet.getInt("damage"))
									.setArmor(resSet.getInt("armor"))
									.setDmgDice(Types.fromInt(resSet.getInt("dice")))
									.setNumDmgDice(resSet.getInt("numDice"))
									.build() );
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
		return accounts;
	}
	
	// User methods
	@Override
	public User createUser(String name, int firewall, int infosec, int dur) {
		User user = new User(name, firewall,infosec, dur);
		final String newUser = "INSERT INTO " + USERS_TABLE
							 + " (name, firewall, infosec, durability, wounds, damage, armor) "
							 + "VALUES (?,?,?,?,0,0,0)";
		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		ResultSet		  generatedKeys = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(newUser,  Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, user.getName());
			statement.setInt(2, user.getFirewall());
			statement.setInt(3, user.getInfosec());
			statement.setInt(4, user.getDurability());
			
			statement.executeUpdate();
			generatedKeys = statement.getGeneratedKeys();
			
			if (generatedKeys.next()) {
				int generatedID = generatedKeys.getInt(1);
				user.setID(generatedID);
			}
			return user;
			
		}catch (SQLException e) {
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
		
		return null;
	}

	@Override
	public User updateUser(User user) {
		
		Connection 		  connection    = null;
		PreparedStatement statement     = null;
		String updateUser = "UPDATE " + USERS_TABLE + " SET "
						  + "name = ?, "
						  + "durability = ?, "
						  + "wounds = ?, "
						  + "damage = ?, "
						  + "armor = ? "
						  + "WHERE id = ?";
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(updateUser);
			
			
			statement.setString(1, user.getName());
			statement.setInt(2, user.getDurability());
			statement.setInt(3, user.getWounds());
			statement.setInt(4, user.getDamage());
			statement.setInt(5, user.getArmor());
			statement.setInt(6, user.getID());
			
			statement.executeUpdate();
			
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
		
		return user;
	}

	@Override
	public User getUser(int id) {
		User user = null;
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resSet = null;
		String getUser = "SELECT * FROM " + USERS_TABLE + " WHERE id = ?";
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getUser);
			statement.setInt(1, id);
			resSet = statement.executeQuery();
			
			if (resSet.next()) {
				user = new User(
						resSet.getInt("id"),
						resSet.getString("name"),
						resSet.getInt("firewall"),
						resSet.getInt("infosec"),
						resSet.getInt("durability") );
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
		return user;
	}
	
	@Override
	public List<User> getAllUsers(){
		List<User> users = new ArrayList<>();
		String getAllUsers= "SELECT * FROM " + USERS_TABLE;
		Connection connection = null;
		Statement statement = null;
		ResultSet resSet = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.createStatement();
			resSet = statement.executeQuery(getAllUsers);
			
			while (resSet.next()) {
				users.add( new User(resSet.getInt("id"),
									resSet.getString("name"),
									resSet.getInt("firewall"),
									resSet.getInt("infosec"),
									resSet.getInt("durability")));
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
		return users;
	}
	
	@Override
	public Boolean deleteUser(int id) {
		String deleteUser = "DELETE FROM " + USERS_TABLE + " WHERE id = ?";
		
		try (Connection connection = DBConnection.getInstance().getConnection();
			PreparedStatement statement = connection.prepareStatement(deleteUser)) {
			statement.setInt(1, id);
	        statement.executeUpdate();
	        return true;
	    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		}
		
	}
	
	// Node Links services
	public Boolean createLink(int source, int target) {
		String createLink = "INSERT INTO " + LINKS_TABLE
						  + " (source, target) "
						  + "VALUES (?,?)";
		Connection 		  connection 	= null;
		PreparedStatement statement		= null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			// Create the linke from source to target
			statement = connection.prepareStatement(createLink);
			statement.setInt	(1, source);
			statement.setInt	(2, target);
			statement.executeUpdate();
			// Then create the mirror connection from target to source
			statement = connection.prepareStatement(createLink);
			statement.setInt	(1, target);
			statement.setInt	(2, source);
			statement.executeUpdate();
			return true;
		} catch (SQLException e) {
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
		return false;
	}
	
	public Boolean deleteLink(int linkID) {
		String deleteLink = "DELETE FROM " + LINKS_TABLE + " WHERE id = ?";
		try (Connection connection = DBConnection.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(deleteLink)) {
				statement.setInt(1, linkID);
		        statement.executeUpdate();
		        return true;
			
		    } catch (SQLException e) {
			        e.printStackTrace();
			        return false;
			}
	}
	
	public Boolean deleteLink(int source, int target) {
		String deleteLink = "DELETE FROM " + LINKS_TABLE 
						  + " WHERE source = ? AND target = ?";
		PreparedStatement statement = null;
		try (Connection connection = DBConnection.getInstance().getConnection()) {
			
			statement = connection.prepareStatement(deleteLink);
				statement.setInt(1, source);
				statement.setInt(2, target);
		        statement.executeUpdate();
			        
	        statement = connection.prepareStatement(deleteLink) ;
				statement.setInt(1, target);
				statement.setInt(2, source);
		        statement.executeUpdate();
	        return true;
			
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return false;
		}
	}
	
	public Boolean deleteLinks(int source) {
		String deleteLink = "DELETE FROM " + LINKS_TABLE 
						  + " WHERE source = ? OR target = ?";
		Connection         connection = null;
		PreparedStatement  statement  = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(deleteLink);
			statement.setInt(1, source);
			statement.setInt(2, source);
	        statement.executeUpdate();
	        return true;
		
	    } catch (SQLException e) {
		        e.printStackTrace();
		        return false;
		}
}
	
	public List<Integer> getLinkedNodes(int sourceID){
		List<Integer> linkedNodes = new ArrayList<>();
		String getNodes = "SELECT * FROM " + LINKS_TABLE + " WHERE source = ?";
		Connection         connection = null;
		PreparedStatement  statement  = null;
		ResultSet  		   resSet     = null;
		
		try {
			connection = DBConnection.getInstance().getConnection();
			statement = connection.prepareStatement(getNodes);
			statement.setInt(1, sourceID);
			resSet = statement.executeQuery();
			
			while (resSet.next()) {
				linkedNodes.add(resSet.getInt("target"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resSet != null) {
					resSet.close();
				}
				if (statement != null) {
					statement.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return linkedNodes;
	}
}
