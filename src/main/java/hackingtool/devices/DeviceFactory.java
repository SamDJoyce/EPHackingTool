package hackingtool.devices;

public class DeviceFactory {

	private static final String SERVER = "Server";
	private static final String HOST   = "Host";
	private static final String MOTE   = "Mote";
	private static final int MOTE_OS   = 20;
	private static final int HOST_OS   = 40;
	private static final int SERVER_OS = 60;
	private static final int MOTE_FW   = 30;
	private static final int HOST_FW   = 50;
	private static final int SERVER_FW = 70;

	private static int id = 0;
	
	public DeviceFactory() {
	}
	
	public static Device create(String type) {
		if (MOTE.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(new OS(MOTE_OS))
							 .setFirewall(MOTE_FW)
							 .setInfosec(MOTE_FW)
							 .build();
		} else if (HOST.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(new OS(HOST_OS))
							 .setFirewall(HOST_FW)
							 .setInfosec(HOST_FW)
							 .build();
		} else if (SERVER.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(new OS(SERVER_OS))
							 .setFirewall(SERVER_FW)
							 .setInfosec(SERVER_FW)
							 .build();
		} else {
			return null;
		}
	}
	
	public static Device create(String type, int firewall) {
		if (MOTE.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(new OS(MOTE_OS))
							 .setFirewall(firewall)
							 .setInfosec(firewall)
							 .build();
		} else if (HOST.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(new OS(HOST_OS))
							 .setFirewall(firewall)
							 .setInfosec(firewall)
							 .build();
		} else if (SERVER.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(new OS(SERVER_OS))
							 .setFirewall(firewall)
							 .setInfosec(firewall)
							 .build();
		} else {
			return null;
		}
	}
	
	public static Device createRandom(String type) {
		
		if (MOTE.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(new OS(MOTE_OS))
							 .setFirewall(randomMote())
							 .setInfosec(randomMote())
							 .build();
		} else if (HOST.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(new OS(HOST_OS))
							 .setFirewall(randomHost())
							 .setInfosec(randomHost())
							 .build();
		} else if (SERVER.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(new OS(SERVER_OS))
							 .setFirewall(randomServer())
							 .setInfosec(randomServer())
							 .build();
		} else {
			return null;
		}
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
