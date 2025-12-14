package hackingtool.devices;

import java.util.List;

import hackingtool.hacking.Alerts;

public interface Hackable {
	String getName();
	void setName(String name);
	
	void setFirewall(int firewall);
	int getFirewall();
	
	void setInfosec(int infosec);
	int getInfosec();
	
	List<Account> getAccounts();
	Account getAccount(int id);
	Account getAccount (User user);
	void setAccounts(List<Account> accounts);
	void addAccount(Account account);
	void updateAccount (Account account);
	void removeAccount(Account account);
	void clearAccounts();
	
	void setAlert(Alerts alertLevel);
	void setAlertLevel(int level);
	Alerts getAlert();
	int getAlertLevel();
	void increaseAlert();
	void reduceAlert();
	boolean isMindware();
}
