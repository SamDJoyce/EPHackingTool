package hackingtool.devices;

import java.util.ArrayList;
import java.util.List;

public class Device implements Hackable{

    private String  systemName;
    private Boolean mindware;
    private Boolean defended;
    private OS 	    os;
    private Alerts  alert;
    private List<Account> accounts;
    
    //private Device       masterDevice;
    //private List<Device> slavedDevices;


    private Device() {
    }
    
    public static class Builder {
    	private String systemName;
    	private Boolean mindware;
    	private Boolean defended;
    	private OS     os;
        private Alerts alert;
        private List<Account> accounts;
    	
        public Builder setSystemName(String systemName) {
			this.systemName = systemName;
			return this;
		}
        public Builder setMindware(Boolean isMindware) {
        	this.mindware = isMindware;
        	return this;
        }
        public Builder setDefended(Boolean isDefended) {
        	this.defended = isDefended;
        	return this;
        }
        public Builder setOS(OS os) {
        	this.os = os;
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
			
			Device d = new Device();
			
			d.systemName = systemName;
			d.mindware = (mindware != null) ? mindware : false;
			d.defended   = (defended != null) ? defended : false;
			d.os		 = os;
			d.alert      = (alert != null) ? alert : Alerts.NONE;
			d.accounts   = (accounts != null) ? accounts : new ArrayList<>() ;
			
			return d;
		}
    }

    public String getName() {
		return systemName;
	}

	public void setName(String name) {
		this.systemName = name;
	}
	
	public Account getAccount(int id) {
		for (Account a : accounts) {
			if (a.getID() == id) {
				return a;
			}
		}
		return null;
	}
	
	public Account getAccount (User user) {
		for (Account a : accounts) {
			if (a.getUser().equals(user)) {
				return a;
			}
		}
		return null;
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
    
    public void updateAccount (Account account) {
    	for (Account a : accounts) {
    		if (a.getID() == account.getID()) {
    			a = account;
    		}
    	}
    }

    public void removeAccount(Account account) {
    	accounts.remove(account);
    }
    
    public void clearAccounts() {
    	accounts.clear();
    }
    
    public void setFirewall(int firewall){
        os.setFirewall(firewall);
    }
    
    public int getFirewall(){
        return os.getFirewall();
    }
    
    public void setInfosec(int infosec){
        os.setInfosec(infosec);
    }
    
    public int getInfosec(){
        return os.getInfosec();
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
    
    public String getAlertString() {
    	return Alerts.toString(alert);
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
    
    public OS getOS() {
		return os;
	}

	public void setOs(OS os) {
		this.os = os;
	}
	
	public int getDamage() {
		return os.getDamage();
	}
	
	public int getWounds() {
		return os.getWounds();
	}
	
	public int getDurability() {
		return os.getDurability();
	}

	@Override
	public Boolean isMindware() {
		return mindware;
	}

	@Override
	public Boolean isDefended() {
		return defended;
	}
    
	

}