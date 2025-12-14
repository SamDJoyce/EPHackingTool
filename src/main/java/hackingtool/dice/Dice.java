package hackingtool.dice;

import java.util.List;

public class Dice {

    private static final String FAIL 		 = "Fail";
	private static final String SUCCESS 	 = "Success";
	private static final String SUP_FAIL 	 = "Superior Failure";
	private static final String DUB_SUP_FAIL = "Double Superior Failure";
	private static final String SUP_SUCC 	 = "Superior Success";
	private static final String DUB_SUP_SUCC = "Double Superior Success";
	private static final String CRIT_FAIL 	 = "Critical Failure";
	private static final String CRIT_SUCC 	 = "Critical Success";
	
	private static final List<Integer> crits = List.of(00,11,22,33,44,55,66,77,88);
	
	private int 	sides;
	private int 	target;	
    private int 	results;
    private String	eval;
  
	// ----- Constructors ----- \\
	public Dice(){  
 
    }
	
	public Dice (int sides) {
		this.sides = sides;
	}
	
	// ----- Getters/Setters ----- \\
	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public int getResults() {
		return results;
	}

	public void setResults(int results) {
		this.results = results;
	}

	public String getEval() {
		return eval;
	}

	public void setEval(String eval) {
		this.eval = eval;
	}
	
	public int getSides() {
		return sides;
	}

	public void setSides(int sides) {
		this.sides = sides;
	}

	// ----- Roll evaluation -----\\
	
    /**
     * @return true if results is in crits (a double number)
     */
    public boolean isCrit(){         //
        return crits.contains(results);
    }
    
    /**
     * @param roll	A value between 0 and 99
     * @return		true if results is in crits (a double number)
     */
    public boolean isCrit(int roll){         //returns true if results is in crits
        return crits.contains(roll);
    }
    
    public boolean isCritSuccess(){
        return isHit()
            && isCrit();
    }
    
    public boolean isCritSuccess(int roll, int target){
        return isHit(roll, target)
            && isCrit();
    }

    /**
     * @return true if not a hit and a crit or 99
     */
    public boolean isCritFail(){
        return !isHit()
           && ( isCrit()
            ||  results == 99);      //99 Automatically fails any test
    }

    /**
     * @return true if the results is less than or equal to target
     */
    public boolean isHit(){         
        return results <= target;
    }
    
    /**
     * @param roll		A value between 0 and 99
     * @param target	The value to roll equal or less than
     * @return 			true if the roll is less than or equal to target
     */
    public boolean isHit(int roll, int target) { //
    	return roll <= target;
    }

    /**
     * @return true if results is a hit and above 65
     */
    public boolean isDubSupSuccess(){
        return  isHit()
             && results >= 66;
    }

    /**
     * @return true if results is a hit and above 32
     */
    public boolean isSupSuccess(){ 
        return  isHit()
             && results >= 33;
    }

    /**
     * @return true if not a hit and below 34
     */
    public boolean isDubSupFail(){ 
        return !isHit()
            && results <= 33;
    }

    /**
     * @return true if not a hit and below 67
     */
    public boolean isSupFail(){  
        return !isHit()
            && results <= 66;
    }

    /**
     * Evaluate the results of a percentile dice roll
     * 
     * @param target	The value the player aims to roll equal or less than
     * @return			A string describing the outcome
     */
    public String checkRoll(int target){
        this.target = target;

        if      (isCritSuccess()){  //Critical Success
            System.out.printf("%d/%d, Critical Success%n",results, target);
            eval = CRIT_SUCC;
            return eval;
        }else if(isCritFail()){     //critical fail
            System.out.printf("%d/%d, Critical Failure%n",results, target);
            eval = CRIT_FAIL;
            return eval;
        }else if(isDubSupSuccess()){//double superior success
            System.out.printf("%d/%d, x2 Superior Success%n",results, target);
            eval = DUB_SUP_SUCC;
            return eval;
        }else if(isSupSuccess()){   //superior success
            System.out.printf("%d/%d, Superior Success%n",results, target);;
            eval = SUP_SUCC;
            return eval;
        }else if (isDubSupFail()){  //double superior failure
            System.out.printf("%d/%d, x2 Superior Failure%n",results, target);
            eval = DUB_SUP_FAIL;
            return eval;
        }else if (isSupFail()){     //superior failure
            System.out.printf("%d/%d, Superior Failure%n",results, target);
            eval = SUP_FAIL;
            return eval;
        }else{                      //regular success or failure
            System.out.printf("The roll is %d/%d.%nThis is a %s.%n", results, target, isHit() ? "success" : "failure");
            if(isHit()) { 
            	eval = SUCCESS;
            	return SUCCESS;
            }
            eval = FAIL;
            return eval;
        }
    }

    /**
     * Checks an arbitrary roll and target
     * 
     * @param roll		The value rolled
     * @param target	The value the player aims to roll equal or less than
     * @return			A String describing the outcome
     */
    public String checkRoll(int roll, int target){
        this.results = roll;
        return checkRoll(target);
    }
   
    /**
     * @param dice	An enum of dice types & their number of sides
     * @return		The value produced by the dice roll
     */
    public int roll(Types dice){
    	this.sides = dice.get();
        this.results = (int) (Math.random()*sides);
        return this.results;
    } 
    
    /**
     * @param sides	An integer value representing the die's number of sides
     * @return		The value produced by the dice roll
     */
    public int roll (int sides) {
    	this.sides = sides;
    	this.results = (int) (Math.random()*sides);
        return this.results;
    }
    
    /**
     * @return	The value produced by the dice roll
     */
    public int roll () {
        this.results = (int) (Math.random()*sides);
        return this.results;
    }
}
