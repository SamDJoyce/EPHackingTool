package hackingtool.devices;

import hackingtool.hacking.IntruderStatus;
import hackingtool.hacking.Privileges;

/**
 * An Account is a container for a user's system privileges
 * intruder status and other important metadata
 */
public class Account {

	//private static int nextID = 0;
	private int id;
	private User user;
	private IntruderStatus status;
	private Privileges priv;
    private int woundThresh;
    private int durability;
    private int deathRating;


    private Account () {
    }
	
	public static class Builder {
		private static int nextID = 0;
		private int id;
		private User user;
		private IntruderStatus status;
		private Privileges priv;
	    private int woundThresh;
	    private int durability;
	    private int deathRating;
	    
	    public Builder setID(int id) {
	    	this.id = id;
	    	return this;
	    }
	    
	    public Builder setUser (User user) {
	    	this.user = user;
	    	return this;
	    }
	    
	    public Builder setStatus(IntruderStatus status) {
	    	this.status = status;
	    	return this;
	    }
	    
	    public Builder setPriv(Privileges priv) {
	    	this.priv = priv;
	    	return this;
	    }
	    
	    public Builder setDur(int durability) {
	    	this.durability  = durability;
	        this.woundThresh = durability/5;
	        this.deathRating = durability*2;
	    	return this;
	    }
	    
	    public Account build() {
	    	Account a = new Account();
	    	
	    	a.id = (id == 0 ? ++nextID : id);
	    	a.user 		  = user;
	    	a.status 	  = status;
	    	a.priv 		  = priv;
	    	a.durability  = durability;
	    	a.deathRating = deathRating;
	    	a.woundThresh = woundThresh;
	    	
	    	return a;
	    }
	}

	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public IntruderStatus getStatus() {
		return status;
	}

	public void setStatus(IntruderStatus status) {
		this.status = status;
	}
	
	public void setStatusLevel(int level) {
		status = IntruderStatus.fromLevel(level);
	}
	
	public void improveStatus() {
		if (status != IntruderStatus.HIDDEN) {
			setStatusLevel(status.getLevel()-1);
		}
	}
	
	public void worsenStatus() {
		if (status != IntruderStatus.SPOTTED) {
			setStatusLevel(status.getLevel()+1);
		}
	}

	public Privileges getPriv() {
		return priv;
	}

	public void setPriv(Privileges priv) {
		this.priv = priv;
	}
	
	public void setPrivLevel(int level) {
		priv = Privileges.fromLevel(level);
	}
	
	public void increasePriv() {
		if (priv != Privileges.ADMIN) {
			setPrivLevel(priv.getLevel() + 1);
		}
	}
	
	public void decreasePriv() {
		if (priv != Privileges.PUBLIC) {
			setPrivLevel(priv.getLevel() - 1);
		}
	}
	
	public String getUserName() {
		return user.getName();
	}
	
	public int getUserInfoSec() {
		return user.getInfosec();
	}
	
	public int getUserFirewall() {
		return user.getFirewall();
	}
	
	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
		this.woundThresh = durability/5;
		this.deathRating = durability*2;
	}

	public int getWoundThresh() {
		return woundThresh;
	}

	public int getDeathRating() {
		return deathRating;
	}

}
