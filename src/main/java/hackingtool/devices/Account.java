package hackingtool.devices;

import hackingtool.dice.Dice;
import hackingtool.dice.DiceFactory;
import hackingtool.dice.Tests;
import hackingtool.dice.Types;
import hackingtool.hacking.MeshCombatant;

/**
 * An Account is a container for a user's system privileges
 * intruder status and other important metadata
 */
public class Account implements MeshCombatant{

	//private static int nextID = 0;
	private int id;
	private User user;
	private IntruderStatus status;
	private Privileges priv;
    private int woundThresh;
    private int durability;
    private int deathRating;
	private int wounds;
    private int damage;
	private int armor;
	private Boolean defended;

    private Account () {
    	this.wounds = 0;
    	this.damage = 0;
    	this.armor  = 0;
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
	    private Boolean defended;
	    
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
	    
	    public Builder setDefended(Boolean defended) {
	    	this.defended = defended;
	    	return this;
	    }
	    
	    public Account build() {
	    	Account a = new Account();
	    	
	    	a.id 		  = (id == 0 ? ++nextID : id);
	    	a.user 		  = user;
	    	a.status 	  = status;
	    	a.priv 		  = priv;
	    	a.durability  = durability;
	    	a.deathRating = deathRating;
	    	a.woundThresh = woundThresh;
	    	a.defended    = (defended != null) ? defended : false;
	    	
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
	
	public int getInfosec() {
		return user.getInfosec();
	}
	
	public int getFirewall() {
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
	
    public int getWounds() {
		return wounds;
	}

	public void setWounds(int wounds) {
		this.wounds = wounds;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}
    public int getArmor() {
		return armor;
	}

	public void setArmor(int armor) {
		this.armor = armor;
	}
	
	// MeshCombatant Methods
	
	@Override
	public int meshAttack() {
		return calcMeshDamage(Types.D10, 2);
	}

	@Override
	public int calcMeshDamage(Types type, int num) {
		int total = 0;
		Dice dice = DiceFactory.get(type);
		for (int i = 0; i > num; i++) {
			total += dice.roll();
		}
		return total;
	}

	@Override
	public void takeMeshDamage(int damage) {
		int modDamage = damage - armor;
		this.damage += modDamage;
		this.wounds += calcMeshWounds(modDamage);
		if (this.damage >= durability) {
			// Unconscious
		}
		if (this.damage >= deathRating) {
			// Dead
		}	
	}

	@Override
	public int calcMeshWounds(int damage) {
		if (damage >= woundThresh) {
			return damage/woundThresh;
		}
		return 0;
	}

	@Override
	public Boolean detectMeshAttack() {
		Tests test = new Tests();
		return test.successTest(getInfosec());
	}
	
	@Override
	public Boolean isDefended() {
		return defended;
	}

}
