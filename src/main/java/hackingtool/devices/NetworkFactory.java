package hackingtool.devices;

import java.util.ArrayList;
import java.util.List;

import hackingtool.dao.HackingDAO;
import hackingtool.services.HackingService;

public class NetworkFactory {
	private static final HackingService hackServ = new HackingDAO();
	
	public NetworkFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static void random(String type, String owner) {
		if ("PAN".equalsIgnoreCase(type)) {
			createRandomPAN(owner);
		} else if ("retail".equalsIgnoreCase(type)) {
			// create retail network
		} else if ("corp".equalsIgnoreCase(type)) {
			// create corp office network
			// could also be a factory
		}
	}
	
	public static void create(String type) {
		
	}

	
	// ----- Helper Methods ----- \\
	
	private static void createRandomPAN(String owner) {
		// Generate point of entry - Host mesh inserts or ecto
			// 50/50 chance of inserts or ecto
		Long rand = Math.round(Math.random());
		Hackable hub = null;
		if (rand == 1) {
			hub = hackServ.createNode(DeviceFactory.get("host", owner + "'s Mesh-Inserts"));
		} else {
			hub = hackServ.createNode(DeviceFactory.get("host", owner + "'s Ecto"));
		}
		
		// Generate several items/implants
		rand = Math.round(Math.random() * 5);
		Hackable device = null;
		for (int i = 0; i < rand; i++) {
			// Create a device
			device = hackServ.createNode(DeviceFactory.getRandom("Mote", owner + "'s " + randomDeviceName()));
			// Connect the device to the network hub (inserts/ecto)
			hackServ.createLink(device.getID(), hub.getID());
		}
		
		// Determine if has cyberbrain and generate if true	
		// TODO
	}
	
	// private static void createRandomPAN(String owner, int numDevices)
	// TODO
	
	private static String randomDeviceName() {
		return "TBD";
	}
}
