public class Hacking {

    public Dice dice = new Dice();

    private static int testInfosec = 70;
    private static int testFirewall = 50;


    
    public static void main (String[] args) throws Exception{
        Hacker hacker = new Hacker(testFirewall,testInfosec);
        TargetSystem targetSys = new TargetSystem("Test", testFirewall, testInfosec);

        targetSys.bruteForce();
        //dice.roll(100);
        //dice.checkRoll(50);
    }

}
