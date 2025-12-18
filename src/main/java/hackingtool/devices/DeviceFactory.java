package hackingtool.devices;

public class DeviceFactory {

	private static final String SERVER = "Server";
	private static final String HOST   = "Host";
	private static final String MOTE   = "Mote";
	private static int   id = 0;
	
	public DeviceFactory() {
	}
	
	public static Device create(String type) {
		if (MOTE.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(OSFactory.get(type))
							 .build();
		} else if (HOST.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(OSFactory.get(type))
							 .build();
		} else if (SERVER.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(OSFactory.get(type))
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
							 .setOS(OSFactory.get(type, firewall))
							 .build();
		} else if (HOST.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(OSFactory.get(type, firewall))
							 .build();
		} else if (SERVER.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(OSFactory.get(type, firewall))
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
							 .setOS(OSFactory.getRandom(type))
							 .build();
		} else if (HOST.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(OSFactory.getRandom(type))
							 .build();
		} else if (SERVER.equalsIgnoreCase(type)) {
			return new Device.Builder()
							 .setSystemName(type + "-" + ++id)
							 .setMindware(false)
							 .setOS(OSFactory.getRandom(type))
							 .build();
		} else {
			return null;
		}
	}
	

}
