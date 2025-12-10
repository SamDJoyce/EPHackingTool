package hackingtool.dice;

import java.util.List;

public class Dice {

    private static final String FAIL 		 = "Fail";
	private static final String SUCCESS 	 = "Success";
	private static final String SUP_FAIL 	 = "Superior Failure";
	private static final String DUP_SUP_FAIL = "Double Superior Failure";
	private static final String SUP_SUCC 	 = "Superior Success";
	private static final String DUB_SUP_SUCC = "Double Superior Success";
	private static final String CRIT_FAIL 	 = "Critical Failure";
	private static final String CRIT_SUCC 	 = "Critical Success";
	
	private final List<Integer> crits = List.of(00,11,22,33,44,55,66,77,88);
	
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
	
    public boolean isCrit(){         //returns true if results is in crits
        return crits.contains(results);
    }
    
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

    public boolean isCritFail(){      //true if not a hit and a crit or 99
        return !isHit()
           && ( isCrit()
            ||  results == 99);      //99 Automatically fails any test
    }

    public boolean isHit(){           //true if the results is less than or equal to target
        return results <= target;
    }
    
    public boolean isHit(int roll, int target) { //true if the roll is less than or equal to target
    	return roll <= target;
    }

    public boolean isDubSupSuccess(){ //true if results is a hit and above 65
        return  isHit()
             && results >= 66;
    }

    public boolean isSupSuccess(){    //true if results is a hit and above 32
        return  isHit()
             && results >= 33;
    }

    public boolean isDubSupFail(){    //true if not a hit and below 34
        return !isHit()
            && results <= 33;
    }

    public boolean isSupFail(){       //true if not a hit and below 67
        return !isHit()
            && results <= 66;
    }

    /**
     * @param target	The value the player aims to roll equal or less than
     * @return			A string describing the outcome
     */
    public String checkRoll(int target){
        this.target = target;

        if      (isCritSuccess()){  //Critical Success
            System.out.printf("%d/%d, Critical Success%n",results, target);
            eval = CRIT_SUCC;
            return CRIT_SUCC;
        }else if(isCritFail()){     //critical fail
            System.out.printf("%d/%d, Critical Failure%n",results, target);
            eval = CRIT_FAIL;
            return CRIT_FAIL;
        }else if(isDubSupSuccess()){//double superior success
            System.out.printf("%d/%d, x2 Superior Success%n",results, target);
            eval = DUB_SUP_SUCC;
            return DUB_SUP_SUCC;
        }else if(isSupSuccess()){   //superior success
            System.out.printf("%d/%d, Superior Success%n",results, target);;
            eval = SUP_SUCC;
            return SUP_SUCC;
        }else if (isDubSupFail()){  //double superior failure
            System.out.printf("%d/%d, x2 Superior Failure%n",results, target);
            eval = DUP_SUP_FAIL;
            return DUP_SUP_FAIL;
        }else if (isSupFail()){     //superior failure
            System.out.printf("%d/%d, Superior Failure%n",results, target);
            eval = SUP_FAIL;
            return SUP_FAIL;
        }else{                      //regular success or failure
            System.out.printf("The roll is %d/%d.%nThis is a %s.%n", results, target, isHit() ? "success" : "failure");
            if(isHit()) { 
            	eval = SUCCESS;
            	return SUCCESS;
            }
            eval = FAIL;
            return FAIL;
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
   

    public int roll(int dice){
    	this.sides = dice;
        this.results = (int) (Math.random()*sides);
        return this.results;
    } 
    
    public int roll () {
        this.results = (int) (Math.random()*sides);
        return this.results;
    }
}
