public class TargetSystem {
    public Dice dice = new Dice();

    private final int publicAcc = 0;
    private final int userAcc = 1;
    private final int securityAcc = 2;
    private final int adminAcc = 3;

    private final int hidden = 0;
    private final int covert = 1;
    private final int spotted = 2;

    private final int noAlert = 0;
    private final int passiveAlert = 1;
    private final int activeAlert = 2;

    private String systemName;
    private int firewall;
    private int infosec;
    private int accountLevel;
    private int intruderStatus;
    private int alertLevel;


    public TargetSystem(String systemName, int firewall, int infosec){
        this.systemName = systemName;
        this.firewall = firewall;
        this.infosec = infosec;
        accountLevel = publicAcc;
        intruderStatus = covert;
        alertLevel = noAlert;
    }

    public void setName(String name){
        this.systemName = name;
    }
    public String getname(){
        return systemName;
    }
    public void setFirewall(int firewall){
        this.firewall = firewall;
    }
    public int getFirewall(){
        return firewall;
    }
    public void setInfosec(int infosec){
        this.infosec = infosec;
    }
    public int getInfosec(){
        return infosec;
    }
    public void setAccount(int accountLevel){
        this.accountLevel = accountLevel;
    }
    public int getAccount(){
        return accountLevel;
    }
    public void setStatus(int intruderStatus){
        this.intruderStatus = intruderStatus;
    }
    public int getStatus(){
        return intruderStatus;
    }
    public void setAlert(int alertLevel){
        this.alertLevel = alertLevel;
    }
    public int getAlert(){
        return alertLevel;
    }

    public void bruteForce(){
        // hacking test v firewall
        dice.roll(100);
        dice.checkRoll(firewall);
        // begin with covert status
        // failure              - no account, spotted, active alert
        // success              - user account, spotted, active alert
        // superior successes   - +1 level of account, spotted, active alert
        // critical success     - user account, covert, passive alert;
    }     

    public void subtleIntrusion(){
        // subtle intrusion procedure
    }

    public void upgradeIntruderStatus(){
        // hacking test v firewall
        // complex action
        // success  - status improves by 1 level
        // spr succ - status improved by 2 levels
        // failure  - detection?
    }

}