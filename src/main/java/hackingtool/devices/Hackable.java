package hackingtool.devices;

import java.util.List;

public interface Hackable {
	int getID();
	void setID(int id);
	
	String getName();
	void setName(String name);
	
	void setFirewall(int firewall);
	int getFirewall();
	
	void setInfosec(int infosec);
	int getInfosec();
	
	int getDamage();
	void setDamage(int damage);
	int getWounds();
	void setWounds(int wounds);
	
	int getDurability();
	
	List<Account> getAccounts();
	Account getAccount(int id);
	Account getAccount (User user);
	void setAccounts(List<Account> accounts);
	void addAccount(Account account);
	void updateAccount (Account account);
	void removeAccount(Account account);
	void clearAccounts();
	Boolean accountPresent(User user);
	
	void setAlert(Alerts alertLevel);
	void setAlertLevel(int level);
	Alerts getAlert();
	int getAlertLevel();
	String getAlertString();
	void increaseAlert();
	void reduceAlert();
	
	Boolean isMindware();
	void setMindware(Boolean mindware);
	Boolean isDefended();
	void setDefended(Boolean defended);
	Boolean isVisible();
	void setVisible(Boolean visible);
	
	OS getOS();
	String getStability();
	
	List<Integer> getLinkedNodes();
	void setLinkedNodes(List<Integer> linkedNodes);
	void addLinkedNode(Integer nodeID);
	void removeLinkedNode(Integer nodeID);
	Boolean linksToNode(Integer nodeID);
}
