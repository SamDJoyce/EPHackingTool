package hackingtool.hacking;

import hackingtool.dice.Types;

public interface MeshCombatant {
	int meshAttack();
	int calcMeshDamage(Types type, int num);
	int takeMeshDamage(int damage, Boolean isCrit);
	int calcMeshWounds(int damage);
	
	Boolean detectMeshAttack();
	Boolean isDefended();
	int getFirewall();
	int getInfosec();
	int woundModifier();
}