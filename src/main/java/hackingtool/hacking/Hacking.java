package hackingtool.hacking;

import hackingtool.devices.Account;
import hackingtool.devices.Hackable;
import hackingtool.devices.User;
import hackingtool.dice.Tests;

public class Hacking {
	private static final int 	BF_MOD		 = -30;
	private static final String SUP_SUCC 	 = "Superior Success";
	private static final String DUB_SUP_SUCC = "Double Superior Success";
	private static final String CRIT_SUCC 	 = "Critical Success";
	private static final String SUCCESS		 = "Success";
	private static final String CRIT_FAIL 	 = "Critical Failure";
	private static final String SUP_FAIL 	 = "Superior Failure";
	private static final String DUB_SUP_FAIL = "Double Superior Failure";
	
	private Hackable target;
	private User     hacker;
	private Boolean  bruteForce;


	// ----- Constructor ----- \\
	public Hacking(Hackable target, User hacker, Boolean bruteForce) {
		this.target = target;
		this.hacker = hacker;
		this.bruteForce = bruteForce;
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
	
	// Intrusion Logic
	/**
	 * Gain access to a device or system with
	 * a hacking test
	 * 
	 * @return	true if the hacker successfully gains
	 * 			access to the target system
	 */
	public Boolean intrusion() {
		
		Tests   hack = new Tests();
		Boolean success;
		Account intruder;
		
		if (bruteForce) { 	// Brute Force intrusion
			// Perform the opposed test
			success = hack.opposedTest(hacker.getInfosec() - BF_MOD, target.getFirewall());
			
			// The attacker has won the opposed check
			if (success) {
				intruder = checkBFIntrusionSuccess(hack);
				
			} else {
				// Failure
				// Spotted Status
				// Active Alert
				// Public account
				intruder = bfIntrusionFailure();
			}
			// Register the new account
			target.addAccount(intruder);
			return success;
			
		} else { 			// Subtle Intrusion
			success = hack.opposedTest(hacker.getInfosec(), target.getFirewall());
			
			if (success) {
				intruder = checkSubtleIntrusionSuccess(hack);
				
			} else { //Failure
				// Fail to gain access
				// System goes on passive alert
				if (target.getAlert() == Alerts.NONE) {
					target.setAlert(Alerts.PASSIVE);
				}
			}
			return success;
		}
	}

	private Account checkSubtleIntrusionSuccess(Tests hack) {
		Account intruder;
		String attOutcome = hack.getAttOutcome();
		// Check success to determine Intruder Status
		if (CRIT_SUCC.equalsIgnoreCase(attOutcome)) {
			// Hidden status
			intruder = new Account.Builder()
								  .setUser(hacker)
								  .setStatus(IntruderStatus.HIDDEN)
								  .build();
		} else { // Normal success
			// covert status
			intruder = new Account.Builder()
								  .setUser(hacker)
								  .setStatus(IntruderStatus.COVERT)
								  .setDur(hacker.getDurability())
								  .build();
		}
		// Check the degree of success to determine privileges
		if (DUB_SUP_SUCC.equalsIgnoreCase(attOutcome)) {
			// Admin privileges
			intruder.setPriv(Privileges.ADMIN);
		} else if (SUP_SUCC.equalsIgnoreCase(attOutcome)) {
			// Security privileges
			intruder.setPriv(Privileges.SECURITY);
		} else { // Normal success
			// User privileges
			intruder.setPriv(Privileges.USER);
		}
		// Check target's results to determine Alert Status
		String defOutcome = hack.getDefOutcome();
		if (SUCCESS.equalsIgnoreCase(defOutcome)
		||	SUP_SUCC.equalsIgnoreCase(defOutcome)
		||	DUB_SUP_SUCC.equalsIgnoreCase(defOutcome) ) {
			if (target.getAlert() == Alerts.NONE) {
				target.setAlert(Alerts.PASSIVE);
			}
		}
		return intruder;
	}

	/**
	 * Set up the repercussions of a failed brute force
	 * intrusion attempt.
	 * 
	 * @return	The intruder's account configured based
	 * 			on a failed intrusion.
	 */
	private Account bfIntrusionFailure() {
		Account intruder;
		intruder = new Account.Builder()
							  .setUser(hacker)
							  .setStatus(IntruderStatus.SPOTTED)
							  .setPriv(Privileges.PUBLIC)
							  .setDur(hacker.getDurability())
							  .build();
		target.setAlert(Alerts.ACTIVE);
		return intruder;
	}

	/**
	 * Evaluate a successful intrusion and set the
	 * account appropriately
	 * 
	 * @param hack	The test used for the intrusion
	 * @return		The intruder's account configured
	 * 				based on their hack test outcome
	 */
	private Account checkBFIntrusionSuccess(Tests hack) {
		Account intruder;
		// Check for criticals
		if (CRIT_SUCC.equalsIgnoreCase(hack.getAttOutcome())) {
			// critical success: 
			// covert status
			// Passive alert triggered
			
			// Create the new account
			intruder = new Account.Builder()
								  .setUser(hacker)
								  .setStatus(IntruderStatus.COVERT)
								  .setDur(hacker.getDurability())
								  .build();

			// Set the target to Passive alert
			target.setAlert(Alerts.PASSIVE);
		} else {
			// Normal success:
			// spotted status, active alert
			
			// Create the new account
			intruder = new Account.Builder()
								  .setUser(hacker)
								  .setStatus(IntruderStatus.SPOTTED)
								  .setDur(hacker.getDurability())
								  .build();
			// Set the target to Active alert
			target.setAlert(Alerts.ACTIVE);
		}
		// Check the degree of success to determine privileges
		if (DUB_SUP_SUCC.equalsIgnoreCase(hack.getAttOutcome())) {
			// Gain admin privileges
			intruder.setPriv(Privileges.ADMIN);
		} else if (SUP_SUCC.equalsIgnoreCase(hack.getAttOutcome())) {
			// Gain security privileges
			intruder.setPriv(Privileges.SECURITY);
		}else {
			// Not a superior success
			// gain user privileges
			intruder.setPriv(Privileges.USER);
		}
		return intruder;
	}
	// End of Intrusion Logic
	
	// Upgrade Status
	public void upgradeStatus() {
		Tests upgrade = new Tests();
		Boolean success = upgrade.opposedTest(hacker.getInfosec(), target.getFirewall());
		String attOutcome = upgrade.getAttOutcome();
		Account a = null;
			
		if (target.getAccount(hacker) != null) {
				a = target.getAccount(hacker);
		}
		
		if (success) {
			// Superior success - upgrade status two levels
			if (SUP_SUCC.equalsIgnoreCase(attOutcome)
			||  DUB_SUP_SUCC.equalsIgnoreCase(attOutcome)) {
				a.improveStatus();
				a.improveStatus();
			} else { // normal success - upgrade status one level
				a.improveStatus();
			}
		} else { // Failure
			checkExposure(attOutcome, a);
		}
	}
	
	// System Subversion
	public Boolean subvertSystem() {
		// TODO a hacking check with generic output
		return false;
	}
	
	// Countermeasures

	private void checkExposure(String attOutcome, Account a) {
		if (CRIT_FAIL.equalsIgnoreCase(attOutcome)) {
		// Critical failure causes active alert, spotted status
			target.setAlert(Alerts.ACTIVE);
			a.setStatus(IntruderStatus.SPOTTED);
			// Add the account back into the target
			target.updateAccount(a);
		}
		
		if(SUP_FAIL.equalsIgnoreCase(attOutcome)
		|| DUB_SUP_FAIL.equalsIgnoreCase(attOutcome)) {
		// Superior failure causes a passive alert	
			if (target.getAlert() == Alerts.NONE) {
				target.setAlert(Alerts.PASSIVE);
			}	
		}
	}

}
