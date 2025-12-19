package hackingtool.devices;

import hackingtool.dice.Dice;
import hackingtool.dice.DiceFactory;
import hackingtool.dice.Tests;
import hackingtool.dice.Types;
import hackingtool.hacking.MeshCombatant;

public class User implements MeshCombatant{
	private String name;
    private int infosec;
    private int firewall;
    private int woundThresh;
    private int durability;
    private int deathRating;
	private int wounds;
    private int damage;
	private int armor;

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
	
	public void setDeathRating(int deathRating) {
		this.deathRating = deathRating;
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

	public void setWoundThresh(int woundThresh) {
		this.woundThresh = woundThresh;
	}
	
	// Mesh Combatant methods
	@Override
	public int meshAttack() {
		int damage = calcMeshDamage(Types.D10, 2);
		return damage;
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
		return true;
	}
	@Override
	public int woundModifier() {
		return wounds * -10;
	}


}

