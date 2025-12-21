package hackingtool.services;

import java.util.List;

import hackingtool.devices.Account;
import hackingtool.devices.Alerts;
import hackingtool.devices.Device;
import hackingtool.devices.IntruderStatus;
import hackingtool.devices.OS;
import hackingtool.devices.Privileges;
import hackingtool.devices.User;
import hackingtool.dice.Types;

public interface HackingService {

	// Node Services
	
	Device createNode(String  name,
					  Boolean mindware,
					  Boolean defended,
					  OS	  os,
					  Alerts  alert );
	Device createNode(Device device);
	Device  updateNode(Device device);
	Device  getNode(int id);
	Boolean deleteNode(int id);
	List<Device> getAllNodes();
	
	// OS Services
	
	OS createOS(OS os);
	OS getOS(int id);
	OS updateOS(OS os);
	Boolean deleteOS(int id);
	
	// Account Services
	
	Account createAccount(
						  User user,
						  int deviceID,
						  IntruderStatus status,
						  Privileges     priv,
						  int 	  dur,
						  Boolean defended,
						  Types   dmgDice,
						  int 	  numDmgDice);
	Account createAccount (Account account);
	Account updateAccount(Account account);
	Account getAccount(int id);
	Boolean 	deleteAccount(int id);
	List<Account> getAllNodeAccounts(int nodeID);
	
	
	// User Services
	
	User createUser(String name, 
					int firewall, 
					int infosec, 
					int dur);
	User updateUser(User user);
	User getUser(int id);
	Boolean deleteUser(int id);

	
}
