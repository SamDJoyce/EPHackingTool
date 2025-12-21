package hackingtool.devices;

import java.util.List;

public interface Hackable {
	int getID();
	String getName();
	void setName(String name);
	
	void setFirewall(int firewall);
	int getFirewall();
	
	void setInfosec(int infosec);
	int getInfosec();
	
	int getDamage();
	int getWounds();
	
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
	Boolean isDefended();
	OS getOS();
}
