package hackingtool.devices;

import hackingtool.dice.Dice;
import hackingtool.dice.DiceFactory;
import hackingtool.dice.Tests;
import hackingtool.dice.Types;
import hackingtool.hacking.MeshCombat;
import hackingtool.hacking.MeshCombatant;

public class OS implements MeshCombatant{

	private int id;
	private String type;
	private int woundThresh;
    private int durability;
    private int deathRating;
	private int wounds;
    private int damage;
	private int armor;
	private int firewall;
    private int infosec;
    private Boolean defended;

	public OS(String type, int durability, int firewall) {
		this.type = type;
		this.durability = durability;
		this.woundThresh = durability/5;
		this.deathRating = durability*2;
		this.firewall = firewall;
		this.infosec = firewall;
		this.damage = 0;
		this.wounds = 0;
		this.armor = 0;
		this.defended = false;
	}
	
	public OS( 	int id, 
				String type, 
				int durability, 
				int armor, 
				int firewall, 
				int infosec, 
				Boolean defended) {
		this.id = id;
		this.type = type;
		this.durability = durability;
		this.woundThresh = durability/5;
		this.deathRating = durability*2;
		this.damage = 0;
		this.wounds = 0;
		this.armor = armor;
		this.firewall = firewall;
		this.infosec = infosec;
		this.defended = defended;
		
	}
	
	public OS( 	int id, 
				String type, 
				int durability,
				int wounds,
				int damage,
				int armor, 
				int firewall, 
				int infosec, 
				Boolean defended) {
		this.id = id;
		this.type = type;
		this.durability = durability;
		this.woundThresh = durability/5;
		this.deathRating = durability*2;
		this.damage = damage;
		this.wounds = wounds;
		this.armor = armor;
		this.firewall = firewall;
		this.infosec = infosec;
		this.defended = defended;
	}
	public OS(
			String type, 
			int durability,
			int armor, 
			int firewall, 
			int infosec, 
			Boolean defended) {
	this.id = 0;
	this.type = type;
	this.durability = durability;
	this.woundThresh = durability/5;
	this.deathRating = durability*2;
	this.damage = 0;
	this.wounds = 0;
	this.armor = armor;
	this.firewall = firewall;
	this.infosec = infosec;
	this.defended = defended;
}
    

	public int getID() {
		return id;
	}
	
	public void setID(int id) {
		this.id = id;
	}
	
    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
    public int getWoundThresh() {
		return woundThresh;
	}

	public void setWoundThresh(int woundThresh) {
		this.woundThresh = woundThresh;
	}

	public int getDurability() {
		return durability;
	}

	public void setDurability(int durability) {
		this.durability = durability;
	}

	public int getDeathRating() {
		return deathRating;
	}

	public void setDeathRating(int deathRating) {
		this.deathRating = deathRating;
	}

    public int getFirewall() {
		return firewall;
	}

	public void setFirewall(int firewall) {
		this.firewall = firewall;
	}

	public int getInfosec() {
		return infosec;
	}

	public void setInfosec(int infosec) {
		this.infosec = infosec;
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

    public Boolean getDefended() {
		return defended;
	}

	public void setDefended(Boolean defended) {
		this.defended = defended;
	}

	// Mesh Combatant Methods
	@Override
	public int meshAttack() {
		return calcMeshDamage(Types.D10, 2);
	}

	@Override
	public int calcMeshDamage(Types type, int num) {
		int total = 0;
		Dice dice = DiceFactory.get(type);
		for (int i = 0; i < num; i++) {
			total += dice.roll();
		}
		return total;
	}

	@Override
	public int takeMeshDamage(int damage, Boolean isCrit) {
		int modDamage = (damage * (isCrit ? 2 : 1)) - armor ;
		this.damage += modDamage;
		this.wounds += calcMeshWounds(modDamage);
		if (this.damage >= durability) {
			// Unconscious
		}
		if (this.damage >= deathRating) {
			// Dead
		}
		return modDamage;
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
		return test.successTest(this.infosec);
	}

	@Override
	public Boolean isDefended() {
		return defended;
	}
	
	@Override
	public int woundModifier() {
		return wounds * -10;
	}
    public String getStability() {
    	String stability = "Stable";
		 if (damage >= deathRating * 0.75) {
		    return "Severely damaged";
		 }
		 if (damage >= durability
    	  || wounds >= 5) {
    		return "Offline";
    	}
		if (damage >= durability * 0.75
    	  || wounds == 4) {
			return  "Critical";
    	}   	
    	if ((damage >= durability/2)
    	   || wounds >= 2 ) {
    		return "Unstable";
    	}

    	return stability;
    }
}
