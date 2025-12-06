public class Dice {

    public final int[] crits = {00,11,22,33,44,55,66,77,88};
    public int diceRoll;
    public int target;

    public Dice(){  //constructor
        
    }

    public boolean isCrit(){         //returns true if diceRoll is in crits
        for(int i = 0; i < crits.length; i++){
            if (diceRoll == crits[i])
                return true;
        }
        return false;
    }
    
    public boolean isCritSuccess(){
        return isHit()
            && isCrit();
    }

    public boolean isCritFail(){      //true if not a hit and a crit or 99
        return !isHit()
           && ( isCrit()
            ||  diceRoll == 99);             //99 automaticaly fails any test
    }

    public boolean isHit(){           //true if the diceRoll is less than or equal to target
        return diceRoll <= target;
    }

    public boolean isDubSupSuccess(){ //true if diceRoll is a hit and above 65
        return  isHit()
             && diceRoll >= 66;
    }

    public boolean isSupSuccess(){    //true if diceRoll is a hit and above 32
        return  isHit()
             && diceRoll >= 33;
    }

    public boolean isDubSupFail(){    //true if not a hit and below 34
        return !isHit()
            && diceRoll <= 33;
    }

    public boolean isSupFail(){       //true if not a hit and below 67
        return !isHit()
            && diceRoll <= 66;
    }

    public String checkRoll(int target){ //create a no message setting
        this.target = target;

        if      (isCritSuccess()){  //Critical Success
            System.out.printf("%d/%d, Critical Success%n",diceRoll, target);
            return "CritSucc";
        }else if(isCritFail()){     //critical fail
            System.out.printf("%d/%d, Critical Failure%n",diceRoll, target);
            return "CritFail";
        }else if(isDubSupSuccess()){//double superior success
            System.out.printf("%d/%d, x2 Superior Success%n",diceRoll, target);
            return "DubSupSucc";
        }else if(isSupSuccess()){   //superior success
            System.out.printf("%d/%d, Superior Success%n",diceRoll, target);;
            return "SupSucc";
        }else if (isDubSupFail()){  //double superior failure
            System.out.printf("%d/%d, x2 Superior Failure%n",diceRoll, target);
            return "DupSupFail";
        }else if (isSupFail()){     //superior failure
            System.out.printf("%d/%d, Superior Failure%n",diceRoll, target);
            return "SupFail";
        }else{                      //regular success or failure
            System.out.printf("The roll is %d/%d.%nThis is a %s.%n", diceRoll, target, isHit() ? "success" : "failure");
            if(isHit()) return "Succ";
            return "Fail";
        }
    }

    public void roll(int dice){
        this.diceRoll = (int) (Math.random()*dice);
    } 

}
