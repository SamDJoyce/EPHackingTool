package hackingtool.hacking;

public enum Alerts {
	
	NONE(0),
	PASSIVE(1),
	ACTIVE(2);
	
	private final int level;
	
	Alerts(int level){
		this.level = level;
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
}
