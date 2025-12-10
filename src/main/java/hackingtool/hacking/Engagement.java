package hackingtool.hacking;

import hackingtool.User;
import hackingtool.devices.Hackable;
import hackingtool.dice.Dice;

public class Engagement {
	private static final int bfMod = -30;
	
	private Dice	 dice;
	private Hackable target;
	private User     hacker;
	
	// Constructor
	public Engagement(Hackable target, User hacker) {
		this.target = target;
		this.hacker = hacker;
		dice = new Dice();
	}
	
	// getters/setters
	public void setTarget(Hackable target) {
		this.target = target;
	}
	
	public Hackable getTarget() {
		return this.target;
	}
	
	public User getHacker() {
		return hacker;
	}

	public void setHacker(User hacker) {
		this.hacker = hacker;
	}
	
	// Methods
	
	public Boolean bruteForce() {
		return hackingTest(hacker.getInfosec() + bfMod,
						   target.getFirewall());
	}
	
	public Boolean hackingTest() {
		return dice.opposedTest(hacker.getInfosec(),
								target.getFirewall());
	}
	public Boolean hackingTest(int attacker, int defender) {
		return dice.opposedTest(attacker, defender);
	}
}
