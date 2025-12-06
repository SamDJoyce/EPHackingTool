public class Hacker {
    private int infosec;
    private int firewall;

    public Hacker(int firewall, int infosec){ // constructor
        this.firewall = firewall;
        this.infosec = infosec;
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

}

