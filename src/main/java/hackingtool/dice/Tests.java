package hackingtool.dice;

/**
 * Contains different types of tests a character
 * could be called on to make in Eclipse Phase
 * 
 * @author SamJ
 */
public class Tests {
	private String attOutcome;
	private String defOutcome;
	
	// ----- Constructor ----- \\
	
	public Tests() {
	}
	
	// ----- Getters/Setters ----- \\
	
	public String getAttOutcome() {
		return attOutcome;
	}

	public void setAttOutcome(String attOutcome) {
		this.attOutcome = attOutcome;
	}

	public String getDefOutcome() {
		return defOutcome;
	}

	public void setDefOutcome(String defOutcome) {
		this.defOutcome = defOutcome;
	}
	
	// ----- Methods ----- \\
	
	/**
	 * @param target	Roll target value
	 * @return			true if the roller succeeds
	 */
	public Boolean successTest(int target) {
		Dice dice = DiceFactory.get(Types.PERCENTILE);
		dice.roll();
		attOutcome = dice.checkRoll(target);
		return dice.isHit();
	}
	
    /**
     * @param attacker	Attackers target value
     * @param defender	Defender's target value
     * @return			true if the attacker wins, false if defender wins
     */
    public Boolean opposedTest(int attacker, int defender) {
    	Dice attDice = DiceFactory.get(Types.PERCENTILE);
    	Dice defDice = DiceFactory.get(Types.PERCENTILE);
    	
    	// Roll and check the attacker's dice
    	attDice.roll();
    	attOutcome = attDice.checkRoll(attacker);
    	
    	// Roll and check the defender's dice
    	defDice.roll();
    	defOutcome = defDice.checkRoll(defender);
    	
    	// Compare results
    	
    	    // If both succeed but the attacker crits
    	if (attDice.isHit()
    	&&  attDice.isCrit() 
    	&&  defDice.isHit() 
    	&& !defDice.isCrit()) {
    		return true;
    	}
    		//If both sides succeed but the defender crits
    	if (attDice.isHit()
    	&& !attDice.isCrit() 
    	&&  defDice.isHit() 
    	&&  defDice.isCrit()) {
			return true;
		}
    	    // If both succeed, higher roll wins
    	if (attDice.isHit() && defDice.isHit()) {
    		return attDice.getResults() > defDice.getResults();
    	}
    		// Attacker succeeds, defender fails
    	if (attDice.isHit() && !defDice.isHit() ) {
    		return true;
    	}
    		// Attacker fails, defender succeeds
    	if (!attDice.isHit() && defDice.isHit()) {
    		return false;
    	}
    	return false;
    }
	
}
