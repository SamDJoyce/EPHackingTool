package hackingtool.devices;

public class User {
	private String name;
    private int infosec;
    private int firewall;
    private int woundThresh;
    private int durability;
    private int deathRating;

    public User(String name, int firewall, int infosec, int dur){ // constructor
        this.name = name;
    	this.firewall = firewall;
        this.infosec = infosec;
        this.durability = dur;
        this.woundThresh = dur/5;
        this.deathRating = dur*2;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
    
	public int getDurability() {
		return durability;
	}

	public void setDurability(int dur) {
		this.durability = dur;
		this.woundThresh = dur/5;
		this.deathRating = dur*2;
	}

	public int getWoundThresh() {
		return woundThresh;
	}

	public int getDeathRating() {
		return deathRating;
	}

}

