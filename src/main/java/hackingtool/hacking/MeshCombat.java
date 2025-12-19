package hackingtool.hacking;

import hackingtool.devices.Account;
import hackingtool.devices.Privileges;
import hackingtool.dice.Tests;

public class MeshCombat { // This should probably be observable for log keeping

	// Fields
	private MeshCombatant attacker;
	private MeshCombatant defender;
	private Boolean local;
	private Boolean opposed;
	private Tests   test;
	private int     damage;
	
	// Constructor
	public MeshCombat(MeshCombatant attacker, MeshCombatant defender, Boolean local) {
		this.attacker = attacker;
		this.defender = defender;
		this.local    = local;
		this.opposed  = false;
		this.test	  = new Tests();
	}
	
	// Getters/Setters
	public MeshCombatant getAttacker() {
		return attacker;
	}

	public void setAttacker(MeshCombatant attacker) {
		this.attacker = attacker;
	}

	public MeshCombatant getDefender() {
		return defender;
	}

	public void setDefender(MeshCombatant defender) {
		this.defender = defender;
	}

	public Boolean getLocal() {
		return local;
	}

	public void setLocal(Boolean local) {
		this.local = local;
	}
	
	public Tests getTest() {
		return this.test;
	}
	
	public Boolean isOpposed() {
		return opposed;
	}
	
	public int getDamage() {
		return damage;
	}
	
	public int getRoll() {
		return test.getRoll();
	}
	
	public int getAttRoll() {
		return test.getAttRoll();
	}
	
	public int getDefRoll() {
		return test.getDefRoll();
	}
	
	public String getOutcome() {
		return test.getOutcome();
	}
	
	public String getAttOutcome() {
		return test.getAttOutcome();
	}
	
	public String getDefOutcome() {
		return test.getDefOutcome();
	}
	
	public Boolean attCrit() {
		return test.attCrit();
	}
	
	// ----- Methods ----- \\
	public Boolean meshAttack(Account account) {
		Boolean success;
		// Determine success
		if (!local) { 	// if attack is remote
			success = remoteMeshAttack();
		} else {		// if attack is local
			success  = localMeshAttack(account);
		}
		// Assess damage
		if (success) {
			damage = defender.takeMeshDamage(attacker.meshAttack(), test.attCrit());
		}
		return success;
	}
	
	private Boolean remoteMeshAttack() {
		opposed = true;
		return test.opposedTest(attacker.getInfosec(), defender.isDefended() ? defender.getInfosec() : defender.getFirewall());
	}
	
	private Boolean localMeshAttack(Account account) {
		int modifier;
		if (account != null
		&& Privileges.ADMIN.equals(account.getPriv())) {
			modifier = 0;
		} else {
			modifier = -30;
		}
		// If system is actively defended opposed Infosec v Infosec
		if(defender.isDefended()) {
			opposed = true;
			return test.opposedTest(attacker.getInfosec() + modifier, defender.getInfosec());
		} else { // If undefended, Success test v Infosec
			opposed = false;
			return test.successTest(attacker.getInfosec());
		}
		
	}
	
}
