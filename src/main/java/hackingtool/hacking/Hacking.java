package hackingtool.hacking;

import java.util.ArrayList;
import java.util.List;

import hackingtool.devices.Account;
import hackingtool.devices.Alerts;
import hackingtool.devices.Hackable;
import hackingtool.devices.IntruderStatus;
import hackingtool.devices.Privileges;
import hackingtool.devices.User;
import hackingtool.dice.Tests;
// test
public class Hacking implements Observable{
	private static final int 	BF_MOD		  = -30;
	private static final int    HIDDEN_MOD	  = 10;
	private static final int ACTIVE_ALERT_MOD = -10;
	private static final int    MINDWARE_MOD  = -30;
	private static final String SUP_SUCC 	  = "Superior Success";
	private static final String DUB_SUP_SUCC  = "Double Superior Success";
	private static final String CRIT_SUCC 	  = "Critical Success";
	private static final String SUCCESS		  = "Success";
	private static final String CRIT_FAIL 	  = "Critical Failure";
	private static final String SUP_FAIL 	  = "Superior Failure";
	private static final String DUB_SUP_FAIL  = "Double Superior Failure";
	
	private final Tests test = new Tests();

	private Hackable target;
	private User     hacker;
	private Boolean  bruteForce;
	private String   log;
	private List<Observer> observers;


	// ----- Constructor ----- \\
	public Hacking(Hackable target, User hacker, Boolean bruteForce) {
		this.target = target;
		this.hacker = hacker;
		this.bruteForce = bruteForce;
		this.observers = new ArrayList<>();
		this.log = "";
	}
	
	// ----- getters/setters ----- \\
	
	public Tests getTest() {
		return test;
	}
	
	public void setTarget(Hackable target) {
		this.target = target;
	}
	
	public Hackable getTarget() {
		return this.target;
	}
	
	public String getTargetName() {
		return target.getName();
	}
	
	public User getHacker() {
		return hacker;
	}
	
	public String getHackerName() {
		return hacker.getName();
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
	
	public String getLog() {
		return log;
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
		//Tests   hack = new Tests();
		Boolean success;
		Account intruder;
		
		if (bruteForce) { 	// Brute Force intrusion
			// Log the event initialisation
			log += hacker.getName() + " is attempting a Brute Force Intrusion\n";
			// Perform the opposed test
			success = test.opposedTest(hacker.getInfosec() + BF_MOD, target.getFirewall());
			
			log += "Hacker rolled: " + test.getAttRoll() + "/" + (hacker.getInfosec() + BF_MOD) + "\n" ;
			log += test.getAttOutcome() + "\n";
			log += "Target rolled: " + test.getDefRoll() + "/" + target.getFirewall() + "\n";
			log += test.getDefOutcome() + "\n";
			
			// The attacker has won the opposed check
			if (success) {
				intruder = checkBFIntrusionSuccess(test);
				
			} else {
				// Failure
				// Spotted Status
				// Active Alert
				// Public account
				intruder = bfIntrusionFailure();
			}
			// Register the new account
			target.addAccount(intruder);
			notifyObservers(log);
			return success;
			
		} else { 			// Subtle Intrusion
			log += hacker.getName() + " is attempting a Subtle Intrusion\n";
			success = test.opposedTest(hacker.getInfosec(), target.getFirewall());
			// Construct log message
			log += "Hacker rolled: " + test.getAttRoll() + "/" + (hacker.getInfosec()) + "\n" ;
			log += test.getAttOutcome() + "\n";
			log += "Target rolled: " + test.getDefRoll() + "/" + target.getFirewall() + "\n";
			log += test.getDefOutcome() + "\n";
			
			if (success) {
				intruder = checkSubtleIntrusionSuccess(test);
				target.addAccount(intruder);
			} else { //Failure
				// Fail to gain access
				// System goes on passive alert
				if (target.getAlert() == Alerts.NONE) {
					target.setAlert(Alerts.PASSIVE);
					log += Alerts.PASSIVE.toString() + " Alert triggered";
				}
			}
			notifyObservers(log);
			return success;
		}
	}

	private Account checkSubtleIntrusionSuccess(Tests hack) {
		Account intruder;
		Alerts 		   alert;
		IntruderStatus status;
		Privileges     priv;;
		String attOutcome = hack.getAttOutcome();
		// Check success to determine Intruder Status
		if (CRIT_SUCC.equalsIgnoreCase(attOutcome)) {
			// Hidden status
			status = IntruderStatus.HIDDEN;
		} else { // Normal success
			// covert status
			status = IntruderStatus.COVERT;
		}
		// Check the degree of success to determine privileges
		if (DUB_SUP_SUCC.equalsIgnoreCase(attOutcome)) {
			// Admin privileges
			priv = Privileges.ADMIN;
		} else if (SUP_SUCC.equalsIgnoreCase(attOutcome)) {
			// Security privileges
			priv = Privileges.SECURITY;
		} else { // Normal success
			// User privileges
			priv = Privileges.USER;
		}
		// Check target's results to determine Alert Status
		String defOutcome = hack.getDefOutcome();
		if (SUCCESS.equalsIgnoreCase(defOutcome)
		||	SUP_SUCC.equalsIgnoreCase(defOutcome)
		||	DUB_SUP_SUCC.equalsIgnoreCase(defOutcome) ) {
			if (target.getAlert() == Alerts.NONE) {
				alert = Alerts.PASSIVE;
			} else {
				alert = target.getAlert();
			}
		} else {
			alert = target.getAlert();
		}
		
		intruder = new Account.Builder()
				  .setUser(hacker)
				  .setStatus(status)
				  .setPriv(priv)
				  .setDur(hacker.getDurability())
				  .build();
		
		target.setAlert(alert);
		
		// Log Alerts triggered
		log += alert.toString() + " Alert triggered.";
		// Log privileges gained
		log += priv.toString() + " Privileges aquired.";
		// Log Intruder Status
		log += status.toString() + " Status aquired.\n";
		
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
		log += "Active Alert triggered!\n";
		log += hacker.getName() +" has been Spotted by " + target.getName();
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
		Alerts 		   alert;
		IntruderStatus status;
		Privileges     priv;
		// Check for criticals
		if (CRIT_SUCC.equalsIgnoreCase(hack.getAttOutcome())) {
			// critical success: 
			// covert status
			// Passive alert triggered
			alert = Alerts.PASSIVE;
			status = IntruderStatus.COVERT;
		} else {
			// Normal success:
			// spotted status, active alert
			alert = Alerts.ACTIVE;
			status = IntruderStatus.SPOTTED;
		}
		// Check the degree of success to determine privileges
		if (DUB_SUP_SUCC.equalsIgnoreCase(hack.getAttOutcome())) {
			// Gain admin privileges
			priv = Privileges.ADMIN;
		} else if (SUP_SUCC.equalsIgnoreCase(hack.getAttOutcome())) {
			// Gain security privileges
			priv = Privileges.SECURITY;
		}else { // Not a superior success
			// gain user privileges
			priv = Privileges.USER;
		}
		
		intruder = new Account.Builder()
							  .setUser(hacker)
							  .setStatus(status)
							  .setPriv(priv)
							  .setDur(hacker.getDurability())
							  .build();
		target.setAlert(alert);
		
		// Log Alerts triggered
		log += alert.toString() + " Alert triggered.";
		// Log privileges gained
		log += priv.toString() + " Privileges aquired.";
		// Log Intruder Status
		log += status.toString() + " Status aquired.\n";
		
		return intruder;
	}
	// End of Intrusion Logic
	
	// Upgrade Status
	public void upgradeStatus() {
		
		String attOutcome = test.getAttOutcome();
		Account a = null;
		if (target.getAccount(hacker) != null) {
				a = target.getAccount(hacker);
		}
		int     modifier = applyModifiers(a);
		
		Boolean success = test.opposedTest(hacker.getInfosec() + modifier, target.getFirewall());
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
		Account a = target.getAccount(hacker);
		int     modifier = applyModifiers(a);
		Boolean success = test.opposedTest(hacker.getInfosec() + modifier, target.getFirewall());
		String  attOutcome = test.getAttOutcome();
		
		if (!success) { // Failure
			checkExposure(attOutcome,a);
		} // On a success the thing happens
		  // but there are no logical changes
		
		return success;
	}

	private int applyModifiers(Account a) {
		int modifier = 0;
		// -30 modifier if the hacker is brute forcing
		if (bruteForce) {
			modifier += BF_MOD;
		}
		// +10 modifier to subvert if user is hidden
		if (IntruderStatus.HIDDEN.equals(a.getStatus())) {
			modifier += HIDDEN_MOD;
		}
		// -10 modifier to subvert if the system is on active alert
		if (Alerts.ACTIVE.equals(target.getAlert())) {
			modifier += ACTIVE_ALERT_MOD;
		}
		if (target.isMindware()) {
			modifier += MINDWARE_MOD;
		}
		return modifier;
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

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
		
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(String event) {
		for (Observer o : observers) {
			o.update(event);
		}
		log = "";
	}

}
