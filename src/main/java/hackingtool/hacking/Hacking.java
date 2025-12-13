package hackingtool.hacking;

import hackingtool.User;
import hackingtool.devices.Hackable;
import hackingtool.dice.Dice;
import hackingtool.dice.Tests;

public class Hacking {
	private static final int bfMod = -30;
	private static final String SUP_SUCC 	 = "Superior Success";
	private static final String DUB_SUP_SUCC = "Double Superior Success";
	private static final String CRIT_SUCC 	 = "Critical Success";
	
	
	private Dice	 dice;
	private Hackable target;
	private User     hacker;
	private Boolean  bruteForce;


	// ----- Constructor ----- \\
	public Hacking(Hackable target, User hacker) {
		this.target = target;
		this.hacker = hacker;
		dice = new Dice();
		bruteForce = false;
	}
	
	// ----- getters/setters ----- \\
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
	
	
	public Boolean getBruteForce() {
		return bruteForce;
	}

	public void setBruteForce(Boolean bruteForce) {
		this.bruteForce = bruteForce;
	}
	
	// ----- Methods ----- \\
	
	/**
	 * Gain access to a device or system with
	 * a hacking test
	 * 
	 * @return	true if the hacker successfully gains
	 * 			access to the target system
	 */
	public Boolean intrusion() {
		
		Tests hack = new Tests();
		Boolean success;
		Account intruder;
		
		if (bruteForce) { 	// Brute Force 
			
			success = hack.opposedTest(hacker.getInfosec() - bfMod, target.getFirewall());
			
			if (success) {
				
				if (CRIT_SUCC.equalsIgnoreCase(hack.getAttOutcome())) {
					// critical success: 
					// covert status
					// Passive alert triggered
					
					// Create the user's new account
					intruder = new Account.Builder()
										  .setUser(hacker)
										  .setStatus(IntruderStatus.COVERT)
										  .setDur(hacker.getDurability())
										  .build();
					// Register the new account
					target.addAccount(intruder);
					// Set the target to passive alert
					target.setAlert(Alerts.PASSIVE);
				} else {
					// Normal success:
					// spotted status, active alert
					
					// Create the account
					intruder = new Account.Builder()
										  .build();
				}
				if (DUB_SUP_SUCC.equalsIgnoreCase(hack.getAttOutcome())) {
					// Gain admin privileges
				} else if (SUP_SUCC.equalsIgnoreCase(hack.getAttOutcome())) {
					// Gain security privileges
				}else {
					// Not a superior success
					// gain user privileges
				}
			} else {
				// Failure
			}
			
		} else { 			// Subtle
			success = hack.opposedTest(hacker.getInfosec(), target.getFirewall());
		}
		
		return null;
	}
	

}
