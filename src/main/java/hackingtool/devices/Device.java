package hackingtool.devices;

import hackingtool.hacking.Account;
import hackingtool.hacking.Alerts;

import java.util.ArrayList;
import java.util.List;

public class Device implements Hackable{

    private String systemName;
    private OS 	   os;
	private int    firewall;
    private int    infosec;
    private Alerts alert;
    private List<Account> accounts;
    
    //private Device       masterDevice;
    //private List<Device> slavedDevices;


    private Device() {
    }
    
    public class Builder {
    	private String systemName;
    	private OS     os;
        private int    firewall;
        private int    infosec;
        private Alerts alert;
        private List<Account> accounts;
    	
        public Builder setSystemName(String systemName) {
			this.systemName = systemName;
			return this;
		}
        public Builder setOS(OS os) {
        	this.os = os;
        	return this;
        }
		public Builder setFirewall(int firewall) {
			this.firewall = firewall;
			return this;
		}
		public Builder setInfosec(int infosec) {
			this.infosec = infosec;
			return this;
		}
		public Builder setAlert(Alerts alert) {
			this.alert = alert;
			return this;
		}
		public Builder setAccounts(List<Account> accounts) {
			this.accounts = accounts;
			return this;
		}
		
		public Device build() {
			
			Device t = new Device();
			
			t.systemName = systemName;
			t.os		 = os;
			t.firewall   = firewall;
			t.infosec    = infosec;
			t.alert      = (alert != null) ? alert : Alerts.NONE;
			t.accounts   = (accounts != null) ? accounts : new ArrayList<>() ;
			
			return t;
		}
    }

    public String getName() {
		return systemName;
	}

	public void setName(String name) {
		this.systemName = name;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
    public void addAccount(Account account) {
    	accounts.add(account);
    }

    public void removeAccount(Account account) {
    	accounts.remove(account);
    }
    
    public void clearAccounts() {
    	accounts.clear();
    }
    
    public void setFirewall(int firewall){
        this.firewall = firewall;
    }
    
    public int getFirewall(){
        return firewall;
    }
    
    public void setInfosec(int infosec){
        this.infosec = infosec;
    }
    
    public int getInfosec(){
        return infosec;
    }

    public void setAlert(Alerts alertLevel){
        this.alert = alertLevel;
    }
    
    public void setAlertLevel(int level) {
    	alert = Alerts.fromLevel(level);
    }
  
    public Alerts getAlert(){
        return alert;
    }
    
    public int getAlertLevel() {
    	return alert.getLevel();
    }
    
    public void increaseAlert() {
    	// Cannot go higher than ACTIVE
    	if (alert != Alerts.ACTIVE) {
    		setAlertLevel(alert.getLevel() + 1);
    	}
    }
 
    public void reduceAlert() {
    	// Cannot go lower than NONE
    	if (alert != Alerts.NONE) {
    		setAlertLevel(alert.getLevel() - 1);
    	}
    }
    
    public OS getOs() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}
    

}