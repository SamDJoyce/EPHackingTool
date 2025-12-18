package hackingtool.hacking;

import hackingtool.dice.Tests;

public class MeshCombat {

	// Fields
	MeshCombatant attacker;
	MeshCombatant defender;
	Boolean local;
	
	// Constructor
	public MeshCombat(MeshCombatant attacker, MeshCombatant defender, Boolean local) {
		this.attacker = attacker;
		this.defender = defender;
		this.local    = local;
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
	
	// Methods
	public Boolean meshAttack() {
		// TODO
		Tests test = new Tests();
		// if attack is remote
		if (!local) {
		// - opposed v Firewall, or infosec if defended	
			if(defender.isDefended()) {
				//TODO
			}
		} else { // if attack is local
		// - If system is actively defended opposed Infosec v Infosec
		// - If undefended, Success test v Infosec	
			//TODO
		}
		return null;
	}

}
