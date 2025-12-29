package hackingtool.devices;

public enum Alerts {
	
	NONE(0,"None"),
	PASSIVE(1,"Passive"),
	ACTIVE(2,"Active");
	
	private final int level;
	private final String description;
	
	Alerts(int level, String description){
		this.level = level;
		this.description = description;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public static Alerts fromLevel(int level) {
		for (Alerts a : Alerts.values()) {
			if (a.getLevel() == level) {
				return a;
			}
		}
		return NONE;
	}
	
	public static String toString(Alerts alert) {
		return alert.description;
	}
	
	public static Alerts fromString(String alert) {
		for (Alerts a : Alerts.values()) {
			if (a.description.equalsIgnoreCase(alert)) {
				return a;
			}
		}
		return NONE;
	}
}
