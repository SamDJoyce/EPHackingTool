package hackingtool.devices;


import java.util.ArrayList;
import java.util.List;

import hackingtool.dao.HackingDAO;
import hackingtool.services.HackingService;

public class NetworkFactory {
	private static final String PAN = "PAN";
	private static final int DEFAULT_NUM_DEVICES = 5;
	private static final HackingService hackServ = new HackingDAO();
	
	public NetworkFactory() {
		// TODO Auto-generated constructor stub
	}
	
	public static void random(String type, String owner) {
		if (PAN.equalsIgnoreCase(type)) {
			getRandomPAN(owner);
		} else if ("retail".equalsIgnoreCase(type)) {
			// create retail network
		} else if ("corp".equalsIgnoreCase(type)) {
			// create corp office network
			// could also be a factory
		}
	}
	
	public static void random(String type, String owner, int numDevices) {
		if (PAN.equalsIgnoreCase(type)) {
			getRandomPAN(owner, numDevices);
		} else if ("retail".equalsIgnoreCase(type)) {
			// create retail network
		} else if ("corp".equalsIgnoreCase(type)) {
			// create corp office network
			// could also be a factory
		}
	}
	
	public static void random(String type, String owner, int numDevices, String hubDevice) {
		if (PAN.equalsIgnoreCase(type)) {
			getRandomPAN(owner, numDevices, hubDevice);
		} else if ("retail".equalsIgnoreCase(type)) {
			// create retail network
		} else if ("corp".equalsIgnoreCase(type)) {
			// create corp office network
			// could also be a factory
		}
	}

	
	// ----- Helper Methods ----- \\
	
	private static void getRandomPAN(String owner) {
		// Generate point of entry - Host mesh inserts or ecto
			// 50/50 chance of inserts or ecto
		Long rand = Math.round(Math.random());
		Hackable hub    = null;
		Hackable device = null;
		List<String> used = new ArrayList<>();
		String name = null;
		
		if (rand == 1) {
			hub = hackServ.createNode(DeviceFactory.getRandom("host", owner + "'s Mesh-Inserts"));
		} else {
			hub = hackServ.createNode(DeviceFactory.getRandom("host", owner + "'s Ecto"));
		}
		
		// Generate several items/implants
		rand = Math.round(Math.random() * DEFAULT_NUM_DEVICES);
		
		for (int i = 0; i < rand; i++) {
			// Pick a name that hasn't already been used
			do {
				name = randomPANDeviceName();
			} while (used.contains(name));
			// Record that name as used
			used.add(name);
			// Create a device
			device = hackServ.createNode(DeviceFactory.getRandomHidden("Mote", owner + "'s " + name));
			// Connect the device to the network hub (inserts/ecto)
			hackServ.createLink(device.getID(), hub.getID());
		}
		
		// Determine if has cyberbrain and generate if true	
		// TODO
	}
	
	private static void getRandomPAN(String owner, int numDevices) {
		// Generate point of entry - Host mesh inserts or ecto
			// 50/50 chance of inserts or ecto
		Long rand = Math.round(Math.random());
		Hackable hub = null;
		List<String> used = new ArrayList<>();
		String name = null;
		
		if (rand == 1) {
			hub = hackServ.createNode(DeviceFactory.getRandom("host", owner + "'s Mesh-Inserts"));
		} else {
			hub = hackServ.createNode(DeviceFactory.getRandom("host", owner + "'s Ecto"));
		}
		
		// Generate several items/implants
		Hackable device = null;
		for (int i = 0; i < numDevices; i++) {
			// Pick a name that hasn't already been used
			do {
				name = randomPANDeviceName();
			} while (used.contains(name));
			// Record that name as used
			used.add(name);
			// Create a device
			device = hackServ.createNode(DeviceFactory.getRandomHidden("Mote", owner + "'s " + name));
			// Connect the device to the network hub (inserts/ecto)
			hackServ.createLink(device.getID(), hub.getID());
		}
		
		// Determine if has cyberbrain and generate if true	
		// TODO
	}
	
	private static void getRandomPAN(String owner, int numDevices, String hubDevice) {
		Hackable hub = null;
		List<String> used = new ArrayList<>();
		String name = null;
		
		if ("inserts".equalsIgnoreCase(hubDevice)) { // Mesh Inserts
			hub = hackServ.createNode(DeviceFactory.getRandom("host", owner + "'s Mesh-Inserts"));
		} else { // Ecto
			hub = hackServ.createNode(DeviceFactory.getRandom("host", owner + "'s Ecto"));
		}
		
		// Generate several items/implants
		Hackable device = null;
		for (int i = 0; i < numDevices; i++) {
			// Pick a name that hasn't already been used
			do {
				name = randomPANDeviceName();
			} while (used.contains(name));
			// Record that name as used
			used.add(name);
			// Create a device
			device = hackServ.createNode(DeviceFactory.getRandomHidden("Mote", owner + "'s " + name));
			// Connect the device to the network hub (inserts/ecto)
			hackServ.createLink(device.getID(), hub.getID());
		}
		
		// Determine if has cyberbrain and generate if true	
		// TODO
	}
	
	private static String randomPANDeviceName() {
		List<String> names = new ArrayList<>(List.of(
				"Smart Clothing","Holographic Projector","Specs","Utilitool", "Breather",
				"Access Jacks", "Health Monitor", "VR Rig", "Neo-Vape", "Portable Storage","Secure Data Module",
				"Skillware","Nanotats","Cyberarm","Cyberleg","Cyberheart","Cyberlungs","Cybereye","Cleaner Swarm",
				"Audio-implant", "Headphones","Drug pump"));
		// Pick a random device from the list
		int rand = (int) Math.round(Math.random() * (names.size()-1));
		return names.get(rand);
	}
}
