package hackingtool.hacking;

import java.util.ArrayList;
import java.util.List;

import hackingtool.dao.HackingDAO;
import hackingtool.devices.Account;
import hackingtool.devices.Alerts;
import hackingtool.devices.Hackable;
import hackingtool.devices.IntruderStatus;
import hackingtool.devices.Privileges;
import hackingtool.devices.User;
import hackingtool.dice.Tests;
import hackingtool.logging.Observable;
import hackingtool.logging.Observer;
import hackingtool.services.HackingService;
import hackingtool.logging.Event;
// test
public class Hacking implements Observable{
	
	private static final int ACCOUNT_DUR = 25;
	private static final int 	BF_MOD		   = -30;
	private static final int    HIDDEN_MOD	   = 10;
	private static final int    MINDWARE_MOD   = -30;
	private static final int ACTIVE_ALERT_MOD  = -10;
	
	private static final String SUP_SUCC 	   = "Superior Success";
	private static final String DUB_SUP_SUCC   = "Double Superior Success";
	private static final String CRIT_SUCC 	   = "Critical Success";
	private static final String SUCCESS		   = "Success";
	private static final String CRIT_FAIL 	   = "Critical Failure";
	private static final String SUP_FAIL 	   = "Superior Failure";
	private static final String DUB_SUP_FAIL   = "Double Superior Failure";
	
	private static final String FAILED    	   = " Failed";
	private static final String SUCCEEDED 	   = " Succeeded";
	private static final String TARGET_ROLLED  = "Target rolled: ";
	private static final String HACKER_ROLLED  = "Hacker rolled: ";
	private static final String STATUS_AQUIRED = " Status aquired.";
	private static final String EXPOSED 	   = "Exposed! ";
	private static final String SPOTTED_BY 	   = " has been Spotted by ";
	private static final String ATTEMPTING_TO_IMPROVE = " is attempting to improve their intruder status.";
	private static final String STATUS_IMPROVED 	  = "Status improved to ";
	private static final String ATTEMPTING_TO_SUBVERT = " is attempting to subvert ";
	private static final String PRIVILEGES_AQUIRED 	  = " Privileges aquired.";
	private static final String ALERT_TRIGGERED    	  = " Alert triggered.";
	private static final String ATTEMPTING_BF_INTRUSION     = " is attempting a Brute Force Intrusion";
	private static final String ATTEMPTING_SUBTLE_INTRUSION = " is attempting a Subtle Intrusion";
	
	private static final HackingService hackServ = new HackingDAO();
	
	private final Tests test = new Tests();

	private Hackable target;
	private User     hacker;
	private Boolean  bruteForce;
	private Event    log;
	private List<Observer> observers;


	// ----- Constructor ----- \\
	public Hacking(Hackable target, User hacker, Boolean bruteForce) {
		this.target 	= target;
		this.hacker 	= hacker;
		this.bruteForce	= bruteForce;
		this.observers 	= new ArrayList<>();
		this.log 		= new Event();
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
	
	public Event getLog() {
		return log;
	}
	
	public void clearLog() {
		log.clear();
	}
	
	// ----- ^^^ getters/setters ^^^ ----- \\
	
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

			// Perform the opposed test
			success = test.opposedTest(hacker.getInfosec() + BF_MOD, target.getFirewall());
			// Log the event
			log.add(hacker.getName() + ATTEMPTING_BF_INTRUSION);
			log.add(HACKER_ROLLED + test.getAttRoll() + "/" + (hacker.getInfosec() + BF_MOD) + " - " + test.getAttOutcome());
			log.add(TARGET_ROLLED + test.getDefRoll() + "/" + target.getFirewall() + " - " + test.getDefOutcome());
			log.add(hacker.getName() + (success ? SUCCEEDED : FAILED) + " in creating an account.");
			
			// The attacker has won the opposed check
			if (success) {
				intruder = checkBFIntrusionSuccess(test);
				
			} else {
				// Failure
				// Spotted Status, Active Alert, Public account
				intruder = bfIntrusionFailure();
			}

			// Insert the account into the DB
			hackServ.createAccount(intruder);
			// Add the account to the target
			target.addAccount(intruder);
			// Update the target in the DB
			hackServ.updateNode(target);
			// Record the log in event log
			notifyObservers(log);
			return success;
			
		} else { // Subtle Intrusion
			success = test.opposedTest(hacker.getInfosec(), target.getFirewall());
			// Construct log message
			log.add(hacker.getName() + ATTEMPTING_SUBTLE_INTRUSION);
			log.add(HACKER_ROLLED + test.getAttRoll() + "/" + hacker.getInfosec() + " - " + test.getAttOutcome());
			log.add(TARGET_ROLLED + test.getDefRoll() + "/" + target.getFirewall() + " - " + test.getDefOutcome());
			log.add(hacker.getName() + (success ? SUCCEEDED : FAILED) + " in creating an account.");
			
			if (success) {
				intruder = checkSubtleIntrusionSuccess(test);
				// Create the new account
				hackServ.createAccount(intruder);
				// Register the account to the device
				target.addAccount(intruder);
				// Update the target in the database
				hackServ.updateNode(target);
			} else { //Failure
				// Fail to gain access
				// System goes on passive alert
				if (target.getAlert() == Alerts.NONE) {
					// Trigger a passive alert
					target.setAlert(Alerts.PASSIVE);
					// Log the alert
					log.add(Alerts.PASSIVE.toString() + ALERT_TRIGGERED);
					// Update the node in the DB
					hackServ.updateNode(target);
				}
			}
			notifyObservers(log);
			return success;
		}
	}

	// Upgrade Status
	public void upgradeStatus() {
		Account a = null;
		if (target.getAccount(hacker) != null) {
				a = target.getAccount(hacker);
		}
		int     modifier = applyModifiers(a);
		
		Boolean success = test.opposedTest(hacker.getInfosec() + modifier, target.getFirewall());
		String attOutcome = test.getAttOutcome();
		
		// Logging
		log.add(hacker.getName() + ATTEMPTING_TO_IMPROVE);
		log.add(HACKER_ROLLED + test.getAttRoll() + "/" + (hacker.getInfosec() + modifier) + " - " + attOutcome);
		log.add(TARGET_ROLLED + test.getDefRoll() + "/" + target.getFirewall() + " - " + test.getDefOutcome());
		log.add(hacker.getName() + (success ? " Succeeded" : " Failed"));
		
		if (success) {
			// Superior success - upgrade status two levels
			if (SUP_SUCC.equalsIgnoreCase(attOutcome)
			||  DUB_SUP_SUCC.equalsIgnoreCase(attOutcome)) {
				a.improveStatus();
				a.improveStatus();
			} else { // normal success - upgrade status one level
				a.improveStatus();
			}
			
			// Log the event
			log.add(STATUS_IMPROVED + a.getStatus().toString());
		} else { // Failure
			checkExposure(attOutcome, a);
		}
		// Update the account in the DB
		hackServ.updateAccount(a);
		// Update the target in the DB
		hackServ.updateNode(target);
		// Record the log to event log
		notifyObservers(log);
	}
	
	// System Subversion
	public Boolean subvertSystem() {
		Account a = target.getAccount(hacker);
		int     modifier = applyModifiers(a);
		Boolean success = test.opposedTest(hacker.getInfosec() + modifier, target.getFirewall());
		String  attOutcome = test.getAttOutcome();
		
		// Logging
		log.add(hacker.getName() + ATTEMPTING_TO_SUBVERT + target.getName());
		log.add(HACKER_ROLLED + test.getAttRoll() + "/" + (hacker.getInfosec() + modifier)  + " - " + attOutcome);
		log.add(TARGET_ROLLED + test.getDefRoll() + "/" + target.getFirewall()  + " - " + test.getDefOutcome());
		log.add(hacker.getName() + (success ? " Succeeded" : " Failed"));
		
		if (!success) { // Failure
			checkExposure(attOutcome,a);
		} // On a success the thing happens
		  // but there are no logical changes
		// Update the account in the DB
		hackServ.updateAccount(a);
		// Update the target in the DB
		hackServ.updateNode(target);
		// Record the log in log event
		notifyObservers(log);
		return success;
	}
	
	public Boolean meshAttack(Boolean local) {
		MeshCombat combat   = new MeshCombat(hacker, target.getOS(), local);
		Boolean    success  = combat.meshAttack(target.getAccount(hacker));
		Boolean    detected = test.opposedTest(hacker.getInfosec(), target.getFirewall());
		

		if (detected 
		&& target.getAlert() != null
		&& Alerts.NONE.equals(target.getAlert())) {
			target.setAlert(Alerts.PASSIVE);
		}
		
		// Log events
		log.add(hacker.getName() + " attacked " + target.getName());
		if(combat.isOpposed()) { // Opposed check
			log.add(combat.getAttRoll() + "/" + hacker.getInfosec() + " " + combat.getAttOutcome());
			log.add(combat.getDefRoll() + "/" + target.getInfosec() + " " + combat.getDefOutcome());
		} else { // Not opposed
			log.add(combat.getRoll() + "/" + hacker.getInfosec() + " " + combat.getOutcome());
		}
		if (success) {
			log.add(hacker.getName() + " inflicted " + combat.getDamage() + "DV on " + target.getName());
		}
		log.add(hacker.getName() + " was " + (detected ? "" : "not ") + "detected" );
		if (detected) {
			log.add(Alerts.PASSIVE + ALERT_TRIGGERED);
		}
		
		// Update the account in the DB
		hackServ.updateAccount(target.getAccount(hacker));
		// Update the target in the DB
		hackServ.updateNode(target);
		// Record the event with the event log
		notifyObservers(log);
		
		return success;
	}
	// ----- ^^^ Public Methods ^^^ ----- \\
	
	// ----- Helper Methods ----- \\
	
	private Account checkSubtleIntrusionSuccess(Tests hack) {
		Account 	   intruder;
		Alerts 		   alert;
		IntruderStatus status;
		Privileges     priv;
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
		// Create the account and insert it into the database
		intruder = new Account.Builder()
				  .setUser(hacker)
				  .setDeviceID(target.getID())
				  .setStatus(status)
				  .setPriv(priv)
				  .setDur(hacker.getDurability())
				  .build();
		
		target.setAlert(alert);
		
		// Log Alerts triggered
		log.add(alert.toString() + ALERT_TRIGGERED);
		// Log privileges gained
		log.add(priv.toString() + PRIVILEGES_AQUIRED);
		// Log Intruder Status
		log.add(status.toString() + STATUS_AQUIRED);;
		
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
		// Create the account
		intruder = new Account.Builder()
							  .setUser(hacker)
							  .setDeviceID(target.getID())
							  .setStatus(IntruderStatus.SPOTTED)
							  .setPriv(Privileges.PUBLIC)
							  .setDur(hacker.getDurability())
							  .build();
		// Update the target
		target.setAlert(Alerts.ACTIVE);
		// Log the event
		log.add(Alerts.ACTIVE.toString() + ALERT_TRIGGERED);
		log.add(hacker.getName() + SPOTTED_BY + target.getName());
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
		Account 	   intruder;
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
		// Create the account and insert it into the DB
		intruder = new Account.Builder()
							  .setUser(hacker)
							  .setDeviceID(target.getID())
							  .setStatus(status)
							  .setPriv(priv)
							  .setDur(hacker.getDurability())
							  .setDefended(true) // True as long as the player is actively hacking
							  .build() ;
		// Update the target object and DB
		target.setAlert(alert);
		
		// Log Alerts triggered
		log.add(alert.toString() + ALERT_TRIGGERED);
		// Log privileges gained
		log.add(priv.toString() + PRIVILEGES_AQUIRED);
		// Log Intruder Status
		log.add(status.toString() + STATUS_AQUIRED);
		
		return intruder;
	}
	// End of Intrusion Logic
	


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
		// -30 modifier if the device is mindware
		if (target.isMindware()) {
			modifier += MINDWARE_MOD;
		}
		// -10 modifier for each wound the user has
		// Is this already done in user?
		//modifier += hacker.getWounds() * -10;
		
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
			log.add(EXPOSED + Alerts.ACTIVE.toString() + ALERT_TRIGGERED
					+ " " + IntruderStatus.SPOTTED.toString() + STATUS_AQUIRED);
		}
		
		if(SUP_FAIL.equalsIgnoreCase(attOutcome)
		|| DUB_SUP_FAIL.equalsIgnoreCase(attOutcome)) {
		// Superior failure causes a passive alert	
			if (target.getAlert() == Alerts.NONE) {
				target.setAlert(Alerts.PASSIVE);
				log.add(EXPOSED + Alerts.PASSIVE.toString() + ALERT_TRIGGERED);
			}	
		}
	}
	
	// ----- ^^^ Helper Methods ^^^ ----- \\
	
	// Observer methods
	@Override
	public void addObserver(Observer observer) {
		if (observer != null) {
			observers.add(observer);
		}
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers(Event event) {
		
		for (Observer o : observers) {
			o.update(event);
		}
		log = new Event();
	}

}
