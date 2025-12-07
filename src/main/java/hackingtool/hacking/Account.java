package hackingtool.hacking;

import hackingtool.User;

public class Account {

	private User user;
	private IntruderStatus status;
	private Privileges priv;
    private int woundThresh;
    private int durability;
    private int deathRating;



	public Account(User user, IntruderStatus status, Privileges priv, int dur) {
		this.user = user;
		this.status = status;
		this.priv = priv;
        this.durability = dur;
        this.woundThresh = dur/5;
        this.deathRating = dur*2;
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
