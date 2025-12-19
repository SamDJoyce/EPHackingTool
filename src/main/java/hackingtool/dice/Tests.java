package hackingtool.dice;

/**
 * Contains different types of tests a character
 * could be called on to make in Eclipse Phase
 * 
 * @author SamJ
 */
public class Tests {
	
	private int		attRoll;
	private int		defRoll;
	private Dice    dice;
	private Dice 	attDice;
	private Dice 	defDice;
	
	// ----- Constructor ----- \\
	
	public Tests() {
		dice    = DiceFactory.get(Types.PERCENTILE);
		attDice = DiceFactory.get(Types.PERCENTILE);
		defDice = DiceFactory.get(Types.PERCENTILE);
	}
	
	// ----- Getters/Setters ----- \\
	
	public int getRoll() {
		return dice.getResults();
	}

	public int getAttRoll() {
		return attDice.getResults();
	}

	public int getDefRoll() {
		return defDice.getResults();
	}

	public String getOutcome() {
		return dice.getEval();
	}
	
	public String getAttOutcome() {
		return attDice.getEval();
	}
	
	public String getDefOutcome() {
		return defDice.getEval();
	}
	
	public Boolean attCrit() {
		return attDice.isCrit();
	}
	
	public Boolean defCrit() {
		return defDice.isCrit();
	}
	
	public Dice getDice() {
		return dice;
	}
	
	public Dice getAttDice() {
		return attDice;
	}
	
	public Dice getDefDice() {
		return defDice;
	}
	
	// ----- Methods ----- \\
	
	/**
	 * @param target	Roll target value
	 * @return			true if the roller succeeds
	 */
	public Boolean successTest(int target) {
		//Dice dice = DiceFactory.get(Types.PERCENTILE);
		dice.roll();
		dice.checkRoll(target);
		return dice.isHit();
	}
	
    /**
     * @param attacker	Attackers target value
     * @param defender	Defender's target value
     * @return			true if the attacker wins, false if defender wins
     */
    public Boolean opposedTest(int attacker, int defender) {

    	
    	// Roll and check the attacker's dice
    	attDice.roll();
    	attDice.checkRoll(attacker);
    	
    	// Roll and check the defender's dice
    	defDice.roll();
    	defDice.checkRoll(defender);
    	
    	// Compare results
    	
    	    // If both succeed but the attacker crits
    	if (attDice.isCritSuccess() 
    	&&  defDice.isHit() 
    	&& !defDice.isCrit()) {
    		return true;
    	}
    		//If both sides succeed but the defender crits
    	if (attDice.isHit()
    	&& !attDice.isCrit() 
    	&&  defDice.isCritSuccess()) {
			return false;
		}
    	
    	if (attDice.isCritSuccess()
    	&&  defDice.isCritSuccess()) {
    		return attRoll > defRoll;
    	}
    	    // If both succeed, higher roll wins
    	if (attDice.isHit() && defDice.isHit()) {
    		return attRoll > defRoll;
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
