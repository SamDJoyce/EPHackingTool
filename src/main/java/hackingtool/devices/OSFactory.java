package hackingtool.devices;

public class OSFactory {
	private static final int MOTE_DUR   = 20;
	private static final int HOST_DUR   = 40;
	private static final int SERVER_DUR = 60;
	private static final int MOTE_FW   = 30;
	private static final int HOST_FW   = 50;
	private static final int SERVER_FW = 70;
	public OSFactory() {
	}

	public static OS get(String type) {
		if ("Mote".equalsIgnoreCase(type)) {
			return new OS(type,MOTE_DUR, MOTE_FW);
		} else if ("Host".equalsIgnoreCase(type)) {
			return new OS(type,HOST_DUR, HOST_FW);
		} else if ("Server".equalsIgnoreCase(type)) {
			return new OS(type,SERVER_DUR, SERVER_FW);
		}
		else return new OS(type,MOTE_DUR, MOTE_FW);
	}
	
	public static OS get(String type, int firewall) {
		if ("Mote".equalsIgnoreCase(type)) {
			return new OS(type,MOTE_DUR, firewall);
		} else if ("Host".equalsIgnoreCase(type)) {
			return new OS(type,HOST_DUR, firewall);
		} else if ("Server".equalsIgnoreCase(type)) {
			return new OS(type,SERVER_DUR, firewall);
		}
		else return new OS(type,MOTE_DUR, firewall);
	}
	
	public static OS getRandom(String type) {
		if ("Mote".equalsIgnoreCase(type)) {
			return new OS(type,MOTE_DUR, randomMote());
		} else if ("Host".equalsIgnoreCase(type)) {
			return new OS(type,HOST_DUR, randomHost());
		} else if ("Server".equalsIgnoreCase(type)) {
			return new OS(type,SERVER_DUR, randomServer());
		}
		else return new OS(type,MOTE_DUR, randomMote());
	}
	
	// Helper Methods
	
	private static int randomMote() {
		int range = 10;
		int rand = (int) (Math.random() * range)-(range/2);
		return MOTE_FW + rand;
	}
	
	private static int randomHost() {
		int range = 16;
		int rand = (int) (Math.random() * range)-(range/2);
		return HOST_FW + rand;
	}
	
	private static int randomServer() {
		int range = 20;
		int rand = (int) (Math.random() * range)-(range/2);
		return SERVER_FW + rand;
	}

}
