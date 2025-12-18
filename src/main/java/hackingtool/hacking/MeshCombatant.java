package hackingtool.hacking;

import hackingtool.dice.Types;

public interface MeshCombatant {
	int meshAttack();
	int calcMeshDamage(Types type, int num);
	void takeMeshDamage(int damage);
	int calcMeshWounds(int damage);
	Boolean detectMeshAttack();
	Boolean isDefended();
	int getFirewall();
	int getInfosec();
}