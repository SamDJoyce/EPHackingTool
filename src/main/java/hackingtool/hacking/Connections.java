package hackingtool.hacking;

import java.util.ArrayList;
import java.util.List;

import hackingtool.devices.Hackable;

public class Connections {

	private List<Hackable> devices;
	private Hackable myDevice;
	
	public Connections(Hackable myDevice) {
		this.myDevice = myDevice;
		devices = new ArrayList<>();
	}

}
